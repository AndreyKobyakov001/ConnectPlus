package Entities;

public class LoggedInUser {
    private static User loggedInUser;

    private LoggedInUser() {
    }

    public static User getLoggedInUser() {
        if (loggedInUser == null) {
            throw new IllegalStateException("No user is logged in.");
        }
        return loggedInUser;
    }

    public static void logIn(User user) {
        if (loggedInUser != null) {
            throw new IllegalStateException("A user is already logged in.");
        } else {
            loggedInUser = user;
        }
    }

    public static void logOut() {
        loggedInUser = null;
    }
}
