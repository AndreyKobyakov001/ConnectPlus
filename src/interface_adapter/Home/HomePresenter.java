package interface_adapter.Home;

import interface_adapter.Setup.SetupViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.Home.HomeOutputBoundary;

public class HomePresenter implements HomeOutputBoundary {

        private ViewManagerModel viewManagerModel;
        private SetupViewModel setUpViewModel;
        private LoggedInViewModel loggedInViewModel;


    public HomePresenter(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel, SetupViewModel setUpViewModel){
            this.loggedInViewModel = loggedInViewModel;
            this.viewManagerModel = viewManagerModel;
            this.setUpViewModel = setUpViewModel;
    }
    @Override
    public void play() {
        viewManagerModel.setActiveView(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void restart() {
        viewManagerModel.setActiveView(setUpViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }



}
