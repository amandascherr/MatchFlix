package service.dataManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import exception.UserNotFoundException;
import model.dto.UserProfileDTO;

/**
 * Salva cada {@link DataDTO} como um arquivo JSON na pasta de resources.
 * O campo {@code id} vira o nome do arquivo ({@code <id>.json}) e o {@code body}
 * e sempre gravado como uma lista (mesmo com um unico item). O nome da classe
 * do body tambem e salvo para validar concatenacoes do mesmo tipo.
 */
public class JsonDataManager implements DataManager {

    private static final Path BASE_DIR = Path.of("src", "main", "resources", "data");

    private final ObjectMapper mapper = new ObjectMapper();

    public JsonDataManager(){
        mapper.registerModule(new JavaTimeModule());
    }

    /**
     * Cria (ou sobrescreve) o arquivo {@code <id>.json} guardando o corpo do DTO
     * como uma lista de um unico item.
     *
     * @param data dado a ser salvo; o {@code id} define o nome do arquivo e o
     *             {@code body} o conteudo gravado.
     */
    @Override
    public void createData(DataDTO<?> data) {
        write(data.id(), data.body().getClass().getName(), List.of(data.body()));
    }

    /**
     * Sobrecarga que instancia o {@link DataDTO} internamente a partir do
     * {@code id} e do {@code body} informados e delega para
     * {@link #createData(DataDTO)}. Usa a tabela padrao {@code "default"}.
     *
     * @param <T>  tipo do corpo (um {@code Record}).
     * @param id   identificador do registro.
     * @param body corpo do dado a ser persistido.
     */
    @Override
    public <T extends Record> void createData(String id, T body) {
        createData("default", id, body);
    }

    /**
     * Sobrecarga que monta o nome do arquivo no formato
     * {@code <table>%<id>}, instancia o {@link DataDTO} internamente e delega
     * para {@link #createData(DataDTO)}.
     *
     * @param <T>   tipo do corpo (um {@code Record}).
     * @param table nome da tabela, usado como prefixo do nome do arquivo.
     * @param id    identificador do registro dentro da tabela.
     * @param body  corpo do dado a ser persistido.
     */
    @Override
    public <T extends Record> void createData(String table, String id, T body) {
        String fileName = table + "%" + id;
        createData(new DataDTO<>(fileName, body));
    }

    /**
     * Remove o arquivo JSON correspondente ao {@code id} do DTO.
     *
     * @param data dado cujo {@code id} indica o arquivo a ser apagado.
     */
    @Override
    public void deleteData(DataDTO<?> data) {
        fileFor(data.id()).delete();
    }

    /**
     * Le o arquivo {@code <id>.json} e devolve seu corpo como uma lista do tipo
     * informado.
     *
     * @param <T>  tipo dos itens (um {@code Record}).
     * @param id   identificador do dado (nome do arquivo sem extensao).
     * @param type classe dos itens, usada para desserializar o corpo.
     * @return a lista de itens lida, ou {@code null} se o arquivo nao existir ou
     *         ocorrer falha na leitura.
     */
    @Override
    public <T extends Record> List<T> readData(String id, Class<T> type) {
        return readData("default", id, type);
    }

    /**
     * Sobrecarga que le o arquivo cujo nome segue o formato
     * {@code <table>%<id>} e devolve seu corpo como uma lista do tipo informado.
     *
     * @param <T>   tipo dos itens (um {@code Record}).
     * @param table nome da tabela, usado como prefixo do nome do arquivo.
     * @param id    identificador do dado dentro da tabela.
     * @param type  classe dos itens, usada para desserializar o corpo.
     * @return a lista de itens lida, ou {@code null} se o arquivo nao existir ou
     *         ocorrer falha na leitura.
     */
    @Override
    public <T extends Record> List<T> readData(String table, String id, Class<T> type) {
        String fileName = table + "%" + id;
        try {
            Map<?, ?> file = mapper.readValue(fileFor(fileName), Map.class);
            var listType = mapper.getTypeFactory().constructCollectionType(List.class, type);
            return mapper.convertValue(file.get("body"), listType);
        } catch (IOException e) {
            System.out.println("[ERROR] Falha ao ler o dado com id: " + fileName);
            return null;
        }
    }

