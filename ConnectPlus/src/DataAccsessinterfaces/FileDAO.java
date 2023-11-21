package DataAccsessinterfaces;

import Entities.User;
import use_case.login.LoginUserDataAccessInterface;

import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileDAO implements LoginUserDataAccessInterface {
        private final File csvFile;

    private final Map<String, Integer> headers = new LinkedHashMap<>();

    private final Map<String, User> accounts = new HashMap<>();

    private UserFactory userFactory;

    public FileDAO(String csvPath, UserFactory userFactory) throws IOException {
        this.userFactory = userFactory;

        csvFile = new File(csvPath);
        headers.put("username", 0);
//        Username, Password, GamesWon, GamesLost, ELO

        if (csvFile.length() == 0) {
            save();
        } else {

            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                String header = reader.readLine();

                assert header.equals("username,Games Played,elo");

                String row;
                while ((row = reader.readLine()) != null) {
                    String[] col = row.split(",");
                    String username = String.valueOf(col[headers.get("username")]);
                    User user = new User(username);
                    accounts.put(username, user);
                }
            }
        }
    }


    public void saveUser(User user) {
        accounts.put(user.getUsername(), user);
        this.save();
    }


    public String getUsers(){
        return accounts.keySet().toString();
    }


    public User getUser(String username) {
        return accounts.get(username);
    }

    private void save() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(csvFile));
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (User user : accounts.values()) {
                String line = String.format("%s", user.getUsername());
                writer.write(line);
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    public boolean existsByName(String identifier) {
        return accounts.containsKey(identifier);
    }

}

