package view;

import Entities.User;
import interface_adapter.Home.EndViewModel;
import interface_adapter.Home.EndViewState;
import interface_adapter.Home.HomeController;
import interface_adapter.logged_in.LoggedInController;
import interface_adapter.logged_in.LoggedInViewModel;

import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EndView extends JPanel implements ActionListener, PropertyChangeListener {

    private final EndViewModel endViewModel;
    private final HomeController homeController;
    public final String viewName = "end view";



    private JLabel elo = new JLabel();

    private JButton playagain = new JButton();

    private JButton exit = new JButton();

    private JLabel goodgame = new JLabel();

    public EndView(EndViewModel endViewModel, HomeController homeController) {
        this.endViewModel = endViewModel;
        this.homeController = homeController;
        this.setPreferredSize(new Dimension(1920, 1080));
        this.endViewModel.addPropertyChangeListener(this);


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));



        exit.addActionListener(
                new ActionListener()    {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == exit) {
                            homeController.play();
                        }

                    }
                }
        );

        playagain.addActionListener(
                new ActionListener()    {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == playagain) {
                            homeController.restart();
                        }

                    }
                }
        );


        this.add(elo);
        this.add(playagain);
        this.add(exit);

        }



    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        EndViewState currentState = endViewModel.getState();
        if (currentState.getELODelta() > 0) {
            elo.setText(endViewModel.ELO_LABEL + currentState.getELODelta());
        }
        if (currentState.getIsWon() == 2) {
            goodgame.setText("Good luck next time!");
        } else if (currentState.getIsWon() == 1) {
            goodgame.setText("Congratulations!");
        } else {
            goodgame.setText("It's a tie!");
        }
        goodgame.setAlignmentX(Component.CENTER_ALIGNMENT);

    }
}

