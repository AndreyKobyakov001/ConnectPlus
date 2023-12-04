package use_case.GameBuild;

public class GameBuildInteractor implements GameBuildInputBoundary {
    private final GameBuildOutputBoundary gameBuildOutputBoundary;
    public GameBuildInteractor(GameBuildOutputBoundary gameBuildOutputBoundary){
        this.gameBuildOutputBoundary = gameBuildOutputBoundary;
    }

    //Check if the height is valid (between 3 and 10), width is valid (between 3 and 10),
    //win condition is valid (between 2<= winCond <= min(height - 1, width - 1), and bot difficulty is valid (between 1 and 10 and if they choose PVB).
    //If any of these are invalid, return error so they can retry. Otherwise, return user to play the game. I also want to know which condition fail to return approriate message
    @Override
    public void execute(GameBuildInputData gameBuildInputData) {
        int height = gameBuildInputData.getHeight();
        int width = gameBuildInputData.getWidth();
        int winCondition = gameBuildInputData.getWinCondition();
        int botDifficulty = gameBuildInputData.getBotDifficulty();
        boolean isPVP = gameBuildInputData.isPVP();
        int minHeight = gameBuildInputData.getMinHeight();
        int minWidth = gameBuildInputData.getMinWidth();
        int minWinCond = gameBuildInputData.getMinWinCond();
        int maxWinCond = gameBuildInputData.getMaxWinCond();
        int minBotDiff = gameBuildInputData.getMinBotDiff();
        int maxBotDiff = gameBuildInputData.getMaxBotDiff();
        int maxHeight = gameBuildInputData.getMaxHeight();
        int maxWidth = gameBuildInputData.getMaxWidth();


        if (checkHeight(minHeight, maxHeight, height) &&
                checkWidth(minWidth, maxWidth, width)) {
            if (checkWinCondition(minWinCond, maxWinCond,
                    winCondition, height, width)) {
                if (isPVP) {
                    gameBuildOutputBoundary.displayPVP(new GameBuildOutputData(height,
                            width, winCondition));
                } else {
                    if (checkBotDifficulty(minBotDiff, maxBotDiff, botDifficulty)) {
                        gameBuildOutputBoundary.displayPVB(new GameBuildOutputData(height,
                                width, winCondition, botDifficulty));
                    } else {
                        gameBuildOutputBoundary.
                                displayError("Invalid bot difficulty");
                    }
                }
            } else {
                gameBuildOutputBoundary.
                        displayError("Invalid win condition");
            }
        } else {
            gameBuildOutputBoundary.
                    displayError("Invalid height or width");
        }
    }

    @Override
    public void back() {
        gameBuildOutputBoundary.back();

    }


    public boolean checkHeight(int minHeight, int maxHeight, int height) {
        return height >= minHeight && height <= maxHeight;
    }


    public boolean checkWidth(int minWidth, int maxWidth, int width) {
        return width >= minWidth && width <= maxWidth;
    }


    public boolean checkWinCondition(int minWinCond, int maxWinCond,
                                     int winCondition, int height, int width) {
        if (winCondition >= minWinCond && winCondition <= maxWinCond) {
            return winCondition <= Math.min(height - 1, width - 1 );
        } return false; //This code block is unintuitive af.
    }


    public boolean checkBotDifficulty(int minBotDiff, int maxBotDiff,
                                      int botDifficulty) {
        return botDifficulty >= minBotDiff && botDifficulty <= maxBotDiff;
    }

    // return to main menu if user presses back, warn users of
}
