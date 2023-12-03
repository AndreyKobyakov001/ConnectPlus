package use_case.setup;

import use_case.GameBuild.GameBuildOutputData;

public interface SetupInputBoundary {
    public void startGame(GameBuildOutputData setupInputData);

    public void makeMove(int column, String playerName);
    public void undoMove();
    public void forfeitGame();
}
