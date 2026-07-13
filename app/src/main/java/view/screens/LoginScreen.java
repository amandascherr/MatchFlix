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

import view.Theme;
import view.components.FormField;
import view.components.button.LinkButton;
import view.components.button.PrimaryButton;

public class LoginScreen extends JFrame {

    private FormField emailField;
    private FormField passwordField;

    private JButton loginButton;
    private JButton registerButton;

    public LoginScreen() {

        setTitle("MatchFlix");

        setSize(960, 700);
        setMinimumSize(new Dimension(520, 620));

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buildUI();
    }

    private void buildUI() {

        // Fundo escuro que centraliza o cartão em qualquer tamanho de janela
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(Theme.BG);

        // Cartão de login
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
        card.setBorder(new EmptyBorder(48, 56, 48, 56));

        // Logo
        JLabel titleLabel = Theme.logo(38);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Campos
        emailField = new FormField("E-mail", false);
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordField = new FormField("Senha", true);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão de login
        loginButton = new PrimaryButton("Entrar");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

        // Link de cadastro
        registerButton = new LinkButton("Não tem login? Faça seu cadastro");
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Composição
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(40));

        card.add(emailField);
        card.add(Box.createVerticalStrut(18));

        card.add(passwordField);
        card.add(Box.createVerticalStrut(32));

        card.add(loginButton);
        card.add(Box.createVerticalStrut(18));

        card.add(registerButton);

        background.add(card);

        setContentPane(background);
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
