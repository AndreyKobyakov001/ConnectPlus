package view;

import Entities.User;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EndView extends JPanel implements ActionListener, PropertyChangeListener {
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();

    private JButton share = new JButton("Share");

    private JLabel elo = new JLabel("Your new elo is:" );

    private JButton playagain = new JButton("Play Again");

    private JButton exit = new JButton("Exit");

    private JLabel goodgame = new JLabel("Create new Account");

    public EndView(User user){
        panel.setBorder(BorderFactory.createEmptyBorder(200, 100, 200, 100));
        panel.setLayout(new GridLayout(0, 1));
        goodgame.setAlignmentX(Component.CENTER_ALIGNMENT);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Create new Account");
        frame.pack();
        frame.setVisible(true);

        exit.addActionListener(
                new ActionListener()    {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        new SignupView();
                    }
                }
        );

        playagain.addActionListener(
                new ActionListener()    {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        new SignupView();
                    }
                }
        );
        share.addActionListener(
                new ActionListener()    {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                }
        );

        panel.add(elo);
        panel.add(playagain);
        panel.add(exit);
        panel.add(share);
        }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
    public static void main(String[] args) {
        new EndView(new User("hello"));
    }
}

