package view.screens;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import view.components.FormField;

public class CreateGroupScreen extends JFrame {

    private FormField groupNameField;
    private JButton createButton;

    // CHamado em HomeScreen
    public CreateGroupScreen() {

        setTitle("Criar Grupo");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI();
    }

    private void buildUI() {

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));

        root.add(Box.createVerticalStrut(30));

        // Título
        javax.swing.JLabel title = new javax.swing.JLabel("Crie um novo grupo");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        root.add(title);

        root.add(Box.createVerticalStrut(30));

        // Campo nome do grupo
        groupNameField = new FormField("Nome do grupo", false);

        groupNameField.setMaximumSize(
            new Dimension(300, 50)
        );

        groupNameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        root.add(groupNameField);

        root.add(Box.createVerticalStrut(30));

        // Botão criar
        createButton = new JButton("Criar");

        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        createButton.setMaximumSize(
            new Dimension(200, 40)
        );

        root.add(createButton);

        add(root, BorderLayout.CENTER);
    }

    public String getGroupName() {
        return groupNameField.getText();
    }
    // É a ação do botão, que é definida pelo CreateGruopController, chamado em HomeScreen.
    public void setOnCreate(Runnable action) {
        createButton.addActionListener(e -> action.run());
    }
}