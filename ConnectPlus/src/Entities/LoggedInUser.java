package Entities;

import javax.annotation.Nullable;

public class LoggedInUser {
    private static User loggedInUser;

    private LoggedInUser() {
    }

    public static Nullable getLoggedInUser() {
        return (Nullable) loggedInUser;
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
