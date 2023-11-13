package Entities;

public class WinChecker {
    private int piecesToConnect;
    private char[][] board;

    public WinChecker(int piecesToConnect, char[][] board) {
        this.piecesToConnect = piecesToConnect;//This might not be a good fix.
        this.board = board;
    }

    public boolean checkWin(char playerSymbol) {
        int length = board[0].length;
        int height = board.length;

        return checkHorizontal(playerSymbol, length, height) ||
                checkVertical(playerSymbol, length, height) ||
                checkDiagonal(playerSymbol, length, height);
    }

    private boolean checkVertical(char playerSymbol, int length, int height) {
        for (int col = 0; col < length; col++) {
            int count = 0;
            for (int row = 0; row < height; row++) {
                if (board[row][col] == playerSymbol) {
                    count++;
                    if (count == piecesToConnect) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }
        return false;
    }

    private boolean checkHorizontal(char playerSymbol, int length, int height) {
        for (int row = 0; row < height; row++) {
            int count = 0;
            for (int col = 0; col < length; col++) {
                if (board[row][col] == playerSymbol) {
                    count++;
                    if (count == piecesToConnect) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal(char playerSymbol, int length, int height) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < length; col++) {
                if (checkDiagonalNWSE(playerSymbol, row, col, length, height) ||
                        checkDiagonalNESW(playerSymbol, row, col, length, height)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalNWSE(char playerSymbol, int row, int col, int length, int height) {
        int count = 0;
        while (row < height && col < length) {
            if (board[row][col] == playerSymbol) {
                count++;
                if (count == piecesToConnect) {
                    return true;
                }
            } else {
                count = 0;
            }
            row++;
            col++;
        }
        return false;
    }

    private boolean checkDiagonalNESW(char playerSymbol, int row, int col, int length, int height) {
        int count = 0;
        while (row < height && col >= 0) {
            if (board[row][col] == playerSymbol) {
                count++;
                if (count == piecesToConnect) {
                    return true;
                }
            } else {
                count = 0;
            }
            row++;
            col--;
        }
        return false;
    }
}
