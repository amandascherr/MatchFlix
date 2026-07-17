package model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/**
 * Testa as regras do {@link User} que independem de persistencia e de
 * carregamento assincrono.
 * <p>
 * Os metodos {@code userLike}, {@code saveUser} e {@code beNotified} nao sao
 * exercitados aqui por dependerem de {@code loadMoviesFuture} (nao inicializado
 * no construtor simples) e de acesso a disco.
 * </p>
 */
public class UserTest {

  /**
   * Verifica que um usuario recem-criado tem nome e email definidos e listas de
   * filmes, grupos e notificacoes vazias.
   */
  @Test
  public void newUserStartsEmpty() {
    User user = new User("Bixao", "nobrega");

    assertEquals("Bixao", user.getName());
    assertEquals("nobrega", user.getEmail());
    assertTrue(user.getLikedMovies().isEmpty());
    assertTrue(user.getGroups().isEmpty());
    assertTrue(user.getNotifications().isEmpty());
  }

  /**
   * Verifica que entrar em um grupo registra a adesao dos dois lados: o grupo
   * entra na lista do usuario e o usuario passa a contar como membro/inscrito do
   * grupo.
   */
  @Test
  public void joinGroupRegistersMembershipOnBothSides() {
    User user = new User("Bixao", "nobrega");
    Group group = new Group("Conpecs");

    user.joinGroup(group);

    assertEquals(1, user.getGroups().size());
    assertEquals(1, group.getNumOfUsers());
    assertEquals(1, group.getSubsSize());
  }

  /**
   * Verifica que entrar duas vezes no mesmo grupo e idempotente (nao duplica a
   * adesao nem a contagem de membros).
   */
  @Test
  public void joinGroupTwiceIsIdempotent() {
    User user = new User("Bixao", "nobrega");
    Group group = new Group("Conpecs");

    user.joinGroup(group);
    user.joinGroup(group);

    assertEquals(1, user.getGroups().size());
    assertEquals(1, group.getNumOfUsers());
  }

  /**
   * Verifica que {@code getLikedMovies} devolve uma copia defensiva: alterar a
   * lista retornada nao afeta o estado interno do usuario.
   */
  @Test
  public void getLikedMoviesReturnsDefensiveCopy() {
    User user = new User("Bixao", "nobrega");

    ArrayList<Movie> copy = user.getLikedMovies();
    copy.add(new Movie(1, "Filme", "sinopse", "poster"));

    assertTrue(user.getLikedMovies().isEmpty());
  }

  /**
   * Verifica que rejeitar um filme sem estar inscrito em nenhum grupo nao lanca
   * excecao nem altera a lista de curtidos.
   */
  @Test
  public void dislikeWithoutSubscribersDoesNothing() {
    User user = new User("Bixao", "nobrega");
    Movie movie = new Movie(1, "Filme", "sinopse", "poster");

    assertDoesNotThrow(() -> user.userDislike(movie));
    assertTrue(user.getLikedMovies().isEmpty());
  }
}