    /**
     * Concatena um novo corpo a lista ja gravada no arquivo, usando o {@code id}
     * do proprio DTO para localizar o registro. Se o arquivo ainda nao existir,
     * cria com um unico item. A operacao so ocorre se o tipo do corpo recebido
     * for igual ao ja gravado.
     *
     * @param appendData dado cujo {@code body} sera adicionado a lista.
     */
    @Override
    public void appendData(DataDTO<?> appendData) {
        String id = appendData.id();
        File file = fileFor(id);
        String type = appendData.body().getClass().getName();
        List<Object> body = new ArrayList<>();
        try {
            if (file.exists()) {
                Map<?, ?> content = mapper.readValue(file, Map.class);

                String existingType = (String) content.get("type");
                if (!type.equals(existingType)) {
                    System.out.println("[ERROR] Tipo incompativel para o id: " + id);
                    return;
                }

                body.addAll((List<?>) content.get("body"));
            }
            body.add(appendData.body());
            write(id, type, body);
        } catch (IOException e) {
            System.out.println("[ERROR] Falha ao adicionar o dado ao id: " + id);
        }
    }

    /**
     * Grava o conteudo no arquivo {@code <id>.json} no formato
     * {@code {"type": ..., "body": [...]}}, criando o diretorio base se preciso.
     *
     * @param id   identificador do dado (nome do arquivo sem extensao).
     * @param type nome da classe dos itens, usado depois para desserializar.
     * @param body lista de itens a ser gravada.
     */
    private void write(String id, String type, List<?> body) {
        try {
            Files.createDirectories(BASE_DIR);
            Map<String, Object> content = Map.of(
                "type", type,
                "body", body
            );
            mapper.writeValue(fileFor(id), content);
        } catch (IOException e) {
            System.out.println("[ERROR] Falha ao gravar o dado com id: " + id);
        }
    }

    /**
     * Resolve o {@link File} correspondente a um {@code id} dentro do diretorio base.
     *
     * @param id identificador do dado (nome do arquivo sem extensao).
     * @return o arquivo {@code <BASE_DIR>/<id>.json}.
     */
    private File fileFor(String id) {
        return BASE_DIR.resolve(formatId(id) + ".json").toFile();
    }

    /**
     * Formata um {@code id} para que seja valido como nome de arquivo,
     * substituindo caracteres especiais invalidos por {@code '_'}. Sao
     * considerados validos letras, digitos, {@code '-'}, {@code '.'} e
     * {@code '%'} (usado como separador no formato {@code [tipo]%[id].json},
     * aceito por todos os sistemas operacionais); qualquer outro caractere
     * (incluindo separadores de diretorio como {@code '/'} e {@code '\'}) e
     * substituido.
     *
     * @param id identificador a ser formatado.
     * @return o {@code id} seguro para uso como nome de arquivo.
     */
    private String formatId(String id) {
        return id.replaceAll("[^a-zA-Z0-9-.%]", "_");
    }

    /**
     * Procura um usuário pelo identificador (nome do arquivo JSON).
     *
     * @param id identificador do usuário.
     * @return o UserProfileDTO encontrado.
     * @throws UserNotFoundException caso o usuário não exista.
     */
    public UserProfileDTO findUser(String id) throws UserNotFoundException {
        return findUser("default", id);
    }

    /**
     * Procura um usuário pelo identificador dentro da tabela informada,
     * lendo o arquivo cujo nome segue o formato {@code <table>%<id>}.
     *
     * @param table nome da tabela, usado como prefixo do nome do arquivo.
     * @param id    identificador do usuário dentro da tabela.
     * @return o UserProfileDTO encontrado.
     * @throws UserNotFoundException caso o usuário não exista.
     */
    @Override
    public UserProfileDTO findUser(String table, String id) throws UserNotFoundException {

        List<UserProfileDTO> users = readData(table, id, UserProfileDTO.class);

        if (users == null || users.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        return users.get(0);
    }
}
