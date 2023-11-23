package interface_adapter.logged_in;

public class LoggedInState {
    private String name = "";
    private int wins = 0;
    private int losses = 0;
    private int elo = 0;

    public LoggedInState(LoggedInState copy) {
        name = copy.name;
        wins = copy.wins;
        losses = copy.losses;
        elo = copy.elo;


    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public LoggedInState() {}

    public String getName() {
        return name;
    }
    public void setName(String username) {
        this.name = name;
    }
    public int getWins() {
        return wins;
    }
    public void setWins(int wins) {
        this.wins = wins;
    }
    public int getLosses() {
        return losses;
    }
    public void setLosses(int losses) {
        this.losses = losses;
    }
    public int getElo() {
        return elo;
    }
    public void setElo(int elo) {
        this.elo = elo;
    }
}
