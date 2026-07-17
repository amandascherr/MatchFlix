package exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Testa as mensagens das excecoes de validacao usadas nas regras de cadastro,
 * login e busca de usuarios.
 */
public class ExceptionTest {

  @Test
  public void emptyFieldMessageNamesTheField() {
    EmptyFieldException exception = new EmptyFieldException("Email");

    assertEquals("O campo \"Email\" deve ser preenchido.", exception.getMessage());
  }

  @Test
  public void wrongPasswordMessage() {
    PasswordMismatchException exception = new PasswordMismatchException();

    assertEquals("Senha incorreta.", exception.getMessage());
  }

  @Test
  public void passwordConfirmationMismatchMessage() {
    PasswordMismatchException exception = new PasswordMismatchException("abc", "xyz");

    assertEquals("As senhas não coincidem.", exception.getMessage());
  }

  @Test
  public void userAlreadyExistsMessageNamesTheEmail() {
    UserAlreadyExistsException exception = new UserAlreadyExistsException("a@b.com");

    assertEquals("O email \"a@b.com\" já está cadastrado.", exception.getMessage());
  }

  @Test
  public void userNotFoundMessageNamesTheUser() {
    UserNotFoundException exception = new UserNotFoundException("bob");

    assertEquals("Usuário \"bob\" não encontrado.", exception.getMessage());
  }
}
