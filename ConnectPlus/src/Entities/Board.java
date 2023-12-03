package Entities;

import java.util.*;

public class Board {
    private char[][] board;
    private int width;
    private int height;
    private int winCondition;

//BOT - UNDER CONSTRUCTION

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getWinCondition() {
        return winCondition;
    }


    public int evaluate() {
        int playerScore = evaluatePlayer('X');
        int botScore = evaluatePlayer('O');

        return playerScore - botScore;
    }

    private int evaluatePlayer(char playerSymbol) {
        int score = 0;

        // Check horizontal connections
        for (int i = 0; i < height; i++) {
            for (int j = 0; j <= width - winCondition; j++) {
                int count = 0;
                for (int k = 0; k < winCondition; k++) {
                    if (board[i][j + k] == playerSymbol) {
                        count++;
                    }
                }
                score += count * count;
            }
        }

        // Check vertical connections
        for (int i = 0; i <= height - winCondition; i++) {
            for (int j = 0; j < width; j++) {
                int count = 0;
                for (int k = 0; k < winCondition; k++) {
                    if (board[i + k][j] == playerSymbol) {
                        count++;
                    }
                }
                score += count * count;
            }
        }

        // Check diagonal (positive slope) connections
        for (int i = 0; i <= height - winCondition; i++) {
            for (int j = 0; j <= width - winCondition; j++) {
                int count = 0;
                for (int k = 0; k < winCondition; k++) {
                    if (board[i + k][j + k] == playerSymbol) {
                        count++;
                    }
                }
                score += count * count;
            }
        }

        // Check diagonal (negative slope) connections
        for (int i = winCondition - 1; i < height; i++) {
            for (int j = 0; j <= width - winCondition; j++) {
                int count = 0;
                for (int k = 0; k < winCondition; k++) {
                    if (board[i - k][j + k] == playerSymbol) {
                        count++;
                    }
                }
                score += count * count;
            }
        }

        return score;
    }

    public List<Integer> generateLegalMoves() {
        List<Integer> legalMoves = new ArrayList<>();

        for (int column = 0; column < width; column++) {
            if (findEmptyRow(column) != -1) {
                legalMoves.add(column);
            }
        }

        return legalMoves;
    }




//BOT - UNDER CONSTRUCTION


    public Board(int length, int height, int piecesToConnect) {

        this.width = length;
        this.height = height;
        this.winCondition = piecesToConnect;


        initializeBoard();
    }

    private void initializeBoard() {
        board = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = '?'; // Initialize the board with '?' for empty squares
            }
        }
    }

    public BoardState getState() {
        return new BoardState(board);
    }

    public void displayBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean makeMove(int column, char playerSymbol) {
        if (column < 0 || column >= width) {
            return false; // Invalid column
        }

        int row = findEmptyRow(column);
        if (row != -1) {
            board[row][column] = playerSymbol;
            return true;
        }

        return false; // Column is full
    }

//    public void undoMove(int move) {
//        if (!moveStack.isEmpty()) {
////            System.out.printf("UNDOING");
//            int lastMove = (int) moveStack.pop(); // Pop the most recent move
//            int row = findEmptyRow(lastMove); // Find the row for that column
//            if (row != -1) {
//                board[row][lastMove] = '?'; // Revert the slot to empty ('?')
//            }
//        }
//    }

    int findEmptyRow(int column) {
        for (int i = height - 1; i >= 0; i--) {
            if (board[i][column] == '?') {
                return i;
            }
        }
        return -1; // Column is full
    }

    public boolean isFull() {
        int totalMoves = width * height;
        int currentMoves = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
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

    public void setBoard(char[][] board) {
        this.board = board;
    }
    public boolean isValidMove(int column) {
        return findEmptyRow(column) != -1;
    }

    public char getCell(int row, int col) {
        return board[row][col];
    }

    public void setCell(int row, int col, char symbol) {
        board[row][col] = symbol;
    }
}

