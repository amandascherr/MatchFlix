package view.screens;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.InviteController;
import model.Group;
import view.Theme;
import view.components.MoviesPanel;
import view.components.ProfileAvatar;
import view.components.button.PrimaryButton;

public class ViewGroupDetailsScreen extends JFrame {

    private final Group group;

    private ProfileAvatar groupAvatar;
    private JButton inviteButton;

    public ViewGroupDetailsScreen(Group group) {

        this.group = group;

        setTitle(group.getName());

        setSize(580, 680);
        setMinimumSize(new Dimension(520, 480));

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI();
    }

    private void buildUI() {

        JPanel root = new JPanel();

        root.setBackground(Theme.BG);

        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));

        root.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));

        // Foto
        groupAvatar = new ProfileAvatar(
                group.getProfileImage(),
                120);

        groupAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);

        groupAvatar.setCursor(
                new Cursor(Cursor.HAND_CURSOR));

        // Nome
        JLabel nameLabel = Theme.title(group.getName(), 22);

        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Participantes
        JLabel membersLabel = new JLabel(group.getNumOfUsers() + " participantes");

        membersLabel.setFont(Theme.font(Font.PLAIN, 14));
        membersLabel.setForeground(Theme.TEXT_SECONDARY);
        membersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão convite
        inviteButton = new PrimaryButton("Convidar usuário");

        inviteButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Quando clicar no botão
        inviteButton.addActionListener(e -> {

            InviteScreen inviteScreen =
                    new InviteScreen(this);

            new InviteController(group, inviteScreen);

            inviteScreen.setVisible(true);

        });

        root.add(groupAvatar);
        root.add(Box.createVerticalStrut(16));

        root.add(nameLabel);
        root.add(Box.createVerticalStrut(4));

        root.add(membersLabel);
        root.add(Box.createVerticalStrut(20));

        root.add(inviteButton);

        root.add(Box.createVerticalStrut(36));

        JLabel matchesTitle = Theme.title("Matches", 17);

        matchesTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        root.add(matchesTitle);

        root.add(Box.createVerticalStrut(16));

        MoviesPanel moviesPanel = new MoviesPanel(group.getMatches());
        moviesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        root.add(moviesPanel);

        JScrollPane scrollPane = new JScrollPane(root);

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Theme.styleScrollPane(scrollPane);

        setContentPane(scrollPane);
    }
}
