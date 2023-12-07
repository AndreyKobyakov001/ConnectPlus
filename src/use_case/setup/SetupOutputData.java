package use_case.setup;

public class SetupOutputData {
    // setters and getters for the board state, bot difficulty, and win condition
    private final int height;
    private final int width;
    private final int botDifficulty;
    private final String player1;
    private final String player2;

    private char[][] boardState;
    private int isWon;

    private int ELODelta;

    private boolean isPlayer1Turn = true; // true if it is the player's turn

    public SetupOutputData(int height, int width,
                           int botDifficulty,
                           String player1, String player2){
        this.height = height;
        this.width = width;
        this.botDifficulty = botDifficulty;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void setELODelta(int ELODelta) {
        this.ELODelta = ELODelta;
    }

    public int getELODelta() {
        return ELODelta;
    }

    public void setPlayer1Turn(boolean isPlayer1Turn) {
        this.isPlayer1Turn = isPlayer1Turn;
    }

    public boolean getPlayer1Turn() {
        return isPlayer1Turn;
    }
    public void setBoardState(char[][] boardState) {
        this.boardState = boardState;
    }

    public char[][] getBoardState() {
        return boardState;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getBotDifficulty() {
        return botDifficulty;
    }

    public int getIsWon() {
        return isWon;
    }

    public void setisWon(int isWon) {
        this.isWon = isWon;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }
}
