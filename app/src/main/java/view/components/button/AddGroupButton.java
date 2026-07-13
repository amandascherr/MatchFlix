package view.components.button;

import java.awt.Graphics2D;

public class AddGroupButton extends CircleIconButton {

    public AddGroupButton(Runnable onClick) {
        super(onClick);
        setToolTipText("Criar grupo");
    }

    @Override
    protected void paintIcon(Graphics2D g2, int size) {
        int center = size / 2;
        int radius = size / 5;

        g2.drawLine(center - radius, center, center + radius, center);
        g2.drawLine(center, center - radius, center, center + radius);
    }
}
