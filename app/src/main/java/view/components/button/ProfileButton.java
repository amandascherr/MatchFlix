package view.components.button;

import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import util.Theme;

/**
 * Avatar circular do usuário na barra superior; ganha um anel
 * vermelho no hover.
 */
public class ProfileButton extends JComponent {

    private final Runnable onClick;
    private ImageIcon icon;
    private boolean hover;

    public ProfileButton(Runnable onClick, ImageIcon icon) {
        this.onClick = onClick;
        this.icon = icon;

        setPreferredSize(new Dimension(44, 44));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setToolTipText("Meu perfil");

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ProfileButton.this.onClick != null) {
                    ProfileButton.this.onClick.run();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
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

        int size = Math.min(getWidth(), getHeight());

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

            g2.drawImage(img, (size - finalWidth) / 2, (size - finalHeight) / 2, finalWidth, finalHeight, this);
            g2.setClip(null);
        } else {
            // Silhueta de pessoa
            g2.setColor(Theme.TEXT_MUTED);
            g2.fillOval(Math.round(size * 0.34f), Math.round(size * 0.18f),
                    Math.round(size * 0.32f), Math.round(size * 0.32f));
            g2.fillArc(Math.round(size * 0.16f), Math.round(size * 0.56f),
                    Math.round(size * 0.68f), Math.round(size * 0.64f), 0, 180);
        }

        if (hover) {
            g2.setColor(Theme.RED);
            g2.setStroke(new BasicStroke(2f));
            g2.drawOval(1, 1, size - 3, size - 3);
        }

        g2.dispose();
    }
}
