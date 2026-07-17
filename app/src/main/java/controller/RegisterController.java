package controller;

import java.util.ArrayList;
import java.util.List;

import dto.UserProfileDTO;
import dto.UserTableDTO;
import exception.EmptyFieldException;
import service.Services;
import service.dataManager.DataDTO;
import service.dataManager.DataManager;
import util.Dialogs;
import view.screens.RegisterScreen;

/**
 * Controla a tela de cadastro, validando os dados e criando o novo usuário na
 * base de dados.
 */
public class RegisterController {

    private final RegisterScreen screen;
    private final Runnable onSuccess;
    private final DataManager manager = Services.getManager();

    /**
     * Liga o controlador à tela de cadastro.
     *
     * @param screen    tela de cadastro.
     * @param onSuccess ação executada após o cadastro bem-sucedido.
     */
    public RegisterController(RegisterScreen screen, Runnable onSuccess) {
        this.screen = screen;
        this.onSuccess = onSuccess;

        screen.setOnRegister(this::register);
    }

    /**
     * Valida que nome, email, senha e confirmação de senha foram preenchidos.
     *
     * @throws EmptyFieldException se algum campo obrigatório estiver vazio.
     */
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

    /**
     * Executa o cadastro: valida os campos, confere se as senhas coincidem e se
     * o email ainda não está em uso, e então grava o perfil do usuário e o
     * registra na tabela de usuários antes de disparar {@code onSuccess}. Erros
     * são exibidos como diálogos.
     */
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