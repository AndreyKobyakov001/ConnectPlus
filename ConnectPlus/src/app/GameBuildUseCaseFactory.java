package app;

import interface_adapter.GameBuild.GameBuildController;
import interface_adapter.GameBuild.GameBuildPresenter;
import interface_adapter.GameBuild.GameBuildViewModel;
import interface_adapter.Setup.SetupViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.GameBuild.GameBuildInputBoundary;
import use_case.GameBuild.GameBuildInteractor;
import use_case.GameBuild.GameBuildOutputBoundary;
import view.GameBuildView;

import javax.swing.*;

public class GameBuildUseCaseFactory {

    private GameBuildUseCaseFactory() {}

    public static GameBuildView create(GameBuildViewModel gameBuildViewModel, LoggedInViewModel loggedInViewModel, ViewManagerModel viewManagerModel, SetupViewModel setupViewModel ) {
        try{
            GameBuildController gameBuildController = createGameBuildUseCase(gameBuildViewModel, loggedInViewModel, viewManagerModel, setupViewModel);
            return new GameBuildView(gameBuildController, gameBuildViewModel);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can't open GameBuild");
        } return null;
    }

    private static GameBuildController createGameBuildUseCase(GameBuildViewModel gameBuildViewModel, LoggedInViewModel loggedInViewModel, ViewManagerModel viewManagerModel, SetupViewModel setupViewModel) {
        GameBuildOutputBoundary gameBuildOutputBoundary = new GameBuildPresenter(gameBuildViewModel, loggedInViewModel, viewManagerModel, setupViewModel);
        GameBuildInputBoundary gameBuildInteractor = new GameBuildInteractor(gameBuildOutputBoundary);
        return new GameBuildController(gameBuildInteractor);
    }



}
