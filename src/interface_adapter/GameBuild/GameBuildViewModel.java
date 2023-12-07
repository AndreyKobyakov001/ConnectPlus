package interface_adapter.GameBuild;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameBuildViewModel extends ViewModel {

    public final String TITLE_TEXT = "Game Build";
    public final String BACK_BUTTON_TEXT = "Back";
    public final String PLAY_BUTTON_TEXT = "Play";

    public final int MIN_HEIGHT = 3;
    public final int MAX_HEIGHT = 10;
    public final int DEF_HEIGHT = 5;
    public final String HEIGHT_LABEL = "Height";
    public final int MIN_WIDTH = 3;
    public final int MAX_WIDTH = 10;
    public final int DEF_WIDTH = 5;
    public final String WIDTH_LABEL = "Width";

    public final int MIN_BOT_DIFF = 1;
    public final int MAX_BOT_DIFF = 10;
    public final int DEF_BOT_DIFF = 5;
    public final String BOT_DIFF_LABEL = "Bot Difficulty";

    public final int MIN_WIN_COND = 2;
    public final int MAX_WIN_COND = 9;
    public final int DEF_WIN_COND = 2;
    public final String WIN_COND_LABEL = "Win Condition";



    private GameBuildState state = new GameBuildState();

    public GameBuildViewModel() {
        super("game build");
    }

    public void setState(GameBuildState state) {
        this.state = state;
    }
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);


    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }


    public GameBuildState getState() {
        return state;
    }
}
