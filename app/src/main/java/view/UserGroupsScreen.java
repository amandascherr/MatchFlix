package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Group;

public class UserGroupsScreen extends JFrame {

    public UserGroupsScreen(List<Group> groups) {

        setTitle("Meus Grupos");

        setSize(400, 500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI(groups);
    }

    private void buildUI(List<Group> groups) {

        JPanel content = new JPanel();

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        if (groups == null || groups.isEmpty()) {

            JButton emptyButton =
                new JButton("Você ainda não participa de grupos");

            emptyButton.setEnabled(false);

            content.add(emptyButton);

        } else {
            for (Group group : groups) {
                JButton groupButton =
                    new JButton(group.getName());

                groupButton.setMaximumSize(
                    new Dimension(
                        Integer.MAX_VALUE,
                        50
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

        JScrollPane scrollPane =
            new JScrollPane(content);

        add(scrollPane, BorderLayout.CENTER);
    }
}