package use_case.logged_in;

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
