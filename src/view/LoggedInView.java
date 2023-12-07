package view;

import interface_adapter.logged_in.LoggedInController;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoggedInView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;

    private final LoggedInController loggedInController;

    JLabel name;
    JLabel wins;
    JLabel losses;
    JLabel elo;

    final JButton logOut;
    final JButton play;

    /**
     * A window with a title and a JButton.
     */
    public LoggedInView(LoggedInViewModel loggedInViewModel, LoggedInController loggedInController) {
        this.setPreferredSize(new Dimension(1920, 1080));
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);
        this.loggedInController = loggedInController;

        JLabel title = new JLabel("Logged In Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameInfo = new JLabel("Currently logged in: ");
        name = new JLabel();
        JLabel winsInfo = new JLabel("Wins: ");
        wins = new JLabel();
        JLabel lossesInfo = new JLabel("Losses: ");
        losses = new JLabel();
        JLabel eloInfo = new JLabel("Elo: ");
        elo = new JLabel();




        JPanel buttons = new JPanel();
        logOut = new JButton(loggedInViewModel.LOGOUT_BUTTON_LABEL);
        buttons.add(logOut);

        logOut.addActionListener(this); //TODO


        play = new JButton(loggedInViewModel.PLAY_BUTTON_LABEL);
        buttons.add(play);
        play.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == play) {
                            loggedInController.play();
                        }
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(nameInfo);
        this.add(name);
        this.add(winsInfo);
        this.add(wins);
        this.add(lossesInfo);
        this.add(losses);
        this.add(eloInfo);
        this.add(elo);
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
        if (evt.getSource() == logOut) {
            return;
        }    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoggedInState state = (LoggedInState) evt.getNewValue();
        name.setText(state.getName());
        wins.setText(String.valueOf(state.getWins()));
        losses.setText(String.valueOf(state.getLosses()));
        elo.setText(String.valueOf(state.getElo()));
    }
}