package controller;

import model.Group;
import service.Services;
import service.dataManager.DataManager;
import view.screens.CreateGroupScreen;

public class CreateGroupController {

    private final CreateGroupScreen screen;
    private final Runnable onSuccess;

    private final DataManager manager = Services.getManager();

    // É chamado em HomeScreen enquanto está criando grupo
    public CreateGroupController(CreateGroupScreen screen, Runnable onSuccess) {
        this.screen = screen;
        this.onSuccess = onSuccess;

        screen.setOnCreate(this::createGroup);
    }

    // É chamado ao apertar o botão de criar grupo.
    private void createGroup() {

        String groupName = screen.getGroupName();

        if (groupName.isBlank()) {
            screen.showError("O nome do grupo é obrigatório.");

            return;
        }

        Group newGroup = new Group(groupName);
        GroupController.joinGroup(Session.getLoggedUser(), newGroup);

        onSuccess.run();
    }
}