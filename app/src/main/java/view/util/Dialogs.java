package view.util;

import java.awt.Component;

import javax.swing.JOptionPane;

public final class Dialogs {

    private Dialogs() {}

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static void showMessage(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message);
    }
}