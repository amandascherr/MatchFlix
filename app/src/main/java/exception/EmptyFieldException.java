package exception;

/**
 * Lançada quando um campo obrigatório de formulário não é preenchido.
 */
public class EmptyFieldException extends Exception {

    /**
     * Cria a exceção para o campo obrigatório informado.
     *
     * @param field nome do campo obrigatório que ficou vazio.
     */
    public EmptyFieldException(String field) {
        super("O campo \"" + field + "\" deve ser preenchido.");
    }
}