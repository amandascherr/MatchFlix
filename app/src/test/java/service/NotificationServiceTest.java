package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import model.Group;
import model.Invite;
import model.Match;
import model.Notification;
import model.User;

/**
 * Testa o registro em memoria e a consulta de notificacoes do
 * {@link NotificationService}.
 * <p>
 * O servico usa uma lista estatica compartilhada; cada teste remove o que
 * adicionou em {@link #cleanup()} para preservar o isolamento.
 * </p>
 */
public class NotificationServiceTest {

  private final List<Notification> added = new ArrayList<>();

  private void addTracked(Notification notification) {
    NotificationService.add(notification);
    added.add(notification);
  }

  @AfterEach
  public void cleanup() {
    for (Notification notification : added) {
      NotificationService.remove(notification);
    }
    added.clear();
  }

  @Test
  public void matchIsReturnedForAnyUser() {
    User user = new User("Bixao", "nobrega");
    Match match = new Match(10, "Conpecs");

    addTracked(match);

    assertTrue(NotificationService.getNotifications(user).contains(match));
  }

  @Test
  public void removeDropsNotification() {
    User user = new User("Bixao", "nobrega");
    Match match = new Match(10, "Conpecs");
    NotificationService.add(match);

    NotificationService.remove(match);

    assertFalse(NotificationService.getNotifications(user).contains(match));
  }

  @Test
  public void matchesForAllUsersAreListedTogether() {
    User user = new User("Bixao", "nobrega");
    Match first = new Match(1, "Conpecs");
    Match second = new Match(2, "Selva");

    addTracked(first);
    addTracked(second);

    List<Notification> notifications = NotificationService.getNotifications(user);
    assertTrue(notifications.contains(first));
    assertTrue(notifications.contains(second));
  }

  /**
   * Caracteriza o comportamento ATUAL (com bug) do filtro de convites: como
   * {@code Invite.getReceiver()} devolve uma {@code String} (o nome) e ela e
   * comparada a um objeto {@link User} em {@code getNotifications}, a igualdade
   * nunca e verdadeira e todo convite acaba filtrado, mesmo que o destinatario
   * seja o usuario consultado. Este teste protege contra regressao e deve ser
   * atualizado quando o bug do tipo do {@code receiver} for corrigido.
   */
  @Test
  public void invitesAreCurrentlyNeverReturnedDueToReceiverTypeMismatch() {
    User receiver = new User("Amanda", "amanda");
    Invite invite = new Invite(new User("Bixao", "nobrega"), receiver.getName(), new Group("Conpecs"));

    addTracked(invite);

    assertFalse(NotificationService.getNotifications(receiver).contains(invite));
  }
}
