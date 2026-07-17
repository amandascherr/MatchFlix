package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testa as regras de negocio do {@link Group}: adesao de membros, contagem de
 * curtidas e a regra central de match (um filme so vira match quando todos os
 * membros o curtem, e apenas uma vez).
 * <p>
 * A regra de match e exercitada por {@link Group#beNotified(String, Object)},
 * que e o ponto por onde as curtidas dos usuarios realmente chegam ao grupo no
 * padrao Observer. Isso evita o caminho {@code User.userLike}, que depende de
 * carregamento assincrono e de persistencia, tornando os testes deterministicos.
 * </p>
 */
public class GroupTest {

  /** Diretorio onde o {@code JsonDataManager} grava os dados (relativo ao modulo). */
  private static final File DATA_DIR = new File("src/main/resources/data");

  private Set<String> preexistingFiles;

  /** Fotografa os arquivos existentes antes do teste. */
  @BeforeEach
  public void snapshotDataDir() {
    preexistingFiles = new HashSet<>();
    if (DATA_DIR.isDirectory()) {
      for (File file : DATA_DIR.listFiles()) {
        preexistingFiles.add(file.getName());
      }
    }
  }

  /** Remove apenas os arquivos que o teste criou (ex.: {@code group%...json}). */
  @AfterEach
  public void cleanupDataDir() {
    if (DATA_DIR.isDirectory()) {
      for (File file : DATA_DIR.listFiles()) {
        if (!preexistingFiles.contains(file.getName())) {
          file.delete();
        }
      }
    }
  }

  /**
   * Cria um filme de teste com o id informado.
   *
   * @param id id do filme.
   * @return um {@link Movie} com titulo e dados ficticios.
   */
  private Movie movie(int id) {
    return new Movie(id, "Filme " + id, "sinopse", "poster");
  }

  /**
   * Verifica que ao um usuario entrar no grupo o total de membros e de inscritos
   * do publisher e incrementado.
   */
  @Test
  public void addUserIncrementsMembersAndSubscribers() {
    Group group = new Group("Conpecs");
    User user = new User("Bixao", "nobrega");

    user.joinGroup(group);

    assertEquals(1, group.getNumOfUsers());
    assertEquals(1, group.getSubsSize());
  }

  /**
   * Verifica que entrar duas vezes no mesmo grupo nao gera adesao duplicada.
   */
  @Test
  public void joinGroupTwiceKeepsSingleMembership() {
    Group group = new Group("Conpecs");
    User user = new User("Bixao", "nobrega");

    user.joinGroup(group);
    user.joinGroup(group);

    assertEquals(1, group.getNumOfUsers());
    assertEquals(1, group.getSubsSize());
  }

  /**
   * Verifica que o id do grupo e derivado do nome (prefixo {@code nome_}) e que
   * o nome e preservado.
   */
  @Test
  public void groupIdIsDerivedFromName() {
    Group group = new Group("Conpecs");

    assertTrue(group.getId().startsWith("Conpecs_"));
    assertEquals("Conpecs", group.getName());
  }

  /**
   * Verifica que a curtida de um filme e contabilizada no mapa do grupo pela
   * chave correta (o id do filme).
   */
  @Test
  public void likeCountsPerMovieId() {
    Group group = new Group("Conpecs");
    new User("Bixao", "nobrega").joinGroup(group);
    new User("Scherr", "amanda").joinGroup(group);

    Movie movie = movie(10);
    group.beNotified("like", movie);

    assertEquals(1, group.getLikedMovies().get(movie.getId()));
  }

  /**
   * Verifica a regra central: o match so ocorre quando todos os membros do grupo
   * curtem o mesmo filme; antes disso o estado permanece {@code check_match}.
   */
  @Test
  public void matchHappensOnlyWhenAllMembersLike() {
    Group group = new Group("Conpecs");
    new User("Bixao", "nobrega").joinGroup(group);
    new User("Scherr", "amanda").joinGroup(group);
    new User("Gustavin", "selva").joinGroup(group);

    Movie movie = movie(20);

    group.beNotified("like", movie);
    assertEquals("check_match", controller.Session.logAction);
    assertFalse(group.getMatches().contains(movie.getId()));

    group.beNotified("like", movie);
    assertEquals("check_match", controller.Session.logAction);
    assertFalse(group.getMatches().contains(movie.getId()));

    group.beNotified("like", movie);
    assertEquals("match", controller.Session.logAction);
    assertTrue(group.getMatches().contains(movie.getId()));
  }

  /**
   * Verifica que um match e registrado uma unica vez: novas curtidas no mesmo
   * filme apos o match nao recontam nem duplicam o registro.
   */
  @Test
  public void matchIsRegisteredOnlyOnce() {
    Group group = new Group("Conpecs");
    new User("Bixao", "nobrega").joinGroup(group);

    Movie movie = movie(30);
    group.beNotified("like", movie);

    assertTrue(group.getMatches().contains(movie.getId()));
    assertEquals(1, group.getMatches().size());
    assertEquals(1, group.getLikedMovies().get(movie.getId()));

    // Nova curtida no mesmo filme apos o match: nao deve recontar nem duplicar.
    group.beNotified("like", movie);

    assertEquals(1, group.getMatches().size());
    assertEquals(1, group.getLikedMovies().get(movie.getId()));
  }

  /**
   * Verifica que uma rejeicao ({@code dislike}) nao contabiliza curtida nem gera
   * match.
   */
  @Test
  public void dislikeDoesNotCountAsLike() {
    Group group = new Group("Conpecs");
    new User("Bixao", "nobrega").joinGroup(group);

    Movie movie = movie(40);
    group.beNotified("dislike", movie);

    assertFalse(group.getLikedMovies().containsKey(movie.getId()));
    assertFalse(group.getMatches().contains(movie.getId()));
  }
}
