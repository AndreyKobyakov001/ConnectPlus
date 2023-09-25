import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class Main {
    private static final String USER_DATA_FILE = "ConnectPlus\\user_data.txt"; // Text file to store usernames and hashed passwords
    private static final String SETTINGS_FILE = "ConnectPlus\\settings.txt"; // Text file to store usernames and hashed passwords
    //this stupidly long mess was necessary


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean signedIn = false;
        String currentUser = "";


        while (true) {
            System.out.println("Select an option:");
            if (!signedIn) {
                System.out.println("1. Create a new account");
                System.out.println("2. Sign in");
                System.out.println("3. Exit");
                String choice = scanner.nextLine();
//                scanner.nextLine(); // Consume the newline character

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
            } else {
                System.out.println("1. View statistics");
                System.out.println("2. Play a game");
                System.out.println("3. Sign out");
                String choice = scanner.nextLine();
//                scanner.nextLine(); // Consume the newline character


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
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                        break;
                }
            }
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
            String password = enterPasswordMasked(scanner);


            System.out.println("Confirm password:");
            String confirmPassword = enterPasswordMasked(scanner);


            if (password.equals(confirmPassword)) {
                try {
                    User newUser = new User(username, password, confirmPassword);


                    // Save the new username and hashed password to the local text file
                    saveUserDataToFile(newUser.getUsername(), newUser.getPassword());


                    System.out.println("User account created successfully.");
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


    private static boolean isUsernameExists(String username) {
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
        String password = enterPasswordMasked(scanner);


        if (authenticateUser(username, password)) {
            return username;
        }
        return "";
    }


    private static boolean authenticateUser(String username, String password) {
        try {
            File file = new File(USER_DATA_FILE);
            Scanner fileScanner = new Scanner(file);


            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] userData = line.split("■");
                if (userData[0].equals(username) && userData[1].equals(hashPassword(password))) {
                    return true;
                }
            }


            fileScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    private static void displayUserStatistics(String username) {
        // You can implement the logic to retrieve and display user statistics here.
        // For now, use a placeholder.
        System.out.println("\n" + "*****************************************\n" + "\nStatistics for user: " + username);
        int gamesWon = 0;
        int gamesLost = 0;
        int elo = 1000;

        try {
            File file = new File(USER_DATA_FILE);
            Scanner fileScanner = new Scanner(file);


            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] userData = line.split("■");


                // Check if the current line's username matches the desired username
                if (userData.length >= 1 && userData[0].equals(username)) {
                    // Make sure there are enough elements in the array to access games won and lost
                    if (userData.length >= 4) {
                        //FIX THIS LATER
                        gamesWon = Integer.parseInt(userData[2]);
                        gamesLost = Integer.parseInt(userData[3]);
                        elo = Integer.parseInt(userData[4]);
                    }
                    break; // Exit the loop once we've found the desired username
                }
            }


            fileScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Games Won: " + gamesWon);
        System.out.println("Games lost: " + gamesLost + "\nELO RATING: " +
                elo + "\n*****************************************\n");
    }


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
            String userData = username + "■" + hashPassword(password) + "■0■0■1000";
            //default ELO of 1,000
            printWriter.println(userData);


            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String hashPassword(String password) {
        // In a real application, you should use a secure hashing algorithm (e.g., bcrypt)
        // For simplicity, we'll use a basic approach here (not secure for production)
        return password; // Insecure placeholder
    }


    private static String enterPasswordMasked(Scanner scanner) {
        Console console = System.console();
        if (console != null) {
            char[] passwordChars = console.readPassword();
            return new String(passwordChars);
        } else {
            // If the console is not available, just read the password as normal text
            return scanner.nextLine();
        }
    }

    private static void playGame(String username, Scanner scanner) {
        System.out.println("Select a game mode:");
        System.out.println("1. Player vs. Player");
        System.out.println("2. Player vs. Bot");
        String gameModeChoice = scanner.nextLine();
        switch (gameModeChoice) {
            case "1":
                // Player vs. Player
                boolean logged = false;
                String secondPlayer = null;
                while (!logged) {
                    System.out.println("Enter the username of the second player:");
                    secondPlayer = scanner.nextLine();
                    if (Objects.equals(secondPlayer, username)) {
                        System.out.println("You can't play a game against yourself!");
                    } else {
                        // Set logged to true to exit the loop when a different username is provided.
                        logged = true;
                    }
                }


                // Check if the second player exists and is not the same as the current
                if (isUsernameExists(secondPlayer) && !Objects.equals(secondPlayer, username)) {
                    System.out.println("Game started: " + username + " vs. " + secondPlayer);

                    System.out.println("Select an option:");
                    System.out.println("1. Set Board Dimensions");
                    System.out.println("2. Use Last Game's Settings");
                    System.out.println("Continue: Use Default Settings");
                    String choice = scanner.nextLine();
                    int length = 0;
                    int height = 0;
                    int piecesToConnect = 0;

                    switch (choice) {
                        case "1":
                            length = getBoardLength(scanner);
                            height = getBoardHeight(scanner);
                            boolean valid = false;
                            while (!valid) {
                                piecesToConnect = getRequiredPieces(scanner);
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
                    // Implement your player vs. player game logic here
                    char currentPlayer = 'X';
                    while (true) {
//                        prev = board;
                        board.displayBoard();
                        if (currentPlayer == 'X') {
                            System.out.println(username + ", enter the column to drop your piece:");
                        } else {
                            System.out.println(secondPlayer + ", enter the column to drop your piece:");
                        }
                        // Use a try-catch block to handle non-integer input
                        int column = -1; // Initialize to an invalid value
                        boolean validInput = false;

                        while (!validInput) {
                            String input = scanner.nextLine();
                            if (input.equalsIgnoreCase("r")) {
                                char otherPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                                String winnerName = (otherPlayer == 'X') ? username : secondPlayer;
                                String loserName = (otherPlayer == 'X') ? secondPlayer : username;
                                System.out.println("Player " + winnerName + " wins! (Player " + loserName + " quit)");
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
                            System.out.println("Player " + currentPlayer + " wins!");
                            break;
                        }

                        if (board.makeMove(column, currentPlayer)) {
                            if (winChecker.checkWin(currentPlayer)) {
                                board.displayBoard();
                                String winnerName = (currentPlayer == 'X') ? username : secondPlayer;
                                String loserName = (currentPlayer == 'X') ? secondPlayer : username;
                                System.out.println("Player " + winnerName + " wins!");
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

                } else {
                    System.out.println("The second player does not exist. Please try again.");
                }
                break;


            case "2":
                // Player vs. Bot
                System.out.println("Select bot difficulty (1-10):");
                int botDifficulty = scanner.nextInt();

                if (botDifficulty >= 1 && botDifficulty <= 10) {
                    System.out.println("Game started: " + username + " vs. Bot (Difficulty: " + botDifficulty + ")");
                } else {
                    System.out.println("Invalid bot difficulty. Please select a number between 1 and 10.");
                }
                String[] difficulty = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
                String machine = "Machine " + difficulty[botDifficulty-1];
                System.out.println("Game started: " + username + " vs." + machine);

                System.out.println("Select an option:");
                System.out.println("1. Set Board Dimensions");
                System.out.println("2. Use Last Game's Settings");
                System.out.println("Continue: Use Default Settings");
                String choice = scanner.nextLine();
                int length = 0;
                int height = 0;
                int piecesToConnect = 0;
                switch (choice) {
                    case "1":
                        length = getBoardLength(scanner);
                        height = getBoardHeight(scanner);
                        boolean valid = false;
                        while (!valid) {
                            piecesToConnect = getRequiredPieces(scanner);
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
                        System.out.println("Bot chooses column " + 1);
                    }
                    int column = -1; // Initialize to an invalid value
                    boolean validInput = false;

                    while (!validInput) {
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("r")) {
                            char otherPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                            String winnerName = (otherPlayer == 'X') ? username : machine;
                            String loserName = (otherPlayer == 'X') ? machine : username;
                            System.out.println("Player " + winnerName + " wins! (Player " + loserName + " quit)");
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
                        System.out.println("Player " + currentPlayer + " wins!");
                        break;
                    }

                    if (board.makeMove(column, currentPlayer)) {
                        if (winChecker.checkWin(currentPlayer)) {
                            board.displayBoard();
                            String winnerName = (currentPlayer == 'X') ? username : machine;
                            String loserName = (currentPlayer == 'X') ? machine : username;
                            System.out.println("Player " + winnerName + " wins!");
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
    private static int getBoardLength(Scanner scanner) {
        System.out.println("Enter the board length (2-10):");
        int length = scanner.nextInt();
//        scanner.nextLine(); // Consume the newline character
        return length;
    }


    private static int getBoardHeight(Scanner scanner) {
        System.out.println("Enter the board height (2-10):");
        int height = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        return height;
    }


    private static int getRequiredPieces(Scanner scanner) {
        System.out.println("Enter the number of pieces needed to connect (2-9):");
        int pieces = scanner.nextInt() + 1; //this is a temporary fix ONLY! - make it work later.
//        scanner.nextLine(); // Consume the newline character
        return pieces;
    }
}
