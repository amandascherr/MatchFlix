package controller;

import java.util.ArrayList;

import model.Group;
import service.Services;
import service.dataManager.DataDTO;
import service.dataManager.DataManager;
import view.CreateGroupScreen;

public class CreateGroupController {

    private final CreateGroupScreen screen;
    private final Runnable onSuccess;

    private final DataManager manager = Services.getManager();

    public CreateGroupController(CreateGroupScreen screen, Runnable onSuccess) {
        this.screen = screen;
        this.onSuccess = onSuccess;

        screen.setOnCreate(this::createGroup);
    }

    private void createGroup() {

        String groupName = screen.getGroupName();

        if (groupName.isBlank()) {
            screen.showError("O nome do grupo é obrigatório.");

            return;
        }

        Group newGroup = new Group(groupName);
        Session.getLoggedUser().joinGroup(newGroup);

        onSuccess.run();
    }
}