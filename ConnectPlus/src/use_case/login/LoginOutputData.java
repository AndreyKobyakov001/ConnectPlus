package use_case.login;

public class LoginOutputData {
    private String name;
    private int wins;
    private int losses;
    private int elo;

    public LoginOutputData(String name, int wins, int losses, int elo) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.elo = elo;
    }
    // getters and setters
    public String getName() {
        return name;
    }
    public int getWins() {
        return wins;
    }
    public int getLosses() {
        return losses;
    }
    public int getElo() {
        return elo;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setWins(int wins) {
        this.wins = wins;
    }
    public void setLosses(int losses) {
        this.losses = losses;
    }
    public void setElo(int elo) {
        this.elo = elo;
    }

}
