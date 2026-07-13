package view.components.button;

import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import view.Theme;

/**
 * Base dos botões circulares da barra superior: círculo escuro
 * que clareia no hover, com ícone vetorial desenhado pela subclasse.
 */
public abstract class CircleIconButton extends JComponent {

    private final Runnable onClick;
    private boolean hover;

    protected CircleIconButton(Runnable onClick) {
        this.onClick = onClick;

        setPreferredSize(new Dimension(42, 42));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (CircleIconButton.this.onClick != null) {
                    CircleIconButton.this.onClick.run();
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight());

        g2.setColor(hover ? Theme.SURFACE_HOVER : Theme.SURFACE);
        g2.fillOval(0, 0, size, size);

        g2.setColor(hover ? Theme.TEXT : Theme.TEXT_SECONDARY);
        g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        paintIcon(g2, size);

        g2.dispose();
    }

    /** Desenha o ícone dentro de um círculo de lado {@code size}. */
    protected abstract void paintIcon(Graphics2D g2, int size);
}
