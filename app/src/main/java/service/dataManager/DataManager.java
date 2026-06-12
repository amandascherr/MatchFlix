package service.dataManager;

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
     * Remove o registro correspondente ao DTO informado.
     *
     * @param data dado cujo {@code id} identifica o registro a ser removido.
     */
    public void deleteData(DataDTO<?> data);

    /**
     * Le o registro identificado pelo {@code id}.
     *
     * @param id identificador do registro.
     * @return o {@link DataDTO} encontrado, ou {@code null} se nao existir.
     */
    public DataDTO<?> readData(String id);

    /**
     * Adiciona um novo conteudo a um registro existente, sem substituir o que
     * ja foi gravado.
     *
     * @param id         identificador do registro.
     * @param appendData dado a ser concatenado ao registro.
     */
    public void appendData(String id, DataDTO<?> appendData);
}
