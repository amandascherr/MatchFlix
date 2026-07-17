package controller;

import dto.UserProfileDTO;
import exception.EmptyFieldException;
import exception.UserNotFoundException;
import model.User;
import service.Services;
import service.dataManager.DataManager;
import util.Dialogs;
import view.screens.LoginScreen;

/**
 * Controla a tela de login, validando as credenciais e iniciando a sessão do
 * usuário autenticado.
 */
public class LoginController {

    private final LoginScreen screen;
    private final Runnable onSuccess;
    private final DataManager manager = Services.getManager();

    /**
     * Liga o controlador à tela de login.
     *
     * @param screen    tela de login.
     * @param onSuccess ação executada após a autenticação bem-sucedida.
     */
    public LoginController(LoginScreen screen, Runnable onSuccess) {
        this.screen = screen;
        this.onSuccess = onSuccess;

        screen.setOnLogin(this::login);
    }

    /**
     * Valida que email e senha foram preenchidos.
     *
     * @throws EmptyFieldException se algum campo obrigatório estiver vazio.
     */
    private void validateFields() throws EmptyFieldException {
        if (screen.getEmail().isBlank()) {
            throw new EmptyFieldException("Email");
        }

        if (screen.getPassword().isBlank()) {
            throw new EmptyFieldException("Senha");
        }
    }
    
    /**
     * Executa o login: valida os campos, verifica a senha do usuário e, em caso
     * de sucesso, carrega o perfil, define o usuário da sessão e dispara
     * {@code onSuccess}. Erros são exibidos como diálogos.
     */
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
