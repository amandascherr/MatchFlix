package service.dataManager;

/**
 * Objeto de transferencia usado pelo {@link DataManager}, associando um
 * identificador a um corpo de dados.
 *
 * @param <Body> tipo do corpo; deve ser um {@code Record} para permitir a
 *               serializacao/desserializacao em JSON.
 * @param id     identificador do dado (usado como nome do arquivo na
 *               implementacao {@link JsonDataManager}).
 * @param body   corpo do dado a ser persistido.
 */
public record DataDTO<Body extends Record>(
    String id,
    Body body
) {
}
