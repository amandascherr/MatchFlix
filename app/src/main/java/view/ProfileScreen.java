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
import view.components.ProfileAvatar;

public class ProfileScreen extends JFrame {

    private User user;
    private ProfileAvatar profileAvatar;
    private Runnable onImageUpdated;

    public ProfileScreen(User user, Runnable onImageUpdated) {
        this.user = user;
        this.onImageUpdated = onImageUpdated;

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

        // Foto
        profileAvatar = new ProfileAvatar(user.getProfileImage(), 120);
        profileAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileAvatar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        profileAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                uploadImage();
            }
        });

        // Texto
        JLabel nameLabel = new JLabel(user.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel emailLabel = new JLabel(user.getEmail());
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        root.add(profileAvatar);
        root.add(Box.createVerticalStrut(15));
        root.add(nameLabel);
        root.add(emailLabel);

        add(root);
    }

    private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            ImageIcon originalIcon = new ImageIcon(file.getAbsolutePath());
            
            Image scaledImage = originalIcon.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            user.setProfileImage(scaledIcon);
            profileAvatar.setIcon(scaledIcon);
            
            if (onImageUpdated != null) {
                onImageUpdated.run();
            }
        }
    }
}