package controller;

import exception.EmptyFieldException;
import exception.UserNotFoundException;
import model.User;
import model.UserProfileDTO;
import service.Services;
import service.dataManager.DataManager;
import view.screens.LoginScreen;
import view.util.Dialogs;

public class LoginController {

    private final LoginScreen screen;
    private final Runnable onSuccess;
    private final DataManager manager = Services.getManager();

    public LoginController(LoginScreen screen, Runnable onSuccess) {
        this.screen = screen;
        this.onSuccess = onSuccess;

        screen.setOnLogin(this::login);
    }

    private void validateFields() throws EmptyFieldException {
        if (screen.getEmail().isBlank()) {
            throw new EmptyFieldException("Email");
        }

        if (screen.getPassword().isBlank()) {
            throw new EmptyFieldException("Senha");
        }
    }
    
    private void login() {

        try {
            validateFields();
        } 
        catch(EmptyFieldException e) {
            Dialogs.showError(screen, e.getMessage());
            return;
        }

        try {
            UserProfileDTO info = manager.findUser("user", screen.getEmail());

            if (info.password().equals(screen.getPassword())) {

                UserProfileDTO userInfo = manager.readData("user", screen.getEmail(), UserProfileDTO.class).get(0);

                User currentUser = new User(userInfo);
                Session.setLoggedUser(currentUser);
                onSuccess.run();
                
            } else {
                Dialogs.showError(screen, "A senha está incorreta.");
            }

        }
        catch(UserNotFoundException e){
            Dialogs.showError(screen, e.getMessage());
        }
    }
}
