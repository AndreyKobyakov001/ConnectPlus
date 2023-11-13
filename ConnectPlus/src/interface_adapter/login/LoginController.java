//package interface_adapter.login;
//
//import Entities.User;
//import use_case.login.LoginInputBoundary;
//import use_case.login.LoginInteractor;
//
//
//public class LoginController {
//    private final LoginInputBoundary loginInteractor;
//    public LoginController() {
//        String appId = "820899762858583";
//        String redirectUri = "http://localhost:8080/facebook/callback";
//        String clientSecret = "http://localhost:8080/facebook/callback";
//        this.loginInteractor = new LoginInteractor(
//                "your-app-id",
//                "your-app-secret",
//                "http://localhost:8080/facebook/callback");
//
//    }
//
//    public LoginController(LoginInputBoundary loginInteractor) {
//        this.loginInteractor = loginInteractor;
//    }
//
//    public void execute() {
//        // TODO: login
//    }
//
//}
