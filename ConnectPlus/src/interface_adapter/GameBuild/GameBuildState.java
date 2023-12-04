package interface_adapter.GameBuild;

public class GameBuildState {
    private String gameBuildError;
    private boolean isPVP;

    private int height;
    private int width;
    private int winCondition;
    private int botDiff;
    private boolean startGame = false;

    public GameBuildState(GameBuildState copy) {
        gameBuildError = copy.gameBuildError;
        isPVP = copy.isPVP;
        height = copy.height;
        width = copy.width;
        winCondition = copy.winCondition;
        botDiff = copy.botDiff;
        startGame = copy.startGame;
    }
    public void setStartGame(boolean startGame) {
        this.startGame = startGame;
    }

    public boolean getStartGame() {
        return startGame;
    }
    // Because of the previous copy constructor, the default constructor must be explicit.
    public GameBuildState() {}

    public String getGameBuildError() {
        return gameBuildError;
    }
    //set game build error
    public void setGameBuildError(String gameBuildError) {
        this.gameBuildError = gameBuildError;
    }

    public boolean getIsPVP() {
        return isPVP;
    }
    //set isPVP
    public void setIsPVP(boolean isPVP) {
        this.isPVP = isPVP;
    }

    public int getBotDiff() {
        return botDiff;
    }
    //set botDiff
    public void setBotDiff(int botDiff) {
        this.botDiff = botDiff;
    }

    public int getHeight() {
        return height;
    }
    //set height
    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }
    //set width
    public void setWidth(int width) {
        this.width = width;
    }


    public int getWinCondition() {
        return winCondition;
    }
    //set winCondition
    public void setWinCondition(int winCondition) {
        this.winCondition = winCondition;
    }

    public void setPvP(boolean selected) {
        this.isPVP = selected;
    }

    public void setBotDifficulty(int value) {
        this.botDiff = value;
    }

    public boolean isPvP() {
        return isPVP;
    }
}

