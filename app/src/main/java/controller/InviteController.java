package controller;

import javax.swing.JOptionPane;

import model.Group;
import model.Invite;
import model.User;
import model.UserProfileDTO;
import service.InvitationService;
import service.Services;
import service.dataManager.DataManager;
import view.InviteScreen;

public class InviteController {

    private InviteScreen screen;
    private Group group;
    private final DataManager manager = Services.getManager();

    public InviteController(Group group, InviteScreen screen){

        this.group = group;
        this.screen = screen;

        configureActions();
    }

    private void configureActions(){

        screen.setOnSend(() -> {

            UserProfileDTO info = manager.readData(screen.getTypedUsername(), UserProfileDTO.class).get(0);

            if(screen.getTypedUsername().isBlank()){
                JOptionPane.showMessageDialog(screen,"Digite um usuário.");
                return;
            }

            if (info == null) {
                JOptionPane.showMessageDialog(screen, "Usuário não encontrado.");
                return;
            }

            User user = new User(info);

            Invite invite = new Invite(Session.getLoggedUser(), user, group);

            InvitationService.sendInvitation(invite);

            JOptionPane.showMessageDialog(
                    screen,
                    "Convite enviado!");

            screen.dispose();

        });

    }

}