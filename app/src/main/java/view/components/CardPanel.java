package view.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import util.Theme;

/**
 * Cartão escuro com cantos arredondados e faixa de destaque
 * opcional na borda esquerda.
 */
public class CardPanel extends JPanel {

    private final Color accent;

    public CardPanel(Color accent) {
        this.accent = accent;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Theme.SURFACE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);

        if (accent != null) {
            g2.setColor(accent);
            g2.fillRoundRect(0, 0, 8, getHeight(), 8, 8);
            g2.fillRect(4, 0, 4, getHeight());
        }

        g2.dispose();
    }
}
