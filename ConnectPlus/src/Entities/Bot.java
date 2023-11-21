package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//import static Entities.Game.moves;
import static Entities.Game.reconstruct;

public class Bot {
    private static int MAX_DEPTH = 0;
    //#TODO: make this dependent, at least partially, on both ELO and bot difficulty selected
    static Scanner scanner = new Scanner(System.in);
    static List<Integer> moves = new ArrayList<>();
    public Bot(String username) {
        System.out.println("Select bot difficulty (1-10):");

        int botDifficulty = getBotDifficulty(username) - 1;
        String[] difficulty = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        String machine = "Machine " + difficulty[botDifficulty];
        Player.boardSetup(username, machine);
    }
//TODO: add undo feature for the PLAYER ONLY!
    public static void playGame(Board board, String username, String secondPlayer, char currentPlayer, WinChecker winChecker, int d) {
        while (true) {
            if(!moves.isEmpty()) {
                board = Game.redo(board, moves);
            }
            board.displayBoard();

            if (currentPlayer == 'X') {
                System.out.println(username + ", enter the column to drop your piece:");
                int column = getUserMove();
                moves.add(column);
//                System.out.println(moves);
                handleMove(board, username, secondPlayer, winChecker, column, currentPlayer, moves);
            } else {
                System.out.println(secondPlayer + "'s turn (Bot 'O'):");

                if (d > 0) {
                    int column = findBestMove(board);

                    moves.add(column);
                    System.out.println("Bot chooses column " + (column));
//                    System.out.println(moves);
//                    board = Game.reconstruct(board, moves);
                    handleMove(board, username, secondPlayer, winChecker, column, currentPlayer, moves);
                } else {
                    System.out.println("Bot is not making a move. Enter 'r' to quit.");
                    String input = scanner.nextLine();

                    if (input.equalsIgnoreCase("r")) {
                        char otherPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                        String winnerName = (otherPlayer == 'X') ? username : secondPlayer;
                        String loserName = (otherPlayer == 'X') ? secondPlayer : username;
                        System.out.println("Entities.Player " + winnerName + " wins! (Entities.Player " + loserName + " quit)");
                        Main.winner(winnerName, loserName);
                        return; // Exit the game
                    }
                }
            }
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    private static void handleMove(Board board, String username, String secondPlayer, WinChecker winChecker, int column, char currentPlayer, List<Integer> moves) {
        if (winChecker.checkWin(currentPlayer)) {
            board.displayBoard();
            System.out.println("Entities.Player " + currentPlayer + " wins!");
            Main.winner((currentPlayer == 'X') ? username : secondPlayer, (currentPlayer == 'X') ? secondPlayer : username);
            System.exit(0);
        }

        if (board.makeMove(column, currentPlayer)) {
//            System.out.println(moves);
            if (winChecker.checkWin(currentPlayer)) {
                board.displayBoard();
                System.out.println("Entities.Player " + currentPlayer + " wins!");
                Main.winner((currentPlayer == 'X') ? username : secondPlayer, (currentPlayer == 'X') ? secondPlayer : username);
                System.exit(0);
            } else if (board.isFull() || moves.size() == board.getHeight() * board.getLength()) {
                board.displayBoard();
                System.out.println("It's a tie!");
                System.exit(0);
            }
        } else {
            System.out.println("Invalid move. Please try again.");
        }
    }

    private static int getUserMove() {
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("r")) {
                System.out.println("Enter 'r' again to confirm quitting the game.");
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("r")) {
                    System.out.println("You quit the game. Bot wins!");
                    Main.winner("Bot", "Entities.Player");
                    System.exit(0);
                }
            }

            try {
                return Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for the column.");
            }
        }
    }

    //TODO: make this mess more elegant, and remove redundant and unnecessary assignments
    public int getBotDifficulty(String username) {
        int botDifficulty = scanner.nextInt();

        boolean b = (botDifficulty >= 1 && botDifficulty <= 10);
        if (!b) {
            System.out.println("Invalid bot difficulty. Please select a number between 1 and 10.");
        }

//        System.out.println("Entities.Game started: " + username + " vs." + machine);
        return botDifficulty;
    }

    public static int findBestMove(Board board) {
        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        List<Integer> legalMoves = board.generateLegalMoves();

        for (int move : legalMoves) {
            // Make the move
            Board fakeBoard = board;
            fakeBoard.makeMove(move, 'O');

            // Evaluate the move using the minimax algorithm
            int score = minimax(fakeBoard, 0, false, alpha, beta);

            List<Integer> currentMoves = moves;
            fakeBoard = board;
//            board.displayBoard();

            // Update the best move if a better score is found
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }

            // Update alpha for pruning
            alpha = bestScore;

            // Alpha-beta pruning
            if (beta == alpha) {
                break;
            }
        }

        return bestMove;
    }
//TODO: make the depth actually work - the bot should look forwards, but make only one move rather than filling out the board
    //with his prediction.
    private static int minimax(Board board, int depth, boolean isMaximizingPlayer, int alpha, int beta) {
        if (depth == MAX_DEPTH || board.isFull() || board.evaluate() == Integer.MAX_VALUE || board.evaluate() == Integer.MIN_VALUE) {
            return board.evaluate();
        }

        List<Integer> legalMoves = board.generateLegalMoves();

        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int move : legalMoves) {
                board.makeMove(move, 'O');
                List<Integer> currentMoves = moves;
                int eval = minimax(board, depth + 1, false, alpha, beta);
                board = Game.redo(board, currentMoves);
//                board = reconstruct(board, moves);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int move : legalMoves) {
                board.makeMove(move, 'X');
                int eval = minimax(board, depth + 1, true, alpha, beta);
//                System.out.println(moves);
                List<Integer> currentMoves = moves;
                board = Game.redo(board, currentMoves);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }




}
