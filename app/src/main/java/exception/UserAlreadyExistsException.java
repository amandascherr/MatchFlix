package exception;

/**
 * Lançada ao tentar cadastrar um usuário com um email que já existe na base.
 */
public class UserAlreadyExistsException extends Exception {

    /**
     * @param email email já cadastrado que gerou o conflito.
     */
    public UserAlreadyExistsException(String email) {
        super("O email \"" + email + "\" já está cadastrado.");
    }
}