import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * GUI class for the Wumpus World game.
 * This class creates a graphical user interface for the game, allowing users to interact with it.
 * It includes buttons for moving and shooting, as well as a display for the game state.
 */
public class GUI extends JFrame {
  private BackEnd backEnd = new BackEnd();
  private BotInterface bot = new Bot(this.backEnd);
  private final JLabel statusLabel = new JLabel("Wumpus World", SwingConstants.LEFT);
  private final JPanel[][] panels = new JPanel[4][4];
  private final JLabel[][] labels = new JLabel[4][4];
  private int games = 0;
  private double total = 0, average = 0;

  /**
   * Action to reset the game.
   * This action resets the game state and updates the display.
   */
  private final Action reset = new AbstractAction() {
    public void actionPerformed(ActionEvent e) {
      updateCounters();
      backEnd = new BackEnd();
      bot = new Bot(backEnd);
      backEnd.updateObservations();
      updateLabels();
    }
  };

  /**
   * Action to perform a bot action.
   * This action allows the bot to take its turn and updates the display.
   * It also resets the game if it has already ended.
   */
  private final Action botAction = new AbstractAction() {
    public void actionPerformed(ActionEvent e) {
      if (backEnd.getLevel().hasEnded()) reset.actionPerformed(e);
      bot.action();
      backEnd.updateObservations();
      updateLabels();
    }
  };

  /**
   * Constructs a GUI object.
   * This constructor initializes the GUI components and sets up the layout.
   */
  public GUI() {
    this.backEnd.updateObservations();
    this.setupGUI();
    this.updateLabels();
  }

  /**
   * Initializes the text panel for displaying status information.
   * This method creates a panel with a label to show the current game status.
   *
   * @return The initialized text panel.
   */
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

  /**
   * Creates a reset button for the GUI.
   * This button allows the user to reset the game state.
   *
   * @return The created reset button.
   */
  private JButton createReset() {
    JButton resetButton = new JButton("Reset");
    resetButton.setFont(new Font("Arial", Font.BOLD, 18));
    resetButton.setForeground(Color.BLACK);
    resetButton.addActionListener(reset);
    return resetButton;
  }

  /**
   * Creates a button for the bot to take its turn.
   * This button allows the user to trigger the bot's action.
   *
   * @return The created bot button.
   */
  private JButton botButton() {
    JButton botButton = new JButton("Bot");
    botButton.setFont(new Font("Arial", Font.BOLD, 18));
    botButton.setForeground(Color.BLACK);
    botButton.addActionListener(e -> {
      bot.action();
      this.backEnd.updateObservations();
      this.updateLabels();
    });
    return botButton;
  }

  /**
   * Starts a bot game.
   * This method initializes the bot and runs the game until it ends.
   */
  private void botGame() {
    this.updateCounters();
    this.backEnd = new BackEnd();
    this.bot = new Bot(backEnd);
    this.backEnd.updateObservations();
    this.updateLabels();
    while (!this.backEnd.getLevel().hasEnded()) {
      this.bot.action();
      this.backEnd.updateObservations();
      this.updateLabels();
    }
  }

  /**
   * Creates a button for starting a bot game.
   * This button allows the user to start a game where the bot plays automatically.
   *
   * @return The created bot game button.
   */
  private JButton botGameButton() {
    JButton botGameButton = new JButton("Bot Game");
    botGameButton.setFont(new Font("Arial", Font.BOLD, 18));
    botGameButton.setForeground(Color.BLACK);
    botGameButton.addActionListener(e -> this.botGame());
    return botGameButton;
  }

  /**
   * Creates the top bar of the GUI.
   * This method initializes the top bar with a title label, reset button, and bot button.
   *
   * @return The created top bar panel.
   */
  private JPanel createTopBar() {
    JPanel bar = new JPanel(new GridLayout(1, 5, 5, 5));
    bar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    bar.setBackground(new Color(240, 240, 240));
    bar.add(this.initializeText());
    bar.add(this.createReset());
    bar.add(this.botButton());
    bar.add(this.botGameButton());
    return bar;
  }

