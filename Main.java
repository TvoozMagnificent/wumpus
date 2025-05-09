/**
 * Main class to run the Wumpus game.
 */
public class Main {
  /**
   * Main method to start the game.
   *
   * @param args Command line arguments. Ignored.
   */
  public static void main(String[] args) {
    while (true) {
      TUI play = new TUI();
      while (!play.getLevel().hasEnded()) play.action();
      System.out.println("Game ended. Score: " + play.getLevel().getScore() + ". ");
      System.out.println("\nLet's start a new game! ");
    }
  }
}
