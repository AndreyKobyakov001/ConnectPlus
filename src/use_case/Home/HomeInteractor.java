package use_case.Home;

public class HomeInteractor implements HomeInputBoundary {

    final HomeOutputBoundary homePresenter;


    public HomeInteractor(HomeOutputBoundary homePresenter) {
        this.homePresenter = homePresenter;
    }

    @Override
    public void play() {
        homePresenter.play();
    }


    @Override
    public void restart() {
        homePresenter.restart();
    }



}