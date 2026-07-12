package view.components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Invite;

public class InviteNotificationPanel extends JPanel {

    private final JButton acceptButton;
    private final JButton denyButton;

    public InviteNotificationPanel(Invite notification) {

        setLayout(new BorderLayout());

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel message = new JLabel(notification.getMessage());

        message.setFont(new Font("Arial", Font.PLAIN, 15));

        add(message, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout());

        acceptButton = new JButton("✔");

        denyButton = new JButton("✖");

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