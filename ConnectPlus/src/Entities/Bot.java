package Entities;

public class Bot extends Player{
    private int difficulty;

    public Bot(String name, BoardState boardState, Character symbol, int difficulty) {
        super(name, boardState, symbol);
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void move() {

    }
}


// TODO: apply the bot classes here.