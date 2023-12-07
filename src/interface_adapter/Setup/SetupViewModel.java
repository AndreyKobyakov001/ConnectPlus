package interface_adapter.Setup;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SetupViewModel extends ViewModel {

    public String TITLE_LABEL = "Setup View";

    public final String FORFEIT_LABEL = "forfeit";
    public final String UNDO_LABEL = "undo";


    private SetupState state = new SetupState();

    public SetupViewModel() {
        super("setup view");
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public SetupState getState() {
        return state;
    }
}
