package interface_adapter.Home;

import interface_adapter.ViewModel;
import interface_adapter.login.LoginState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class EndViewModel extends ViewModel {
    public String TITLE_LABEL = "end view";

    public final String RESTART_SAME_LABEL = "restart (same settings)";
    public final String HOME_LABEL = "home";

    public final String ELO_LABEL = "Your ELO increase is: ";

    private EndViewState state = new EndViewState();

    public EndViewModel() {
        super("end view");
    }

    public void setState(EndViewState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the Signup Presenter will call to let the ViewModel know
    // to alert the View
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public EndViewState getState() {
        return state;
    }
}


