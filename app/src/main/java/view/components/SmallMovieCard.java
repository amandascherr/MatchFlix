package view.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Movie;

public class SmallMovieCard extends JPanel {

    public SmallMovieCard(Movie movie) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setOpaque(false);

        JLabel posterLabel = new JLabel();
        posterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        try {
            String imageUrl = "https://image.tmdb.org/t/p/w342" + movie.getPosterPath();

            ImageIcon icon = new ImageIcon(new URL(imageUrl));
            Image scaled = icon.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);

            posterLabel.setIcon(new ImageIcon(scaled));

        } catch (Exception e) {
            posterLabel.setText("Sem pôster");
        }

        JLabel titleLabel = new JLabel("<html><center>" + movie.getTitle() + "</center></html>");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(posterLabel);
        add(Box.createVerticalStrut(5));
        add(titleLabel);

        setPreferredSize(new Dimension(140, 220));
    }
}