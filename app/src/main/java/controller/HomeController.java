package controller;

import model.Movie;
import service.TMDBService;
import view.HomeScreen;

public class HomeController {

    private final HomeScreen homeScreen;
    private final TMDBService tmdbService;

    public HomeController(HomeScreen homeScreen, TMDBService tmdbService) {
        this.homeScreen = homeScreen;
        this.tmdbService = tmdbService;

        configureEvents();
        loadMovie();
    }

    // Eventos dos Botões
    private void configureEvents() {

        homeScreen.setOnLike(() -> {
            try {
                handleLike();

            } catch (Exception e) {

                e.printStackTrace();
            }
        });

        homeScreen.setOnDislike(() -> {
            try {
                handleDislike();

            } catch (Exception e) {

                e.printStackTrace();
            }
        });
    }

    // Carrega um filme
    private void loadMovie() {
        try {
            Movie movie = tmdbService.getRandomMovie();

            homeScreen.setMovie(movie);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // Curte um filme
    private void handleLike() throws Exception {

        Movie currentMovie = homeScreen.getCurrentMovie();

        if (currentMovie == null) {
            return;
        }

        System.out.println("Curtiu: " + currentMovie.getTitle());

        loadMovie();
    }

    // Descurte um filme
    private void handleDislike() throws Exception {

        Movie currentMovie = homeScreen.getCurrentMovie();

        if (currentMovie == null) {
            return;
        }

        System.out.println("Descurtiu: " + currentMovie.getTitle());

        loadMovie();
    }
}