package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Group;
import model.Invite;
import model.User;

/**
 * Testa o registro em memoria de convites feito pelo {@link InvitationService}.
 * <p>
 * Como o servico guarda os convites em uma lista estatica compartilhada, a
 * lista e limpa antes e depois de cada teste para garantir isolamento.
 * </p>
 */
public class InvitationServiceTest {

  @BeforeEach
  @AfterEach
  public void clearInvitations() {
    InvitationService.getInvitations().clear();
  }

  private Invite invite(String receiver) {
    return new Invite(new User("Bixao", "nobrega"), receiver, new Group("Conpecs"));
  }

  @Test
  public void startsEmpty() {
    assertTrue(InvitationService.getInvitations().isEmpty());
  }

  @Test
  public void sendInvitationStoresIt() {
    Invite invite = invite("amanda");

    InvitationService.sendInvitation(invite);

    assertEquals(1, InvitationService.getInvitations().size());
    assertEquals(invite, InvitationService.getInvitations().get(0));
  }

  @Test
  public void invitationsAccumulateInOrder() {
    Invite first = invite("amanda");
    Invite second = invite("gustavo");

    InvitationService.sendInvitation(first);
    InvitationService.sendInvitation(second);

    assertEquals(2, InvitationService.getInvitations().size());
    assertEquals(first, InvitationService.getInvitations().get(0));
    assertEquals(second, InvitationService.getInvitations().get(1));
  }
}
