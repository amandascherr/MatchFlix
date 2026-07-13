package view.screens;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.Theme;
import view.components.FormField;
import view.components.button.PrimaryButton;

public class CreateGroupScreen extends JFrame {

    private FormField groupNameField;
    private JButton createButton;

    // CHamado em HomeScreen
    public CreateGroupScreen() {

        setTitle("Criar Grupo");
        setSize(440, 340);
        setMinimumSize(new Dimension(380, 300));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI();
    }

    private void buildUI() {

        JPanel root = new JPanel();
        root.setBackground(Theme.BG);
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(new EmptyBorder(36, 40, 36, 40));

        // Título
        JLabel title = Theme.title("Crie um novo grupo", 22);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        root.add(title);

        root.add(Box.createVerticalStrut(28));

        // Campo nome do grupo
        groupNameField = new FormField("Nome do grupo", false);
        groupNameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        root.add(groupNameField);

        root.add(Box.createVerticalStrut(28));

        // Botão criar
        createButton = new PrimaryButton("Criar grupo");
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

        root.add(createButton);

        root.add(Box.createVerticalGlue());

        setContentPane(root);
    }

    public String getGroupName() {
        return groupNameField.getText();
    }
    // É a ação do botão, que é definida pelo CreateGruopController, chamado em HomeScreen.
    public void setOnCreate(Runnable action) {
        createButton.addActionListener(e -> action.run());
    }
}
