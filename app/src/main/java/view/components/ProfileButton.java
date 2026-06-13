package view.components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

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

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // círculo
        g2.setColor(new Color(220, 220, 220));
        g2.fillOval(0, 0, 50, 50);

        if (icon != null) {
            Image img = icon.getImage()
                    .getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            g2.drawImage(img, 5, 5, null);
        } else {
            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.drawString("👤", 15, 32);
        }
    }
}