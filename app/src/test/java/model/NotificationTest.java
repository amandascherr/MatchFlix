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

  @Test
  public void matchExposesMovieAndGroup() {
    Match match = new Match(42, "Cinefilos");

    assertEquals(42, match.getmovieId());
    assertEquals("Cinefilos", match.getGroup());
  }

  @Test
  public void matchMessageMentionsMovieAndGroup() {
    Match match = new Match(42, "Cinefilos");

    assertEquals("\"42\" foi um match para \"Cinefilos\"!", match.getMessage());
  }

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

  @Test
  public void notificationStartsUnreadAndCanBeMarked() {
    Match match = new Match(1, "G");

    assertFalse(match.isRead());
    match.markAsRead();
    assertTrue(match.isRead());
  }

  @Test
  public void inviteMessageMentionsGroupName() {
    User sender = new User("Bixao", "nobrega");
    Group group = new Group("Cinefilos");
    Invite invite = new Invite(sender, "amanda", group);

    assertEquals("Convite para participar de \"Cinefilos\"", invite.getMessage());
  }

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
