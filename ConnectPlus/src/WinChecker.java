public class WinChecker {
    private int piecesToConnect;
    private char[][] board;

    public WinChecker(int piecesToConnect, char[][] board) {
        this.piecesToConnect = piecesToConnect;
        this.board = board;
    }

    public boolean checkWin(int row, int col, char playerSymbol) {
        int length = board[0].length;
        int height = board.length;

        return checkVertical(row, col, playerSymbol, length, height) ||
                checkHorizontal(row, col, playerSymbol, length, height) ||
                checkDiagonal(row, col, playerSymbol, length, height);
    }

    private boolean checkVertical(int row, int col, char playerSymbol, int length, int height) {
        int count = 1;

        // Check upwards
        for (int i = row - 1; i >= 0 && board[i][col] == playerSymbol; i--) {
            count++;
        }

        // Check downwards
        for (int i = row + 1; i < height && board[i][col] == playerSymbol; i++) {
            count++;
        }

        return count >= piecesToConnect;
    }

    private boolean checkHorizontal(int row, int col, char playerSymbol, int length, int height) {
        int count = 1;

        // Check left
        for (int i = col - 1; i >= 0 && board[row][i] == playerSymbol; i--) {
            count++;
        }

        // Check right
        for (int i = col + 1; i < length && board[row][i] == playerSymbol; i++) {
            count++;
        }

        return count >= piecesToConnect;
    }

    private boolean checkDiagonal(int row, int col, char playerSymbol, int length, int height) {
        return checkDiagonalNWSE(row, col, playerSymbol, length, height) ||
                checkDiagonalNESW(row, col, playerSymbol, length, height);
    }

    private boolean checkDiagonalNWSE(int row, int col, char playerSymbol, int length, int height) {
        int count = 1;

        // Check northwest
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0 && board[i][j] == playerSymbol; i--, j--) {
            count++;
        }

        // Check southeast
        for (int i = row + 1, j = col + 1; i < height && j < length && board[i][j] == playerSymbol; i++, j++) {
            count++;
        }

        return count >= piecesToConnect;
    }

    private boolean checkDiagonalNESW(int row, int col, char playerSymbol, int length, int height) {
        int count = 1;

        // Check northeast
        for (int i = row - 1, j = col + 1; i >= 0 && j < length && board[i][j] == playerSymbol; i--, j++) {
            count++;
        }

        // Check southwest
        for (int i = row + 1, j = col - 1; i < height && j >= 0 && board[i][j] == playerSymbol; i++, j--) {
            count++;
        }

        return count >= piecesToConnect;
    }
}
