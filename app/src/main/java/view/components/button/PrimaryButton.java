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
 * Botão de ação principal: vermelho Netflix, cantos arredondados.
 */
public class PrimaryButton extends JButton {

    public PrimaryButton(String text) {
        this(text, false);
    }

    public PrimaryButton(String text, boolean compact) {
        super(text);

        setFont(Theme.font(Font.BOLD, compact ? 13 : 15));
        setForeground(Theme.TEXT);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        if (compact) {
            setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        } else {
            setBorder(BorderFactory.createEmptyBorder(12, 28, 12, 28));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color background = Theme.RED;
        if (!isEnabled()) {
            background = Theme.FIELD_BG;
        } else if (getModel().isPressed()) {
            background = Theme.RED.darker();
        } else if (getModel().isRollover()) {
            background = Theme.RED_HOVER;
        }

        g2.setColor(background);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        g2.dispose();

        super.paintComponent(g);
    }
}
