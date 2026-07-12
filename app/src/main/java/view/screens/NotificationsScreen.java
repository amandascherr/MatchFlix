package view.screens;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.Session;
import model.Invite;
import model.Match;
import model.Notification;
import service.NotificationService;
import view.components.InviteNotificationPanel;
import view.components.MatchNotificationPanel;

public class NotificationsScreen extends JFrame {

    public NotificationsScreen() {

        setTitle("Notificações");

        setSize(500, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI();
    }

    private void buildUI() {

        JPanel root = new JPanel();

        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));

        root.setBorder(BorderFactory.createEmptyBorder(
                15,
                15,
                15,
                15));

        for (Notification notification :
                NotificationService.getNotifications(Session.getLoggedUser())) {

            if (notification instanceof Invite invite) {

                InviteNotificationPanel panel =
                        new InviteNotificationPanel(invite);

                panel.setAlignmentX(Component.LEFT_ALIGNMENT);

                panel.setOnAccept(() -> {

                    invite.getGroup().addUser(invite.getReceiver());
                    System.out.println("Convite aceito");

                    NotificationService.remove(invite);
                    dispose();
                    new NotificationsScreen().setVisible(true);


                });

                panel.setOnDeny(() -> {
                    NotificationService.remove(invite);
                    System.out.println("Convite recusado");

                    dispose();
                    new NotificationsScreen().setVisible(true);
                });

                root.add(panel);
            }

            else if (notification instanceof Match match) {

                MatchNotificationPanel panel = new MatchNotificationPanel(match);

                panel.setAlignmentX(Component.LEFT_ALIGNMENT);

                root.add(panel);

            }

            root.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(root);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        setContentPane(scrollPane);
    }
}