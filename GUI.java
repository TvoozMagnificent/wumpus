import javax.swing.*;
import java.awt.*;

/*
TODO:
1. Add a button to restart the game.
2. Add keyboard shortcuts for the buttons.
3. Comment the code.
 */
public class GUI extends JFrame {
  private final BackEnd backEnd = new BackEnd();
  private final JLabel statusLabel = new JLabel("Wumpus World", SwingConstants.LEFT);
  private final JPanel[][] panels = new JPanel[4][4];
  private final JLabel[][] labels = new JLabel[4][4];

  public GUI() {
    backEnd.updateObservations();
    this.setupGUI();
    this.updateLabels();
  }

  private JPanel initializeText() {
    JPanel textPanel = new JPanel();
    textPanel.setLayout(new BorderLayout());
    textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    textPanel.setBackground(new Color(240, 240, 240));

    this.statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
    this.statusLabel.setForeground(Color.BLUE);
    textPanel.add(this.statusLabel, BorderLayout.CENTER);

    return textPanel;
  }

  private JPanel initializeDisplay() {
    JPanel display = new JPanel(new GridLayout(4, 4, 5, 5));
    display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    display.setBackground(new Color(240, 240, 240));

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        JPanel cellPanel = new JPanel(new BorderLayout());
        cellPanel.setBackground(new Color(200, 200, 200));
        cellPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel label = new JLabel("Unexplored", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.RED);
        cellPanel.add(label, BorderLayout.CENTER);
        labels[i][j] = label;
        panels[i][j] = cellPanel;
        display.add(cellPanel);
      }
    }
    return display;
  }

  private JPanel createControls() {
    JPanel controls = new JPanel(new GridLayout(2, 4, 5, 5));
    controls.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    controls.setBackground(new Color(240, 240, 240));
    String[] directions = {"Up", "Down", "Left", "Right"};

    for (String direction : directions) {
      JButton button = new JButton(direction);
      button.setFont(new Font("Arial", Font.BOLD, 24));
      button.setForeground(Color.BLACK);
      button.addActionListener(e -> this.handleMove(direction));
      controls.add(button);
    }

    for (String direction : directions) {
      JButton button = new JButton("Shoot " + direction);
      button.setFont(new Font("Arial", Font.BOLD, 24));
      button.setForeground(Color.BLACK);
      button.addActionListener(e -> this.handleShoot(direction));
      controls.add(button);
    }
    return controls;
  }

  private void setupGUI() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Wumpus World GUI");
    this.setLayout(new BorderLayout());
    this.getContentPane().setBackground(new Color(240, 240, 240));
    this.add(this.initializeText(), BorderLayout.NORTH);
    this.add(this.initializeDisplay(), BorderLayout.CENTER);
    this.add(this.createControls(), BorderLayout.SOUTH);
    this.pack();
    this.setMinimumSize(getSize());
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

  public static Direction getDirection(String direction) {
    return Direction.valueOf(direction.toUpperCase());
  }

  private void handleMove(String direction) {
    backEnd.getLevel().move(getDirection(direction));
    backEnd.updateObservations();
    this.updateLabels();
  }

  private void handleShoot(String direction) {
    backEnd.getLevel().shoot(getDirection(direction));
    backEnd.updateObservations();
    this.updateLabels();
  }

  private void updateLabels() {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        String labelText = backEnd.getCellInfo(new Coordinate(i, j));
        labels[i][j].setText(labelText);
        boolean loaded = backEnd.getLoaded().get(new Coordinate(i, j));
        panels[i][j].setBackground(loaded ? new Color(200, 200, 200) : new Color(50, 50, 50));
      }
    }
    this.statusLabel.setText(backEnd.getGeneralInfo());
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(GUI::new);
  }
}
