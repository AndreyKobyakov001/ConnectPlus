package use_case.logged_in;

import interface_adapter.GameBuild.GameBuildViewModel;
import interface_adapter.ViewManagerModel;

public class LoggedInInteractor implements LoggedInInputBoundary{
    final LoggedInOutputBoundary loggedInPresenter;


    public LoggedInInteractor(LoggedInOutputBoundary loggedInPresenter) {
        this.loggedInPresenter = loggedInPresenter;
    }

    @Override
    public void play() {
        loggedInPresenter.play();
    }
}
