package view.components.button;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class AddGroupButton extends JPanel {

    private final Runnable onClick;

    public AddGroupButton(Runnable onClick) {

        this.onClick = onClick;

        setPreferredSize(new Dimension(45, 45));
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (AddGroupButton.this.onClick != null) {
                    AddGroupButton.this.onClick.run();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(new Color(76, 175, 80));
        g2.fillOval(0, 0, 45, 45);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 24));

        g2.drawString("+", 15, 30);
    }
}