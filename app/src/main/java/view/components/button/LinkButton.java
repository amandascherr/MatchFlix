package view.components.button;

import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;

import view.Theme;

/**
 * Botão em estilo de link: texto cinza que clareia no hover.
 */
public class LinkButton extends JButton {

    public LinkButton(String text) {
        super(text);

        setFont(Theme.font(Font.PLAIN, 14));
        setForeground(Theme.TEXT_SECONDARY);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        getModel().addChangeListener(e ->
            setForeground(getModel().isRollover() ? Theme.TEXT : Theme.TEXT_SECONDARY)
        );
    }
}
