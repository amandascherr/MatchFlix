package view.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Movie;
import view.Theme;

/**
 * Cartão principal da Home: pôster que se redimensiona junto com a
 * janela (mantendo a proporção). O pôster escurece no hover e, ao
 * ser clicado, mostra/esconde a sinopse ao lado do filme.
 */
public class MovieCard extends JPanel {

    private static final Color HOVER_OVERLAY = new Color(0, 0, 0, 120);

    private Image posterImage;
    private boolean hover;
    private boolean showInfo;

    private final JPanel posterPanel;
    private final JPanel infoPanel;
    private final JLabel titleLabel;
    private final JTextArea overviewArea;

    public MovieCard() {

        setOpaque(false);
        setLayout(new BorderLayout(24, 0));

        // Pôster adaptável e clicável
        posterPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintPoster((Graphics2D) g.create(), getWidth(), getHeight());
            }
        };
        posterPanel.setOpaque(false);
        posterPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        posterPanel.setToolTipText("Clique para ver a sinopse");

        posterPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showInfo = !showInfo;
                infoPanel.setVisible(showInfo);
                revalidate();
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                posterPanel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                posterPanel.repaint();
            }
        });

        // Sinopse ao lado do pôster (escondida até o clique)
        titleLabel = new JLabel();
        titleLabel.setFont(Theme.font(Font.BOLD, 24));
        titleLabel.setForeground(Theme.TEXT);

        overviewArea = new JTextArea();
        overviewArea.setEditable(false);
        overviewArea.setFocusable(false);
        overviewArea.setLineWrap(true);
        overviewArea.setWrapStyleWord(true);
        overviewArea.setOpaque(false);
        overviewArea.setFont(Theme.font(Font.PLAIN, 14));
        overviewArea.setForeground(Theme.TEXT_SECONDARY);

        infoPanel = new JPanel(new BorderLayout(0, 12));
        infoPanel.setOpaque(false);
        infoPanel.setPreferredSize(new Dimension(320, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        infoPanel.add(titleLabel, BorderLayout.NORTH);
        infoPanel.add(overviewArea, BorderLayout.CENTER);
        infoPanel.setVisible(false);

        add(posterPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);
    }

    private void paintPoster(Graphics2D g2, int width, int height) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if (posterImage == null || posterImage.getWidth(null) <= 0) {
            g2.setColor(Theme.SURFACE);
            g2.fillRoundRect(0, 0, width, height, 18, 18);

            g2.setColor(Theme.TEXT_MUTED);
            g2.setFont(Theme.font(Font.PLAIN, 14));
            String text = "Sem imagem";
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(text, (width - fm.stringWidth(text)) / 2, height / 2);
        } else {
            int imgWidth = posterImage.getWidth(null);
            int imgHeight = posterImage.getHeight(null);

            double scale = Math.min((double) width / imgWidth, (double) height / imgHeight);
            int finalWidth = (int) (imgWidth * scale);
            int finalHeight = (int) (imgHeight * scale);

            int x = (width - finalWidth) / 2;
            int y = (height - finalHeight) / 2;

            RoundRectangle2D shape = new RoundRectangle2D.Float(x, y, finalWidth, finalHeight, 18, 18);

            g2.setClip(shape);
            g2.drawImage(posterImage, x, y, finalWidth, finalHeight, null);

            if (hover) {
                g2.setColor(HOVER_OVERLAY);
                g2.fill(shape);

                g2.setColor(Theme.TEXT);
                g2.setFont(Theme.font(Font.BOLD, 15));
                String hint = showInfo ? "Ocultar sinopse" : "Ver sinopse";
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(hint,
                        x + (finalWidth - fm.stringWidth(hint)) / 2,
                        y + finalHeight / 2 + fm.getAscent() / 2);
            }
        }

        g2.dispose();
    }

    // Exibe um filme
    public void setMovie(Movie movie) {
        titleLabel.setText(movie.getTitle());

        overviewArea.setText(movie.getDescription());
        overviewArea.setCaretPosition(0);

        loadPoster(movie);
    }

    // Carrega poster da API
    private void loadPoster(Movie movie) {
        try {
            String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();

            posterImage = new ImageIcon(new URL(imageUrl)).getImage();

        } catch (Exception e) {
            posterImage = null;

            System.out.println("Erro ao carregar poster: " + e.getMessage());
        }

        posterPanel.repaint();
    }
}
