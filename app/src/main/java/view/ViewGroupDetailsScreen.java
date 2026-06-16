package view;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Group;
import view.components.ProfileAvatar;
import view.components.MoviesPanel;

public class ViewGroupDetailsScreen extends JFrame {

    private final Group group;

    private ProfileAvatar groupAvatar;
    private JButton inviteButton;

    public ViewGroupDetailsScreen(Group group) {

        this.group = group;

        setTitle(group.getName());

        setSize(450, 550);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI();
    }

    private void buildUI() {

        JPanel root = new JPanel();

        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));

        root.setBorder(
            BorderFactory.createEmptyBorder(
                20,
                20,
                20,
                20
            )
        );

        // Foto

        groupAvatar = new ProfileAvatar(
            group.getProfileImage(),
            120
        );

        groupAvatar.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        groupAvatar.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );

        // Nome

        JLabel nameLabel =
            new JLabel(group.getName());

        nameLabel.setFont(
            new Font("Arial", Font.BOLD, 18)
        );

        nameLabel.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        // Participantes

        JLabel membersLabel = new JLabel(group.getNumOfUsers() + " participantes");

        membersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão convite
        inviteButton = new JButton("Convidar usuário");

        inviteButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        root.add(groupAvatar);
        root.add(Box.createVerticalStrut(15));

        root.add(nameLabel);
        root.add(Box.createVerticalStrut(5));

        root.add(membersLabel);
        root.add(Box.createVerticalStrut(15));

        root.add(inviteButton);

        root.add(Box.createVerticalStrut(30));

        // Matches
        JLabel matchesTitle = new JLabel("Matches");

        matchesTitle.setFont(new Font("Arial", Font.BOLD, 16));

        matchesTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        root.add(matchesTitle);
        root.add(Box.createVerticalStrut(10));

        root.add(new MoviesPanel(group.getMatches()));

        JScrollPane scrollPane = new JScrollPane(root);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        setContentPane(scrollPane);
    }

    public void setOnInvite(Runnable action) {

        inviteButton.addActionListener(
            e -> action.run()
        );
    }
}