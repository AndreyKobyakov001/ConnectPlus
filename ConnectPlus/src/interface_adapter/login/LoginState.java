package interface_adapter.login;

public class LoginState {
    private String loginError = null;

    public LoginState(LoginState copy) {
        loginError = copy.loginError;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public LoginState() {}

    public String getLoginError() {
        return loginError;
    }
    //set login error
    public void setLoginError(String loginError) {
        this.loginError = loginError;
    }
}
