import groups.User;

public class Session {

    private static User loggedUser;

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