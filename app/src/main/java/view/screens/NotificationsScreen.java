package view.screens;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.GroupController;
import controller.Session;
import model.Invite;
import model.Match;
import model.Notification;
import service.NotificationService;
import view.Theme;
import view.components.InviteNotificationPanel;
import view.components.MatchNotificationPanel;

public class NotificationsScreen extends JFrame {

    public NotificationsScreen() {

        setTitle("Notificações");

        setSize(560, 640);
        setMinimumSize(new Dimension(460, 400));

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI();
    }

    private void buildUI() {

        JPanel root = new JPanel();

        root.setBackground(Theme.BG);

        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));

        root.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JLabel title = Theme.title("Notificações", 22);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        root.add(title);
        root.add(Box.createVerticalStrut(20));

        boolean hasNotifications = false;

        for (Notification notification :
                Session.getLoggedUser().getNotifications()) {

            if (notification instanceof Invite invite) {

                InviteNotificationPanel panel =
                        new InviteNotificationPanel(invite);

                panel.setAlignmentX(Component.LEFT_ALIGNMENT);

                panel.setOnAccept(() -> {
                    GroupController.joinGroup(Session.getLoggedUser(), invite.getGroup());
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
                hasNotifications = true;
            }

            else if (notification instanceof Match match) {

                MatchNotificationPanel panel = new MatchNotificationPanel(match);

                panel.setAlignmentX(Component.LEFT_ALIGNMENT);

                root.add(panel);
                hasNotifications = true;

            }

            root.add(Box.createVerticalStrut(12));
        }

        if (!hasNotifications) {
            JLabel emptyLabel = Theme.muted("Nenhuma notificação por enquanto.", 14);
            emptyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            root.add(emptyLabel);
        }

        root.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(root);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Theme.styleScrollPane(scrollPane);

        setContentPane(scrollPane);
    }

}
