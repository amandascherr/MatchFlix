package view.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.Movie;

public class MovieCard extends JPanel {

    private JLabel posterLabel;
    private JLabel titleLabel;
    private JTextArea overviewArea;

    public MovieCard() {
        buildUI();
    }

    private void buildUI() {

        setLayout(new BorderLayout());

        setPreferredSize(new Dimension(320, 650));

        setBackground(Color.WHITE);

        // Poster
        posterLabel = new JLabel();

        posterLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(posterLabel, BorderLayout.CENTER);

        // Informações
        JPanel infoPanel = new JPanel();

        infoPanel.setLayout(new BorderLayout());

        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        titleLabel = new JLabel();

        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        overviewArea = new JTextArea();

        overviewArea.setEditable(false);

        overviewArea.setLineWrap(true);

        overviewArea.setWrapStyleWord(true);

        overviewArea.setBackground(Color.WHITE);

        infoPanel.add(titleLabel, BorderLayout.NORTH);

        infoPanel.add(overviewArea, BorderLayout.CENTER);

        add(infoPanel, BorderLayout.SOUTH);
    }

    // Exibe um filme
    public void setMovie(Movie movie) {
        titleLabel.setText(movie.getTitle());

        overviewArea.setText(movie.getDescription());

        loadPoster(movie);
    }

    // Carrega poster da API
    private void loadPoster(Movie movie) {
        try {
            String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();

            ImageIcon icon = new ImageIcon(new URL(imageUrl));

            Image scaledImage = icon.getImage().getScaledInstance(320, 480, Image.SCALE_SMOOTH);

            posterLabel.setIcon(new ImageIcon(scaledImage));

        } catch (Exception e) {
            posterLabel.setIcon(null);

            System.out.println("Erro ao carregar poster: " + e.getMessage());
        }
    }
}