package controller;

import view.RegisterScreen;

public class RegisterController {

    private final RegisterScreen screen;
    private final Runnable onSuccess;

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

            screen.showError("Todos os campos são obrigatórios.");

            return;
        }

        if (!password.equals(confirmPassword)) {

            screen.showError("As senhas não coincidem.");
            return;
        }

        onSuccess.run();
    }
}