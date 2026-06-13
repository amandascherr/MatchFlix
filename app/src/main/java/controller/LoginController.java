package controller;

import java.util.ArrayList;

import service.dataManager.DataDTO;
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

        String password = screen.getPassword();

        if (screen.getEmail().isBlank() || screen.getPassword().isBlank()) {

            screen.showError("Todos os campos são obrigatórios.");
            return;
        }

        DataDTO info = manager.readData("loginInfo");

        ArrayList<LoginDTO> logins = info.body();


        // if (!password.equals(confirmPassword)) {

        //     screen.showError("As senhas não coincidem.");
        //     return;
        // }

        onSuccess.run();
    }

}
