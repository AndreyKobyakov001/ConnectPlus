package use_case.login;



import DataAccsessinterfaces.FileDAO;
import DataAccsessinterfaces.UserFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class loginTest {
    static LoginUserDataAccessInterface userDataAccessObject;


    public static void main(String[] args) throws Exception {
        UserFactory userFactory = new UserFactory();
        userDataAccessObject = new FileDAO("./user_data.txt", userFactory);
        LoginInteractor loginInteractor = new LoginInteractor(userDataAccessObject, null);
        loginInteractor.execute();
    }

    //TODO: test log in vs sign up
    //TODO: test loggedIn



}
