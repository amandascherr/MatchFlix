package view.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import model.Match;
import view.Theme;

public class MatchNotificationPanel extends CardPanel {

    public MatchNotificationPanel(Match notification) {
        super(Theme.GREEN);

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(16, 22, 16, 16));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));

        JLabel message = new JLabel(notification.getMessage());
        message.setFont(Theme.font(Font.PLAIN, 14));
        message.setForeground(Theme.TEXT);

        add(message, BorderLayout.CENTER);
    }

}
