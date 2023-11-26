//package use_case.check_win;
//
//import Entities.WinChecker;
//
//public class CheckWinInteractor {
//    private final CheckWinInputBoundary input;
//    private final CheckWinOutputBoundary output;
//
//    public CheckWinInteractor(CheckWinInputBoundary input, CheckWinOutputBoundary output) {
//        this.input = input;
//        this.output = output;
//    }
//
//    public void execute() {
//        WinChecker checkWin = new WinChecker(input.getPiecesToConnect(), input.getBoard());
//        boolean isWin = checkWin.checkWin(input.getPlayerSymbol());
////   #TODO: make some indication of victory, when it is present.
//    }
//}

package use_case.check_win;

import Entities.WinChecker;

public class CheckWinInteractor implements CheckWinInputBoundary {
    private final char[][] board;
    private final int piecesToConnect;
    private final char playerSymbol;

    public CheckWinInteractor(CheckWinInputBoundary input, CheckWinOutputBoundary output) {
        this.board = input.getBoard();
        this.piecesToConnect = input.getPiecesToConnect();
        this.playerSymbol = input.getPlayerSymbol();
    }

    public void execute() {
        WinChecker checkWin = new WinChecker(piecesToConnect, board);
        boolean isWin = checkWin.checkWin(playerSymbol);
        // #TODO: Make some indication of victory when it is present.
    }

    @Override
    public int getPiecesToConnect() {
        return piecesToConnect;
    }

    @Override
    public char getPlayerSymbol() {
        return playerSymbol;
    }

    @Override
    public char[][] getBoard() {
        return board;
    }
}
