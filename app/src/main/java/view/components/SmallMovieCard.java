package view.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Movie;
import view.Theme;

/**
 * Miniatura de filme com pôster arredondado, usada nas grades
 * de curtidos e matches.
 */
public class SmallMovieCard extends JPanel {

    private Image posterImage;

    public SmallMovieCard(Movie movie) {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        try {
            String imageUrl = "https://image.tmdb.org/t/p/w342" + movie.getPosterPath();
            posterImage = new ImageIcon(new URL(imageUrl)).getImage();

        } catch (Exception e) {
            posterImage = null;
        }

        JPanel posterPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                int width = getWidth();
                int height = getHeight();

                if (posterImage == null || posterImage.getWidth(null) <= 0) {
                    g2.setColor(Theme.SURFACE);
                    g2.fillRoundRect(0, 0, width, height, 12, 12);

                    g2.setColor(Theme.TEXT_MUTED);
                    g2.setFont(Theme.font(Font.PLAIN, 12));
                    String text = "Sem pôster";
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(text, (width - fm.stringWidth(text)) / 2, height / 2);
                } else {
                    g2.setClip(new RoundRectangle2D.Float(0, 0, width, height, 12, 12));
                    g2.drawImage(posterImage, 0, 0, width, height, null);
                }

                g2.dispose();
            }
        };

        posterPanel.setOpaque(false);
        posterPanel.setPreferredSize(new Dimension(130, 195));
        posterPanel.setMaximumSize(new Dimension(130, 195));
        posterPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(
                "<html><center>" + movie.getTitle() + "</center></html>",
                SwingConstants.CENTER);
        titleLabel.setFont(Theme.font(Font.PLAIN, 12));
        titleLabel.setForeground(Theme.TEXT_SECONDARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(posterPanel);
        add(Box.createVerticalStrut(8));
        add(titleLabel);

        setPreferredSize(new Dimension(150, 240));
    }
}
