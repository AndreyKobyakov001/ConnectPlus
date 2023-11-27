package interface_adapter.logged_in;

import interface_adapter.GameBuild.GameBuildViewModel;
import interface_adapter.ViewManagerModel;
import use_case.logged_in.LoggedInOutputBoundary;

public class LoggedInPresenter implements LoggedInOutputBoundary {
    private ViewManagerModel viewManagerModel;
    private final GameBuildViewModel gameBuildViewModel;


    public LoggedInPresenter(ViewManagerModel viewManagerModel, GameBuildViewModel gameBuildViewModel) {
        this.gameBuildViewModel = gameBuildViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void play() {
        viewManagerModel.setActiveView(gameBuildViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
