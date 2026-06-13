package view;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.User;

public class ProfileScreen extends JFrame {

    private User user;
    private JLabel imageLabel;

    public ProfileScreen(User user) {
        this.user = user;

        setTitle("Perfil do Usuário");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI();
    }

    private void buildUI() {

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ===== FOTO =====
        imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        updateImage(user.getProfileImage());

        imageLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        imageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                uploadImage();
            }
        });

        // ===== TEXTO =====
        JLabel nameLabel = new JLabel(user.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel emailLabel = new JLabel(user.getEmail());
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        root.add(imageLabel);
        root.add(Box.createVerticalStrut(10));
        root.add(nameLabel);
        root.add(emailLabel);

        add(root);
    }

    private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            ImageIcon icon = new ImageIcon(
                    new ImageIcon(file.getAbsolutePath())
                            .getImage()
                            .getScaledInstance(120, 120, Image.SCALE_SMOOTH)
            );

            user.setProfileImage(icon);
            updateImage(icon);
        }
    }

    private void updateImage(ImageIcon icon) {
        if (icon == null) {
            icon = new ImageIcon(
                    new ImageIcon("assets/default.png")
                            .getImage()
                            .getScaledInstance(120, 120, Image.SCALE_SMOOTH)
            );
        }

        imageLabel.setIcon(icon);
    }
}