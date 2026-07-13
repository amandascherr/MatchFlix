package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * Identidade visual do MatchFlix, inspirada na Netflix.
 * Centraliza cores, fontes e estilos compartilhados pelas telas.
 */
public final class Theme {

    private Theme() {}

    // Paleta
    public static final Color BG = new Color(0x141414);
    public static final Color SURFACE = new Color(0x1F1F1F);
    public static final Color SURFACE_HOVER = new Color(0x2A2A2A);
    public static final Color FIELD_BG = new Color(0x333333);
    public static final Color FIELD_HOVER = new Color(0x454545);
    public static final Color BORDER = new Color(0x404040);
    public static final Color RED = new Color(0xE50914);
    public static final Color RED_HOVER = new Color(0xF6121D);
    public static final Color GREEN = new Color(0x46D369);
    public static final Color TEXT = Color.WHITE;
    public static final Color TEXT_SECONDARY = new Color(0xB3B3B3);
    public static final Color TEXT_MUTED = new Color(0x808080);
    public static final Color SCROLL_THUMB = new Color(0x4D4D4D);

    public static Font font(int style, int size) {
        return new Font(Font.SANS_SERIF, style, size);
    }

    public static JLabel logo(int size) {
        JLabel label = new JLabel("MATCHFLIX");
        label.setFont(font(Font.BOLD, size));
        label.setForeground(RED);
        return label;
    }

    public static JLabel title(String text, int size) {
        JLabel label = new JLabel(text);
        label.setFont(font(Font.BOLD, size));
        label.setForeground(TEXT);
        return label;
    }

    public static JLabel muted(String text, int size) {
        JLabel label = new JLabel(text);
        label.setFont(font(Font.PLAIN, size));
        label.setForeground(TEXT_MUTED);
        return label;
    }

    public static void styleScrollPane(JScrollPane scrollPane) {
        scrollPane.setBorder(null);
        scrollPane.setBackground(BG);
        scrollPane.getViewport().setBackground(BG);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        styleScrollBar(scrollPane.getVerticalScrollBar());
        styleScrollBar(scrollPane.getHorizontalScrollBar());
    }

    private static void styleScrollBar(JScrollBar bar) {
        bar.setPreferredSize(new Dimension(10, 10));
        bar.setBackground(BG);
        bar.setUI(new BasicScrollBarUI() {

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return zeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return zeroButton();
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle track) {
                g.setColor(BG);
                g.fillRect(track.x, track.y, track.width, track.height);
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumb) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(SCROLL_THUMB);
                g2.fillRoundRect(thumb.x + 2, thumb.y + 2, thumb.width - 4, thumb.height - 4, 8, 8);
                g2.dispose();
            }

            private JButton zeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });
    }
}
