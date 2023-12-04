package interface_adapter.Setup;

import use_case.GameBuild.GameBuildOutputData;
import use_case.setup.SetupInputBoundary;

public class SetupController {

        final SetupInputBoundary setupUseCaseInteractor;
        public SetupController(SetupInputBoundary setupUseCaseInteractor) {
            this.setupUseCaseInteractor = setupUseCaseInteractor;
        }

        public void startGame(GameBuildOutputData gameBuildOutputData) {
            this.setupUseCaseInteractor.startGame(gameBuildOutputData);
        }

        public void makeMove(int column, String playerName) {
            this.setupUseCaseInteractor.makeMove(column, playerName);
        }

        public void undoMove() {
            this.setupUseCaseInteractor.undoMove();
        }

        public void forfeitGame() {
            setupUseCaseInteractor.forfeitGame();
        }







}
