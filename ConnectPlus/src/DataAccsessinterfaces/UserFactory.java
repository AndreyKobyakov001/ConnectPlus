package DataAccsessinterfaces;

import Entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserFactory {

    public User create(String name, String password, String hm, int elo, String games) {
        User user = new User(name, password, password);
        user.setEloRating(elo);
        user.setgames(games);
        return user;
    }
}
