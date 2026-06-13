package controller;

import java.util.List;

import model.User;
import model.UserProfileDTO;
import service.dataManager.DataManager;
import view.LoginScreen;

public class LoginController {

    private final LoginScreen screen;
    private final Runnable onSuccess;
    private DataManager manager;


    public LoginController(LoginScreen screen, Runnable onSuccess, DataManager manager) {
        this.screen = screen;
        this.onSuccess = onSuccess;
        this.manager = manager;

        screen.setOnLogin(this::login);
    }
    
    private void login() {

        if (screen.getEmail().isBlank() || screen.getPassword().isBlank()) {

            screen.showError("Todos os campos são obrigatórios.");
            return;
        }

        List<LoginDTO> info = manager.readData("loginInfo", LoginDTO.class);

        if (info == null) {
            screen.showError("Não há cadastros no sistema.");
            return;
        }

        for (int i = 0; i < info.size(); i++) {
            if (info.get(i).email().equals(screen.getEmail()) && info.get(i).password().equals(screen.getPassword())) {

                UserProfileDTO userInfo = manager.readData(screen.getEmail(), UserProfileDTO.class).get(0);

                User currentUser = new User(info.get(i).name(), info.get(i).email(), userInfo);
                Session.setLoggedUser(currentUser);
                onSuccess.run();
                return;
            }
        }

        screen.showError("Login não existe ou a senha está incorreta.");
    }
}
