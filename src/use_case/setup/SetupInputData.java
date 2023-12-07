package use_case.setup;

public class SetupInputData {
    private String Player;
    private int col;

    //    height, width, pieces, mode, bot difficulty if applicable from input data structure
    //please note that by default, the bot is played - mode is false; true is the player-vs-player mode
    public SetupInputData(){
    }

    public int getCol() {
        return col;
    }
    public void setCol(int col) {
        this.col = col;
    }

    public String getPlayer() {
        return Player;
    }

    public void setPlayer(String Player) {
        this.Player = Player;
    }
}


