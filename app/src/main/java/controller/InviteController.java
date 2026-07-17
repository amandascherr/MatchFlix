package controller;

import dto.UserProfileDTO;
import exception.UserNotFoundException;
import model.Group;
import model.Invite;
import model.User;
import service.InvitationService;
import service.Services;
import service.dataManager.DataManager;
import util.Dialogs;
import view.screens.InviteScreen;

/**
 * Controla a tela de convite, buscando o usuário destinatário e enviando-lhe um
 * convite para um grupo.
 */
public class InviteController {

    private InviteScreen screen;
    private Group group;
    private final DataManager manager = Services.getManager();

    /**
     * Liga o controlador à tela de convite para um grupo.
     *
     * @param group  grupo ao qual o usuário será convidado.
     * @param screen tela de convite.
     */
    public InviteController(Group group, InviteScreen screen){
        this.group = group;
        this.screen = screen;

        configureActions();
    }

    /**
     * Configura o envio do convite: valida o nome digitado, localiza o usuário
     * destinatário, cria o {@link Invite}, registra-o e persiste a notificação
     * no perfil do destinatário.
     */
    private void configureActions(){

        screen.setOnSend(() -> {
            if(screen.getTypedUsername().isBlank()){
                Dialogs.showError(screen, "Digite um usuário.");
                return;
            }

            try {
                UserProfileDTO receiver = manager.findUser("user", screen.getTypedUsername());

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