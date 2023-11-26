package use_case.check_win;

import Entities.Board;

public interface CheckWinInputBoundary {
    int getPiecesToConnect();
    char getPlayerSymbol();
    char[][] getBoard();
}


//public class CheckWinInputBoundary {

//    private final char[][] board;
//    private final int piecesToConnect;
//    private final char playerSymbol;
//
//    public CheckWinInputBoundary(Board board, int piecesToConnect, char playerSymbol) {
//        this.board = board.getBoard();
//        this.piecesToConnect = piecesToConnect;
//        this.playerSymbol = playerSymbol;
//    }
//
//    public int getPiecesToConnect() {
//        return piecesToConnect;
//    }
//
//    public char getPlayerSymbol() {
//        return playerSymbol;
//    }
//
//    public char[][] getBoard() {
//        return board;
//    }
//}

