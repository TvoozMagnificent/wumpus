/**
 * Contains the inner calculations of the representations required for the GUI.
 */
public class BackEnd {
  private final Level level;
  public final BitMap loaded = new BitMap(), breeze = new BitMap(), stench = new BitMap(), gold = new BitMap();

  /**
   * Constructs a BackEnd object with a randomly generated level.
   */
  public BackEnd() {
    this(Level.generateLevel());
  }

  /**
   * Constructs a BackEnd object with the specified level.
   *
   * @param level The level to be played.
   */
  public BackEnd(Level level) {
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
   * Update on the current observations. We update these BitMaps:
   * loaded (or observed), observed breeze, observed stench, and observed gold.
   */
  public void updateObservations() {
    Coordinate current = level.getAgentCoord();
    // Update the loaded BitMap with the current agent's position
    loaded.update(current);
    // Update the breeze BitMap with the current breeze observations
    if (level.detectsBreeze()) breeze.update(current);
    // Update the stench BitMap with the current stench observations
    if (level.detectsStench()) stench.update(current);
    // Update the gold BitMap with the current gold observations
    if (level.detectsGlitter()) gold.update(current);
  }

  /**
   * Returns the BitMap represented loaded coordinates.
   *
   * @return The BitMap representing loaded coordinates.
   */
  public BitMap getLoaded() {
    return loaded;
  }

  /**
   * Returns the BitMap represented observed breeze coordinates.
   *
   * @return The BitMap representing observed breeze coordinates.
   */
  public BitMap getBreeze() {
    return breeze;
  }

  /**
   * Returns the BitMap represented observed stench coordinates.
   *
   * @return The BitMap representing observed stench coordinates.
   */
  public BitMap getStench() {
    return stench;
  }

  /**
   * Returns the BitMap represented observed gold coordinates.
   *
   * @return The BitMap representing observed gold coordinates.
   */
  public BitMap getGold() {
    return gold;
  }

  /**
   * Returns a unique ID connected to the current observed level state.
   * (The observed level state includes:
   * 1. The BitMap loaded
   * 2. The BitMap breeze
   * 3. The BitMap stench
   * 4. Arrow information, which includes
   * 4a. Whether the arrow is shot,
   * 4b. If so, whether it hit the Wumpus,
   * and 4c. If so, the coordinate and direction of the shot.
   * and finally,
   * 5. Gold status (notice that where the gold is picked up is irrelevant)
   * Notice how score is not included in this ID, since this ID
   * is for Q-learning purposes, and the score is not relevant for Q-learning.
   *
   * @return A unique ID representing the current observed level state.
   */
  public ID getID() {
    if (!level.hasArrow()) {
      return new ID(loaded, breeze, stench, level.getShotCoordinate(), level.getShotDirection(), level.hasWumpus(), level.hasGold());
    } else {
      return new ID(loaded, breeze, stench, new Coordinate(0, 0), Direction.UP, false, level.hasGold());
    }
  }

  /**
   * Returns the information about the cell at the specified coordinate in HTML.
   *
   * @param coordinate The coordinate to check.
   * @return A string containing the information about the cell.
   */
  public String getCellInfo(Coordinate coordinate) {
    StringBuilder info = new StringBuilder("<html>");
    if (level.getAgentCoord().equals(coordinate)) info.append("You are here. <br/>");
    if (this.getBreeze().get(coordinate)) info.append("You feel a breeze. <br/>");
    if (this.getStench().get(coordinate)) info.append("You smell a stench. <br/>");
    if (this.getGold().get(coordinate)) info.append("You see glitter. <br/>");
    info.append("</html>");
    return info.toString().replace("<br/></html>", "</html>"); // replace trailing <br/>
  }

  /**
   * Returns the general information (information that does not belong to any coordinate)
   * about the game in HTML.
   *
   * @return A string containing the general information about the game.
   */
  public String getGeneralInfo() {
    if (level.hasEnded()) {
      switch (level.endType()) {
        case PIT:
          return "You fell into a pit. <br/> Game over. <br/> Score: " + level.getScore() + "<br/> Play again? ";
        case WUMPUS:
          return "You were eaten by the Wumpus. <br/> Game over. <br/> Score: " + level.getScore() + "<br/> Play again? ";
        case WIN:
          return "You brought the gold back! <br/> Game over. <br/> Score: " + level.getScore() + "<br/> Play again? ";
      }
    }
    StringBuilder info = new StringBuilder();
    if (level.hasWumpus()) info.append("The Wumpus still dwells. <br/>");
    else info.append("The Wumpus is dead. <br/>");
    if (level.hasArrow()) info.append("You have an arrow. <br/>");
    else info.append("You used your arrow. <br/>");
    if (level.hasGold()) info.append("You have to retrieve the gold. <br/>");
    else info.append("You have to bring back the gold. <br/>");
    info.append("Current score: ").append(level.getScore());
    return info.toString(); // replace trailing <br/>
  }
}
