package interface_adapter.GameBuild;

import use_case.GameBuild.GameBuildInputBoundary;
import use_case.GameBuild.GameBuildInputData;
import use_case.GameBuild.GameBuildOutputData;

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
