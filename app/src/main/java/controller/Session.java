package controller;

import model.User;

public class Session {

    private static User loggedUser;
    public static String logAction = "";

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User user) {
        loggedUser = user;
    }

    public static void logout() {
        loggedUser = null;
    }
}