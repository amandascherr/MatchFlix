package view.components;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Movie;
import util.Theme;

public class MoviesPanel extends JPanel {

    public MoviesPanel(ArrayList<Movie> movies) {
        setOpaque(false);

        if (movies == null || movies.isEmpty()) {

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            JLabel emptyLabel = Theme.muted("Nenhum filme por aqui ainda.", 14);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            add(emptyLabel);

            return;
        }

        setLayout(new GridLayout(0, 3, 14, 18));
        for (Movie movie : movies) {
            add(new SmallMovieCard(movie));
        }
    }
}
