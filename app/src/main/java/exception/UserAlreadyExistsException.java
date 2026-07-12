package exception;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String email) {
        super("O email \"" + email + "\" já está cadastrado.");
    }
}