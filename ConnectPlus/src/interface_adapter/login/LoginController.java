package interface_adapter.login;

import use_case.login.LoginInputBoundary;

public class LoginController {

    final LoginInputBoundary loginUseCaseInteractor;
    public LoginController(LoginInputBoundary loginUseCaseInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
    }


    public void execute() {
        loginUseCaseInteractor.execute();
    }


}
