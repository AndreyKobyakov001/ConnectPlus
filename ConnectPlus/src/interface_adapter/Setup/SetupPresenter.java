package interface_adapter.Setup;

import interface_adapter.ViewManagerModel;
import use_case.setup.SetupOutputBoundary;
import use_case.setup.SetupOutputData;

public class SetupPresenter implements SetupOutputBoundary {

    private ViewManagerModel viewManagerModel;
    private final SetupViewModel setupViewModel;

    public SetupPresenter(ViewManagerModel viewManagerModel, SetupViewModel setupViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.setupViewModel = setupViewModel;
    }
    @Override
    public void updateBoard(SetupOutputData outputData) {
        SetupState setupState = setupViewModel.getState();
        setupState.setBoardState(outputData.getBoardState());
        setupViewModel.firePropertyChanged();

    }

    @Override
    public void startGame(SetupOutputData outputData) {
        SetupState setupState = setupViewModel.getState();
        setupState.setStartGame(true);
        setupState.setBoardState(outputData.getBoardState());
        setupState.setPlayer1Name(outputData.getPlayer1());
        setupState.setPlayer2Name(outputData.getPlayer2());
        setupState.setIsPlayer1Turn(outputData.getPlayer1Turn());
        setupState.setHeight(outputData.getHeight());
        setupState.setWidth(outputData.getWidth());
        setupState.setBotDiff(outputData.getBotDifficulty());
        viewManagerModel.setActiveView(setupViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
        setupViewModel.firePropertyChanged();
    }

    @Override
    public void endGame(SetupOutputData outputData) {
        SetupState setupState = setupViewModel.getState();
//        //TODO: make sure this is correct
//        setupState.setScore1(outputData.getIsWon() == 1 ? setupState.getScore1() + 1 : setupState.getScore1());
//        setupState.setScore2(outputData.getIsWon() == 2 ? setupState.getScore2() + 1 : setupState.getScore2());

        setupState.setBoardState(outputData.getBoardState());
        setupViewModel.firePropertyChanged();
    }

    @Override
    public void illegalMove(){
        SetupState setupState = setupViewModel.getState();
        setupState.setIllegalMoveError("Illegal Move");
        setupViewModel.firePropertyChanged();
        setupState.setIllegalMoveError(null);
    }
}
