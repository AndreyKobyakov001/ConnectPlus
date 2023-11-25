package view;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;



public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();

    private JButton signupbutton = new JButton("sign up");

    private JLabel createnewaccount = new JLabel("Create new Account");

    private JLabel alreadyregistered = new JLabel("Already Registered? Login");

    private JLabel name = new JLabel("NAME");

    private JLabel email = new JLabel("EMAIL");

    private JLabel password = new JLabel("PASSWORD");

    private JLabel connectpluslog = new JLabel("CONNECTPLUS LOG");

    private final JTextField usernameInputField = new JTextField(15);

    private final JTextField emailenter = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);

    public SignupView(){
        panel.setBorder(BorderFactory.createEmptyBorder(200, 100, 200, 100));
        panel.setLayout(new GridLayout(0, 1));
        createnewaccount.setAlignmentX(Component.CENTER_ALIGNMENT);
        LabelTextPanel usernameInfo = new LabelTextPanel(
                name, usernameInputField);
        LabelTextPanel passwordInfo = new LabelTextPanel(
                password, passwordInputField);
        LabelTextPanel emailInfo = new LabelTextPanel(
               email, emailenter);


        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Create new Account");
        frame.pack();
        frame.setVisible(true);

        signupbutton.addActionListener(
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

        panel.add(createnewaccount);
        panel.add(alreadyregistered);
        panel.add(usernameInfo);
        panel.add(passwordInfo);
        panel.add(emailInfo);
        panel.add(connectpluslog);
        panel.add(signupbutton);
    }
    public static void main(String[] args) {
        new SignupView();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
