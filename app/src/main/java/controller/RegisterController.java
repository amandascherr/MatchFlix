package controller;

import service.Services;
import service.dataManager.DataDTO;
import service.dataManager.DataManager;
import view.screens.RegisterScreen;

import java.util.ArrayList;
import java.util.List;

import controller.DTO.UserTableDTO;
import model.UserProfileDTO;
import view.util.Dialogs;

public class RegisterController {

    private final RegisterScreen screen;
    private final Runnable onSuccess;
    private final DataManager manager = Services.getManager();

    public RegisterController(RegisterScreen screen, Runnable onSuccess) {
        this.screen = screen;
        this.onSuccess = onSuccess;

        screen.setOnRegister(this::register);
    }

    private void register() {

        String password = screen.getPassword();
        String confirmPassword = screen.getConfirmPassword();

        if (screen.getNameInput().isBlank()
            || screen.getEmail().isBlank()
            || screen.getPassword().isBlank()
            || screen.getConfirmPassword().isBlank()) {

            Dialogs.showError(screen, "Todos os campos são obrigatórios.");
            return;
        }

        if (!password.equals(confirmPassword)) {

            Dialogs.showError(screen, "As senhas não coincidem.");
            return;
        }

        List<UserProfileDTO> existing = manager.readData(screen.getEmail(), UserProfileDTO.class);
        if (existing != null && !existing.isEmpty()) {

            Dialogs.showError(screen, "Já existe um usuário com esse email.");
            return;
        }

        UserProfileDTO bodyProfile = new UserProfileDTO(
            screen.getNameInput(),
            screen.getEmail(),
            screen.getPassword(),
            "",
            new ArrayList<>(),
            new ArrayList<>()
        );

        DataDTO<UserProfileDTO> profilePayload = new DataDTO<UserProfileDTO>(bodyProfile.email(), bodyProfile);
        manager.createData(profilePayload);

        UserTableDTO tableEntry = new UserTableDTO(bodyProfile.name(), bodyProfile.email());
        manager.appendData(new DataDTO<UserTableDTO>("users", tableEntry));

        onSuccess.run();
    }
}