package view.components.button;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import view.Theme;

/**
 * Botão secundário: cinza escuro, cantos arredondados.
 */
public class DarkButton extends JButton {

    public DarkButton(String text) {
        super(text);

        setFont(Theme.font(Font.BOLD, 14));
        setForeground(Theme.TEXT);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color background = Theme.FIELD_BG;
        if (!isEnabled()) {
            background = Theme.SURFACE;
        } else if (getModel().isPressed()) {
            background = Theme.SURFACE_HOVER;
        } else if (getModel().isRollover()) {
            background = Theme.FIELD_HOVER;
        }

        g2.setColor(background);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        g2.dispose();

        super.paintComponent(g);
    }
}
