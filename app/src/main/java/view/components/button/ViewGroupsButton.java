package view.components.button;

import java.awt.Graphics2D;

public class ViewGroupsButton extends CircleIconButton {

    public ViewGroupsButton(Runnable onClick) {
        super(onClick);
        setToolTipText("Meus grupos");
    }

    @Override
    protected void paintIcon(Graphics2D g2, int size) {
        // Pessoa de trás
        g2.drawOval(round(size, 0.56f), round(size, 0.28f), round(size, 0.16f), round(size, 0.16f));
        g2.drawArc(round(size, 0.52f), round(size, 0.50f), round(size, 0.24f), round(size, 0.26f), 0, 180);

        // Pessoa da frente
        g2.drawOval(round(size, 0.26f), round(size, 0.26f), round(size, 0.20f), round(size, 0.20f));
        g2.drawArc(round(size, 0.19f), round(size, 0.52f), round(size, 0.34f), round(size, 0.30f), 0, 180);
    }

    private static int round(int size, float fraction) {
        return Math.round(size * fraction);
    }
}
