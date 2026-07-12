package exception;

public class PasswordMismatchException extends Exception {

    public PasswordMismatchException() {
        super("Senha incorreta.");
    }

    public PasswordMismatchException(String password, String confirmation) {
        super("As senhas não coincidem.");
    }
}