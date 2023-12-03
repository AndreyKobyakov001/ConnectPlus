package app;

import interface_adapter.Setup.SetupController;
import interface_adapter.Setup.SetupPresenter;
import interface_adapter.Setup.SetupViewModel;
import interface_adapter.ViewManagerModel;
import use_case.setup.SetUpInteractor;
import use_case.setup.SetupInputBoundary;
import use_case.setup.SetupOutputBoundary;
import view.GameView;

public class GameUseCaseFactory {

    private GameUseCaseFactory() {}

    public static GameView create(SetupViewModel setupViewModel, ViewManagerModel viewManagerModel){
        try{
            SetupController setupController = createSetupUseCase(setupViewModel, viewManagerModel);
            return new GameView(setupController, setupViewModel);
        } catch (Exception e) {
            System.out.println("Can't open Game");
        } return null;
    }

    private static SetupController createSetupUseCase(SetupViewModel setupViewModel, ViewManagerModel viewManagerModel) {
        SetupOutputBoundary setupOutputBoundary = new SetupPresenter(viewManagerModel, setupViewModel);
        SetupInputBoundary setupInteractor = new SetUpInteractor(setupOutputBoundary);
        return new SetupController(setupInteractor);
    }
}
