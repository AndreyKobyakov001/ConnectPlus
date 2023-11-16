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
        while (true) {
            if (!signedIn) {
                handleNotSignedIn();
            } else {
                handleSignedIn();
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

    static void handleSignedIn() {
        System.out.println("1. View statistics");
        System.out.println("2. Play a game");
        System.out.println("3. Sign out");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                User.displayUserStatistics(currentUser);
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
                //#TODO: Fix this default; it prints twice without cause.
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                break;
        }
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
//                System.out.println("hello");

            case "2":
                // Entities.Player vs. Entities.Bot
                System.out.println("Select bot difficulty (1-10):");
                int botDifficulty = scanner.nextInt();

                if (botDifficulty >= 1 && botDifficulty <= 10) {
                    System.out.println("Entities.Game started: " + username + " vs. Entities.Bot (Difficulty: " + botDifficulty + ")");
                } else {
                    System.out.println("Invalid bot difficulty. Please select a number between 1 and 10.");
                }
                String[] difficulty = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
                String machine = "Machine " + difficulty[botDifficulty-1];
                System.out.println("Entities.Game started: " + username + " vs." + machine);

                System.out.println("Select an option:");
                System.out.println("1. Set Entities.Board Dimensions");
                System.out.println("2. Use Last Entities.Game's Settings");
                System.out.println("Continue: Use Default Settings");
                String choice = scanner.nextLine();
                int length = 0;
                int height = 0;
                int piecesToConnect = 0;
                switch (choice) {
                    case "1":
                        length = Player.getBoardLength(scanner);
                        height = Player.getBoardHeight(scanner);
                        boolean valid = false;
                        while (!valid) {
                            piecesToConnect = Player.getRequiredPieces(scanner);
                            if (piecesToConnect <= length && piecesToConnect <= height) {
                                valid = true;
                            }
                        }
                        break;
                    case "2":
                        try {
                            File file = new File(SETTINGS_FILE);
                            Scanner fileScanner = new Scanner(file);


                            while (fileScanner.hasNextLine()) {
                                String line = fileScanner.nextLine();
                                String[] settings = line.split("■");
                                length = Integer.parseInt(settings[0]);
                                height = Integer.parseInt(settings[1]);
                                piecesToConnect = Integer.parseInt(settings[2]);
                            }
                            fileScanner.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "t"://for testing purposes only; delete for submission
                        length = 4;
                        height = 4;
                        piecesToConnect = 3;
                        break;
                    default:
                        length = 7;
                        height = 6;
                        piecesToConnect = 4;
                        break;
                }

                try {
                    FileWriter fileWriter = new FileWriter(SETTINGS_FILE, false); // Open the file in overwrite mode
                    PrintWriter printWriter = new PrintWriter(fileWriter);

                    String userData = length + "■" + height + "■" + piecesToConnect;
                    printWriter.println(userData);

                    printWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Board board = new Board(length, height, piecesToConnect);
                WinChecker winChecker = new WinChecker(piecesToConnect, board.getBoard());
                Board prev = new Board(length, height, piecesToConnect);
                ;
                char currentPlayer = 'X';
                while (true) {
//                        prev = board;
                    board.displayBoard();
                    if (currentPlayer == 'X') {
                        System.out.println(username + ", enter the column to drop your piece:");
                    } else {
                        System.out.println("Entities.Bot chooses column " + 1);
                    }
                    int column = -1; // Initialize to an invalid value
                    boolean validInput = false;

                    while (!validInput) {
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("r")) {
                            char otherPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                            String winnerName = (otherPlayer == 'X') ? username : machine;
                            String loserName = (otherPlayer == 'X') ? machine : username;
                            System.out.println("Entities.Player " + winnerName + " wins! (Entities.Player " + loserName + " quit)");
                            winner(winnerName, loserName);
                            return; // Exit the game
                        }
                        if (input.equalsIgnoreCase("q")) {
                            board = prev;
                            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                        }
                        try {
                            column = Integer.parseInt(input) - 1;
                            validInput = true; // Input is valid; exit the loop
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid integer for the column.");
                        }
                    }

                    if (winChecker.checkWin(currentPlayer)) {
                        board.displayBoard();
                        System.out.println("Entities.Player " + currentPlayer + " wins!");
                        break;
                    }

                    if (board.makeMove(column, currentPlayer)) {
                        if (winChecker.checkWin(currentPlayer)) {
                            board.displayBoard();
                            String winnerName = (currentPlayer == 'X') ? username : machine;
                            String loserName = (currentPlayer == 'X') ? machine : username;
                            System.out.println("Entities.Player " + winnerName + " wins!");
                            winner(winnerName, loserName);
                            break;
                        } else if (board.isFull()) {
                            board.displayBoard();
                            System.out.println("It's a tie!");
                            break;
                        }
                        prev = board;
                        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                    } else {
                        System.out.println("Invalid move. Please try again.");
                    }
                }

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
