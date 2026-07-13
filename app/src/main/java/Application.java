import javax.swing.SwingUtilities;

import controller.NavigationController;
import io.github.cdimascio.dotenv.Dotenv;
import service.Services;

public class Application {

    public static void main(String[] args) {

        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        SwingUtilities.invokeLater(() -> {

            try {

                Dotenv dotenv = Dotenv.load();
                
                String apiKey = dotenv.get("TMDB_API_KEY");

                Services.initializeTMDB(apiKey);
                
                NavigationController navigation = new NavigationController();
                navigation.showLogin();

            } catch (Exception e) {

                e.printStackTrace();
            }
        });
    }
}