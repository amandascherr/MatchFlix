package service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import controller.Session;
import model.Movie;

/**
 * Serviço de acesso à API TMDB (The Movie Database).
 * <p>
 * Fornece os filmes que o usuário avalia, buscando páginas de filmes populares
 * sob demanda e sorteando um filme por vez. Mantém o controle dos filmes já
 * exibidos ({@code shownMoviesId}) e evita repetir filmes já curtidos pelo
 * usuário logado.
 * </p>
 */
public class TMDBService {

    private static final int maxPages = 500;
    private final String apiKey;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Random random = new Random();
    private Set<Integer> shownMoviesId = new HashSet<>();
    private List<JsonNode> currentPage = new ArrayList<>();
    private int page = 0;
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Cria o serviço com a chave de API a ser usada nas requisições à TMDB.
     *
     * @param apiKey chave de acesso à API TMDB.
     */
    public TMDBService(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Busca a próxima página de filmes populares na TMDB e a armazena já
     * filtrada. A paginação é circular: ao passar de {@code maxPages}, volta à
     * primeira página.
     *
     * @throws Exception se a requisição ou a leitura da resposta falhar.
     */
    private void getMoviesPage() throws Exception {
        this.page++;

        if (this.page > maxPages) {
            this.page = 1;
        }

        String url = "https://api.themoviedb.org/3/movie/popular" + "?api_key=" + apiKey + "&page=" + this.page;

        String response = restTemplate.getForObject(url, String.class);
        JsonNode root = mapper.readTree(response);

        for (JsonNode movie : root.get("results")) {
            this.currentPage.add(movie);
        }

        this.currentPage = filterMovies(currentPage);
    }

    /**
     * Busca um filme específico pelo seu id na TMDB.
     *
     * @param id id do filme na TMDB.
     * @return o {@link Movie} correspondente.
     * @throws Exception se a requisição ou a leitura da resposta falhar.
     */
    public Movie getMovieById(int id) throws Exception {

        String url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=" + apiKey;

        String response = restTemplate.getForObject(url, String.class);
        JsonNode movieNode = mapper.readTree(response);

        return buildMovie(movieNode);
    }

    /**
     * Filtra os filmes de uma página, descartando os já exibidos, os adultos,
     * os sem sinopse e os sem pôster.
     *
     * @param movies filmes brutos vindos da API.
     * @return os filmes aptos a serem apresentados ao usuário.
     */
    private List<JsonNode> filterMovies(List<JsonNode> movies) {
        List<JsonNode> filtered = new ArrayList<>();

        for (JsonNode movie : movies) {
            int id = movie.get("id").asInt();

            if (!shownMoviesId.contains(id) 
                    && !movie.get("adult").asBoolean()
                    && !movie.get("overview").asText().isBlank()
                    && !movie.get("poster_path").isNull()) {
                filtered.add(movie);
            }
        }

        return filtered;
    }

    /**
     * Constrói um {@link Movie} a partir do nó JSON retornado pela TMDB.
     *
     * @param movieNode nó JSON com os dados do filme.
     * @return o filme correspondente.
     */
    private Movie buildMovie(JsonNode movieNode) {
        Movie movie = new Movie();
        movie.setId(movieNode.get("id").asInt());
        movie.setTitle(movieNode.get("title").asText());
        movie.setDescription(movieNode.get("overview").asText());
        movie.setPosterPath(movieNode.get("poster_path").asText());

        return movie;
    }

    /**
     * Sorteia o próximo filme a ser apresentado ao usuário.
     * <p>
     * Carrega uma nova página quando a atual se esgota, remove o filme sorteado
     * da página e o marca como exibido. Filmes que o usuário logado já curtiu
     * são pulados, retornando recursivamente o próximo sorteio.
     * </p>
     *
     * @return um filme ainda não exibido nem curtido pelo usuário.
     * @throws Exception se a busca de uma nova página falhar.
     */
    public Movie getRandomMovie() throws Exception {
        if (currentPage.isEmpty()) {
            getMoviesPage();
        }

        int randomIndex = random.nextInt(this.currentPage.size());
        JsonNode movieNode = this.currentPage.remove(randomIndex);
        shownMoviesId.add(movieNode.get("id").asInt());
        
        Movie currentMovie = buildMovie(movieNode);
        boolean alreadyLiked = Session.getLoggedUser().getLikedMovies().stream().anyMatch(movie -> movie.getId() == currentMovie.getId());
        if (alreadyLiked) {
            return getRandomMovie();
        }

        return currentMovie;
    }
}