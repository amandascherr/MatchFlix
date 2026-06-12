package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import view.components.FormField;

public class RegisterScreen extends JFrame {

    private FormField nameField;
    private FormField emailField;

    private FormField passwordField;
    private FormField confirmPasswordField;

    private JButton registerButton;
    private JButton loginButton;

    public RegisterScreen() {

        setTitle("MatchFlix");

        setSize(500, 750);

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
        nameField = new FormField("Nome", false);
        emailField = new FormField("Email", false);
        passwordField = new FormField("Senha", true);
        confirmPasswordField = new FormField("Confirmar senha", true);

        // Botão Cadastro
        registerButton = new JButton("Cadastrar");
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão Login
        loginButton = new JButton("Já possui uma conta? Faça login");
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setForeground(Color.BLUE);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Composição
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(40));

        // Nome
        mainPanel.add(nameField);
        mainPanel.add(Box.createVerticalStrut(15));

        // Email
        mainPanel.add(emailField);
        mainPanel.add(Box.createVerticalStrut(15));

        // Senha
        mainPanel.add(passwordField);
        mainPanel.add(Box.createVerticalStrut(15));

        // Confirmar senha
        mainPanel.add(confirmPasswordField);
        mainPanel.add(Box.createVerticalStrut(30));

        // Botão cadastrar
        mainPanel.add(registerButton);
        mainPanel.add(Box.createVerticalStrut(20));

        // Botão login
        mainPanel.add(loginButton);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel);
    }

    // Getters
    public String getNameInput() {
        return nameField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getText());
    }

    public String getConfirmPassword() {
        return new String(confirmPasswordField.getText());
    }

    // Eventos
    public void setOnRegister(Runnable action) {
        registerButton.addActionListener(
                e -> action.run()
        );
    }

    public void setOnLogin(Runnable action) {
        loginButton.addActionListener(
                e -> action.run()
        );
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}