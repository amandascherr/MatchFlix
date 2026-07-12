package service;

import java.util.ArrayList;
import java.util.List;

import model.Invite;

public class InvitationService {

    private static final List<Invite> invitations = new ArrayList<>();

    public static void sendInvitation(Invite invite) {
        invitations.add(invite);
    }

    public static List<Invite> getInvitations() {
        return invitations;
    }
}