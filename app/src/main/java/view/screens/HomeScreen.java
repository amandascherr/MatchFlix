package view.screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.CreateGroupController;
import controller.Session;
import model.Movie;
import view.Theme;
import view.components.MovieCard;
import view.components.button.AddGroupButton;
import view.components.button.LikeDislikeButtons;
import view.components.button.NotificationButton;
import view.components.button.ProfileButton;
import view.components.button.ViewGroupsButton;

public class HomeScreen extends JFrame {

    private final MovieCard movieCard;

    private DefaultListModel<String> likedMoviesModel;

    private LikeDislikeButtons buttons;
    private ProfileButton profileButton;

    private Movie currentMovie;

    public HomeScreen() {

        movieCard = new MovieCard();

        setTitle("MatchFlix");

        setSize(1280, 800);
        setMinimumSize(new Dimension(720, 560));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        buildUI();
    }

    // Constrói a Interface Gráfica
    private void buildUI() {

        setLayout(new BorderLayout());
        getContentPane().setBackground(Theme.BG);

        // Barra superior: logo à esquerda, ações à direita
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Theme.BG);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.SURFACE),
                new EmptyBorder(14, 24, 14, 24)));

        topPanel.add(Theme.logo(28), BorderLayout.WEST);

        // Botão de Perfil
        profileButton = new ProfileButton(
            () -> new ProfileScreen(Session.getLoggedUser(), () -> {
                profileButton.setIcon(Session.getLoggedUser().getProfileImage());
            }).setVisible(true),
            Session.getLoggedUser().getProfileImage()
        );

        AddGroupButton addGroupButton = new AddGroupButton(() -> {
                CreateGroupScreen screen = new CreateGroupScreen();

                new CreateGroupController(screen,
                    () -> screen.dispose()
                );

                screen.setVisible(true);
            });

        ViewGroupsButton viewGroupsButton = new ViewGroupsButton(() -> {
                new UserGroupsScreen(Session.getLoggedUser().getGroups()).setVisible(true);});

        NotificationButton notificationButton = new NotificationButton(() -> { new NotificationsScreen().setVisible(true);});

        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        rightButtons.setOpaque(false);

        rightButtons.add(addGroupButton);
        rightButtons.add(viewGroupsButton);
        rightButtons.add(notificationButton);
        rightButtons.add(profileButton);

        topPanel.add(rightButtons, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Centro: o cartão do filme ocupa todo o espaço disponível e os
        // botões ficam ancorados embaixo, sempre visíveis
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Theme.BG);
        centerPanel.setBorder(new EmptyBorder(20, 24, 20, 24));

        centerPanel.add(movieCard, BorderLayout.CENTER);

        buttons = new LikeDislikeButtons();
        buttons.setBorder(new EmptyBorder(16, 0, 0, 0));

        centerPanel.add(buttons, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
    }

    // Getters e Setters

    public void setMovie(Movie movie) {
        currentMovie = movie;

        movieCard.setMovie(movie);
    }

    public Movie getCurrentMovie() {
        return currentMovie;
    }

    // Eventos

    public void setOnLike(Runnable action) {
        buttons.setOnLike(action);
    }

    public void setOnDislike(Runnable action) {
        buttons.setOnDislike(action);
    }
}
