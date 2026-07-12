package controller;

import javax.swing.JOptionPane;

import model.Group;
import model.Invite;
import service.InvitationService;
import view.InviteScreen;

public class InviteController {

    private InviteScreen screen;
    private Group group;

    public InviteController(Group group, InviteScreen screen){

        this.group = group;

        this.screen = screen;

        configureActions();
    }

    private void configureActions(){

        screen.setOnSend(() -> {

            String username = screen.getTypedUsername();

            if(username.isBlank()){

                JOptionPane.showMessageDialog(
                        screen,
                        "Digite um usuário.");

                return;
            }

            Invite invite = new Invite(
                    Session.getLoggedUser(),
                    username,
                    group);

            InvitationService.sendInvitation(invite);

            JOptionPane.showMessageDialog(
                    screen,
                    "Convite enviado!");

            screen.dispose();

        });

    }

}