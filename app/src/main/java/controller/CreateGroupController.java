package controller;

import model.Group;
import service.Services;
import service.dataManager.DataManager;
import util.Dialogs;
import view.screens.CreateGroupScreen;

/**
 * Controla a tela de criação de grupo, validando a entrada e criando o grupo
 * para o usuário logado.
 */
public class CreateGroupController {

    private final CreateGroupScreen screen;
    private final Runnable onSuccess;

    private final DataManager manager = Services.getManager();

    /**
     * Liga o controlador à tela de criação de grupo.
     *
     * @param screen    tela de criação de grupo.
     * @param onSuccess ação executada após a criação bem-sucedida.
     */
    public CreateGroupController(CreateGroupScreen screen, Runnable onSuccess) {
        this.screen = screen;
        this.onSuccess = onSuccess;

        screen.setOnCreate(this::createGroup);
    }

    /**
     * Cria o grupo com o nome informado na tela. Exige nome não vazio; em
     * seguida faz o usuário logado ingressar no novo grupo e dispara
     * {@code onSuccess}.
     */
    private void createGroup() {

        String groupName = screen.getGroupName();

        if (groupName.isBlank()) {
            Dialogs.showError(screen, "O nome do grupo é obrigatório.");

            return;
        }

        Group newGroup = new Group(groupName);
        GroupController.joinGroup(Session.getLoggedUser(), newGroup);



        onSuccess.run();
    }
}