public class Board {
    private char[][] board;
    private int length;
    private int height;
    private int piecesToConnect;

    public Board(int length, int height, int piecesToConnect) {
        if (length < 2 || length > 10 || height < 2 || height > 10 || piecesToConnect < 2 || piecesToConnect > 9) {
            throw new IllegalArgumentException("Invalid board dimensions or pieces to connect.");
        }

        this.length = length;
        this.height = height;
        this.piecesToConnect = piecesToConnect;

        initializeBoard();
    }

    private void initializeBoard() {
        board = new char[height][length];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                board[i][j] = '?'; // Initialize the board with '?' for empty squares
            }
        }
    }

    public void displayBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean makeMove(int column, char playerSymbol) {
        if (column < 0 || column >= length) {
            return false; // Invalid column
        }

        int row = findEmptyRow(column);
        if (row != -1) {
            board[row][column] = playerSymbol;
            return true;
        }

        return false; // Column is full
    }

    int findEmptyRow(int column) {
        for (int i = height - 1; i >= 0; i--) {
            if (board[i][column] == '?') {
                return i;
            }
        }
        return -1; // Column is full
    }

    public boolean isFull() {
        int totalMoves = length * height;
        int currentMoves = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                if (board[i][j] != '?') {
                    currentMoves++;
                }
            }
        }

        return currentMoves == totalMoves;
    }

    public char[][] getBoard() {
        return board;
    }
}
