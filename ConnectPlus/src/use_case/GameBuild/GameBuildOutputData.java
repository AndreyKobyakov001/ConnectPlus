package use_case.GameBuild;

public class GameBuildOutputData {
    //contains the height, width, win condition, and bot difficulty (bot diff is optional)
    //of the game that the user wants to build
    private int height;
    private int width;
    private int winCondition;
    private int botDifficulty;

    public GameBuildOutputData(int height, int width, int winCondition, int botDifficulty) {
        this.height = height;
        this.width = width;
        this.winCondition = winCondition;
        this.botDifficulty = botDifficulty;
    }

    public GameBuildOutputData(int height, int width, int winCondition) {
        this.height = height;
        this.width = width;
        this.winCondition = winCondition;
        this.botDifficulty = -1;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getWinCondition() {
        return winCondition;
    }

    public int getBotDifficulty() {
        return botDifficulty;
    }
}
