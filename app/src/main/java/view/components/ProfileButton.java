package view.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ProfileButton extends JPanel {

    private Runnable onClick;
    private ImageIcon icon;

    public ProfileButton(Runnable onClick, ImageIcon icon) {
        this.onClick = onClick;
        this.icon = icon;

        setPreferredSize(new Dimension(50, 50));
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ProfileButton.this.onClick != null) {
                    ProfileButton.this.onClick.run();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                repaint();
            }
        });
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

        int buttonSize = 50;

        g2.setColor(new Color(220, 220, 220));
        g2.fillOval(0, 0, buttonSize, buttonSize);

        if (icon != null) {
            g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, buttonSize, buttonSize));

            Image img = icon.getImage();
            int imgWidth = img.getWidth(this);
            int imgHeight = img.getHeight(this);

            double scale = Math.max((double) buttonSize / imgWidth, (double) buttonSize / imgHeight);
            int finalWidth = (int) (imgWidth * scale);
            int finalHeight = (int) (imgHeight * scale);

            int x = (buttonSize - finalWidth) / 2;
            int y = (buttonSize - finalHeight) / 2;

            g2.drawImage(img, x, y, finalWidth, finalHeight, this);
        } else {
            g2.setClip(null);
            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.drawString("👤", 15, 32);
        }

        g2.dispose();
    }
}