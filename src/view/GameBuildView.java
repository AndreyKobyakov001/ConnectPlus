package view;

import interface_adapter.GameBuild.*;
import interface_adapter.Setup.SetupController;
import use_case.GameBuild.GameBuildInputData;
import use_case.GameBuild.GameBuildOutputData;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Hashtable;

public class GameBuildView extends JPanel implements ActionListener, PropertyChangeListener, ChangeListener {

    private final GameBuildController gameBuildController;
    private final GameBuildViewModel gameBuildViewModel;
    public final String viewName = "game build";
    private final SetupController setupController;

    private JButton backButton;
    private JButton playButton;


    private JCheckBox pvpCheckBox;
    private JCheckBox pvbCheckBox;
    private JSlider heightSlider;
    private JSlider bofDiffSlider;
    private JSlider widthSlider;
    private JSlider winCondSlider;


    public GameBuildView(GameBuildController gameBuildController, GameBuildViewModel gameBuildViewModel, SetupController setupController) {
        this.setPreferredSize(new Dimension(1920, 1080));
        this.gameBuildController = gameBuildController;
        this.gameBuildViewModel = gameBuildViewModel;
        this.setupController = setupController;

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
                            pvpCheckBox.setSelected(true);
                            pvbCheckBox.setSelected(false);
                            GameBuildState currentState = gameBuildViewModel.getState();
                            currentState.setPvP(true);

                        }
                    }
                }
        );

        pvbCheckBox.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == pvbCheckBox) {
                            // select the pvb checkbox, then unselect the pvp checkbox
                            pvbCheckBox.setSelected(true);
                            pvpCheckBox.setSelected(false);
                            GameBuildState currentState = gameBuildViewModel.getState();
                            currentState.setPvP(false);
                        }
                    }
                }
        );
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.add(pvpCheckBox);
        checkboxPanel.add(pvbCheckBox);

        // Panel for sliders
        GameBuildState currentState = gameBuildViewModel.getState();
        JPanel slidersPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        heightSlider = new JSlider(JSlider.HORIZONTAL, gameBuildViewModel.MIN_HEIGHT, gameBuildViewModel.MAX_HEIGHT, gameBuildViewModel.DEF_HEIGHT);
        currentState.setHeight(heightSlider.getValue());
        heightSlider.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        GameBuildState currentState = gameBuildViewModel.getState();
                        currentState.setHeight(heightSlider.getValue());
                    }
                }
        );
        // add labels at two ends of the slider, along with showing the current value of the slider
        heightSlider.setMajorTickSpacing(1);
        heightSlider.setPaintTicks(true);
        heightSlider.setPaintLabels(true);
        heightSlider.setLabelTable(heightSlider.createStandardLabels(1));
        heightSlider.setSnapToTicks(true);
        Hashtable<Integer, JLabel> labelTableHeight = new Hashtable<>();
        labelTableHeight.put(gameBuildViewModel.MIN_HEIGHT, new JLabel("" + gameBuildViewModel.MIN_HEIGHT));
        labelTableHeight.put(gameBuildViewModel.MAX_HEIGHT, new JLabel("" + gameBuildViewModel.MAX_HEIGHT));
        heightSlider.setLabelTable(labelTableHeight);

        bofDiffSlider = new JSlider(JSlider.HORIZONTAL, gameBuildViewModel.MIN_BOT_DIFF, gameBuildViewModel.MAX_BOT_DIFF, gameBuildViewModel.DEF_BOT_DIFF);
        currentState.setBotDiff(bofDiffSlider.getValue());
        bofDiffSlider.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        GameBuildState currentState = gameBuildViewModel.getState();
                        currentState.setBotDifficulty(bofDiffSlider.getValue());
                    }
                }
        );
        bofDiffSlider.setMajorTickSpacing(1);
        bofDiffSlider.setPaintTicks(true);
        bofDiffSlider.setPaintLabels(true);
        bofDiffSlider.setLabelTable(bofDiffSlider.createStandardLabels(1));
        bofDiffSlider.setSnapToTicks(true);
        Hashtable<Integer, JLabel> labelTableBotDiff = new Hashtable<>();
        labelTableBotDiff.put(gameBuildViewModel.MIN_BOT_DIFF, new JLabel("" + gameBuildViewModel.MIN_BOT_DIFF));
        labelTableBotDiff.put(gameBuildViewModel.MAX_BOT_DIFF, new JLabel("" + gameBuildViewModel.MAX_BOT_DIFF));
        bofDiffSlider.setLabelTable(labelTableBotDiff);

        widthSlider = new JSlider(JSlider.HORIZONTAL, gameBuildViewModel.MIN_WIDTH, gameBuildViewModel.MAX_WIDTH, gameBuildViewModel.DEF_WIDTH);
        currentState.setWidth(widthSlider.getValue());
        widthSlider.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        GameBuildState currentState = gameBuildViewModel.getState();
                        currentState.setWidth(widthSlider.getValue());
                    }
                }
        );
        widthSlider.setMajorTickSpacing(1);
        widthSlider.setPaintTicks(true);
        widthSlider.setPaintLabels(true);
        widthSlider.setLabelTable(widthSlider.createStandardLabels(1));
        widthSlider.setSnapToTicks(true);
        Hashtable<Integer, JLabel> labelTableWidth = new Hashtable<>();
        labelTableWidth.put(gameBuildViewModel.MIN_WIDTH, new JLabel("" + gameBuildViewModel.MIN_WIDTH));
        labelTableWidth.put(gameBuildViewModel.MAX_WIDTH, new JLabel("" + gameBuildViewModel.MAX_WIDTH));
        widthSlider.setLabelTable(labelTableWidth);

        winCondSlider = new JSlider(JSlider.HORIZONTAL, gameBuildViewModel.MIN_WIN_COND, gameBuildViewModel.MAX_WIN_COND, gameBuildViewModel.DEF_WIN_COND);
        currentState.setWinCondition(winCondSlider.getValue());
        winCondSlider.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        GameBuildState currentState = gameBuildViewModel.getState();
                        currentState.setWinCondition(winCondSlider.getValue());
                    }
                }
        );
        winCondSlider.setMajorTickSpacing(1);
        winCondSlider.setPaintTicks(true);
        winCondSlider.setPaintLabels(true);
        winCondSlider.setLabelTable(winCondSlider.createStandardLabels(1));
        winCondSlider.setSnapToTicks(true);
        // Create a Hashtable for labels
        Hashtable<Integer, JLabel> labelTableWinC = new Hashtable<>();

        // Add a label for the minimum value
        labelTableWinC.put(gameBuildViewModel.MIN_WIN_COND, new JLabel("" + gameBuildViewModel.MIN_WIN_COND));

        // Add a label for the maximum value
        labelTableWinC.put(gameBuildViewModel.MAX_WIN_COND, new JLabel("" + gameBuildViewModel.MAX_WIN_COND));

        // Set the label table on the slider
        winCondSlider.setLabelTable(labelTableWinC);

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
                            if (currentState.getStartGame()){
                                System.out.println("Start game");
                                int botDiff = currentState.getIsPVP() ? -1 : currentState.getBotDiff();
                                setupController.startGame(new GameBuildOutputData(currentState.getHeight(), currentState.getWidth(), currentState.getWinCondition(), botDiff));

                            }
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
