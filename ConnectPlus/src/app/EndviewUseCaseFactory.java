package app;

import interface_adapter.GameBuild.GameBuildViewModel;
import interface_adapter.Home.EndViewModel;
import interface_adapter.Home.HomeController;
import interface_adapter.Home.HomePresenter;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInController;
import interface_adapter.logged_in.LoggedInPresenter;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.Home.HomeInputBoundary;
import use_case.Home.HomeInteractor;
import use_case.Home.HomeOutputBoundary;
import use_case.logged_in.LoggedInInputBoundary;
import use_case.logged_in.LoggedInInteractor;
import use_case.logged_in.LoggedInOutputBoundary;
import view.EndView;
import view.LoggedInView;

public class EndviewUseCaseFactory {

        private EndviewUseCaseFactory() {}

        public static EndView create(ViewManagerModel viewManagerModel,
                                          EndViewModel endViewModel,
                                          GameBuildViewModel gameBuildViewModel) {
            try{
                HomeController homeController = createhomeUseCase(viewManagerModel, gameBuildViewModel);
                return new EndView(endViewModel, homeController);

            } catch (Exception e) {
                System.out.println("Can't open EndView");
            } return null;
        }

        private static HomeController createhomeUseCase(ViewManagerModel viewManagerModel,
                                                                GameBuildViewModel gameBuildViewModel) {
            HomeOutputBoundary homeOutputBoundary = new HomePresenter(viewManagerModel, gameBuildViewModel);
            HomeInputBoundary homeInteractor = new HomeInteractor(homeOutputBoundary);
            return new HomeController(homeInteractor);
        }
    }


