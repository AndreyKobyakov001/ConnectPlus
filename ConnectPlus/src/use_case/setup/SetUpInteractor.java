package use_case.setup;

import Entities.Board;
import Entities.Player;
import Entities.Bot;
import Entities.WinChecker;
import use_case.GameBuild.GameBuildOutputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class SetUpInteractor implements SetupInputBoundary {
    private final SetupOutputBoundary presenter;
    private Board board;
    private Player player1;
    private Player player2;
    private boolean isPlayer1Turn = true;

    private int botDiff;

    // Stack to store previous states for undo functionality
    private Stack<char[][]> previousStates;

    private WinChecker winChecker;

    public SetUpInteractor(SetupOutputBoundary presenter) {
        this.presenter = presenter;
        this.previousStates = new Stack<>();
    }

    @Override
    public void startGame(GameBuildOutputData setupInputData) {
        this.board = new Board(setupInputData.getHeight(),
                setupInputData.getWidth(),
                setupInputData.getWinCondition());
        this.player1 = new Player("Player 1", 'X');
        this.botDiff = setupInputData.getBotDifficulty();
        this.player2 = this.botDiff == -1 ?
                new Player("Player 2", 'O') :
                new Bot("Bot", 'O', setupInputData.getBotDifficulty());
        this.winChecker = new WinChecker(setupInputData.getWinCondition(), board.getBoard());
        previousStates.push(board.getBoard()); // Store initial state
        updatePresenterWithStartGame();
    }

    @Override
    public void makeMove(int column, String playerName) {
        if (!board.isValidMove(column)) {
            presenter.illegalMove();
            return;
        }

        Player currentPlayer = playerName.equals(player1.getName()) ? player1 : player2;

        board.makeMove(column, currentPlayer.getSymbol());
        previousStates.push(board.getBoard());
//        winChecker.setBoard(board.getBoard());
        isPlayer1Turn = !isPlayer1Turn;
        updatePresenterWithBoardState();
        if (!isPlayer1Turn && player2 instanceof Bot) {
//            makeBotMoveRandomly();
            makeBotMove();
        }

        int winner = determineWinner();
        if (winner != -1 || board.isFull()) {
            endGame();
        }
    }


    @Override
    public void undoMove() {
        if (previousStates.size() > 1) { // Ensure there's a previous state
            previousStates.pop(); // Remove the current state
            char[][] prevState = previousStates.peek(); // Get the previous state
            board.setBoard(prevState); // Revert to the previous state
            isPlayer1Turn = !isPlayer1Turn; // Switch turn
            updatePresenterWithBoardState(); //TODO: make sure this works.
        }
    }

    @Override
    public void forfeitGame(int i) {
        SetupOutputData outputData = createOutputData();
        outputData.setisWon(i);
        presenter.endGame(outputData);
    }

    private void endGame() {
        int winner = determineWinner();
        SetupOutputData outputData = createOutputData();
        outputData.setisWon(winner);
        presenter.endGame(outputData);
    }


    private void updatePresenterWithBoardState() {
        SetupOutputData outputData = createOutputData();
        outputData.setBoardState(board.getBoard());
        outputData.setPlayer1Turn(isPlayer1Turn);
        presenter.updateBoard(outputData);
    }

    private void updatePresenterWithStartGame() {
        SetupOutputData outputData = createOutputData();
        outputData.setPlayer1Turn(true); // Who goes first?
        outputData.setisWon(-1);
        outputData.setBoardState(board.getBoard());
        presenter.startGame(outputData);
    }

    private int determineWinner() {
        if (winChecker.checkWin(player1.getSymbol())) {
            return 1; // Player 1 wins
        } else if (winChecker.checkWin(player2.getSymbol())) {
            return 2; // Player 2 wins
        } else if (board.isFull()) {
            return 0; // Draw
        }
        return -1; // Game is ongoing
    }


    private SetupOutputData createOutputData() {
        SetupOutputData setupOutputData = new SetupOutputData(board.getHeight(), board.getWidth(),
                this.botDiff, player1.getName(), player2.getName());
        return setupOutputData;
    }

    private int minimax(int depth, boolean isMaximizingPlayer) {
        int score = evaluateBoard(); // Method to evaluate the score of the current board state

        // If Maximizer has won the game return evaluated score
        if (score == 10) return score - depth;

        // If Minimizer has won the game return evaluated score
        if (score == -10) return score + depth;

        // If there are no more moves and no winner then it is a tie
        if (board.isFull()) return 0;

        int best;

        if (isMaximizingPlayer) {
            best = Integer.MIN_VALUE;

            // Traverse all cells
            for (int i = 0; i < board.getHeight(); i++) {
                for (int j = 0; j < board.getWidth(); j++) {
                    // Check if cell is empty
                    if (board.getCell(i, j) == ' ') {
                        // Make the move
                        board.setCell(i, j, player1.getSymbol());

                        // Call minimax recursively and choose the maximum value
                        best = Math.max(best, minimax(depth + 1, !isMaximizingPlayer));

                        // Undo the move
                        board.setCell(i, j, ' ');
                    }
                }
            }
        } else {
            best = Integer.MAX_VALUE;

            // Traverse all cells
            for (int i = 0; i < board.getHeight(); i++) {
                for (int j = 0; j < board.getWidth(); j++) {
                    // Check if cell is empty
                    if (board.getCell(i, j) == ' ') {
                        // Make the move
                        board.setCell(i, j, player2.getSymbol());

                        // Call minimax recursively and choose the minimum value
                        best = Math.min(best, minimax(depth + 1, !isMaximizingPlayer));

                        // Undo the move
                        board.setCell(i, j, ' ');
                    }
                }
            }
        }
        return best;
    }

    private void makeBotMoveRandomly(){
        Random random = new Random();

        List<Integer> legalColumns = new ArrayList<>();

        // Find all legal columns (columns that are not full)
        for (int j = 0; j < board.getWidth(); j++) {
            if (board.getCell(0, j) == '?') {  // Check if the top cell of the column is empty
                legalColumns.add(j);
            }
        }

        if (!legalColumns.isEmpty()) {
            // Randomly select a legal column
            int chosenColumn = legalColumns.get(random.nextInt(legalColumns.size()));

            // Find the lowest empty cell in the chosen column
            int i = board.getHeight() - 1;
            while (i > 0 && board.getCell(i, chosenColumn) != '?') {
                i--;
            }

            // Make the move in the lowest empty cell of the column
            makeMove(chosenColumn, player2.getName());
        }
    }
    private void makeBotMove() {
        int bestVal = Integer.MIN_VALUE;
        int bestMove = -1;

        // Traverse only the top row of each column
        for (int j = 0; j < board.getWidth(); j++) {
            System.out.println("width: " + j);
            // Check if the top cell of the column is empty (i.e., the column is not full)
            if (board.getCell(0, j) == '?') {
                // Find the lowest empty cell in this column
                int i = board.getHeight() - 1;
                while (i > 0 && board.getCell(i, j) != '?') {
                    i--;
                }

                // Make the move in the lowest empty cell of the column
                board.setCell(i, j, player2.getSymbol());

                // Compute evaluation function for this move
                int moveVal = minimax(board.getWinCondition(), false);

                // Undo the move
                board.setCell(i, j, '?');

                // If the value of the current move is greater than the best value
                if (moveVal > bestVal) {
                    bestMove = j; // Move is identified by the column index
                    bestVal = moveVal;
                }
            }
        }

        if (bestMove != -1) {
            makeMove(bestMove, player2.getName());
        }
    }


    private int evaluateBoard() {
        int score = 0;

        // Score for horizontal, vertical, and both diagonal directions
        score += scoreDirection(0, 1); // Horizontal
        score += scoreDirection(1, 0); // Vertical
        score += scoreDirection(1, 1); // Diagonal (positive slope)
        score += scoreDirection(1, -1); // Diagonal (negative slope)

        return score;
    }

    private int scoreDirection(int dRow, int dCol) {
        int winCondition = board.getWinCondition();
        int tempScore = 0;

        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (canFormLine(i, j, dRow, dCol, winCondition)) {
                    int countPlayer1 = 0;
                    int countPlayer2 = 0;

                    for (int k = 0; k < winCondition; k++) {
                        char cell = board.getCell(i + dRow * k, j + dCol * k);
                        if (cell == player1.getSymbol()) {
                            countPlayer1++;
                        } else if (cell == player2.getSymbol()) {
                            countPlayer2++;
                        }
                    }

                    tempScore += evaluateLine(countPlayer1, countPlayer2, winCondition);
                }
            }
        }

        return tempScore;
    }

    private boolean canFormLine(int startRow, int startCol, int dRow, int dCol, int length) {
        // Check if the line goes out of bounds
        return startRow + dRow * (length - 1) < board.getHeight() &&
                startCol + dCol * (length - 1) < board.getWidth() &&
                startRow + dRow * (length - 1) >= 0 &&
                startCol + dCol * (length - 1) >= 0;
    }

    private int evaluateLine(int countPlayer1, int countPlayer2, int winCondition) {
        if (countPlayer1 > 0 && countPlayer2 > 0) {
            // Mixed line, no potential for either player
            return 0;
        } else if (countPlayer2 == winCondition) {
            // Player 2 wins
            return 10;
        } else if (countPlayer1 == winCondition) {
            // Player 1 wins
            return -10;
        } else {
            // Score based on potential to form a line
            // More pieces in line scores higher, less scores lower
            if (countPlayer2 > 0) {
                return countPlayer2 * 2; // You may adjust the multiplier
            } else if (countPlayer1 > 0) {
                return -countPlayer1 * 2; // You may adjust the multiplier
            }
            return 0;
        }
    }



}

