package interface_adapter.GameBuild;

import interface_adapter.Setup.SetupViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.GameBuild.GameBuildOutputBoundary;
import use_case.GameBuild.GameBuildOutputData;
import use_case.login.LoginOutputData;

public class GameBuildPresenter implements GameBuildOutputBoundary {

    private final GameBuildViewModel gameBuildViewModel;

    private final SetupViewModel setupGameModel;

    private final LoggedInViewModel loggedInViewModel;

    private ViewManagerModel viewManagerModel;

    public GameBuildPresenter(GameBuildViewModel gameBuildViewModel, LoggedInViewModel loggedInViewModel, ViewManagerModel viewManagerModel, SetupViewModel setupViewModel) {
        this.gameBuildViewModel = gameBuildViewModel;
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
        this.setupGameModel = setupViewModel;
    }

    @Override
    public void back(){
        this.viewManagerModel.setActiveView(loggedInViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void displayPVP(GameBuildOutputData gameBuildOutputData){
        this.viewManagerModel.setActiveView(setupGameModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override //TODO: same with displayPVP, initialize game using outputdata and set active view to gameview, then let them play
    public void displayPVB(GameBuildOutputData gameBuildOutputData){
        this.viewManagerModel.setActiveView(setupGameModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void displayError(String invalidBotDifficulty){
        GameBuildState gameBuildState = gameBuildViewModel.getState();
        gameBuildState.setGameBuildError(invalidBotDifficulty);
        gameBuildViewModel.firePropertyChanged();
    }
}
