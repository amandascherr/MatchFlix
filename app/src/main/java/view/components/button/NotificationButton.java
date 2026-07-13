package view.components.button;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;

public class NotificationButton extends CircleIconButton {

    public NotificationButton(Runnable onClick) {
        super(onClick);
        setToolTipText("Notificações");
    }

    @Override
    protected void paintIcon(Graphics2D g2, int size) {
        // Corpo do sino
        Path2D bell = new Path2D.Float();
        bell.moveTo(size * 0.30f, size * 0.60f);
        bell.lineTo(size * 0.30f, size * 0.44f);
        bell.curveTo(size * 0.30f, size * 0.28f, size * 0.70f, size * 0.28f, size * 0.70f, size * 0.44f);
        bell.lineTo(size * 0.70f, size * 0.60f);
        bell.closePath();
        g2.draw(bell);

        // Badalo
        g2.drawArc(Math.round(size * 0.44f), Math.round(size * 0.60f),
                Math.round(size * 0.12f), Math.round(size * 0.12f), 180, 180);
    }
}
