package service.dataManager;

import java.util.List;

import exception.UserNotFoundException;
import model.dto.UserProfileDTO;

/**
 * Contrato para persistencia de dados em formato chave/valor, onde cada
 * {@link DataDTO} e identificado pelo seu {@code id}. Implementacoes definem
 * o meio de armazenamento (ex.: {@link JsonDataManager} grava arquivos JSON).
 */
public interface DataManager {

    /**
     * Cria um novo registro a partir do DTO informado.
     *
     * @param data dado a ser persistido.
     */
    public void createData(DataDTO<?> data);

    /**
     * Cria um novo registro montando o {@link DataDTO} internamente a partir do
     * {@code id} e do corpo informados, evitando que o chamador precise
     * instanciar o DTO manualmente.
     *
     * @param <T>  tipo do corpo (um {@code Record}).
     * @param id   identificador do registro (nome do arquivo).
     * @param body corpo do dado a ser persistido.
     */
    public <T extends Record> void createData(String id, T body);

    /**
     * Cria um novo registro montando o {@link DataDTO} internamente, usando o
     * nome de arquivo no formato {@code <table>%<id>}.
     *
     * @param <T>   tipo do corpo (um {@code Record}).
     * @param table nome da tabela, usado como prefixo do nome do arquivo.
     * @param id    identificador do registro dentro da tabela.
     * @param body  corpo do dado a ser persistido.
     */
    public <T extends Record> void createData(String table, String id, T body);

    /**
     * Remove o registro correspondente ao DTO informado.
     *
     * @param data dado cujo {@code id} identifica o registro a ser removido.
     */
    public void deleteData(DataDTO<?> data);

    /**
     * Le o registro identificado pelo {@code id} e devolve seu corpo como uma
     * lista do tipo informado.
     *
     * @param <T>  tipo dos itens (um {@code Record}).
     * @param id   identificador do registro.
     * @param type classe dos itens, usada para desserializar o corpo.
     * @return a lista de itens, ou {@code null} se nao existir.
     */
    public <T extends Record> List<T> readData(String id, Class<T> type);

    /**
     * Le o registro cujo nome de arquivo segue o formato {@code <table>%<id>} e
     * devolve seu corpo como uma lista do tipo informado.
     *
     * @param <T>   tipo dos itens (um {@code Record}).
     * @param table nome da tabela, usado como prefixo do nome do arquivo.
     * @param id    identificador do registro dentro da tabela.
     * @param type  classe dos itens, usada para desserializar o corpo.
     * @return a lista de itens, ou {@code null} se nao existir.
     */
    public <T extends Record> List<T> readData(String table, String id, Class<T> type);

    /**
     * Adiciona um novo conteudo a um registro existente, sem substituir o que
     * ja foi gravado. O registro alvo e identificado pelo {@code id} do proprio
     * DTO.
     *
     * @param appendData dado a ser concatenado ao registro.;
     */
    public void appendData(DataDTO<?> appendData);

    /**
     * Procura um usuário pelo identificador (nome do arquivo JSON).
     *
     * @param id identificador do usuário.
     * @return o UserProfileDTO encontrado.
     * @throws UserNotFoundException caso o usuário não exista.
     */
    public UserProfileDTO findUser(String id) throws UserNotFoundException;

    /**
     * Procura um usuário pelo identificador dentro da tabela informada,
     * lendo o arquivo cujo nome segue o formato {@code <table>%<id>}.
     *
     * @param table nome da tabela, usado como prefixo do nome do arquivo.
     * @param id    identificador do usuário dentro da tabela.
     * @return o UserProfileDTO encontrado.
     * @throws UserNotFoundException caso o usuário não exista.
     */
    public UserProfileDTO findUser(String table, String id) throws UserNotFoundException;
}
