import java.util.regex.*;

public class User {
    private String username;
    private String password;
    private int wins;
    private int losses;
    private int eloRating;

    public User(String username, String password, String confirmPassword) throws IllegalArgumentException {
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("Username is invalid or already exists.");
        }

        if (!isValidPassword(password, confirmPassword)) {
            throw new IllegalArgumentException("Password requirements not met.");
        }

        this.username = username;
        this.password = password;
        this.wins = 0;
        this.losses = 0;
        this.eloRating = 1000; // Initial ELO rating, you can set it to any default value you prefer.
    }

    // Getters and setters for username, password, wins, losses, and eloRating
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWins() {
        return wins;
    }

    public void incrementWins() {
        this.wins++;
    }

    public int getLosses() {
        return losses;
    }

    public void incrementLosses() {
        this.losses++;
    }

    public int getEloRating() {
        return eloRating;
    }

    public void setEloRating(int eloRating) {
        this.eloRating = eloRating;
    }

    private boolean isValidUsername(String username) {
        // Implement logic to check if the username is unique (e.g., check against a database)
        // Return true if unique, false otherwise.
        return true; // Placeholder, you need to implement this logic.
    }

    // Method to validate password requirements
    private boolean isValidPassword(String password, String confirmPassword) {
        // Ensure password and confirmPassword match
        if (!password.equals(confirmPassword)) {
            return false;
        }

        // Check if password meets the requirements
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", wins=" + wins +
                ", losses=" + losses +
                ", eloRating=" + eloRating +
                '}';
    }
}
