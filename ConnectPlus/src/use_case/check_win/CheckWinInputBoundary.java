package use_case.check_win;

import Entities.Board;

public class CheckWinInputBoundary {
    private char[][] board;
    private int piecesToConnect;
    private char playerSymbol;

    public CheckWinInputBoundary(Board board, int piecesToConnect, char playerSymbol) {
        this.board = board.getBoard();
        this.piecesToConnect = piecesToConnect;
        this.playerSymbol = playerSymbol;
    }

    public int getPiecesToConnect() {
        return piecesToConnect;
    }

    public char getPlayerSymbol() {
        return playerSymbol;
    }

    public char[][] getBoard() {
        return board;
    }
}
