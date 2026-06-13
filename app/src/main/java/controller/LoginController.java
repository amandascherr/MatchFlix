package controller;

import java.util.List;

import model.User;
import model.UserProfileDTO;
import service.Services;
import service.dataManager.DataManager;
import view.LoginScreen;

public class LoginController {

    private final LoginScreen screen;
    private final Runnable onSuccess;
    private final DataManager manager = Services.getManager();


    public LoginController(LoginScreen screen, Runnable onSuccess) {
        this.screen = screen;
        this.onSuccess = onSuccess;

        screen.setOnLogin(this::login);
    }
    
    private void login() {

        if (screen.getEmail().isBlank() || screen.getPassword().isBlank()) {

            screen.showError("Todos os campos são obrigatórios.");
            return;
        }

        UserProfileDTO info = manager.readData(screen.getEmail(), UserProfileDTO.class).get(0);

        if (info == null) {
            screen.showError("Email inválido ou sistema sem cadastros");
            return;
        }

        if (info.password().equals(screen.getPassword())) {

            UserProfileDTO userInfo = manager.readData(screen.getEmail(), UserProfileDTO.class).get(0);

            User currentUser = new User(userInfo);
            Session.setLoggedUser(currentUser);
            onSuccess.run();
            return;
        }

        screen.showError("Login não existe ou a senha está incorreta.");
    }
}
