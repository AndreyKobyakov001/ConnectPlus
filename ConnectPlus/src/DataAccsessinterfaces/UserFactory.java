package DataAccsessinterfaces;

import Entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserFactory {

    public User create(String id, String name, int wins, int losses, int elo) {
        return new User(id, name, wins, losses, elo);
    }
}
