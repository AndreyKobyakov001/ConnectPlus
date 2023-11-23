package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class User {
    private String username;
    private String password;
    private String email;
    private int wins;
    private int losses;
    private int eloRating;

    public List<List<Integer>> games;


    public User(String username, String password, int wins, int losses, int elo) {
        this.username = username;
        this.password = password;
        this.wins = wins;
        this.losses = losses;
        this.eloRating = elo;
    }
    public User(String username, String password, String confirmPassword) throws IllegalArgumentException {
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("Username is invalid or already exists.");
        }

        if (!isValidPassword(password, confirmPassword)) {
            throw new IllegalArgumentException("Password requirements not met.");
        }

        this.games = new ArrayList<>();
        this.username = username;
        this.password = password;
        this.wins = 0;
        this.losses = 0;
        this.eloRating = 1000; // Initial ELO rating, you can set it to any default value you prefer.
    }

    public User(String id) {
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
        return "Entities.User{" +
                "username='" + username + '\'' +
                ", wins=" + wins +
                ", losses=" + losses +
                ", eloRating=" + eloRating +
                '}';
    }

    public void setgames(String games) {
        // need to create this method in order to set the games the user allready has according to the
        // database which gives a string of a list of a list of games
    }

    public void addgame(){
        // need to create this method to add a game after it has ended to the users playing
    }

    public List<List<Integer>> getgames(){
        return this.games;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getemail(){
        return this.email;
    }
}
