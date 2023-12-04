package interface_adapter.Home;

import interface_adapter.GameBuild.GameBuildViewModel;
import interface_adapter.ViewManagerModel;
import use_case.Home.HomeOutputBoundary;

public class HomePresenter implements HomeOutputBoundary {

        private ViewManagerModel viewManagerModel;
        private final GameBuildViewModel gameBuildViewModel;


    public HomePresenter(ViewManagerModel viewManagerModel, GameBuildViewModel gameBuildViewModel) {
            this.gameBuildViewModel = gameBuildViewModel;
            this.viewManagerModel = viewManagerModel;}
    @Override
    public void play() {
        viewManagerModel.setActiveView(gameBuildViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
        }
    }
