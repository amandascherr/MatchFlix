package exception;

/**
 * Lançada quando a senha informada é inválida: incorreta no login ou divergente
 * da confirmação no cadastro.
 */
public class PasswordMismatchException extends Exception {

    /**
     * Indica que a senha informada está incorreta.
     */
    public PasswordMismatchException() {
        super("Senha incorreta.");
    }

    /**
     * Indica que a senha e a confirmação não coincidem.
     *
     * @param password     senha digitada.
     * @param confirmation confirmação digitada.
     */
    public PasswordMismatchException(String password, String confirmation) {
        super("As senhas não coincidem.");
    }
}