package controller;

import java.util.ArrayList;
import java.util.List;

import controller.DTO.UserTableDTO;
import exception.EmptyFieldException;
import model.dto.UserProfileDTO;
import service.Services;
import service.dataManager.DataDTO;
import service.dataManager.DataManager;
import view.screens.RegisterScreen;
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

    private void validateFields() throws EmptyFieldException {
        if (screen.getNameInput().isBlank()) {
            throw new EmptyFieldException("Nome");
        }

        if (screen.getEmail().isBlank()) {
            throw new EmptyFieldException("Email");
        }

        if (screen.getPassword().isBlank()) {
            throw new EmptyFieldException("Senha");
        }

        if (screen.getConfirmPassword().isBlank()) {
            throw new EmptyFieldException("Confirmar Senha");
        }
    }

    private void register() {
        String password = screen.getPassword();
        String confirmPassword = screen.getConfirmPassword();

        try {
            validateFields();
        }
        catch(EmptyFieldException e) {
            Dialogs.showError(screen, e.getMessage());
            return;
        }

        if (!password.equals(confirmPassword)) {

            Dialogs.showError(screen, "As senhas não coincidem.");
            return;
        }

        List<UserProfileDTO> existing = manager.readData("user", screen.getEmail(), UserProfileDTO.class);
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
            new ArrayList<>(),
            new ArrayList<>()
        );

        manager.createData("user", bodyProfile.email(), bodyProfile);

        UserTableDTO tableEntry = new UserTableDTO(bodyProfile.name(), bodyProfile.email());
        manager.appendData(new DataDTO<UserTableDTO>("users", tableEntry));

        onSuccess.run();
    }
}