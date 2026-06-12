package view;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Movie;
import view.components.LikeDislikeButtons;
import view.components.MovieCard;

public class HomeScreen extends JFrame {

    private final MovieCard movieCard;

    private DefaultListModel<String> likedMoviesModel;

    private LikeDislikeButtons buttons;

    private Movie currentMovie;

    public HomeScreen() {

        movieCard = new MovieCard();

        setTitle("MatchFlix");

        setSize(1200, 800);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        buildUI();
    }

    // Constrói a Interface Gráfica
    private void buildUI() {

        setLayout(new BorderLayout());

        // Centro
        JPanel centerPanel = new JPanel();

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        centerPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Filme
        centerPanel.add(movieCard);

        // Botões
        buttons = new LikeDislikeButtons();

        centerPanel.add(
                Box.createVerticalStrut(20)
        );

        centerPanel.add(buttons);

        add(centerPanel, BorderLayout.CENTER);
    }

    // Getters e Setters

    public void setMovie(Movie movie) {
        currentMovie = movie;

        movieCard.setMovie(movie);
    }

    public Movie getCurrentMovie() {
        return currentMovie;
    }

    // Eventos

    public void setOnLike(Runnable action) {
        buttons.setOnLike(action);
    }

    public void setOnDislike(Runnable action) {
        buttons.setOnDislike(action);
    }
}