package Entities;

import use_case.setup.SetupInteractor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static Entities.MainOld.isUsernameExists;

 // Text file to store usernames and hashed passwords
public class PlayerOld {

     static Scanner scanner = new Scanner(System.in);

    public PlayerOld(String username) {
        String secondPlayer = getOpponentUsername(username, scanner);
        boardSetup(username, secondPlayer);
    }


    private static String getOpponentUsername(String currentPlayer, Scanner scanner) {
        String opponentUsername = null;
        boolean validUsername = false;

        while (!validUsername) {
            System.out.println("Enter the username of the second player:");
            opponentUsername = scanner.nextLine();

            if (Objects.equals(opponentUsername, currentPlayer)) {
                System.out.println("You can't play a game against yourself!");
            } else {
                validUsername = isUsernameExists(opponentUsername);
            }
        }

        return opponentUsername;
    }

    public static void boardSetup(String username, String secondPlayer) {
        String regex = "Machine\\s[VIX]{1,2}";
        boolean bot = false;
        if (isUsernameExists(secondPlayer) && !Objects.equals(secondPlayer, username) || secondPlayer.matches("Machine\\s[A-Za-z]{1,2}")) {
            if (secondPlayer.matches("Machine\\s[A-Za-z]{1,2}")) {
                System.out.println("You playing a bot, fool!");
                bot = true;
            }
            System.out.println("Entities.Game started: " + username + " vs. " + secondPlayer);

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

            char currentPlayer = 'X';
            if(!bot) {
                SetupInteractor.playGame(board, username, secondPlayer, currentPlayer, winChecker);
            }
            if(bot) {
                System.out.println("Bot Game!");
                //#TODO: fix this janky mess.
                List<String> difficulty = List.of(new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"});
                int d = difficulty.indexOf(secondPlayer.split(" ")[1]) + 1;
                BotOld.playGame(board, username, secondPlayer, currentPlayer, winChecker, d);
            }
        }
    }


    static int getBoardLength(Scanner scanner) {
        System.out.println("Enter the board length (2-10):");
        //        scanner.nextLine(); // Consume the newline character
        return scanner.nextInt();
    }


    static int getBoardHeight(Scanner scanner) {
        System.out.println("Enter the board height (2-10):");
//        scanner.nextLine(); // Consume the newline character
        return scanner.nextInt();
    }


    static int getRequiredPieces(Scanner scanner) {
        System.out.println("Enter the number of pieces needed to connect (2-9):");
        //        scanner.nextLine(); // Consume the newline character
        return scanner.nextInt() + 1;
    }

     private static final String SETTINGS_FILE = "ConnectPlus\\settings.txt";
 }
