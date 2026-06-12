package view.components;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class FormField extends JPanel {

    private final JTextComponent field;

    public FormField(String labelText, boolean isSecret) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(labelText);

        if (isSecret) {
            field = new JPasswordField();

        } else {
            field = new JTextField();
        }

        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        add(label);
        add(field);
    }

    public String getText() {
        return field.getText();
    }

    public void setText(String text) {
        field.setText(text);
    }
}