package view.components;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Movie;

public class LikedMoviesPanel extends JPanel {

    public LikedMoviesPanel(List<Movie> movies) {
        if (movies == null || movies.isEmpty()) {

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            JLabel emptyLabel = new JLabel("Nenhum filme curtido ainda.");

            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            add(emptyLabel);

            return;
        }

        setLayout(new GridLayout(0, 2, 15, 15));
        for (Movie movie : movies) {
            add(new LikedMovieCard(movie));
        }
    }
}