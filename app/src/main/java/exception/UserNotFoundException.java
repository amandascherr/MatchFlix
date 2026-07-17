package exception;

/**
 * Lançada quando um usuário procurado (por email ou nome) não é encontrado.
 */
public class UserNotFoundException extends Exception {
    /**
     * Cria a exceção para o usuário não encontrado.
     *
     * @param username identificador do usuário não encontrado.
     */
    public UserNotFoundException(String username) {
        super("Usuário \"" + username + "\" não encontrado.");
    }
}
