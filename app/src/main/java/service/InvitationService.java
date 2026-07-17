package service;

import java.util.ArrayList;
import java.util.List;

import model.Invite;

/**
 * Serviço que mantém, em memória, os convites de grupo enviados durante a
 * execução da aplicação.
 */
public class InvitationService {

    private static final List<Invite> invitations = new ArrayList<>();

    /**
     * Registra um convite enviado.
     *
     * @param invite convite a ser armazenado.
     */
    public static void sendInvitation(Invite invite) {
        invitations.add(invite);
    }

    /**
     * @return a lista de convites enviados.
     */
    public static List<Invite> getInvitations() {
        return invitations;
    }
}