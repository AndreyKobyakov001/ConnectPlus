package use_case.Home;

import use_case.logged_in.LoggedInOutputBoundary;

public class HomeInteractor implements HomeInputBoundary{

    final HomeOutputBoundary homePresenter;


    public HomeInteractor(HomeOutputBoundary homePresenter) {
            this.homePresenter = homePresenter;
        }
    @Override
    public void play() {
        homePresenter.play();
        }
    }

