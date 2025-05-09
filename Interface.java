/**
 * Interface for the Wumpus game.
 */
interface Interface {
  /**
   * Returns the current score of the game.
   *
   * @return the current score.
   */
  int getScore();

  /**
   * Moves the agent in the specified direction.
   *
   * @param direction the direction to move in.
   * @return true if the move was successful, false otherwise.
   */
  boolean move(Direction direction);

  /**
   * Shoots an arrow in the specified direction.
   *
   * @param direction the direction to shoot in.
   * @return true if the shot was successful, false otherwise.
   */
  boolean shoot(Direction direction);

  /**
   * Checks if the map still has a Wumpus.
   *
   * @return true if the map has a Wumpus, false otherwise.
   */
  boolean hasWumpus();

  /**
   * Checks if the agent has an arrow.
   *
   * @return true if the agent has an arrow, false otherwise.
   */
  boolean hasArrow();

  /**
   * Checks if the map still has gold.
   *
   * @return true if the map has gold, false otherwise.
   */
  boolean hasGold();

  /**
   * Checks if the agent detects a breeze.
   *
   * @return true if the agent detects a breeze, false otherwise.
   */
  boolean detectsBreeze();

  /**
   * Checks if the agent detects a stench.
   *
   * @return true if the agent detects a stench, false otherwise.
   */
  boolean detectsStench();

  /**
   * Checks if the agent detects glitter.
   *
   * @return true if the agent detects glitter, false otherwise.
   */
  boolean detectsGlitter();

  /**
   * Checks if the game has ended.
   *
   * @return true if the game has ended, false otherwise.
   */
  boolean hasEnded();

  /**
   * Returns the type of game-ending condition.
   *
   * @return the type of game-ending condition.
   */
  End endType();

  /**
   * Returns the current coordinate of the agent.
   *
   * @return the current coordinate of the agent.
   */
  Coordinate getAgentCoord();
}
