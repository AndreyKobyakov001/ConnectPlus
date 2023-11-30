package use_case.setup;

public interface SetupOutputBoundary {
    public void updateBoard(SetupOutputData outputData);
    
    public void startGame(SetupOutputData outputData);


    public void endGame(SetupOutputData outputData);
}
