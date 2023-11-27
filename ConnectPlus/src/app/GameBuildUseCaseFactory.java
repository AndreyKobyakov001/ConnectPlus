package app;

import interface_adapter.GameBuild.GameBuildController;
import interface_adapter.GameBuild.GameBuildPresenter;
import interface_adapter.GameBuild.GameBuildViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.GameBuild.GameBuildInputBoundary;
import use_case.GameBuild.GameBuildInteractor;
import use_case.GameBuild.GameBuildOutputBoundary;
import view.GameBuildView;

import javax.swing.*;

public class GameBuildUseCaseFactory {

    private GameBuildUseCaseFactory() {}

    public static GameBuildView create(GameBuildViewModel gameBuildViewModel) {
        try{
            GameBuildController gameBuildController = createGameBuildUseCase(gameBuildViewModel, new LoggedInViewModel(), new ViewManagerModel());
            return new GameBuildView(gameBuildController, gameBuildViewModel);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can't open GameBuild");
        } return null;
    }

    private static GameBuildController createGameBuildUseCase(GameBuildViewModel gameBuildViewModel, LoggedInViewModel loggedInViewModel, ViewManagerModel viewManagerModel) {
        GameBuildOutputBoundary gameBuildOutputBoundary = new GameBuildPresenter(gameBuildViewModel, loggedInViewModel, viewManagerModel);
        GameBuildInputBoundary gameBuildInteractor = new GameBuildInteractor(gameBuildOutputBoundary);
        return new GameBuildController(gameBuildInteractor);
    }



}
