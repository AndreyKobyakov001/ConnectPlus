package app;

import Entities.Game;
import interface_adapter.GameBuild.GameBuildViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInController;
import interface_adapter.logged_in.LoggedInPresenter;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.logged_in.LoggedInInputBoundary;
import use_case.logged_in.LoggedInInteractor;
import use_case.logged_in.LoggedInOutputBoundary;
import view.LoggedInView;

public class LoggedInUseCaseFactory {

        private LoggedInUseCaseFactory() {}

        public static LoggedInView create(ViewManagerModel viewManagerModel,
                                          LoggedInViewModel loggedInViewModel,
                                          GameBuildViewModel gameBuildViewModel) {
            try{
                LoggedInController loggedInController = createLoggedInUseCase(viewManagerModel, gameBuildViewModel);
                return new LoggedInView(loggedInViewModel, loggedInController);

            } catch (Exception e) {
                System.out.println("Can't open LoggedIn");
            } return null;
        }

        private static LoggedInController createLoggedInUseCase(ViewManagerModel viewManagerModel,
                                                                GameBuildViewModel gameBuildViewModel) {
            LoggedInOutputBoundary loggedInOutputBoundary = new LoggedInPresenter(viewManagerModel, gameBuildViewModel);
            LoggedInInputBoundary loggedInInteractor = new LoggedInInteractor(loggedInOutputBoundary);
            return new LoggedInController(loggedInInteractor);
        }
}
