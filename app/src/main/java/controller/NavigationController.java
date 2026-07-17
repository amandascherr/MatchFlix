package controller;

import service.Services;
import service.TMDBService;
import view.screens.HomeScreen;
import view.screens.LoginScreen;
import view.screens.RegisterScreen;

/**
 * Orquestra a navegação entre as telas da aplicação (login, cadastro e home),
 * criando cada tela junto do seu controlador.
 */
public class NavigationController {

    private final TMDBService tmdbService;

    public NavigationController() {
        this.tmdbService = Services.getTMDBService();
    }

    /**
     * Exibe a tela de login, encaminhando para a home após autenticar e para o
     * cadastro quando solicitado.
     */
    public void showLogin() {
        LoginScreen loginScreen = new LoginScreen();
        LoginController loginController = new LoginController(loginScreen, () -> {
                loginScreen.dispose();
                showHome();
            }
        );

        loginScreen.setOnRegister(() -> {

            loginScreen.dispose();
            showRegister();
        });

        loginScreen.setVisible(true);
    }

    /**
     * Exibe a tela de cadastro, voltando ao login após o cadastro ou quando
     * solicitado.
     */
    public void showRegister() {
        RegisterScreen registerScreen = new RegisterScreen();
        RegisterController registerController = new RegisterController(registerScreen, () -> {
                registerScreen.dispose();
                showLogin();
            }
        );

        registerScreen.setOnLogin(() -> {

            registerScreen.dispose();
            showLogin();
        });

        registerScreen.setVisible(true);
    }

    /**
     * Exibe a tela principal, onde o usuário logado avalia filmes.
     */
    public void showHome() {
        HomeScreen homeScreen = new HomeScreen();
        HomeController homeController = new HomeController(homeScreen, tmdbService);

        homeScreen.setVisible(true);
    }
}