package view.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Invite;
import util.Theme;
import view.components.button.DarkButton;
import view.components.button.PrimaryButton;

public class InviteNotificationPanel extends CardPanel {

    private final JButton acceptButton;
    private final JButton denyButton;

    public InviteNotificationPanel(Invite notification) {
        super(Theme.RED);

        setLayout(new BorderLayout(12, 0));
        setBorder(BorderFactory.createEmptyBorder(14, 22, 14, 14));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 76));

        JLabel message = new JLabel(notification.getMessage());
        message.setFont(Theme.font(Font.PLAIN, 14));
        message.setForeground(Theme.TEXT);

        add(message, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonsPanel.setOpaque(false);

        acceptButton = new PrimaryButton("Aceitar", true);
        denyButton = new DarkButton("Recusar");
        denyButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        denyButton.setFont(Theme.font(Font.BOLD, 13));

        buttonsPanel.add(acceptButton);
        buttonsPanel.add(denyButton);

        add(buttonsPanel, BorderLayout.EAST);
    }

    public void setOnAccept(Runnable action) {
        acceptButton.addActionListener(e -> action.run());
    }

    public void setOnDeny(Runnable action) {
        denyButton.addActionListener(e -> action.run());
    }

}
