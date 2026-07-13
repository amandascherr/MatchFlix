package controller;

import service.Services;
import service.TMDBService;
import view.screens.HomeScreen;
import view.screens.LoginScreen;
import view.screens.RegisterScreen;

public class NavigationController {

    private final TMDBService tmdbService;

    public NavigationController() {
        this.tmdbService = Services.getTMDBService();
    }

    // Tela de login
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

    // Tela de cadastro
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

    // Home
    public void showHome() {
        HomeScreen homeScreen = new HomeScreen();
        HomeController homeController = new HomeController(homeScreen, tmdbService);

        homeScreen.setVisible(true);
    }
}