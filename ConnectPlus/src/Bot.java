import java.util.ArrayList;
import java.util.List;

public class Bot {
    private char botSymbol;
    private char opponentSymbol;
    private int piecesToConnect;
    private char[][] board;

    public Bot(char botSymbol, char opponentSymbol, int piecesToConnect, char[][] board) {
        this.botSymbol = botSymbol;
        this.opponentSymbol = opponentSymbol;
        this.piecesToConnect = piecesToConnect;
        this.board = board;
    }

    public int findBestMove() {
        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;

        for (int col = 0; col < board[0].length; col++) {
            if (isValidMove(col)) {
                int row = dropPiece(col, botSymbol);
                int score = minimax(0, false);
                removePiece(col, row);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = col;
                }
            }
        }

        return bestMove;
    }

    private int minimax(int depth, boolean isMaximizingPlayer) {
        if (depth == 0) {
            return evaluateBoard();
        }

        if (isMaximizingPlayer) {
            int bestScore = Integer.MIN_VALUE;
            for (int col = 0; col < board[0].length; col++) {
                if (isValidMove(col)) {
                    int row = dropPiece(col, botSymbol);
                    int score = minimax(depth - 1, false);
                    removePiece(col, row);
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int col = 0; col < board[0].length; col++) {
                if (isValidMove(col)) {
                    int row = dropPiece(col, opponentSymbol);
                    int score = minimax(depth - 1, true);
                    removePiece(col, row);
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    private int evaluateBoard() {
        int botScore = 0;
        int opponentScore = 0;

        // Evaluate horizontal positions
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col <= board[0].length - piecesToConnect; col++) {
                int botCount = 0;
                int opponentCount = 0;
                for (int k = 0; k < piecesToConnect; k++) {
                    if (board[row][col + k] == botSymbol) {
                        botCount++;
                    } else if (board[row][col + k] == opponentSymbol) {
                        opponentCount++;
                    }
                }
                botScore += scorePosition(botCount, opponentCount);
                opponentScore += scorePosition(opponentCount, botCount);
            }
        }

        // Evaluate vertical positions
        for (int col = 0; col < board[0].length; col++) {
            for (int row = 0; row <= board.length - piecesToConnect; row++) {
                int botCount = 0;
                int opponentCount = 0;
                for (int k = 0; k < piecesToConnect; k++) {
                    if (board[row + k][col] == botSymbol) {
                        botCount++;
                    } else if (board[row + k][col] == opponentSymbol) {
                        opponentCount++;
                    }
                }
                botScore += scorePosition(botCount, opponentCount);
                opponentScore += scorePosition(opponentCount, botCount);
            }
        }

        // Add more evaluation logic for diagonals if needed

        return botScore - opponentScore;
    }

    // Assign scores to positions based on piece counts
    private int scorePosition(int playerCount, int opponentCount) {
        if (playerCount == piecesToConnect) {
            // Bot wins
            return 1000;
        } else if (opponentCount == piecesToConnect) {
            // Opponent wins
            return -1000;
        } else if (playerCount == piecesToConnect - 1 && opponentCount == 0) {
            // Bot has the potential to win next turn
            return 100;
        } else if (opponentCount == piecesToConnect - 1 && playerCount == 0) {
            // Opponent has the potential to win next turn
            return -100;
        } else {
            // No immediate win or threat
            return 0;
        }
    }


    private boolean isValidMove(int col) {
        return board[0][col] == ' ';
    }

    private int dropPiece(int col, char symbol) {
        for (int row = board.length - 1; row >= 0; row--) {
            if (board[row][col] == ' ') {
                board[row][col] = symbol;
                return row;
            }
        }
        return -1; // Column is full
    }

    private void removePiece(int col, int row) {
        board[row][col] = ' ';
    }
}
