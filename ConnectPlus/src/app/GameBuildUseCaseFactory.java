package app;

import interface_adapter.GameBuild.GameBuildController;
import interface_adapter.GameBuild.GameBuildPresenter;
import interface_adapter.GameBuild.GameBuildViewModel;
import interface_adapter.Home.EndViewModel;
import interface_adapter.Setup.SetupController;
import interface_adapter.Setup.SetupPresenter;
import interface_adapter.Setup.SetupViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.GameBuild.GameBuildInputBoundary;
import use_case.GameBuild.GameBuildInteractor;
import use_case.GameBuild.GameBuildOutputBoundary;
import use_case.setup.SetUpInteractor;
import use_case.setup.SetupInputBoundary;
import use_case.setup.SetupOutputBoundary;
import view.GameBuildView;
import view.GameView;

import javax.swing.*;
import javax.swing.text.View;

public class GameBuildUseCaseFactory {

    private GameBuildUseCaseFactory() {}

    public static JPanel[] create(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel, GameBuildViewModel gameBuildViewModel, SetupViewModel setupViewModel, EndViewModel endViewModel) {
        try {
            GameBuildController gameBuildController = createGameBuildUseCase(gameBuildViewModel, loggedInViewModel, viewManagerModel, setupViewModel);
            SetupController setupController = createSetupUseCase(setupViewModel, viewManagerModel, endViewModel);
            GameBuildView gameBuildView = new GameBuildView(gameBuildController, gameBuildViewModel, setupController);
            GameView gameView = new GameView(setupController, setupViewModel);
            return new JPanel[]{gameBuildView, gameView};
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can't open GameBuild");
        } return null;
    }



    private static GameBuildController createGameBuildUseCase(GameBuildViewModel gameBuildViewModel, LoggedInViewModel loggedInViewModel, ViewManagerModel viewManagerModel, SetupViewModel setupViewModel) {
        GameBuildOutputBoundary gameBuildOutputBoundary = new GameBuildPresenter(gameBuildViewModel, loggedInViewModel, viewManagerModel, setupViewModel);
        GameBuildInputBoundary gameBuildInteractor = new GameBuildInteractor(gameBuildOutputBoundary);
        return new GameBuildController(gameBuildInteractor);
    }


    public static SetupController createSetupUseCase(SetupViewModel setupViewModel, ViewManagerModel viewManagerModel, EndViewModel endViewModel) {
        SetupOutputBoundary setupOutputBoundary = new SetupPresenter(viewManagerModel, setupViewModel, endViewModel);
        SetupInputBoundary setupInteractor = new SetUpInteractor(setupOutputBoundary);
        return new SetupController(setupInteractor);
    }



}
