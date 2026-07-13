package view.screens;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dto.UserProfileDTO;
import model.Movie;
import model.User;
import service.Services;
import service.dataManager.DataManager;
import util.Theme;
import util.Loader;
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
        setSize(580, 680);
        setMinimumSize(new Dimension(520, 480));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI();
    }

    private void buildUI() {

        JPanel root = new JPanel();
        root.setBackground(Theme.BG);
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));

        // Foto de perfil
        profileAvatar = new ProfileAvatar(user.getProfileImage(), 120);
        profileAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileAvatar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profileAvatar.setToolTipText("Clique para alterar a foto");

        profileAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                uploadImage();
            }
        });

        // Infos
        JLabel nameLabel = Theme.title(user.getName(), 20);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel emailLabel = new JLabel(user.getEmail());
        emailLabel.setFont(Theme.font(Font.PLAIN, 14));
        emailLabel.setForeground(Theme.TEXT_SECONDARY);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel hintLabel = Theme.muted("Clique na foto para alterá-la", 12);
        hintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        root.add(profileAvatar);
        root.add(Box.createVerticalStrut(8));
        root.add(hintLabel);
        root.add(Box.createVerticalStrut(16));
        root.add(nameLabel);
        root.add(Box.createVerticalStrut(4));
        root.add(emailLabel);

        // Filmes curtidos
        root.add(Box.createVerticalStrut(36));

        JLabel likedMoviesTitle = Theme.title("Filmes Curtidos", 17);
        likedMoviesTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        root.add(likedMoviesTitle);
        root.add(Box.createVerticalStrut(16));

        ArrayList<Movie> likedMovies = user.getLikedMovies();
        int limitMovies = Math.max(likedMovies.size() - 15, 0);

        MoviesPanel moviesPanel = new MoviesPanel(likedMovies.subList(limitMovies, likedMovies.size()));
        moviesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        root.add(moviesPanel);

        JScrollPane scrollPane = new JScrollPane(root);

        scrollPane.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        Theme.styleScrollPane(scrollPane);

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

                user.setProfileImage(Loader.loadProfileImage(copy.getPath()));

                profileAvatar.setIcon(user.getProfileImage());
                if (onImageUpdated != null) {
                    onImageUpdated.run();
                }

                UserProfileDTO userData = manager.readData("user", user.getEmail(), UserProfileDTO.class).get(0);

                if (userData != null) {
                    UserProfileDTO userDTO = new UserProfileDTO(
                        userData.name(),
                        userData.email(),
                        userData.password(),
                        copy.getPath(),
                        userData.likedMovies(),
                        userData.groups(),
                        userData.notifications()
                    );

                    manager.createData("user", user.getEmail(), userDTO);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
