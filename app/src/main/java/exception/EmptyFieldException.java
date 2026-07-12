package exception;

public class EmptyFieldException extends Exception {

    public EmptyFieldException(String field) {
        super("O campo \"" + field + "\" deve ser preenchido.");
    }
}