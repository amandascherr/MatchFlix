package view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InviteScreen extends JDialog {

    private JTextField usernameField;
    private JButton sendButton;

    public InviteScreen(java.awt.Frame owner) {

        super(owner, "Convidar usuário", true);

        setSize(350,180);

        setLocationRelativeTo(owner);

        buildUI();
    }

    private void buildUI(){

        JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Digite o nome do usuário:");

        usernameField = new JTextField();

        sendButton = new JButton("Enviar convite");

        panel.add(label);

        panel.add(Box.createVerticalStrut(10));

        panel.add(usernameField);

        panel.add(Box.createVerticalStrut(15));

        panel.add(sendButton);

        add(panel, BorderLayout.CENTER);
    }

    public String getTypedUsername(){

        return usernameField.getText().trim();

    }

    public void setOnSend(Runnable action){

        sendButton.addActionListener(e -> action.run());

    }
}