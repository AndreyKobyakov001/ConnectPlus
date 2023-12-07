package Entities;

public class Bot extends Player {
    private int difficulty;

    public Bot(String name, Character symbol, int difficulty) {
        super(name, symbol);
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

}
