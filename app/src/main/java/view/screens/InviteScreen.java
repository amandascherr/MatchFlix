package view.screens;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.Theme;
import view.components.FormField;
import view.components.button.PrimaryButton;

public class InviteScreen extends JDialog {

    private FormField usernameField;
    private JButton sendButton;

    public InviteScreen(java.awt.Frame owner) {

        super(owner, "Convidar usuário", true);

        setSize(420, 260);

        setLocationRelativeTo(owner);

        buildUI();
    }

    private void buildUI(){

        JPanel panel = new JPanel();
        panel.setBackground(Theme.BG);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(28, 32, 28, 32));

        usernameField = new FormField("Email do usuário", false);
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        sendButton = new PrimaryButton("Enviar convite");
        sendButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sendButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

        panel.add(usernameField);

        panel.add(Box.createVerticalStrut(24));

        panel.add(sendButton);

        panel.add(Box.createVerticalGlue());

        setContentPane(panel);
    }

    public String getTypedUsername(){

        return usernameField.getText().trim();

    }

    public void setOnSend(Runnable action){

        sendButton.addActionListener(e -> action.run());

    }
}
