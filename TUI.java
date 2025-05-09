import java.util.Scanner;

/**
 * Represents a Text User interface, including the level and user input.
 */
class TUI {
  private final Level level;
  private final Scanner input = new Scanner(System.in);

  /**
   * Constructs a TUI object with a randomly generated level.
   */
  public TUI() {
    this(Level.generateLevel());
  }

  /**
   * Constructs a TUI object with the specified level.
   *
   * @param level The level to be played.
   */
  public TUI(Level level) {
    this.level = level;
  }

  /**
   * Returns the current level.
   *
   * @return The current level.
   */
  public Level getLevel() {
    return this.level;
  }

  /**
   * Converts a string representation of a direction to the corresponding Direction enum.
   *
   * @param direction The string representation of the direction.
   * @return The corresponding Direction enum.
   */
  public static Direction getDirection(String direction) {
    return Direction.valueOf(direction.toUpperCase());
  }

  /**
   * Executes an action cycle, which consists of getting user input,
   * performing the action if valid, and reporting observations.
   * All inputs are fetched from the console.
   * <p>
   * The input can either be a direction (UP, DOWN, LEFT, RIGHT) to move,
   * a command to shoot (SHOOT UP, SHOOT DOWN, etc.), or OBSERVE.
   * Anything else is considered invalid and will tell the user to try again.
   */
  public void action() {
    boolean originalGoldStatus = this.level.hasGold();
    try {
      System.out.println("Enter a direction to move, SHOOT direction, or OBSERVE. ");
      String in = input.nextLine().toUpperCase();
      boolean status;
      if (in.startsWith("SHOOT")) {
        Direction direction = getDirection(in.substring(6));
        if (!this.level.shoot(direction))
          System.out.println("You don't have an arrow. ");
        else if (!this.level.hasWumpus()) {
          System.out.println("You shot the Wumpus! ");
          System.out.println("Your score is now " + this.level.getScore() + ". ");
        } else {
          System.out.println("You shot but missed. ");
          System.out.println("Your score is now " + this.level.getScore() + ". ");
        }
      } else if (in.equals("OBSERVE")) {
        this.reportObservations(originalGoldStatus);
        System.out.println("Your score is " + this.level.getScore() + ". ");
      } else {
        Direction direction = getDirection(in);
        if (!this.level.move(direction))
          System.out.println("You hit a wall. ");
        if (this.level.hasEnded()) {
          switch (this.level.endType()) {
            case WUMPUS:
              System.out.println("You hit the Wumpus. It tears you into pieces. Game over. ");
              break;
            case PIT:
              System.out.println("You drop to your death. Game over. ");
              break;
            case WIN:
              System.out.println("You head back to the village to deliver your newfound gold. You win! ");
              break;
            default:
              throw new IllegalArgumentException("Unidentifiable end type - what the fuck? ");
          }
        } else {
          this.reportObservations(originalGoldStatus);
          System.out.println("Your score is now " + this.level.getScore() + ". ");
        }
      }
    } catch (IllegalArgumentException | StringIndexOutOfBoundsException e) {
      System.out.println("Invalid format. Try again. ");
    }
  }


  /**
   * Reports the observations.
   *
   * @param originalGoldStatus whether the gold is present before the action
   *                           This can change the prompt if glitter is detected.
   */
  private void reportObservations(boolean originalGoldStatus) {
    boolean flag = false;
    if (this.level.detectsStench()) {
      if (this.level.hasWumpus())
        System.out.println("You smell a stench. Wumpus! ");
      else
        System.out.println("You smell a stench. Ah, these memories... ");
      flag = true;
    }
    if (this.level.detectsBreeze()) {
      if (!flag)
        System.out.println("You feel a breeze. A pit, somewhere... ");
      else
        System.out.println("You also feel a breeze. A pit, somewhere... ");
      flag = true;
    }
    if (this.level.detectsGlitter()) {
      if (originalGoldStatus)
        System.out.println("You pick up the gold from the ground. Time to go home. ");
      else
        System.out.println("The glitter on the ground reminds you to take the gold back. ");
      flag = true;
    }
    if (!flag) {
      System.out.println("You observe nothing. The adventure continues. ");
    }
  }
}
