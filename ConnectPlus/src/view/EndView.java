package view;

import Entities.User;
import interface_adapter.Home.EndViewModel;
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
    public final String viewName = "end view";

    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();

    private JLabel elo = new JLabel("Your new elo is:" );

    private JButton playagain = new JButton("Play Again");

    private JButton exit = new JButton("Exit");

    private JLabel goodgame = new JLabel("Create new Account");

    public EndView(EndViewModel endViewModel, HomeController homeController) {


        elo.setText("Your new elo is: 1000");
        panel.setBorder(BorderFactory.createEmptyBorder(200, 100, 200, 100));
        panel.setLayout(new GridLayout(0, 1));
        goodgame.setAlignmentX(Component.CENTER_ALIGNMENT);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Connect Plus");
        frame.pack();
        frame.setVisible(true);

        exit.addActionListener(
                new ActionListener()    {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                }
        );

        playagain.addActionListener(
                new ActionListener()    {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                }
        );


        panel.add(elo);
        panel.add(playagain);
        panel.add(exit);
        ;
        }



    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {


}}