  /**
   * Initializes the display panel for the game.
   * This method creates a grid layout to represent the game board and initializes the cell panels.
   *
   * @return The initialized display panel.
   */
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
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.RED);
        cellPanel.add(label, BorderLayout.CENTER);
        labels[i][j] = label;
        panels[i][j] = cellPanel;
        display.add(cellPanel);
      }
    }
    return display;
  }

  /**
   * Creates the control buttons for the GUI.
   * This method initializes the buttons for moving and shooting in different directions.
   *
   * @return The created control panel.
   */
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

  /**
   * Sets up the GUI components and layout.
   * This method initializes the main frame, sets the layout, and adds components to it.
   */
  private void setupGUI() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Wumpus World GUI");
    this.setLayout(new BorderLayout());
    this.getContentPane().setBackground(new Color(240, 240, 240));
    this.add(this.createTopBar(), BorderLayout.NORTH);
    this.add(this.initializeDisplay(), BorderLayout.CENTER);
    this.add(this.createControls(), BorderLayout.SOUTH);
    this.setupKeyBindings();
    this.pack();
    this.setMinimumSize(new Dimension(1024, 1280));
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

  /**
   * Sets up key bindings for the GUI.
   * This method initializes key bindings for moving and shooting in different directions.
   * The keybindings are set for the arrow keys to move,
   * the arrow keys with Shift to shoot in the respective direction,
   * 'R' to reset the game, and 'B' to trigger the bot action.
   */
  private void setupKeyBindings() {
    InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap actionMap = this.getRootPane().getActionMap();
    String[] directions = {"Up", "Down", "Left", "Right"};

    for (String direction : directions) {
      final String dir = direction;
      int keyCode = getKeyCode(dir);

      KeyStroke moveKey = KeyStroke.getKeyStroke(keyCode, 0);
      String moveActionKey = "move" + dir;
      inputMap.put(moveKey, moveActionKey);
      actionMap.put(moveActionKey, new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
          handleMove(dir);
        }
      });

      KeyStroke shootKey = KeyStroke.getKeyStroke(keyCode, InputEvent.SHIFT_DOWN_MASK);
      String shootActionKey = "shoot" + dir;
      inputMap.put(shootKey, shootActionKey);
      actionMap.put(shootActionKey, new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
          handleShoot(dir);
        }
      });
    }

    KeyStroke resetKey = KeyStroke.getKeyStroke(KeyEvent.VK_R, 0);
    String shootActionKey = "reset";
    inputMap.put(resetKey, shootActionKey);
    actionMap.put(shootActionKey, reset);

    KeyStroke botKey = KeyStroke.getKeyStroke(KeyEvent.VK_B, 0);
    String botActionKey = "botAction";
    inputMap.put(botKey, botActionKey);
    actionMap.put(botActionKey, botAction);
  }

  /**
   * Gets the key code for a given direction.
   * This method maps the direction string to the corresponding key code.
   *
   * @param direction The direction string (e.g., "Up", "Down", "Left", "Right").
   * @return The corresponding key code.
   */
  private int getKeyCode(String direction) {
    switch (direction) {
      case "Up":
        return KeyEvent.VK_UP;
      case "Down":
        return KeyEvent.VK_DOWN;
      case "Left":
        return KeyEvent.VK_LEFT;
      case "Right":
        return KeyEvent.VK_RIGHT;
      default:
        throw new IllegalArgumentException("Invalid direction: " + direction);
    }
  }

  /**
   * Converts a string to a Direction enum.
   * This method converts the string representation of a direction to its corresponding Direction enum value.
   *
   * @param direction The string representation of the direction.
   * @return The corresponding Direction enum value.
   */
  public static Direction getDirection(String direction) {
    return Direction.valueOf(direction.toUpperCase());
  }

  /**
   * Handles the move action for a given direction.
   * This method updates the game state based on the specified direction and refreshes the display.
   *
   * @param direction The direction to move in.
   */
  private void handleMove(String direction) {
    this.backEnd.getLevel().move(getDirection(direction));
    this.backEnd.updateObservations();
    this.updateLabels();
  }

  /**
   * Handles the shoot action for a given direction.
   * This method updates the game state based on the specified direction and refreshes the display.
   *
   * @param direction The direction to shoot in.
   */
  private void handleShoot(String direction) {
    this.backEnd.getLevel().shoot(getDirection(direction));
    this.backEnd.updateObservations();
    this.updateLabels();
  }

  /**
   * Updates the game counters.
   * This method increments the game count and updates the average score if the game has ended.
   */
  private void updateCounters() {
    if (this.backEnd.getLevel().hasEnded()) {
      this.games++;
      this.total += backEnd.getLevel().getScore();
      this.average = total / games;
    }
  }

  /**
   * Gets the title label for the GUI.
   * This method constructs a string representation of the game state to be displayed in the title label.
   * This includes general information about the game, the average score, and the last move made.
   *
   * @return The constructed title label string.
   */
  private String getTitleLabel() {
    return "<html>" + this.backEnd.getGeneralInfo() +
        "<br/> Average Score: " + this.average +
        "<br/> Last Move: " +
        this.backEnd.getLevel().getActionType() +
        " " +
        this.backEnd.getLevel().getActionDirection() +
        "</html>";
  }

  /**
   * Updates the labels and colors of the cells in the GUI.
   * This method refreshes the display to reflect the current game state.
   * It updates the text and background color of each cell based on its status.
   */
  private void updateLabels() {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        String labelText = this.backEnd.getCellInfo(new Coordinate(i, j));
        this.labels[i][j].setText(labelText);
        boolean loaded = this.backEnd.getLoaded().get(new Coordinate(i, j));
        this.panels[i][j].setBackground(loaded ? new Color(200, 200, 200) : new Color(50, 50, 50));
      }
    }
    this.statusLabel.setText(this.getTitleLabel());
  }

  /**
   * Main method to run the GUI application.
   * This method initializes the GUI and sets it to be visible.
   *
   * @param args Command line arguments (not used).
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(GUI::new);
  }
}
