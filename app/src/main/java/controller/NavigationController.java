package controller;

import service.TMDBService;
import service.dataManager.JsonDataManager;
import view.HomeScreen;
import view.LoginScreen;
import view.RegisterScreen;

public class NavigationController {

    private final TMDBService tmdbService;
    private JsonDataManager manager = new JsonDataManager();

    public NavigationController(TMDBService tmdbService) {

        this.tmdbService = tmdbService;
    }

    // Tela de login
    public void showLogin() {
        LoginScreen loginScreen = new LoginScreen();
        LoginController loginController = new LoginController(loginScreen, () -> {
                loginScreen.dispose(); 
                showLogin();
            }, manager
        );

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
            }, manager
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