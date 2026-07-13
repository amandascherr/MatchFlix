package view.screens;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.Group;
import view.Theme;
import view.components.button.DarkButton;

public class UserGroupsScreen extends JFrame {

    public UserGroupsScreen(List<Group> groups) {

        setTitle("Meus Grupos");

        setSize(480, 600);
        setMinimumSize(new Dimension(380, 400));

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI(groups);
    }

    private void buildUI(List<Group> groups) {

        JPanel content = new JPanel();

        content.setBackground(Theme.BG);

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel title = Theme.title("Meus Grupos", 22);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(title);
        content.add(Box.createVerticalStrut(20));

        if (groups == null || groups.isEmpty()) {

            JLabel emptyLabel = Theme.muted("Você ainda não participa de grupos.", 14);

            emptyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            content.add(emptyLabel);

        } else {
            for (Group group : groups) {
                JButton groupButton = new DarkButton(group.getName());

                groupButton.setHorizontalAlignment(SwingConstants.LEFT);

                groupButton.setAlignmentX(Component.LEFT_ALIGNMENT);

                groupButton.setMaximumSize(
                    new Dimension(
                        Integer.MAX_VALUE,
                        52
                    )
                );

                groupButton.addActionListener(e -> {

                    ViewGroupDetailsScreen groupScreen = new ViewGroupDetailsScreen(group);

                    groupScreen.setVisible(true);
                    dispose();
                });

                content.add(groupButton);
                content.add(Box.createVerticalStrut(10));
            }
        }

        content.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(content);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Theme.styleScrollPane(scrollPane);

        setContentPane(scrollPane);
    }
}
