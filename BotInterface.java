/**
 * Interface for a bot of the Wumpus game.
 */
public interface BotInterface {
  /**
   * Makes an action.
   */
  void action();

  /**
   * Returns the backEnd associated with the bot.
   *
   * @return The backEnd associated with the bot.
   */
  BackEnd getBackEnd();
}
