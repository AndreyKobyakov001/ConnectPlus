package use_case.GameBuild;

public interface GameBuildOutputBoundary {
    public void back();


    public void displayPVP(GameBuildOutputData gameBuildOutputData);

    public void displayPVB(GameBuildOutputData gameBuildOutputData);
    public void displayError(String invalidBotDifficulty);
}
