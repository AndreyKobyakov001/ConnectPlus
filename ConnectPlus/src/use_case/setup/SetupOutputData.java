package use_case.setup;

public class SetupOutputData {
    // setters and getters for the board state, bot difficulty, and win condition
    private final int height;
    private final int width;
    private final int winCondition;
    private final int botDifficulty;

    private char[][] boardState;
    private boolean wonOrLost;

    public SetupOutputData(int height, int width, int winCondition, int botDifficulty, boolean wonOrLost) {
        this.wonOrLost = wonOrLost;
        this.height = height;
        this.width = width;
        this.winCondition = winCondition;
        this.botDifficulty = botDifficulty;
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

    public boolean getWonOrLost() {
        return wonOrLost;
    }

    public void setWonOrLost(boolean wonOrLost) {
        this.wonOrLost = wonOrLost;
    }
}
