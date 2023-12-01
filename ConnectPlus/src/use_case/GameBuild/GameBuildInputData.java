package use_case.GameBuild;

public class GameBuildInputData {
    private int height;
    private int width;
    private int winCondition;
    private int botDifficulty;
    private boolean isPVP;
    private int minHeight;
    private int minWidth;
    private int minWinCond;
    private int maxWinCond;
    private int minBotDiff;
    private int maxBotDiff;
    private int maxHeight;
    private int maxWidth;


    public GameBuildInputData(int height, int width, int winCondition,
                              int botDifficulty, boolean isPVP, int minHeight,
                              int minWidth, int maxHeight, int maxWidth,
                              int minWinCond, int maxWinCond,
                              int minBotDiff, int maxBotDiff) {
        this.height = height;
        this.width = width;
        this.winCondition = winCondition;
        this.botDifficulty = botDifficulty;
        this.isPVP = isPVP;
        this.minHeight = minHeight;
        this.minWidth = minWidth;
        this.minWinCond = minWinCond;
        this.maxWinCond = maxWinCond;
        this.minBotDiff = minBotDiff;
        this.maxBotDiff = maxBotDiff;
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
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

    public boolean isPVP() {
        return isPVP;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public int getMinWinCond() {
        return minWinCond;
    }

    public int getMaxWinCond() {
        return maxWinCond;
    }

    public int getMinBotDiff() {
        return minBotDiff;
    }

    public int getMaxBotDiff() {
        return maxBotDiff;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

}
