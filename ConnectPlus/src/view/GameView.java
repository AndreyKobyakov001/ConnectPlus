package view;

import interface_adapter.Setup.SetupState;
import interface_adapter.Setup.SetupViewModel;
import interface_adapter.Setup.SetupController;
import use_case.GameBuild.GameBuildOutputData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class GameView extends JPanel implements ActionListener, PropertyChangeListener {
    //TODO: set default size

    public final String viewName = "setup view";
    private JLabel[][] grid;
    private JButton[] columnButtons;

    private JPanel gridPanel;
    private int rows;
    private int cols;

    private boolean isPVP;

    private boolean player1Turn; // true for green, false for red

    private JButton forfeitButton;
    private JButton undoButton;

    private JLabel player1Label;

    private JLabel player2Label;

    private JLabel turnLabel;

    private String player1Name;
    private String player2Name;

    private final SetupController setupController;
    private final SetupViewModel setupViewModel;

    public GameView(SetupController setupController, SetupViewModel setupViewModel) {
        this.setPreferredSize(new Dimension(1920, 1080));
        this.setupController = setupController;
        this.setupViewModel = setupViewModel;
        this.setupViewModel.addPropertyChangeListener(this);
    }

    private void startGame() {
        SetupState currentState = setupViewModel.getState();
        System.out.println(currentState.getHeight());
        System.out.println(currentState.getWidth());
        this.rows = currentState.getHeight() > 0 ? currentState.getHeight(): 1;
        this.cols = currentState.getWidth() > 0 ? currentState.getWidth(): 1;
        this.player1Turn = currentState.getIsPlayer1Turn();
        this.player1Name = currentState.getPlayer1Name();
        this.player2Name = currentState.getPlayer2Name();

        // Initialize the grid and its respective grid buttons
        setLayout(new BorderLayout());
        gridPanel = new JPanel(new GridLayout(rows, cols));
        grid = new JLabel[rows][cols];
        columnButtons = new JButton[cols];
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS)); // or any other layout manager
        southPanel.add(initializeButtons());
//        southPanel.add(addBottomUserLabels());
        add(southPanel, BorderLayout.SOUTH);
        initializeGrid(gridPanel);
        initializeButtons();
        add(gridPanel, BorderLayout.CENTER);

        // Initialize the menu buttons
        forfeitButton = new JButton(setupViewModel.FORFEIT_LABEL);
        forfeitButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == forfeitButton) {
                            int resigner = 2; //TODO: test if it is this way; might have to change around the 1 and 0.
                            if(player1Turn) {
                                resigner = 1;
                            }
                            setupController.forfeitGame(resigner);
                        }
                    }
                });

        undoButton = new JButton(setupViewModel.UNDO_LABEL);
        undoButton.addActionListener(
                e -> {
                    if (e.getSource() == undoButton) {
                        setupController.undoMove();
                    }
                });
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.add(forfeitButton);
        menuPanel.add(undoButton);
        add(menuPanel, BorderLayout.EAST);

        // Initialize user labels and turn label
        initializeUserLabels(player1Turn);


        // Add turn label at the top of the grid
        addTopTurnLabel();
    }

    private JPanel initializeButtons() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, cols));
        for (int i = 0; i < cols; i++) {
            JButton button = new JButton(Integer.toString(i + 1));
            button.setActionCommand(Integer.toString(i)); // Set the column number as the action command
            button.addActionListener(
                    e -> {
                        if (e.getSource() == button) {
                            handleColumnClick(Integer.parseInt(button.getActionCommand()));
                        }
                    }
            );
            columnButtons[i] = button;
            buttonPanel.add(button);
        }
        return buttonPanel;
    }

    private void initializeGrid(JPanel gridPanel) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JLabel label = new JLabel();
                label.setOpaque(true);
                label.setBackground(Color.GRAY);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                label.setIcon(createCircleIcon(Color.WHITE));
                grid[row][col] = label;
                gridPanel.add(label);
            }
        }
    }


    private void handleColumnClick(int col) {
        SetupState currentState = setupViewModel.getState();

        setupController.makeMove(col, player1Turn ? player1Name : player2Name);
        updateViewFromViewModel();

        }



    private Icon createCircleIcon(Color color) {
        int diameter = 30; // Set the diameter of the circle
        BufferedImage image = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Enable anti-aliasing for smoother circles
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(color);
        g2d.fillOval(0, 0, diameter, diameter); // Draw the circle

        g2d.dispose(); // Dispose graphics to release resources

        return new ImageIcon(image);
    }

    private void updateViewFromViewModel() {
        // Update the grid based on the current board state from the view model
        SetupState state = setupViewModel.getState();
        char[][] boardState = state.getBoardState(); // Assuming getBoardState() is a method in the view model
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col].setIcon(createCircleIcon(getColorFromSymbol(boardState[row][col])));
            }
        }



        // Update turn information
        player1Turn = state.getIsPlayer1Turn(); // Assuming isPlayer1Turn() is a method in the view model
        String currentPlayer = (player1Turn ? player1Name : player2Name) + "'s Turn";
        turnLabel.setText(currentPlayer);
        turnLabel.repaint();
        gridPanel.repaint();
    }


    private Color getColorFromSymbol(char symbol) {
        if (symbol == 'X') {
            return Color.GREEN;
        } else if (symbol == 'O') {
            return Color.RED;
        }
        return Color.WHITE;
    }

    private void initializeUserLabels(boolean isPlayer1Turn) {
        player1Label = new JLabel(this.player1Name);
        player2Label = new JLabel(this.player2Name);
        turnLabel = new JLabel((player1Turn ? this.player1Name : this.player2Name) + " Turn");

        player1Label.setHorizontalAlignment(SwingConstants.LEFT);
        player2Label.setHorizontalAlignment(SwingConstants.RIGHT);
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private JPanel addBottomUserLabels() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(player1Label, BorderLayout.WEST);
        bottomPanel.add(player2Label, BorderLayout.EAST);
        return bottomPanel;
    }

    private void addTopTurnLabel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(turnLabel);
        add(topPanel, BorderLayout.NORTH);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("Click " + actionEvent.getActionCommand());
    }
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {

        SetupState state = (SetupState) propertyChangeEvent.getNewValue();
        if(state.getIllegalMoveError() != null) {
            JOptionPane.showMessageDialog(this, state.getIllegalMoveError());
        }
        if(state.getStartGame()) {
            startGame();
        }

    }


}
