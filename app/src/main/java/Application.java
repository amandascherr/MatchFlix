import javax.swing.SwingUtilities;

import controller.HomeController;
import io.github.cdimascio.dotenv.Dotenv;
import service.TMDBService;
import view.HomeScreen;

public class Application {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {

                Dotenv dotenv = Dotenv.load();
                
                String apiKey = dotenv.get("TMDB_API_KEY");
                
                TMDBService tmdb = new TMDBService(apiKey);

                HomeScreen homeScreen = new HomeScreen();

                HomeController homeController = new HomeController(homeScreen, tmdb);

                homeScreen.setVisible(true);

            } catch (Exception e) {

                e.printStackTrace();
            }
        });
    }
}