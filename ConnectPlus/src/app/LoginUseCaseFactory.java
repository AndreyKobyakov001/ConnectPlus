package app;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import DataAccsessinterfaces.FileDAO;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import use_case.login.LoginOutputBoundary;
import view.LoginView;

import javax.swing.*;
import java.io.IOException;
public class LoginUseCaseFactory {
    private LoginUseCaseFactory() {
    }


    public static LoginView create(ViewManagerModel viewManagerModel,
                                   LoginViewModel loginViewModel,
                                   LoggedInViewModel loggedInViewModel,
                                   FileDAO userDataAccessObject) {
        LoginController loginController = createLoginUseCase(viewManagerModel, loginViewModel, loggedInViewModel, userDataAccessObject);
        return new LoginView(loginViewModel, loginController);

    }

    private static LoginController createLoginUseCase(ViewManagerModel viewManagerModel,
                                                      LoginViewModel loginViewModel,
                                                      LoggedInViewModel loggedInViewModel,
                                                      FileDAO userDataAccessObject) {
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, loggedInViewModel, loginViewModel);

        LoginInputBoundary loginInteractor = new LoginInteractor(userDataAccessObject, loginOutputBoundary);
        return new LoginController(loginInteractor);
    }
}
