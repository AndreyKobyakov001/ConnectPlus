package Entities;

public class Player {
    private final Character symbol;
    private String name;
    private BoardState boardState;


    public Player(String name, BoardState boardState, Character symbol) {
        this.name = name;
        this.boardState = boardState;
        this.symbol = symbol;
    }

    public BoardState move(BoardState boardState) {
        //prompt user to make a move

//        boardState legalMoves = boardState.getLegalMoves();
        //TODO: check moves, then make moves for board state.
    }
}
