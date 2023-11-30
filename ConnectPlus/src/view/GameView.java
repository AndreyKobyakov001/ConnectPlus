package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class GameView extends JPanel implements ActionListener, PropertyChangeListener {

    private JLabel[][] grid; // 7 columns and 6 rows
    private JButton[] columnButtons;
    private final int rows = 6;
    private final int cols = 7;
    private boolean playerTurn = true; // true for green, false for red

    public GameView() {
        setLayout(new BorderLayout());
        JPanel gridPanel = new JPanel(new GridLayout(rows, cols));
        grid = new JLabel[rows][cols];
        columnButtons = new JButton[cols];
        initializeGrid(gridPanel);
        initializeButtons();
        add(gridPanel, BorderLayout.CENTER);
    }
    private void initializeButtons() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, cols));
        for (int i = 0; i < cols; i++) {
            JButton button = new JButton(Integer.toString(i + 1));
            button.addActionListener(this);
            button.setActionCommand(Integer.toString(i)); // Set the column number as the action command
            columnButtons[i] = button;
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int col = Integer.parseInt(actionEvent.getActionCommand());
        handleColumnClick(col);
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
        for (int row = rows - 1; row >= 0; row--) {
            if (grid[row][col].getIcon().equals(createCircleIcon(Color.WHITE))) {
                grid[row][col].setIcon(createCircleIcon(playerTurn ? Color.GREEN : Color.RED));
                playerTurn = !playerTurn; // Switch turns
                break;
            }
        }
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


    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        // Implementation for PropertyChangeListener
    }
}
