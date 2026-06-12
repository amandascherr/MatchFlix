import javax.swing.SwingUtilities;
import io.github.cdimascio.dotenv.Dotenv;

import service.TMDBService;
import model.Movie;

public class Application {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {

                Dotenv dotenv = Dotenv.load();

                String apiKey = dotenv.get("TMDB_API_KEY");

                TMDBService tmdb = new TMDBService(apiKey);

            } catch (Exception e) {

                e.printStackTrace();
            }
        });
    }
}