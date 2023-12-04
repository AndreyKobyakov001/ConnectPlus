package Entities;

import java.util.ArrayList;
import java.util.List;

public class BoardState{
    private char[][] currState;

    public BoardState(char[][] currState) {
        this.currState = currState;

    }

    public char[][] getCurrState() {
        return currState;
    }

    public void getCurrState(char[][] currState) {
        this.currState = currState;
    }

    //TODO: move generateLegalMoves() and its associated findEmptyRow() to this class


}
