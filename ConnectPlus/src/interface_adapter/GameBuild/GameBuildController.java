package interface_adapter.GameBuild;

import use_case.GameBuild.GameBuildInputBoundary;
import use_case.GameBuild.GameBuildInputData;

public class GameBuildController {

    final GameBuildInputBoundary gameBuildUseCaseInteractor;
    public GameBuildController(GameBuildInputBoundary gameBuildUseCaseInteractor) {
        this.gameBuildUseCaseInteractor = gameBuildUseCaseInteractor;
    }

    public void execute(GameBuildInputData gameBuildInputData) {
        gameBuildUseCaseInteractor.execute(gameBuildInputData);
    }

    public void back() {
        gameBuildUseCaseInteractor.back();
    }
}
