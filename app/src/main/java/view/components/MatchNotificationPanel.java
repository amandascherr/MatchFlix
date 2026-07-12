package view.components;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Match;

public class MatchNotificationPanel extends JPanel {

    public MatchNotificationPanel(Match notification) {

        setLayout(new BorderLayout());

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel message = new JLabel(notification.getMessage());

        message.setFont(new Font("Arial", Font.PLAIN, 15));

        add(message, BorderLayout.CENTER);
    }

}