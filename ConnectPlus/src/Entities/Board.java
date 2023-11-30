package Entities;

import java.util.*;

public class Board {
    private char[][] board;
    private int length;
    private int height;
    private int piecesToConnect;

//BOT - UNDER CONSTRUCTION

    public int getLength() {
        return length;
    }
    public int getHeight() {
        return height;
    }
    public int getPieces() {
        return piecesToConnect;
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
            for (int j = 0; j <= length - piecesToConnect; j++) {
                int count = 0;
                for (int k = 0; k < piecesToConnect; k++) {
                    if (board[i][j + k] == playerSymbol) {
                        count++;
                    }
                }
                score += count * count;
            }
        }

        // Check vertical connections
        for (int i = 0; i <= height - piecesToConnect; i++) {
            for (int j = 0; j < length; j++) {
                int count = 0;
                for (int k = 0; k < piecesToConnect; k++) {
                    if (board[i + k][j] == playerSymbol) {
                        count++;
                    }
                }
                score += count * count;
            }
        }

        // Check diagonal (positive slope) connections
        for (int i = 0; i <= height - piecesToConnect; i++) {
            for (int j = 0; j <= length - piecesToConnect; j++) {
                int count = 0;
                for (int k = 0; k < piecesToConnect; k++) {
                    if (board[i + k][j + k] == playerSymbol) {
                        count++;
                    }
                }
                score += count * count;
            }
        }

        // Check diagonal (negative slope) connections
        for (int i = piecesToConnect - 1; i < height; i++) {
            for (int j = 0; j <= length - piecesToConnect; j++) {
                int count = 0;
                for (int k = 0; k < piecesToConnect; k++) {
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

        for (int column = 0; column < length; column++) {
            if (findEmptyRow(column) != -1) {
                legalMoves.add(column);
            }
        }

        return legalMoves;
    }




//BOT - UNDER CONSTRUCTION


    public Board(int length, int height, int piecesToConnect) {

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

    public BoardState getState() {
        return new BoardState(board);
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

