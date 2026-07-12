package controller;

import exception.UserNotFoundException;
import model.Group;
import model.Invite;
import model.User;
import model.UserProfileDTO;
import service.InvitationService;
import service.Services;
import service.dataManager.DataManager;
import view.screens.InviteScreen;
import view.util.Dialogs;

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
            if(screen.getTypedUsername().isBlank()){
                Dialogs.showError(screen, "Digite um usuário.");
                return;
            }

            try {
                UserProfileDTO receiver = manager.findUser(screen.getTypedUsername());

                User user = new User(receiver);
                Invite invite = new Invite(Session.getLoggedUser(), user.getName(), group);

                InvitationService.sendInvitation(invite);

                user.getNotifications().add(invite);

                MatchController.saveMatch(user);

                InvitationService.sendInvitation(invite);
                Dialogs.showMessage(screen, "Convite enviado!");

                screen.dispose();
            }
            catch(UserNotFoundException e){
                Dialogs.showError(screen, e.getMessage());
            }
        });

    }

}