import java.io.*;
import java.util.*;

public class Main {
    private static final String USER_DATA_FILE = "C:\\Users\\andre\\IdeaProjects\\ConnectPlus\\ConnectPlus\\user_data.txt"; // Text file to store usernames and hashed passwords
    //this stupidly long mess was necessary
    private static final char PASSWORD_MASK = '*';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean signedIn = false;
        String currentUser = "";

        while (true) {
            if (!signedIn) {
                System.out.println("Select an option:");
                System.out.println("1. Create a new account");
                System.out.println("2. Sign in");
                System.out.println("3. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        createAccount(scanner);
                        break;
                    case 2:
                        currentUser = signIn(scanner);
                        if (!currentUser.isEmpty()) {
                            signedIn = true;
                            System.out.println("Sign-in successful!");
                        } else {
                            System.out.println("Invalid username or password. Please try again.");
                        }
                        break;
                    case 3:
                        System.out.println("Goodbye!");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                        break;
                }
            } else {
                System.out.println("Select an option:");
                System.out.println("1. View statistics");
                System.out.println("2. Play a game");
                System.out.println("3. Sign out");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        displayUserStatistics(currentUser);
                        break;
                    case 2:
                        playGame(currentUser, scanner);
                        break;
                    case 3:
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
            System.out.println("Enter a password:");
            String password = enterPasswordMasked(scanner);

            System.out.println("Confirm password:");
            String confirmPassword = enterPasswordMasked(scanner);

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

    private static boolean isUsernameExists(String username) {
        try {
            File file = new File(USER_DATA_FILE);
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] userData = line.split("■");
                if (userData.length == 2 && userData[0].equals(username)) {
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


    private static String enterPasswordForExistingUser(String username, Scanner scanner) {
        System.out.println("Enter the password for the existing account:");
        String password = scanner.nextLine();

        if (authenticateUser(username, password)) {
            return password;
        }
        return null;
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
                if (userData.length == 2 && userData[0].equals(username) && userData[1].equals(hashPassword(password))) {
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
        System.out.println("Games won: 1");
        System.out.println("Games lost: 0" + "\n" + "\n*****************************************\n");
    }

    private static void saveUserDataToFile(String username, String password) {
        try {
            FileWriter fileWriter = new FileWriter(USER_DATA_FILE, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // Save username and hashed password in the format: "username■hashed_password"
            String userData = username + "■" + hashPassword(password);
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
        System.out.println("1. Entities.Player vs. Entities.Player");
        System.out.println("2. Entities.Player vs. Entities.Bot");
        int gameModeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch (gameModeChoice) {
//            case 1:
//                // Entities.Player vs. Entities.Player
//                System.out.println("Enter the username of the second player:");
//                String secondPlayer = scanner.nextLine();
//
//                // Check if the second player exists
//                if (isUsernameExists(secondPlayer)) {
//                    System.out.println("Entities.Game started: " + username + " vs. " + secondPlayer);
//                    int length = getBoardLength(scanner);
//                    int height = getBoardHeight(scanner);
//                    int piecesToConnect = getRequiredPieces(scanner);
//                    Entities.Board board = new Entities.Board(length, height, piecesToConnect);
//
//                    // Implement your player vs. player game logic here
//                    // You can use 'board' for the game state
//                } else {
//                    System.out.println("The second player does not exist. Please try again.");
//                }
//                break;
            case 1:
                // Entities.Player vs. Entities.Player
                System.out.println("Enter the username of the second player:");
                String secondPlayer = scanner.nextLine();

                // Check if the second player exists
                if (isUsernameExists(secondPlayer)) {
                    System.out.println("Entities.Game started: " + username + " vs. " + secondPlayer);
                    int length = getBoardLength(scanner);
                    int height = getBoardHeight(scanner);
                    int piecesToConnect = getRequiredPieces(scanner);
                    Board board = new Board(length, height, piecesToConnect);
                    WinChecker winChecker = new WinChecker(piecesToConnect, board.getBoard());

                    // Implement your player vs. player game logic here
                    char currentPlayer = 'X';
                    while (true) {
                        board.displayBoard();
                        System.out.println("Entities.Player " + currentPlayer + ", enter the column to drop your piece:");
                        int column = scanner.nextInt() - 1;
                        scanner.nextLine(); // Consume the newline character
                        if (winChecker.checkWin(board.findEmptyRow(column), column, currentPlayer)) {
                            board.displayBoard();
                            System.out.println("Entities.Player " + currentPlayer + " wins!");
                            break;
                        }
                        if (board.makeMove(column, currentPlayer)) {
                            if (winChecker.checkWin(board.findEmptyRow(column), column, currentPlayer)) {
                                board.displayBoard();
                                System.out.println("Entities.Player " + currentPlayer + " wins!");
                                break;
                            } else if (board.isFull()) {
                                board.displayBoard();
                                System.out.println("It's a tie!");
                                break;
                            }

                            // Switch to the other player
                            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                        } else {
                            System.out.println("Invalid move. Please try again.");
                        }
                    }



                } else {
                    System.out.println("The second player does not exist. Please try again.");
                }
                break;

            case 2:
                // Entities.Player vs. Entities.Bot
                System.out.println("Select bot difficulty (1-10):");
                int botDifficulty = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (botDifficulty >= 1 && botDifficulty <= 10) {
                    System.out.println("Entities.Game started: " + username + " vs. Entities.Bot (Difficulty: " + botDifficulty + ")");
                    // Implement your player vs. bot game logic here
                } else {
                    System.out.println("Invalid bot difficulty. Please select a number between 1 and 10.");
                }
                break;
            default:
                System.out.println("Invalid choice. Please enter 1 or 2.");
                break;
        }
    }

    private static int getBoardLength(Scanner scanner) {
        System.out.println("Enter the board length (2-10):");
        int length = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
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
        scanner.nextLine(); // Consume the newline character
        return pieces;
    }
}