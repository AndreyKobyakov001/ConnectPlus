package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Game {
    public static Scanner scanner = new Scanner(System.in);
    public static List<Integer> moves = new ArrayList<>();
    public static Board reconstruct(Board board, List<Integer> moves) {
        char currentPlayer = 'X';
        Board newBoard = new Board(board.getHeight(), board.getLength(), board.getPieces());
        moves.remove(moves.size() - 1);
        for (int move : moves) {
            if (newBoard.makeMove(move, currentPlayer)) {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            } else {
                System.out.println("Invalid move during reconstruction.");
                break;
            }
        }
        return newBoard;
    }
}