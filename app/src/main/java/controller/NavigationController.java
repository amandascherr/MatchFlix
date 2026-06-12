package controller;

import service.TMDBService;
import view.HomeScreen;
import view.LoginScreen;
import view.RegisterScreen;

public class NavigationController {

    private final TMDBService tmdbService;

    public NavigationController(TMDBService tmdbService) {

        this.tmdbService = tmdbService;
    }

    // Tela de login
    public void showLogin() {
        LoginScreen loginScreen = new LoginScreen();

        loginScreen.setOnLogin(() -> {

            loginScreen.dispose();
            showHome();
        });

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