package use_case.login;

import Entities.User;

public interface LoginUserDataAccessInterface {
    public User getUser(String username);
    public boolean existsByName(String username);

    public void saveUser(User loggedUser);
}
