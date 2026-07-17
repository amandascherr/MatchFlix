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

  /** Limpa a lista estatica de convites antes e depois de cada teste. */
  @BeforeEach
  @AfterEach
  public void clearInvitations() {
    InvitationService.getInvitations().clear();
  }

  /**
   * Cria um convite de teste para o destinatario informado.
   *
   * @param receiver nome do usuario destinatario.
   * @return um {@link Invite} com remetente e grupo ficticios.
   */
  private Invite invite(String receiver) {
    return new Invite(new User("Bixao", "nobrega"), receiver, new Group("Conpecs"));
  }

  /**
   * Verifica que o servico comeca sem convites registrados.
   */
  @Test
  public void startsEmpty() {
    assertTrue(InvitationService.getInvitations().isEmpty());
  }

  /**
   * Verifica que enviar um convite o armazena no servico.
   */
  @Test
  public void sendInvitationStoresIt() {
    Invite invite = invite("amanda");

    InvitationService.sendInvitation(invite);

    assertEquals(1, InvitationService.getInvitations().size());
    assertEquals(invite, InvitationService.getInvitations().get(0));
  }

  /**
   * Verifica que multiplos convites sao acumulados na ordem de envio.
   */
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
