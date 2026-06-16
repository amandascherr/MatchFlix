package view;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.User;
import model.UserProfileDTO;
import service.Services;
import service.dataManager.DataDTO;
import service.dataManager.DataManager;
import view.components.MoviesPanel;
import view.components.ProfileAvatar;

public class ProfileScreen extends JFrame {

    private User user;
    private ProfileAvatar profileAvatar;
    private Runnable onImageUpdated;
    private final DataManager manager = Services.getManager();

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

        // Foto de perfil
        profileAvatar = new ProfileAvatar(user.getProfileImage(), 120);
        profileAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileAvatar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        profileAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                uploadImage();
            }
        });

        // Infos
        JLabel nameLabel = new JLabel(user.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel emailLabel = new JLabel(user.getEmail());
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        root.add(profileAvatar);
        root.add(Box.createVerticalStrut(15));
        root.add(nameLabel);
        root.add(emailLabel);

        // Filmes curtidos
        root.add(Box.createVerticalStrut(30));

        JLabel likedMoviesTitle = new JLabel("Filmes Curtidos");
        likedMoviesTitle.setFont(new Font("Arial", Font.BOLD, 16));
        likedMoviesTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        root.add(likedMoviesTitle);
        root.add(Box.createVerticalStrut(10));

        root.add(new MoviesPanel(user.getLikedMovies()));

        JScrollPane scrollPane = new JScrollPane(root);

        scrollPane.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        setContentPane(scrollPane);
    }

    private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try {
                File destinationFolder = new File("src/main/resources/uploads");
                if (!destinationFolder.exists()) {
                    destinationFolder.mkdirs();
                }

                String format = file.getName().substring(file.getName().lastIndexOf("."));
                String fileName = "profile_" + user.getName().toLowerCase().replaceAll("\\s+", "_") + "_" + System.currentTimeMillis() + format;
                File copy = new File(destinationFolder, fileName);

                Files.copy(file.toPath(), copy.toPath(), StandardCopyOption.REPLACE_EXISTING);

                user.loadProfileImage(copy.getPath());

                profileAvatar.setIcon(user.getProfileImage());
                if (onImageUpdated != null) {
                    onImageUpdated.run();
                }

                UserProfileDTO userData = manager.readData(user.getEmail(), UserProfileDTO.class).get(0);
                
                if (userData != null) {
                    UserProfileDTO userDTO = new UserProfileDTO(
                        userData.name(),
                        userData.email(),
                        userData.password(),
                        copy.getPath(),
                        userData.likedMovies(),
                        userData.groups()
                    );      

                    manager.createData(new DataDTO<>(user.getEmail(), userDTO));
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}