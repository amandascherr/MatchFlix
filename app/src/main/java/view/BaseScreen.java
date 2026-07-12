package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public abstract class BaseScreen extends JFrame {

    public void showError(String message) {

        JOptionPane.showMessageDialog(this, message,"Erro", JOptionPane.ERROR_MESSAGE);
    }

    public void showMessage(String message) {

        JOptionPane.showMessageDialog(this, message);
    }

}