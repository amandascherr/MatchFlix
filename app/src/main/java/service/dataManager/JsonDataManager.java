package service.dataManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Salva cada {@link DataDTO} como um arquivo JSON na pasta de resources.
 * O campo {@code id} vira o nome do arquivo ({@code <id>.json}) e o {@code body}
 * e sempre gravado como uma lista (mesmo com um unico item). O nome da classe
 * do body tambem e salvo para validar concatenacoes do mesmo tipo.
 */
public class JsonDataManager implements DataManager {

    private static final Path BASE_DIR = Path.of("src", "main", "resources", "data");

    private final ObjectMapper mapper = new ObjectMapper();

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
        try {
            Map<?, ?> file = mapper.readValue(fileFor(id), Map.class);
            var listType = mapper.getTypeFactory().constructCollectionType(List.class, type);
            return mapper.convertValue(file.get("body"), listType);
        } catch (IOException e) {
            System.out.println("[ERROR] Falha ao ler o dado com id: " + id);
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
        return BASE_DIR.resolve(id + ".json").toFile();
    }
}
