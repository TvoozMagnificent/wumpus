/**
 * A simple bot that makes random actions.
 */
public class RandomBot implements BotInterface {
  private final BackEnd backEnd;

  /**
   * Constructs a RandomBot with the specified BackEnd.
   *
   * @param backEnd The BackEnd associated with the bot.
   */
  public RandomBot(BackEnd backEnd) {
    this.backEnd = backEnd;
  }

  /**
   * Makes a random action.
   */
  public void action() {
    this.backEnd.getLevel().move(Direction.random());
  }

  /**
   * Returns the BackEnd associated with the bot.
   *
   * @return The BackEnd associated with the bot.
   */
  public BackEnd getBackEnd() {
    return backEnd;
  }
}
