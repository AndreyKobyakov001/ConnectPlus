package use_case.setup;

public class SetupInputData {
    private int width;
    private int pieces;
    private boolean mode;
    private int difficulty;
    private int height;
    private String firstPlayer;
    private String otherPlayer;

    //    height, width, pieces, mode, bot difficulty if applicable from input data structure
    //please note that by default, the bot is played - mode is false; true is the player-vs-player mode
    public SetupInputData(int height, int width, int pieces, boolean mode, int difficulty){
        this.height = height;
        this.width = width;
        this.pieces = pieces;
        this.mode = mode;
        this.difficulty = difficulty;
        this.firstPlayer = firstPlayer;
        this.otherPlayer = otherPlayer;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public int getHeight() {
        return this.height;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getWidth() {
        return this.width;
    }
    public void setPieces(int pieces) {
        this.pieces = pieces;
    }
    public int getPieces() {
        return this.pieces;
    }
    public void setMode(boolean mode) {
        this.mode = mode;
    }
    public boolean getMode() {
        return this.mode;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    public int getDifficulty() {
        return this.difficulty;
    }
    public String getFirstPlayer() {
        return this.firstPlayer;
    }
    public String getOtherPlayer() {
        return this.otherPlayer;
    }
}


