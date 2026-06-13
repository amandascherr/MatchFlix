package controller;

import service.dataManager.DataDTO;
import service.dataManager.DataManager;

import java.util.ArrayList;
import java.util.List;

import model.UserProfileDTO;
import view.RegisterScreen;

public class RegisterController {

    private final RegisterScreen screen;
    private final Runnable onSuccess;
    private DataManager manager;

    public RegisterController(RegisterScreen screen, Runnable onSuccess, DataManager manager) {
        this.screen = screen;
        this.onSuccess = onSuccess;
        this.manager = manager;

        screen.setOnRegister(this::register);
    }

    private void register() {

        String password = screen.getPassword();
        String confirmPassword = screen.getConfirmPassword();

        if (screen.getNameInput().isBlank()
            || screen.getEmail().isBlank()
            || screen.getPassword().isBlank()
            || screen.getConfirmPassword().isBlank()) {

            screen.showError("Todos os campos são obrigatórios.");
            return;
        }

        if (!password.equals(confirmPassword)) {

            screen.showError("As senhas não coincidem.");
            return;
        }

        LoginDTO bodyLogin = new LoginDTO(screen.getNameInput(), screen.getEmail(), screen.getPassword());
        DataDTO<LoginDTO> loginPayload = new DataDTO<LoginDTO>("loginInfo", bodyLogin);
        manager.appendData(loginPayload);

        UserProfileDTO bodyProfile = new UserProfileDTO("", new ArrayList<>(), new ArrayList<>());
        DataDTO<UserProfileDTO> profilePayload = new DataDTO<UserProfileDTO>(bodyLogin.email(), bodyProfile);
        manager.createData(profilePayload);

        onSuccess.run();
    }
}