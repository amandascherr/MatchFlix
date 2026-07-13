package view.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import view.Theme;

public class ProfileAvatar extends JPanel {

    private ImageIcon icon;
    private final int size;

    public ProfileAvatar(ImageIcon icon, int size) {
        this.icon = icon;
        this.size = size;

        setPreferredSize(new Dimension(size, size));
        setMaximumSize(new Dimension(size, size));
        setMinimumSize(new Dimension(size, size));
        setOpaque(false);
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2.setColor(Theme.FIELD_BG);
        g2.fillOval(0, 0, size, size);

        if (icon != null) {
            g2.setClip(new Ellipse2D.Float(0, 0, size, size));

            Image img = icon.getImage();
            int imgWidth = img.getWidth(this);
            int imgHeight = img.getHeight(this);

            double scale = Math.max((double) size / imgWidth, (double) size / imgHeight);
            int finalWidth = (int) (imgWidth * scale);
            int finalHeight = (int) (imgHeight * scale);

            int x = (size - finalWidth) / 2;
            int y = (size - finalHeight) / 2;

            g2.drawImage(img, x, y, finalWidth, finalHeight, this);
        } else {
            // Silhueta de pessoa
            g2.setColor(Theme.TEXT_MUTED);
            g2.fillOval(Math.round(size * 0.34f), Math.round(size * 0.18f),
                    Math.round(size * 0.32f), Math.round(size * 0.32f));
            g2.fillArc(Math.round(size * 0.16f), Math.round(size * 0.56f),
                    Math.round(size * 0.68f), Math.round(size * 0.64f), 0, 180);
        }

        g2.dispose();
    }
}
