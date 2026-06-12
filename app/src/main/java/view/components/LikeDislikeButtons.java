package view.components;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class LikeDislikeButtons extends JPanel {

    private final JButton likeButton;
    private final JButton dislikeButton;

    public LikeDislikeButtons() {
        likeButton = new JButton("♥");

        dislikeButton = new JButton("✕");

        configureButtons();

        add(dislikeButton);

        add(Box.createHorizontalStrut(20));

        add(likeButton);
    }

    private void configureButtons() {

        likeButton.setFont(new Font("Arial", Font.BOLD, 24));

        dislikeButton.setFont(new Font("Arial", Font.BOLD, 24));

        likeButton.setPreferredSize(new Dimension(80, 80));

        dislikeButton.setPreferredSize(new Dimension(80, 80));
    }

    public void setOnLike(Runnable action) {
        likeButton.addActionListener(
                e -> action.run()
        );
    }

    public void setOnDislike(Runnable action) {
        dislikeButton.addActionListener(
                e -> action.run()
        );
    }
}