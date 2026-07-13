package view.screens;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.Theme;
import view.components.FormField;
import view.components.button.LinkButton;
import view.components.button.PrimaryButton;

public class RegisterScreen extends JFrame {

    private FormField nameField;
    private FormField emailField;

    private FormField passwordField;
    private FormField confirmPasswordField;

    private JButton registerButton;
    private JButton loginButton;

    public RegisterScreen() {

        setTitle("MatchFlix");

        setSize(960, 780);
        setMinimumSize(new Dimension(520, 740));

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buildUI();
    }

    private void buildUI() {

        // Fundo escuro que centraliza o cartão em qualquer tamanho de janela
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(Theme.BG);

        // Cartão de cadastro
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Theme.SURFACE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
            }
        };

        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(44, 56, 44, 56));

        // Logo
        JLabel titleLabel = Theme.logo(38);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Campos
        nameField = new FormField("Nome", false);
        emailField = new FormField("E-mail", false);
        passwordField = new FormField("Senha", true);
        confirmPasswordField = new FormField("Confirmar senha", true);

        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão de cadastro
        registerButton = new PrimaryButton("Cadastrar");
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

        // Link de login
        loginButton = new LinkButton("Já possui uma conta? Faça login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Composição
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(36));

        card.add(nameField);
        card.add(Box.createVerticalStrut(16));

        card.add(emailField);
        card.add(Box.createVerticalStrut(16));

        card.add(passwordField);
        card.add(Box.createVerticalStrut(16));

        card.add(confirmPasswordField);
        card.add(Box.createVerticalStrut(32));

        card.add(registerButton);
        card.add(Box.createVerticalStrut(18));

        card.add(loginButton);

        background.add(card);

        setContentPane(background);
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
}
