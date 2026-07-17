package controller;

import model.Movie;
import service.TMDBService;
import view.screens.HomeScreen;

/**
 * Controla a tela principal, onde o usuário avalia filmes. Carrega os filmes
 * pela TMDB e trata as ações de curtir e rejeitar.
 */
public class HomeController {

    private final HomeScreen homeScreen;
    private final TMDBService tmdbService;

    /**
     * Liga o controlador à tela principal, configura os eventos e carrega o
     * primeiro filme.
     *
     * @param homeScreen  tela principal.
     * @param tmdbService serviço da TMDB usado para obter os filmes.
     */
    public HomeController(HomeScreen homeScreen, TMDBService tmdbService) {
        this.homeScreen = homeScreen;
        this.tmdbService = tmdbService;

        configureEvents();
        loadMovie();
    }

    /**
     * Associa as ações de curtir e rejeitar aos botões da tela.
     */
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

    /**
     * Sorteia um filme na TMDB e o exibe na tela.
     */
    private void loadMovie() {
        try {
            Movie movie = tmdbService.getRandomMovie();

            homeScreen.setMovie(movie);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * Registra a curtida do filme atual pelo usuário logado e carrega o
     * próximo. Ignora a ação se não houver filme em exibição.
     *
     * @throws Exception se o carregamento do próximo filme falhar.
     */
    private void handleLike() throws Exception {

        Movie currentMovie = homeScreen.getCurrentMovie();

        if (currentMovie == null) {
            return;
        }

        System.out.println("Curtiu: " + currentMovie.getTitle());
        Session.getLoggedUser().userLike(currentMovie);

        loadMovie();
    }

    /**
     * Registra a rejeição do filme atual pelo usuário logado e carrega o
     * próximo. Ignora a ação se não houver filme em exibição.
     *
     * @throws Exception se o carregamento do próximo filme falhar.
     */
    private void handleDislike() throws Exception {

        Movie currentMovie = homeScreen.getCurrentMovie();

        if (currentMovie == null) {
            return;
        }

        System.out.println("Descurtiu: " + currentMovie.getTitle());
        Session.getLoggedUser().userDislike(currentMovie);

        loadMovie();
    }
}