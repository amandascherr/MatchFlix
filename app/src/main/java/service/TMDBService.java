package service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Movie;

public class TMDBService {

    private static final int maxPages = 500;
    private final String apiKey;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Random random = new Random();
    private Set<Integer> shownMoviesId = new HashSet<>();
    private List<JsonNode> currentPage = new ArrayList<>();
    private int page = 0;


    public TMDBService(String apiKey) {
        this.apiKey = apiKey;
    }

    private void getMoviesPage() throws Exception {
        this.page++;

        if (this.page > maxPages) {
            this.page = 1;
        }

        String url = "https://api.themoviedb.org/3/movie/popular" + "?api_key=" + apiKey + "&page=" + this.page;

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        JsonNode root = mapper.readTree(response);

        for (JsonNode movie : root.get("results")) {
            this.currentPage.add(movie);
        }

        this.currentPage = filterMovies(currentPage);
    }

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

    private Movie buildMovie(JsonNode movieNode) {
        Movie movie = new Movie();
        movie.setId(movieNode.get("id").asInt());
        movie.setTitle(movieNode.get("title").asText());
        movie.setDescription(movieNode.get("overview").asText());
        movie.setPosterPath(movieNode.get("poster_path").asText());

        return movie;
    }

    public Movie getRandomMovie() throws Exception {
        if (currentPage.isEmpty()) {
            getMoviesPage();
        }

        int randomIndex = random.nextInt(this.currentPage.size());
        JsonNode movieNode = this.currentPage.remove(randomIndex);
        shownMoviesId.add(movieNode.get("id").asInt());

        return buildMovie(movieNode);
    }
}