package use_case.check_win;

import Entities.WinChecker;

public class CheckWinInteractor {
    private final CheckWinInputBoundary input;
    private final CheckWinOutputBoundary output;

    public CheckWinInteractor(CheckWinInputBoundary input, CheckWinOutputBoundary output) {
        this.input = input;
        this.output = output;
    }

    public void execute() {
        WinChecker checkWin = new WinChecker(input.getPiecesToConnect(), input.getBoard());
        boolean isWin = checkWin.checkWin(input.getPlayerSymbol());
//   #TODO: make some indication of victory, when it is present.
    }
}
