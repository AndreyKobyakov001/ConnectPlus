package interface_adapter.Home;

import use_case.Home.HomeInputBoundary;

public class HomeController {

    final HomeInputBoundary homeUseCaseInteractor;
    public HomeController(HomeInputBoundary homeInteractor) {
            this.homeUseCaseInteractor = homeInteractor;
        }

        public void play() {
            homeUseCaseInteractor.play();
        }

        public void restart() {
            homeUseCaseInteractor.restart();
        }
    }

