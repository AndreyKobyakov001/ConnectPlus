package Views;

import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Beforeyoustartview extends JPanel implements ActionListener, PropertyChangeListener {
    private JFrame frame = new JFrame();
        private JPanel panel = new JPanel();

        private JButton startbutton = new JButton("start");

        private JLabel Beforeyoustart = new JLabel("Before you start");

        private JLabel gamesetting= new JLabel("Entities.Game setting");

        private JLabel mode = new JLabel("MODE");

        private JLabel boardgrid = new JLabel("BOARD GRID");

        private JLabel connect = new JLabel("CONNECT");

        private JLabel difficulty = new JLabel("DIFFICULTY(ONLY BOT MODE)");

        private final JTextField usernameInputField = new JTextField(15);

        private final JTextField emailenter = new JTextField(15);
    private final JTextField difficultyenter = new JTextField(15);
        private final JPasswordField passwordInputField = new JPasswordField(15);

        public Beforeyoustartview(){
            panel.setBorder(BorderFactory.createEmptyBorder(200, 100, 200, 100));
            panel.setLayout(new GridLayout(0, 1));
            Beforeyoustart.setAlignmentX(Component.CENTER_ALIGNMENT);
            LabelTextPanel mo = new LabelTextPanel(
                    mode, usernameInputField);
            LabelTextPanel grid = new LabelTextPanel(
                    boardgrid, passwordInputField);
            LabelTextPanel con = new LabelTextPanel(
                    connect, emailenter);
            LabelTextPanel diff = new LabelTextPanel(
                    difficulty, difficultyenter);


            frame.add(panel, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Before you start");
            frame.pack();
            frame.setVisible(true);

            startbutton.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                        }
                    }
            );
            usernameInputField.addKeyListener(
                    new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            String text = usernameInputField.getText() + e.getKeyChar();
                        }

                        @Override
                        public void keyPressed(KeyEvent e) {
                        }

                        @Override
                        public void keyReleased(KeyEvent e) {
                        }
                    });

            passwordInputField.addKeyListener(
                    new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            String text = passwordInputField.getText() + e.getKeyChar();
                        }

                        @Override
                        public void keyPressed(KeyEvent e) {

                        }

                        @Override
                        public void keyReleased(KeyEvent e) {

                        }
                    }
            );

            emailenter.addKeyListener(
                    new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            String Text = emailenter.getText() + e.getKeyChar();
                        }

                        @Override
                        public void keyPressed(KeyEvent e) {

                        }

                        @Override
                        public void keyReleased(KeyEvent e) {

                        }
                    }
            );
            difficultyenter.addKeyListener(
                    new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            String Text = difficultyenter.getText() + e.getKeyChar();
                        }

                        @Override
                        public void keyPressed(KeyEvent e) {

                        }

                        @Override
                        public void keyReleased(KeyEvent e) {

                        }
                    }
            );

            panel.add(Beforeyoustart);
            panel.add(gamesetting);
            panel.add(mo);
            panel.add(grid);
            panel.add(con);
            panel.add(diff);
            panel.add(startbutton);
        }
        public static void main(String[] args) {
            new Beforeyoustartview();

        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {

        }
}

