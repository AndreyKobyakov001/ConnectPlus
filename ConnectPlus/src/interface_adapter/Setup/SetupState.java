package interface_adapter.Setup;

public class SetupState {

    private boolean isPVP;
    private int height;
    private int width;
    private int botDiff;
    private String player2Name;

    private char[][] boardState;

    private int score1;

    private int score2;

    private boolean isPlayer1Turn; //its initial will dictate who goes first

    private String illegalMoveError;

    private String player1Name;
    private boolean startGame = false;
    private int winCond;
    private int result;
    public SetupState() {}

    public SetupState(SetupState copy) {
        height = copy.height;
        width = copy.width;
        botDiff = copy.botDiff;
        player1Name = copy.player1Name;
        player2Name = copy.player2Name;
        boardState = copy.boardState;
        illegalMoveError = copy.illegalMoveError;
        isPlayer1Turn = copy.isPlayer1Turn;
        winCond = copy.winCond;
        result = copy.result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public void setWinCond(int winCond) {
        this.winCond = winCond;
    }

    public int getWinCond() {
        return winCond;
    }
    public void setStartGame(boolean startGame) {
        this.startGame = startGame;
    }

    public boolean getStartGame() {
        return startGame;
    }
    //setters and getters for all values above

    public String getPlayer1Name() {
        return player1Name;
    }
    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getIllegalMoveError() {
        return illegalMoveError;
    }

    public void setIllegalMoveError(String illegalMoveError) {
        this.illegalMoveError = illegalMoveError;
    }


    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getBotDiff() {
        return botDiff;
    }

    public void setBotDiff(int botDiff) {
        this.botDiff = botDiff;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public char[][] getBoardState() {
        return boardState;
    }

    public void setBoardState(char[][] boardState) {
        this.boardState = boardState;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public boolean getIsPlayer1Turn() {
        return isPlayer1Turn;
    }

    public void setIsPlayer1Turn(boolean isPlayer1Turn) {
        this.isPlayer1Turn = isPlayer1Turn;
    }
}
