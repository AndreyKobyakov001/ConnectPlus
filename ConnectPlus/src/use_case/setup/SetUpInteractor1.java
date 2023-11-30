package use_case.setup;

import Entities.*;
import use_case.GameBuild.GameBuildOutputData;

public class SetUpInteractor1 implements SetupInputBoundary{

    final SetupOutputBoundary setupPresenter;

    private Player ownPlayer;

    private Player opponent;
    private BoardState state;

    private boolean isTurn = true; // true if it is the player's turn,
    // false if it is the opponent's turn. Default is player goes first
    private Board board;
    private boolean isWon = false; // true if the game is won, false otherwise

    private BoardState[] states; //Implement undo here.



    private int score;

    public SetUpInteractor1(SetupOutputBoundary setupPresenter) {
        this.setupPresenter = setupPresenter;
    }

    public void startGame(GameBuildOutputData setupInputData) {
        this.board = new Board(setupInputData.getHeight(), setupInputData.getWidth(), setupInputData.getWinCondition());
        this.state = this.board.getState();
        this.ownPlayer = new Player("You", this.state, 'X');
        if (setupInputData.getBotDifficulty() == -1) {
            this.opponent = new Player("Opponent", this.state, 'O');
        } else {
            this.opponent = new Bot("Bot", this.state,
                    'O', setupInputData.getBotDifficulty());
        }
        this.score = 0;
        SetupOutputData outputData = new SetupOutputData(setupInputData.getHeight(), setupInputData.getWidth(),
                setupInputData.getWinCondition(), setupInputData.getBotDifficulty(), false); //false == not won
        setupPresenter.startGame(outputData);
        playGame(outputData);
    }

    public void playGame(SetupOutputData outputData) {

        while (!isWon) { //TODO: win checker
            //  undo can run asynchronously, given there's states > 1 ^ there's
            // undos left, and the player wants to undo.
            if (isTurn) {
                //TODO: player make the moves,
                //  given the legal moves in states, and return the new state.
                this.state = ownPlayer.move(this.state);
                // update the score

            } else {

                this.state = opponent.move(this.state);
            }
            isTurn = !isTurn;
            //TODO: update states
            setupPresenter.updateBoard(outputData);
        }
        if (isTurn) {
            outputData.setWonOrLost(false);
        } else {
            outputData.setWonOrLost(true);
        }
        setupPresenter.endGame(outputData);
    }








}
