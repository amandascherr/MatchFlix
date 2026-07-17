package exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Testa as mensagens das excecoes de validacao usadas nas regras de cadastro,
 * login e busca de usuarios.
 */
public class ExceptionTest {

  /**
   * Verifica que a mensagem de {@link EmptyFieldException} nomeia o campo vazio.
   */
  @Test
  public void emptyFieldMessageNamesTheField() {
    EmptyFieldException exception = new EmptyFieldException("Email");

    assertEquals("O campo \"Email\" deve ser preenchido.", exception.getMessage());
  }

  /**
   * Verifica a mensagem do construtor sem argumentos de
   * {@link PasswordMismatchException} (senha incorreta).
   */
  @Test
  public void wrongPasswordMessage() {
    PasswordMismatchException exception = new PasswordMismatchException();

    assertEquals("Senha incorreta.", exception.getMessage());
  }

  /**
   * Verifica a mensagem do construtor de {@link PasswordMismatchException} para
   * senha e confirmacao divergentes.
   */
  @Test
  public void passwordConfirmationMismatchMessage() {
    PasswordMismatchException exception = new PasswordMismatchException("abc", "xyz");

    assertEquals("As senhas não coincidem.", exception.getMessage());
  }

  /**
   * Verifica que a mensagem de {@link UserAlreadyExistsException} nomeia o email
   * em conflito.
   */
  @Test
  public void userAlreadyExistsMessageNamesTheEmail() {
    UserAlreadyExistsException exception = new UserAlreadyExistsException("a@b.com");

    assertEquals("O email \"a@b.com\" já está cadastrado.", exception.getMessage());
  }

  /**
   * Verifica que a mensagem de {@link UserNotFoundException} nomeia o usuario
   * procurado.
   */
  @Test
  public void userNotFoundMessageNamesTheUser() {
    UserNotFoundException exception = new UserNotFoundException("bob");

    assertEquals("Usuário \"bob\" não encontrado.", exception.getMessage());
  }
}
