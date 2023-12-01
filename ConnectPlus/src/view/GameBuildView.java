package view;

import interface_adapter.GameBuild.*;
import use_case.GameBuild.GameBuildInputData;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameBuildView extends JPanel implements ActionListener, PropertyChangeListener, ChangeListener {

    private final GameBuildController gameBuildController;
    private final GameBuildViewModel gameBuildViewModel;
    public final String viewName = "game build";

    private JButton backButton;
    private JButton playButton;
    private JCheckBox pvpCheckBox;
    private JCheckBox pvbCheckBox;
    private JSlider heightSlider;
    private JSlider bofDiffSlider;
    private JSlider widthSlider;
    private JSlider winCondSlider;


    public GameBuildView(GameBuildController gameBuildController, GameBuildViewModel gameBuildViewModel) {
        this.gameBuildController = gameBuildController;
        this.gameBuildViewModel = gameBuildViewModel;

        gameBuildViewModel.addPropertyChangeListener(this);



        // Top panel with Back and Play buttons
        backButton = new JButton("Back");
        playButton = new JButton("Play");
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(playButton, BorderLayout.EAST);

        // Center panel with PvP and PvB checkboxes
        pvpCheckBox = new JCheckBox("PvP");
        pvbCheckBox = new JCheckBox("PvB");
        pvpCheckBox.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == pvpCheckBox) {
                            pvbCheckBox.setSelected(!pvpCheckBox.isSelected());
                            GameBuildState currentState = gameBuildViewModel.getState();
                            currentState.setPvP(false);
                        }
                    }
                }
        );

        pvbCheckBox.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == pvpCheckBox) {
                            pvbCheckBox.setSelected(!pvbCheckBox.isSelected());
                            GameBuildState currentState = gameBuildViewModel.getState();
                            currentState.setPvP(true);
                        }
                    }
                }
        );
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.add(pvpCheckBox);
        checkboxPanel.add(pvbCheckBox);

        // Panel for sliders
        JPanel slidersPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        heightSlider = new JSlider(JSlider.HORIZONTAL, gameBuildViewModel.MIN_HEIGHT, gameBuildViewModel.MAX_HEIGHT, gameBuildViewModel.DEF_HEIGHT);
        heightSlider.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        GameBuildState currentState = gameBuildViewModel.getState();
                        currentState.setHeight(heightSlider.getValue());
                    }
                }
        );
        bofDiffSlider = new JSlider(JSlider.HORIZONTAL, gameBuildViewModel.MIN_BOT_DIFF, gameBuildViewModel.MAX_BOT_DIFF, gameBuildViewModel.DEF_BOT_DIFF);
        bofDiffSlider.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        GameBuildState currentState = gameBuildViewModel.getState();
                        currentState.setBotDifficulty(bofDiffSlider.getValue());
                    }
                }
        );
        widthSlider = new JSlider(JSlider.HORIZONTAL, gameBuildViewModel.MIN_WIDTH, gameBuildViewModel.MAX_WIDTH, gameBuildViewModel.DEF_WIDTH);
        widthSlider.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        GameBuildState currentState = gameBuildViewModel.getState();
                        currentState.setWidth(widthSlider.getValue());
                    }
                }
        );
        winCondSlider = new JSlider(JSlider.HORIZONTAL, gameBuildViewModel.MIN_WIN_COND, gameBuildViewModel.MAX_WIN_COND, gameBuildViewModel.DEF_WIN_COND);
        winCondSlider.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        GameBuildState currentState = gameBuildViewModel.getState();
                        currentState.setWinCondition(winCondSlider.getValue());
                    }
                }
        );

        // Add sliders to the sliders panel
        slidersPanel.add(new JLabel(gameBuildViewModel.BOT_DIFF_LABEL));
        slidersPanel.add(bofDiffSlider);
        slidersPanel.add(new JLabel(gameBuildViewModel.HEIGHT_LABEL));
        slidersPanel.add(heightSlider);
        slidersPanel.add(new JLabel(gameBuildViewModel.WIDTH_LABEL));
        slidersPanel.add(widthSlider);
        slidersPanel.add(new JLabel(gameBuildViewModel.WIN_COND_LABEL));
        slidersPanel.add(winCondSlider);



        // Add the top panel and center panel to the frame
        add(topPanel, BorderLayout.NORTH);
        add(checkboxPanel, BorderLayout.CENTER);
        add(slidersPanel, BorderLayout.SOUTH);

        // Set the back and play buttons to listen for clicks
        backButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == backButton) {
                            gameBuildController.back();
                        }
                    }
                }
        );
        playButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == playButton) {
                            GameBuildState currentState = gameBuildViewModel.getState();
                            GameBuildInputData inputData = new GameBuildInputData(
                                    currentState.getHeight(),
                                    currentState.getWidth(),
                                    currentState.getWinCondition(),
                                    currentState.getBotDiff(),
                                    currentState.getIsPVP(),
                                    gameBuildViewModel.MIN_HEIGHT,
                                    gameBuildViewModel.MIN_WIDTH,
                                    gameBuildViewModel.MAX_HEIGHT,
                                    gameBuildViewModel.MAX_WIDTH,
                                    gameBuildViewModel.MIN_WIN_COND,
                                    gameBuildViewModel.MAX_WIN_COND,
                                    gameBuildViewModel.MIN_BOT_DIFF,
                                    gameBuildViewModel.MAX_BOT_DIFF
                            );
                            gameBuildController.execute(inputData);
                        }
                    }
                }
        );

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        GameBuildState state = (GameBuildState) evt.getNewValue();
        if (state.getGameBuildError() != null) {
            JOptionPane.showMessageDialog(this, state.getGameBuildError());
        }

    }


    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        System.out.println("Slider changed");
    }
}
