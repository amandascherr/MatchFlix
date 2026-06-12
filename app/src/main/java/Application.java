import javax.swing.SwingUtilities;

import controller.NavigationController;
import io.github.cdimascio.dotenv.Dotenv;
import service.TMDBService;

public class Application {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {

                Dotenv dotenv = Dotenv.load();
                
                String apiKey = dotenv.get("TMDB_API_KEY");
                
                TMDBService tmdb = new TMDBService(apiKey);

                NavigationController navigation = new NavigationController(tmdb);
                navigation.showLogin();

            } catch (Exception e) {

                e.printStackTrace();
            }
        });
    }
}