package Entities;

import Entities.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class Main {
    private static final String USER_DATA_FILE = "ConnectPlus\\user_data.txt"; // Text file to store usernames and hashed passwords
    private static final String SETTINGS_FILE = "ConnectPlus\\settings.txt"; // Text file to store usernames and hashed passwords
    //this stupidly long mess was necessary

    //make main() prettier pls :)
    static Scanner scanner = new Scanner(System.in);
    static boolean signedIn = false;
    static String currentUser = "";

    public static void main(String[] args) {
//        int a = 0;
        while (true) {
            if (!signedIn) {
                handleNotSignedIn();
            }
            else {
//                handleSignedIn();
//                a += 1;
//                System.out.println(a);
                System.out.println("1. View statistics");
                System.out.println("2. Play a game");
                System.out.println("3. Sign out");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        displayUserStatistics(currentUser);
                        break;
                    case "2":
                        playGame(currentUser, scanner);
                        break;
                    case "3":
                        signedIn = false;
                        currentUser = "";
                        System.out.println("Signed out.");
                        break;
                    default:
                        System.out.println("Try again. Please enter 1, 2, or 3.");
                        break;
                }
            }
        }
    }

    static void handleNotSignedIn() {
        System.out.println("Select an option:");
        System.out.println("1. Create a new account");
        System.out.println("2. Sign in");
        System.out.println("3. Exit");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                createAccount(scanner);
                break;
            case "2":
                currentUser = signIn(scanner);
                if (!currentUser.isEmpty()) {
                    signedIn = true;
                    System.out.println("Sign-in successful!");
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                }
                break;
            case "3":
                System.out.println("Goodbye!");
                scanner.close();
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                break;
        }
    }

//    static void handleSignedIn() {
//        System.out.println("1. View statistics");
//        System.out.println("2. Play a game");
//        System.out.println("3. Sign out");
//
//        String choice = scanner.nextLine();
//
//        switch (choice) {
//            case "1":
//                displayUserStatistics(currentUser);
//                break;
//            case "2":
//                playGame(currentUser, scanner);
//                break;
//            case "3":
//                signedIn = false;
//                currentUser = "";
//                System.out.println("Signed out.");
//                break;
//            default:
//                System.out.println("Try again. Please enter 1, 2, or 3.");
//                break;
//        }
//    }
    private static void displayUserStatistics(String username) {
        // You can implement the logic to retrieve and display user statistics here.
        // For now, use a placeholder.
        System.out.println("\n" + "*****************************************\n" + "\nStatistics for user: " + username);
        System.out.println("Games won: 1");
        System.out.println("Games lost: 0" + "\n" + "\n*****************************************\n");
    }

    private static void createAccount(Scanner scanner) {
        System.out.println("Enter a new username:");
        String username = scanner.nextLine();


        if (isUsernameExists(username)) {
            System.out.println("This account already exists. Would you like to sign in? (y/n)");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                String currentUser = signIn(scanner);
                if (!currentUser.isEmpty()) {
                    System.out.println("Sign-in successful!");
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                }
            } else {
                // Restart the account creation process
                createAccount(scanner);
            }
        } else {
            System.out.println("Enter a password (password must contain a lower-case letter, an upper-case letter, " +
                    "and a number. Passwords must be at least 8 characters long):");
            String password = scanner.next();


            System.out.println("Confirm password:");

            String confirmPassword = scanner.next();

            if (password.equals(confirmPassword)) {
                try {
                    User newUser = new User(username, password, confirmPassword);


                    // Save the new username and hashed password to the local text file
                    saveUserDataToFile(newUser.getUsername(), newUser.getPassword());


                    System.out.println("Entities.User account created successfully.");
//                    System.out.println(newUser);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("Passwords do not match. Please try again.");
                // Restart the account creation process
                createAccount(scanner);
            }
        }
    }


    public static boolean isUsernameExists(String username) {
        try {
            File file = new File(USER_DATA_FILE);
            Scanner fileScanner = new Scanner(file);


            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] userData = line.split("■");
                if (userData[0].equals(username)) {
                    fileScanner.close();
                    return true;
                }
            }


            fileScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    private static String signIn(Scanner scanner) {
        System.out.println("Enter your username:");
        String username = scanner.nextLine();


        System.out.println("Enter your password:");
        String password = (scanner.next());
//        System.out.println(password);


        if (authenticateUser(username, password)) {
            return username;
        }
        return "";
    }

