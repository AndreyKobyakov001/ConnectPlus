//package use_case.setup;
//import Entities.Board;
//import Entities.Game;
//import Entities.MainOld;
//import Entities.WinChecker;
//
//public class SetupInteractor {
//    private final SetupInputData inputData;
//    private final SetupOutputBoundary setupOutputBoundary;
//    private final SetupUserDataAccessInterface userDataAccessInterface;
//
//    //assuming we have height, width, pieces, mode, bot difficulty if applicable from input data structure
//    public SetupInteractor(SetupUserDataAccessInterface userDataAccessInterface,
//                           SetupOutputBoundary setupOutputBoundary, SetupInputData inputData) {
//        this.userDataAccessInterface = userDataAccessInterface;
//        this.setupOutputBoundary = setupOutputBoundary;
//        this.inputData = inputData;
//    }
//
//    //    static Board redo(Board board, List<Integer> moves) {
//    //        char currentPlayer = 'X';
//    //        Board newBoard = new Board(board.getHeight(), board.getLength(), board.getPieces());
//    //        moves.remove(moves.size() - 1);
//    //        for (int move : moves) {
//    //            if (newBoard.makeMove(move, currentPlayer)) {
//    //                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
//    //            } else {
//    //                System.out.println("Invalid move during reconstruction.");
//    //                break;
//    //            }
//    //        }
//    //        return newBoard;
//    //    }
//        public static void playGame(Board board, String username, String secondPlayer, char currentPlayer, WinChecker winChecker) {
//            while (true) {
//                board.displayBoard();
//                if (currentPlayer == 'X') {
//                    System.out.println(username + ", enter the column to drop your piece:");
//                } else {
//                    System.out.println(secondPlayer + ", enter the column to drop your piece:");
//                }
//                // Use a try-catch block to handle non-integer input
//                int column = -1; // Initialize to an invalid value
//                boolean validInput = false;
//
//                while (!validInput) {
//                    String input = Game.scanner.nextLine();
//                    if (input.equalsIgnoreCase("r")) {
//                        char otherPlayer = (currentPlayer == 'X') ? 'O' : 'X';
//                        String winnerName = (otherPlayer == 'X') ? username : secondPlayer;
//                        String loserName = (otherPlayer == 'X') ? secondPlayer : username;
//                        System.out.println("Entities.Player " + winnerName + " wins! (Entities.Player " + loserName + " quit)");
//                        MainOld.winner(winnerName, loserName);
//                        return; // Exit the game
//                    }
//
//
//
//                    try {
//                        column = Integer.parseInt(input) - 1;
//    //                    System.out.println("MOVE: " + column);
//                        Game.moves.add(column);
//                        System.out.println(Game.moves);
//                        validInput = true; // Input is valid; exit the loop
//                    } catch (NumberFormatException e) {
//                        //#TODO: stop this error message from triggering with a u for undoing a  move
//                        System.out.println("Invalid input. Please enter a valid integer for the column.");
//                    }
//    //
//                }
//
//                if (winChecker.checkWin(currentPlayer)) {
//                    board.displayBoard();
//                    System.out.println("Entities.Player " + currentPlayer + " wins!");
//                    break;
//                }
//
//                if (board.makeMove(column, currentPlayer)) {
//                    if (winChecker.checkWin(currentPlayer)) {
//                        board.displayBoard();
//                        String winnerName = (currentPlayer == 'X') ? username : secondPlayer;
//                        String loserName = (currentPlayer == 'X') ? secondPlayer : username;
//                        System.out.println("Entities.Player " + winnerName + " wins!");
//                        MainOld.winner(winnerName, loserName);
//                        break;
//                    } else if (board.isFull()) {
//                        board.displayBoard();
//                        System.out.println("It's a tie!");
//                        break;
//                    }
//                    String input = Game.scanner.nextLine();
//                    System.out.println("Are you sure? Press 'u' to undo. Press anything to continue.");
//
//                    //#TODO: make this more elegant; make 3 undo limit.
//                    if (input.equalsIgnoreCase("u")) {
//                        board = Game.reconstruct(board, Game.moves);
//                        currentPlayer = (currentPlayer == 'X') ? 'X' : 'O';
//                    } else {
//                        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
//                    }
//                }
//                else {
//                    System.out.println("Invalid move. Please try again.");
//                }
//            }
//        }
//
//    public void execute() {
//        Board board = new Board(inputData.getWidth(), inputData.getHeight(), inputData.getPieces());
//        WinChecker w = new WinChecker(inputData.getPieces(), board.getBoard());
//        playGame(board, inputData.getFirstPlayer(), inputData.getOtherPlayer(), 'X', w);
//    }
//
//    //Use cases - lives in execute; logic in interactors with helpers - interactor for each use case based on extant logic:
//    // initialize game
//    // make move and undo
//    // wincheck - win or loss or tie
//    // resign option
//    // return to main screen
//
//
//}
