package view.screens;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import controller.InviteController;
import model.Group;
import model.Movie;
import service.Services;
import service.TMDBService;
import util.Theme;
import view.components.MoviesPanel;
import view.components.ProfileAvatar;
import view.components.button.PrimaryButton;

public class ViewGroupDetailsScreen extends JFrame {

    private final Group group;

    private ProfileAvatar groupAvatar;
    private JButton inviteButton;

    public ViewGroupDetailsScreen(Group group) {

        this.group = group;

        setTitle(group.getName());

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

        // Foto
        groupAvatar = new ProfileAvatar(
                group.getProfileImage(),
                120);

        groupAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);

        groupAvatar.setCursor(
                new Cursor(Cursor.HAND_CURSOR));

        // Nome
        JLabel nameLabel = Theme.title(group.getName(), 22);

        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Participantes
        JLabel membersLabel = new JLabel(group.getNumOfUsers() + " participantes");

        membersLabel.setFont(Theme.font(Font.PLAIN, 14));
        membersLabel.setForeground(Theme.TEXT_SECONDARY);
        membersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão convite
        inviteButton = new PrimaryButton("Convidar usuário");

        inviteButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Quando clicar no botão
        inviteButton.addActionListener(e -> {

            InviteScreen inviteScreen =
                    new InviteScreen(this);

            new InviteController(group, inviteScreen);

            inviteScreen.setVisible(true);

        });

        root.add(groupAvatar);
        root.add(Box.createVerticalStrut(16));

        root.add(nameLabel);
        root.add(Box.createVerticalStrut(4));

        root.add(membersLabel);
        root.add(Box.createVerticalStrut(20));

        root.add(inviteButton);

        root.add(Box.createVerticalStrut(36));

        JLabel matchesTitle = Theme.title("Matches", 17);

        matchesTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        root.add(matchesTitle);

        root.add(Box.createVerticalStrut(16));

        ArrayList<Movie> moviesMatch = new ArrayList<>();
        MoviesPanel moviePanel;

        List<Integer> matches = group.getMatches();
        int limitMatches = Math.max(matches.size() - 15, 0);
        List<Integer> lastMatches = new ArrayList<>(matches.subList(limitMatches, matches.size()));
        java.util.Collections.reverse(lastMatches);

        loadMoviesByAPI(lastMatches, moviesMatch)
        .thenRun(() -> SwingUtilities.invokeLater(() -> {
            MoviesPanel moviesPanel = new MoviesPanel(moviesMatch);
            moviesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            root.add(moviesPanel);

            root.revalidate();
            root.repaint();
        }));        

        JScrollPane scrollPane = new JScrollPane(root);

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Theme.styleScrollPane(scrollPane);

        setContentPane(scrollPane);
    }

    private CompletableFuture<Void> loadMoviesByAPI(List<Integer> likedMoviesIds, ArrayList<Movie> matchList) {
    TMDBService service = Services.getTMDBService();

    List<CompletableFuture<Movie>> futures = likedMoviesIds.stream()
        .map(id -> CompletableFuture.supplyAsync(() -> {
            try {
                return service.getMovieById(id);
            } catch (Exception e) {
                return null; // falha silenciosa, como no original
            }
        }))
        .collect(Collectors.toList());

    return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
        .thenRun(() -> {
            for (CompletableFuture<Movie> f : futures) {
                Movie m = f.join();
                if (m != null) matchList.add(m);
            }
        });
}
}

