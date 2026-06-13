package service;

import java.util.Random;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Movie;

public class TMDBService {

    private final String apiKey;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Random random = new Random();


    public TMDBService(String apiKey) {
        this.apiKey = apiKey;
    }

    public Movie getRandomMovie() throws Exception {

        int randomPage = random.nextInt(20) + 1;

        String url = "https://api.themoviedb.org/3/movie/popular" + "?api_key=" + apiKey + "&page=" + randomPage;

        RestTemplate restTemplate = new RestTemplate();

        String response = restTemplate.getForObject(url,String.class);

        JsonNode root = mapper.readTree(response);

        JsonNode movies = root.get("results");

        int randomIndex = random.nextInt(movies.size());

        JsonNode movieNode = movies.get(randomIndex);

        Movie movie = new Movie();
        movie.setId(movieNode.get("id").asInt());
        movie.setTitle(movieNode.get("title").asText());
        movie.setDescription(movieNode.get("overview").asText());
        movie.setPosterPath(movieNode.get("poster_path").asText());

        return movie;
    }
}