//HELPER METHOD - possibly move?
    private static boolean authenticateUser(String username, String password) {
        try {
            File file = new File(USER_DATA_FILE);
            Scanner fileScanner = new Scanner(file);


            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] userData = line.split("■");
                if (userData[0].equals(username) && userData[1].equals(password)) {
                    return true;
                }
            }


            fileScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }




//MOVE ELO Calculator - if anyone figures out where!
    public static int eloCalculator(int eloDiff) {
        //ELO is, for the time being, simple. Improve.
//        int eloDiff = abs(currElo - oppElo);
        if (eloDiff <= 100) {
            return 20;
        } else if (eloDiff <= 200) {
            return 50;
        } else if (eloDiff  <= 400) {
            return 90;
        } else {
            return 140;
        }
    }

    private static void saveUserDataToFile(String username, String password) {
        try {
            FileWriter fileWriter = new FileWriter(USER_DATA_FILE, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);


            // Save username and hashed password in the format: "username■hashed_password"
            String userData = username + "■" + (password) + "■0■0■1000";
            //default ELO of 1,000
            printWriter.println(userData);


            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static void playGame(String username, Scanner scanner) {
        System.out.println("Select a game mode:");
        System.out.println("1. Entities.Player vs. Entities.Player");
        System.out.println("2. Entities.Player vs. Entities.Bot");
        String gameModeChoice = scanner.nextLine();
        switch (gameModeChoice) {
            case "1":
                //Player methods were in here; original may be found in RUBBISH
                new Player(username);
                break;
//                System.out.println("hello");

            case "2":
                new Bot(username);
                break;
//                System.out.println("HELlO");

        }
    }



    public static void winner(String winnerName, String loserName) {
// Open the user data file for reading and writing
        File userDataFile = new File(USER_DATA_FILE);
        try {
            // Read the existing user data and update it
            List<String> lines = new ArrayList<>();
            Scanner fileScanner = new Scanner(userDataFile);
            int winnerElo = 0;
            int loserElo = 0;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] userData = line.split("■");
                if (userData.length >= 1 && userData[0].equals(winnerName)) {
                    // Found the winner's data, update games won (3rd element) by 1
                    int gamesWon = Integer.parseInt(userData[2]);
                    winnerElo = Integer.parseInt(userData[4]);
                    //update ELO score.
                    gamesWon++;
                    userData[2] = String.valueOf(gamesWon);
                } else if (userData.length >= 1 && userData[0].equals(loserName)) {
                    // Found the loser's data, update games lost (4th element) by 1
                    int gamesLost = Integer.parseInt(userData[3]);
                    loserElo = Integer.parseInt(userData[4]);
                    gamesLost++;
                    userData[3] = String.valueOf(gamesLost);
                }
                int eloDiff = winnerElo - loserElo;
                if (userData.length >= 1 && userData[0].equals(winnerName)) {
                    userData[4] = String.valueOf(Integer.parseInt(userData[4]) + eloCalculator(eloDiff));
                } else if (userData.length >= 1 && userData[0].equals(loserName)) {
                    userData[4] = String.valueOf(Integer.parseInt(userData[4]) - eloCalculator(eloDiff));
                }
                // Reconstruct the line with updated data
                String updatedLine = String.join("■", userData);
                lines.add(updatedLine);
            }
            fileScanner.close();


            // Write the updated data back to the file
            FileWriter fileWriter = new FileWriter(userDataFile);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (String updatedLine : lines) {
                printWriter.println(updatedLine);
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
