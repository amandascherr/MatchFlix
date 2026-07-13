package view.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import view.Theme;

/**
 * Campo de formulário escuro com cantos arredondados e rótulo acima;
 * a borda clareia quando o campo recebe foco.
 */
public class FormField extends JPanel {

    private final JTextComponent field;
    private final JPanel fieldBox;

    public FormField(String labelText, boolean isSecret) {

        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(labelText);
        label.setFont(Theme.font(Font.PLAIN, 13));
        label.setForeground(Theme.TEXT_SECONDARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (isSecret) {
            field = new JPasswordField();
        } else {
            field = new JTextField();
        }

        field.setOpaque(false);
        field.setFont(Theme.font(Font.PLAIN, 15));
        field.setForeground(Theme.TEXT);
        field.setCaretColor(Theme.TEXT);
        field.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 14));
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                repaint();
            }
        });

        fieldBox = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(Theme.FIELD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                g2.setColor(field.hasFocus() ? Theme.TEXT_MUTED : Theme.BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);

                g2.dispose();
            }
        };

        fieldBox.setOpaque(false);
        fieldBox.add(field, BorderLayout.CENTER);
        fieldBox.setPreferredSize(new Dimension(320, 44));
        fieldBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        fieldBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(label);
        add(Box.createVerticalStrut(6));
        add(fieldBox);

        setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));
    }

    public String getText() {
        return field.getText();
    }

    public void setText(String text) {
        field.setText(text);
    }
}
