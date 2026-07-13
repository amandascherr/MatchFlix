package view.components.button;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

import javax.swing.JButton;
import javax.swing.JPanel;

import view.Theme;

/**
 * Par de botões circulares de curtir/rejeitar, no estilo dos
 * apps de match: X branco e coração vermelho.
 */
public class LikeDislikeButtons extends JPanel {

    private final JButton likeButton;
    private final JButton dislikeButton;

    public LikeDislikeButtons() {
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 28, 0));

        dislikeButton = new CircleGlyphButton(false);
        likeButton = new CircleGlyphButton(true);

        add(dislikeButton);
        add(likeButton);
    }

    public void setOnLike(Runnable action) {
        likeButton.addActionListener(e -> action.run());
    }

    public void setOnDislike(Runnable action) {
        dislikeButton.addActionListener(e -> action.run());
    }

    private static class CircleGlyphButton extends JButton {

        private final boolean like;

        CircleGlyphButton(boolean like) {
            this.like = like;

            setPreferredSize(new Dimension(68, 68));
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setToolTipText(like ? "Curtir" : "Rejeitar");
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int size = Math.min(getWidth(), getHeight());
            boolean hover = getModel().isRollover();
            boolean pressed = getModel().isPressed();

            // Fundo
            g2.setColor(pressed ? Theme.SURFACE : hover ? Theme.SURFACE_HOVER : Theme.SURFACE);
            g2.fillOval(1, 1, size - 2, size - 2);

            // Anel
            Color ring = hover ? (like ? Theme.RED : Theme.TEXT) : Theme.BORDER;
            g2.setColor(ring);
            g2.setStroke(new BasicStroke(2f));
            g2.drawOval(1, 1, size - 3, size - 3);

            int center = size / 2;

            if (like) {
                g2.setColor(hover ? Theme.RED_HOVER : Theme.RED);
                g2.fill(heart(center, center, size * 0.42f));
            } else {
                g2.setColor(hover ? Theme.TEXT : Theme.TEXT_SECONDARY);
                g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int radius = Math.round(size * 0.16f);
                g2.drawLine(center - radius, center - radius, center + radius, center + radius);
                g2.drawLine(center - radius, center + radius, center + radius, center - radius);
            }

            g2.dispose();
        }

        private static Shape heart(float cx, float cy, float width) {
            float r = width / 4f;

            Area heart = new Area(new Ellipse2D.Float(cx - 2 * r, cy - 1.6f * r, 2 * r, 2 * r));
            heart.add(new Area(new Ellipse2D.Float(cx, cy - 1.6f * r, 2 * r, 2 * r)));

            Path2D triangle = new Path2D.Float();
            triangle.moveTo(cx - 2 * r, cy - 0.5f * r);
            triangle.lineTo(cx + 2 * r, cy - 0.5f * r);
            triangle.lineTo(cx, cy + 1.7f * r);
            triangle.closePath();
            heart.add(new Area(triangle));

            return heart;
        }
    }
}
