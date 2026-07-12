package exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String username) {
        super("Usuário \"" + username + "\" não encontrado.");
    }
}
