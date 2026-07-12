package view.screens;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import view.components.FormField;

public class LoginScreen extends JFrame {

    private FormField emailField;
    private FormField passwordField;

    private JButton loginButton;
    private JButton registerButton;

    public LoginScreen() {

        setTitle("MatchFlix");

        setSize(500, 650);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buildUI();
    }

    private void buildUI() {

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.setBorder(new EmptyBorder(40, 50, 40, 50));

        // Logo
        JLabel titleLabel = new JLabel("MATCHFLIX");

        titleLabel.setFont(new Font("Arial", Font.BOLD, 42));

        titleLabel.setForeground(new Color(229, 9, 20));

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Campos
        emailField = new FormField("E-mail", false);
        passwordField = new FormField("Senha", true);

        // Login Button
        loginButton = new JButton("Entrar");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Cadastrar Button
        registerButton = new JButton("Não tem login? Faça seu cadastro");

        registerButton.setBorderPainted(false);

        registerButton.setContentAreaFilled(false);

        registerButton.setForeground(Color.BLUE);

        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Composição
        mainPanel.add(Box.createVerticalGlue());

        mainPanel.add(titleLabel);

        mainPanel.add(Box.createVerticalStrut(50));

        mainPanel.add(emailField);

        mainPanel.add(Box.createVerticalStrut(5));

        mainPanel.add(emailField);

        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(passwordField);

        mainPanel.add(Box.createVerticalStrut(5));

        mainPanel.add(passwordField);

        mainPanel.add(Box.createVerticalStrut(30));

        mainPanel.add(loginButton);

        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(registerButton);

        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel);
    }

    // Getters
    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getText());
    }

    // Eventos
    public void setOnLogin(Runnable action) {
        loginButton.addActionListener(
                e -> action.run()
        );
    }

    public void setOnRegister(Runnable action) {
        registerButton.addActionListener(
                e -> action.run()
        );
    }
}