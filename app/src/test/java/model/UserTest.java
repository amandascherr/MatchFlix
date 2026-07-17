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

  @Test
  public void newUserStartsEmpty() {
    User user = new User("Bixao", "nobrega");

    assertEquals("Bixao", user.getName());
    assertEquals("nobrega", user.getEmail());
    assertTrue(user.getLikedMovies().isEmpty());
    assertTrue(user.getGroups().isEmpty());
    assertTrue(user.getNotifications().isEmpty());
  }

  @Test
  public void joinGroupRegistersMembershipOnBothSides() {
    User user = new User("Bixao", "nobrega");
    Group group = new Group("Conpecs");

    user.joinGroup(group);

    assertEquals(1, user.getGroups().size());
    assertEquals(1, group.getNumOfUsers());
    assertEquals(1, group.getSubsSize());
  }

  @Test
  public void joinGroupTwiceIsIdempotent() {
    User user = new User("Bixao", "nobrega");
    Group group = new Group("Conpecs");

    user.joinGroup(group);
    user.joinGroup(group);

    assertEquals(1, user.getGroups().size());
    assertEquals(1, group.getNumOfUsers());
  }

  @Test
  public void getLikedMoviesReturnsDefensiveCopy() {
    User user = new User("Bixao", "nobrega");

    ArrayList<Movie> copy = user.getLikedMovies();
    copy.add(new Movie(1, "Filme", "sinopse", "poster"));

    assertTrue(user.getLikedMovies().isEmpty());
  }

  @Test
  public void dislikeWithoutSubscribersDoesNothing() {
    User user = new User("Bixao", "nobrega");
    Movie movie = new Movie(1, "Filme", "sinopse", "poster");

    assertDoesNotThrow(() -> user.userDislike(movie));
    assertTrue(user.getLikedMovies().isEmpty());
  }
}
