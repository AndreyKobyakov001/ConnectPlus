package use_case.setup;

import Entities.Board;
import Entities.Player;
import Entities.Bot;
import Entities.WinChecker;
import use_case.GameBuild.GameBuildOutputData;

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
                new Bot("Bot", 'X', setupInputData.getBotDifficulty());
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

        // Save current state before making a move
        board.makeMove(column, currentPlayer.getSymbol());
        previousStates.push(board.getBoard());
        winChecker.setBoard(board.getBoard());
        if (determineWinner() != -1 || board.isFull()) {
            endGame();
        } else {
            isPlayer1Turn = !isPlayer1Turn;
            updatePresenterWithBoardState();
            if (!isPlayer1Turn && player2 instanceof Bot) {
                makeBotMove();
            }
        }
    }


    @Override
    public void undoMove() {
        if (previousStates.size() > 1) { // Ensure there's a previous state
            previousStates.pop(); // Remove the current state
            char[][] prevState = previousStates.peek(); // Get the previous state
            board.setBoard(prevState); // Revert to the previous state
            isPlayer1Turn = !isPlayer1Turn; // Switch turn
            updatePresenterWithBoardState();
        }
    }

    @Override
    public void forfeitGame() {
        return;
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
        presenter.updateBoard(outputData);
    }

    private void updatePresenterWithStartGame() {
        SetupOutputData outputData = createOutputData();
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
    private void makeBotMove() {
        int bestVal = Integer.MIN_VALUE;
        int bestMove = -1;

        // Traverse all cells, evaluate minimax function for all empty cells
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                // Check if cell is empty
                if (board.getCell(i, j) == ' ') {
                    // Make the move
                    board.setCell(i, j, player2.getSymbol());

                    // Compute evaluation function for this move
                    int moveVal = minimax(0, false);

                    // Undo the move
                    board.setCell(i, j, ' ');

                    // If the value of the current move is greater than the best value
                    if (moveVal > bestVal) {
                        bestMove = j; // Assuming the move is identified by the column index
                        bestVal = moveVal;
                    }
                }
            }
        }
        makeMove(bestMove, player2.getName());
    }

    private int evaluateBoard() {
        int score = 0;

        // Check horizontal connections
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j <= board.getWidth() - board.getWinCondition(); j++) {
                int count = 0;
                for (int k = 0; k < board.getWinCondition(); k++) {
                    if (board.getCell(i, j + k) == player1.getSymbol()) {
                        count++;
                    } else if (board.getCell(i, j + k) == player2.getSymbol()) {
                        count--;
                    }
                }
                score += count * count;
            }
        }

        // Check vertical connections
        for (int i = 0; i <= board.getHeight() - board.getWinCondition(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                int count = 0;
                for (int k = 0; k < board.getWinCondition(); k++) {
                    if (board.getCell(i + k, j) == player1.getSymbol()) {
                        count++;
                    } else if (board.getCell(i + k, j) == player2.getSymbol()) {
                        count--;
                    }
                }
                score += count * count;
            }
        }

        // Check diagonal (positive slope) connections
        for (int i = 0; i <= board.getHeight() - board.getWinCondition(); i++) {
            for (int j = 0; j <= board.getWidth() - board.getWinCondition(); j++) {
                int count = 0;
                for (int k = 0; k < board.getWinCondition(); k++) {
                    if (board.getCell(i + k, j + k) == player1.getSymbol()) {
                        count++;
                    } else if (board.getCell(i + k, j + k) == player2.getSymbol()) {
                        count--;
                    }
                }
                score += count * count;
            }
        }

        // Check diagonal (negative slope) connections
        for (int i = board.getWinCondition() - 1; i < board.getHeight(); i++) {
            for (int j = 0; j <= board.getWidth() - board.getWinCondition(); j++) {
                int count = 0;
                for (int k = 0; k < board.getWinCondition(); k++) {
                    if (board.getCell(i - k, j + k) == player1.getSymbol()) {
                        count++;
                    } else if (board.getCell(i - k, j + k) == player2.getSymbol()) {
                        count--;
                    }
                }
            }
        }
        return score;
    }


}

