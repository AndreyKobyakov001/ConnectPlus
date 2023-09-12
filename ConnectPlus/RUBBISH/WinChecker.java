public class WinChecker {
    private int piecesToConnect;
    private char[][] board;

    public WinChecker(int piecesToConnect, char[][] board) {
        this.piecesToConnect = piecesToConnect;
        this.board = board;
    }

    public boolean checkWin(int lastRow, int col, char playerSymbol) {
        int length = board[0].length;
        int height = board.length;

        return checkHorizontal(lastRow, col, playerSymbol, length, height) ||
                checkVertical(lastRow, col, playerSymbol, length, height) ||
                checkDiagonal(lastRow, col, playerSymbol, length, height);
    }

    private boolean checkVertical(int lastRow, int col, char playerSymbol, int length, int height) {
        int count = 1;
        int up = lastRow - 1;
        int down = lastRow + 1;

        // Check upward
        while (up >= 0 && board[up][col] == playerSymbol) {
            count++;
            up--;
        }

        // Check downward
        while (down < height && board[down][col] == playerSymbol) {
            count++;
            down++;
        }

        return count >= piecesToConnect;
    }

    private boolean checkHorizontal(int lastRow, int col, char playerSymbol, int length, int height) {
        int count = 1;
        int left = col - 1;
        int right = col + 1;

        // Check left
        while (left >= 0 && board[lastRow][left] == playerSymbol) {
            count++;
            left--;
        }

        // Check right
        while (right < length && board[lastRow][right] == playerSymbol) {
            count++;
            right++;
        }

        return count >= piecesToConnect;
    }

    private boolean checkDiagonal(int lastRow, int col, char playerSymbol, int length, int height) {
        return checkDiagonalNWSE(lastRow, col, playerSymbol, length, height) ||
                checkDiagonalNESW(lastRow, col, playerSymbol, length, height);
    }

    private boolean checkDiagonalNWSE(int lastRow, int col, char playerSymbol, int length, int height) {
        int count = 1;
        int up = lastRow - 1;
        int down = lastRow + 1;
        int left = col - 1;
        int right = col + 1;

        // Check northwest to southeast
        while (up >= 0 && left >= 0 && board[up][left] == playerSymbol) {
            count++;
            up--;
            left--;
        }

        while (down < height && right < length && board[down][right] == playerSymbol) {
            count++;
            down++;
            right++;
        }

        return count >= piecesToConnect;
    }

    private boolean checkDiagonalNESW(int lastRow, int col, char playerSymbol, int length, int height) {
        int count = 1;
        int up = lastRow - 1;
        int down = lastRow + 1;
        int left = col - 1;
        int right = col + 1;

        // Check northeast to southwest
        while (up >= 0 && right < length && board[up][right] == playerSymbol) {
            count++;
            up--;
            right++;
        }

        while (down < height && left >= 0 && board[down][left] == playerSymbol) {
            count++;
            down++;
            left--;
        }

        return count >= piecesToConnect;
    }
}
