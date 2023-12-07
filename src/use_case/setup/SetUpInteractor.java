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
        outputData.setELODelta(getELODelta(winner));
        presenter.endGame(outputData);
    }

    private int getELODelta(int winner) {
        if (this.player2 instanceof Bot) {
            return this.botDiff * 19;
        } return 0;
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

    private int minimax(int depth, boolean isMaximizingPlayer, int maxDepth) {
        int winner = determineWinner(); // Assuming determineWinner returns 2 for Player 2 win, 1 for Player 1 win, and 0 for no winner
        if (depth == maxDepth || winner != 0) {
            return evaluateBoard();
        }

        int best;

        if (isMaximizingPlayer) {
            best = Integer.MIN_VALUE;

            for (int j = 0; j < board.getWidth(); j++) {
                if (board.getCell(0, j) == '?') {
                    int i = findLowestEmptyCell(j);
                    board.setCell(i, j, player2.getSymbol());

                    int score = minimax(depth + 1, false, maxDepth) - depth;
                    best = Math.max(best, score);

                    board.setCell(i, j, '?');
                }
            }
        } else {
            best = Integer.MAX_VALUE;

            for (int j = 0; j < board.getWidth(); j++) {
                if (board.getCell(0, j) == '?') {
                    int i = findLowestEmptyCell(j);
                    board.setCell(i, j, player1.getSymbol());

                    int score = minimax(depth + 1, true, maxDepth) + depth;
                    best = Math.min(best, score);

                    board.setCell(i, j, '?');
                }
            }
        }
        return best;
    }

    private int findLowestEmptyCell(int col) {
        int i = board.getHeight() - 1;
        while (i > 0 && board.getCell(i, col) != '?') {
            i--;
        }
        return i;
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
                int moveVal = minimax(1, false, botDiff);

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
        int winCondition = board.getWinCondition();

        // Check for immediate win or loss
        int player1WinScore = isWinningCondition(player1.getSymbol(), winCondition) ? -10 : 0;
        int player2WinScore = isWinningCondition(player2.getSymbol(), winCondition) ? 10 : 0;

        if (player1WinScore != 0 || player2WinScore != 0) {
            return player1WinScore + player2WinScore;
        }

        // Strategic evaluation
        int score = 0;
        score += scoreDirection(0, 1); // Horizontal
        score += scoreDirection(1, 0); // Vertical
        score += scoreDirection(1, 1); // Diagonal (positive slope)
        score += scoreDirection(1, -1); // Diagonal (negative slope)

        // Normalize the score to be within the range -10 to 10
        return Math.min(10, Math.max(-10, score));
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

    private int evaluateLine(int countPlayer1, int countPlayer2, int winCondition) {
        // Scoring logic for lines, adjusted to fit within the score range -10 to 10
        if (countPlayer1 > 0 && countPlayer2 > 0) {
            // Mixed line, no potential for either player
            return 0;
        } else if (countPlayer2 > 0) {
            // Player 2 (AI) has a potential line
            return (int)(5.0 * countPlayer2 / winCondition);
        } else if (countPlayer1 > 0) {
            // Player 1 has a potential line
            return -(int)(5.0 * countPlayer1 / winCondition);
        }
        return 0;
    }


    private boolean canFormLine(int startRow, int startCol, int dRow, int dCol, int length) {
        // Check if the line goes out of bounds
        return startRow + dRow * (length - 1) < board.getHeight() &&
                startCol + dCol * (length - 1) < board.getWidth() &&
                startRow + dRow * (length - 1) >= 0 &&
                startCol + dCol * (length - 1) >= 0;
    }

    private boolean isWinningCondition(char playerSymbol, int winCondition) {
        // Check all rows for win condition
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col <= board.getWidth() - winCondition; col++) {
                if (checkConsecutive(playerSymbol, row, col, 0, 1, winCondition)) {
                    return true;
                }
            }
        }

        // Check all columns for win condition
        for (int col = 0; col < board.getWidth(); col++) {
            for (int row = 0; row <= board.getHeight() - winCondition; row++) {
                if (checkConsecutive(playerSymbol, row, col, 1, 0, winCondition)) {
                    return true;
                }
            }
        }

        // Check diagonal (top-left to bottom-right)
        for (int row = 0; row <= board.getHeight() - winCondition; row++) {
            for (int col = 0; col <= board.getWidth() - winCondition; col++) {
                if (checkConsecutive(playerSymbol, row, col, 1, 1, winCondition)) {
                    return true;
                }
            }
        }

        // Check diagonal (bottom-left to top-right)
        for (int row = winCondition - 1; row < board.getHeight(); row++) {
            for (int col = 0; col <= board.getWidth() - winCondition; col++) {
                if (checkConsecutive(playerSymbol, row, col, -1, 1, winCondition)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkConsecutive(char playerSymbol, int startRow, int startCol, int dRow, int dCol, int length) {
        for (int i = 0; i < length; i++) {
            if (board.getCell(startRow + dRow * i, startCol + dCol * i) != playerSymbol) {
                return false;
            }
        }
        return true;
    }




//    private int evaluateBoard1(int difficulty) {
//        // Hard difficulty: More sophisticated evaluation
//        if (difficulty >= 5) {
//            return evaluateBoardHard();
//        }
//        // Easy difficulty: Simple evaluation
//        else {
//            return evaluateBoardEasy();
//        }
//    }

//    private int evaluateBoardHard() {
//        int winCondition = board.getWinCondition();
//        int score = 0;
//
//        // Check for immediate wins or losses
//        int winScore = checkForImmediateWins(player1.getSymbol(), player2.getSymbol(), winCondition);
//        if (winScore == Integer.MAX_VALUE || winScore == Integer.MIN_VALUE) return winScore; // Return -10 or 10 if a win or loss is detected
//
//        // Evaluate potential wins and threats
//        score += evaluatePotentialWins(player1.getSymbol(), player2.getSymbol(), winCondition);
//        score -= evaluatePotentialWins(player2.getSymbol(), player1.getSymbol(), winCondition);
//
//        // Normalize and return the score
//        return score;
//    }
//    private int checkForImmediateWins(char playerSymbol, char opponentSymbol, int winCondition) {
//        if (isWinningCondition(playerSymbol, winCondition)) return Integer.MIN_VALUE;
//        if (isWinningCondition(opponentSymbol, winCondition)) return Integer.MAX_VALUE;
//        return 0;
//    }
//    private boolean isWinningCondition(char playerSymbol, int winCondition) {
//        // Check all rows
//        for (int row = 0; row < board.getHeight(); row++) {
//            for (int col = 0; col <= board.getWidth() - winCondition; col++) {
//                if (checkLine(playerSymbol, row, col, 0, 1, winCondition)) {
//                    return true;
//                }
//            }
//        }
//
//        // Check all columns
//        for (int col = 0; col < board.getWidth(); col++) {
//            for (int row = 0; row <= board.getHeight() - winCondition; row++) {
//                if (checkLine(playerSymbol, row, col, 1, 0, winCondition)) {
//                    return true;
//                }
//            }
//        }
//
//        // Check diagonal (top-left to bottom-right)
//        for (int row = 0; row <= board.getHeight() - winCondition; row++) {
//            for (int col = 0; col <= board.getWidth() - winCondition; col++) {
//                if (checkLine(playerSymbol, row, col, 1, 1, winCondition)) {
//                    return true;
//                }
//            }
//        }
//
//        // Check diagonal (bottom-left to top-right)
//        for (int row = winCondition - 1; row < board.getHeight(); row++) {
//            for (int col = 0; col <= board.getWidth() - winCondition; col++) {
//                if (checkLine(playerSymbol, row, col, -1, 1, winCondition)) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }
//
//    private boolean checkLine(char playerSymbol, int startRow, int startCol, int dRow, int dCol, int length) {
//        for (int k = 0; k < length; k++) {
//            if (board.getCell(startRow + k * dRow, startCol + k * dCol) != playerSymbol) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//
//    private int evaluatePotentialWins(char playerSymbol, char opponentSymbol, int winCondition) {
//        int score = 0;
//
//        // Evaluate rows, columns, and diagonals for potential wins
//        score += evaluateLinesForPotentialWins(playerSymbol, opponentSymbol, winCondition, 0, 1); // Horizontal
//        score += evaluateLinesForPotentialWins(playerSymbol, opponentSymbol, winCondition, 1, 0); // Vertical
//        score += evaluateLinesForPotentialWins(playerSymbol, opponentSymbol, winCondition, 1, 1); // Diagonal (positive)
//        score += evaluateLinesForPotentialWins(playerSymbol, opponentSymbol, winCondition, 1, -1); // Diagonal (negative)
//
//        return score;
//    }
//
//    private int evaluateLinesForPotentialWins(char playerSymbol, char opponentSymbol, int winCondition, int dRow, int dCol) {
//        int lineScore = 0;
//
//        for (int row = 0; row < board.getHeight(); row++) {
//            for (int col = 0; col < board.getWidth(); col++) {
//                // Check if a line can be formed starting from this cell
//                if (canFormLine(row, col, dRow, dCol, winCondition)) {
//                    int playerCount = 0;
//                    int emptyCount = 0;
//
//                    for (int k = 0; k < winCondition; k++) {
//                        char cellSymbol = board.getCell(row + k * dRow, col + k * dCol);
//                        if (cellSymbol == playerSymbol) {
//                            playerCount++;
//                        } else if (cellSymbol == '?') {
//                            emptyCount++;
//                        }
//                    }
//
//                    // Evaluate line only if it is not blocked by the opponent
//                    if (playerCount + emptyCount == winCondition) {
//                        lineScore += scorePotentialLine(playerCount, winCondition);
//                    }
//                }
//            }
//        }
//
//        return lineScore;
//    }
//
//    private int scorePotentialLine(int playerCount, int winCondition) {
//        // Heuristic scoring for potential lines
//        if (playerCount == winCondition - 1) {
//            return 5; // One move away from winning
//        } else if (playerCount > 0) {
//            return playerCount; // Score based on the number of player's symbols in the line
//        }
//        return 0;
//    }




//    private int evaluateBoardEasy() {
//        int score = 0;
//
//        // Score for horizontal, vertical, and both diagonal directions
//        score += scoreDirection(0, 1); // Horizontal
//        score += scoreDirection(1, 0); // Vertical
//        score += scoreDirection(1, 1); // Diagonal (positive slope)
//        score += scoreDirection(1, -1); // Diagonal (negative slope)
//
//        return score;
//    }
//
//    private int scoreDirection(int dRow, int dCol) {
//        int winCondition = board.getWinCondition();
//        int tempScore = 0;
//
//        for (int i = 0; i < board.getHeight(); i++) {
//            for (int j = 0; j < board.getWidth(); j++) {
//                if (canFormLine(i, j, dRow, dCol, winCondition)) {
//                    int countPlayer1 = 0;
//                    int countPlayer2 = 0;
//
//                    for (int k = 0; k < winCondition; k++) {
//                        char cell = board.getCell(i + dRow * k, j + dCol * k);
//                        if (cell == player1.getSymbol()) {
//                            countPlayer1++;
//                        } else if (cell == player2.getSymbol()) {
//                            countPlayer2++;
//                        }
//                    }
//
//                    tempScore += evaluateLine(countPlayer1, countPlayer2, winCondition);
//                }
//            }
//        }
//
//        return tempScore;
//    }

//    private boolean canFormLine(int startRow, int startCol, int dRow, int dCol, int length) {
//        // Check if the line goes out of bounds
//        return startRow + dRow * (length - 1) < board.getHeight() &&
//                startCol + dCol * (length - 1) < board.getWidth() &&
//                startRow + dRow * (length - 1) >= 0 &&
//                startCol + dCol * (length - 1) >= 0;
//    }
//
//    private int evaluateLine(int countPlayer1, int countPlayer2, int winCondition) {
//        if (countPlayer1 > 0 && countPlayer2 > 0) {
//            // Mixed line, no potential for either player
//            return 0;
//        } else if (countPlayer2 == winCondition) {
//            // Player 2 wins
//            return 10;
//        } else if (countPlayer1 == winCondition) {
//            // Player 1 wins
//            return -10;
//        } else {
//            // Score based on potential to form a line
//            // More pieces in line scores higher, less scores lower
//            if (countPlayer2 > 0) {
//                return countPlayer2 * 2; // You may adjust the multiplier
//            } else if (countPlayer1 > 0) {
//                return -countPlayer1 * 2; // You may adjust the multiplier
//            }
//            return 0;
//        }
//    }



}

