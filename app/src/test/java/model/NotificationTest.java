package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dto.MatchDTO;

/**
 * Testa as regras das notificacoes: mensagens exibidas, conversao para DTO e o
 * estado de leitura comum a {@link Match} e {@link Invite}.
 */
public class NotificationTest {

  /**
   * Verifica que o {@link Match} expoe corretamente o id do filme e o nome do
   * grupo informados.
   */
  @Test
  public void matchExposesMovieAndGroup() {
    Match match = new Match(42, "Cinefilos");

    assertEquals(42, match.getmovieId());
    assertEquals("Cinefilos", match.getGroup());
  }

  /**
   * Verifica o texto da mensagem exibida para um match.
   */
  @Test
  public void matchMessageMentionsMovieAndGroup() {
    Match match = new Match(42, "Cinefilos");

    assertEquals("\"42\" foi um match para \"Cinefilos\"!", match.getMessage());
  }

  /**
   * Verifica a conversao de ida e volta entre {@link Match} e {@link MatchDTO},
   * preservando id do filme e grupo.
   */
  @Test
  public void matchConvertsToDtoAndBack() {
    Match original = new Match(7, "Selva");

    MatchDTO dto = original.toDTO();
    assertEquals(7, dto.movieId());
    assertEquals("Selva", dto.group());

    Match restored = new Match(dto);
    assertEquals(original.getmovieId(), restored.getmovieId());
    assertEquals(original.getGroup(), restored.getGroup());
  }

  /**
   * Verifica o estado de leitura comum das notificacoes: comeca como nao lida e
   * pode ser marcada como lida.
   */
  @Test
  public void notificationStartsUnreadAndCanBeMarked() {
    Match match = new Match(1, "G");

    assertFalse(match.isRead());
    match.markAsRead();
    assertTrue(match.isRead());
  }

  /**
   * Verifica que a mensagem do {@link Invite} menciona o nome do grupo de
   * destino.
   */
  @Test
  public void inviteMessageMentionsGroupName() {
    User sender = new User("Bixao", "nobrega");
    Group group = new Group("Cinefilos");
    Invite invite = new Invite(sender, "amanda", group);

    assertEquals("Convite para participar de \"Cinefilos\"", invite.getMessage());
  }

  /**
   * Verifica que o {@link Invite} preserva remetente, destinatario e grupo
   * informados.
   */
  @Test
  public void inviteKeepsSenderReceiverAndGroup() {
    User sender = new User("Bixao", "nobrega");
    Group group = new Group("Cinefilos");
    Invite invite = new Invite(sender, "amanda", group);

    assertSame(sender, invite.getSender());
    assertEquals("amanda", invite.getReceiver());
    assertSame(group, invite.getGroup());
  }
}
