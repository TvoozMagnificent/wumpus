/**
 * Represents the game level, including the agent, Wumpus, gold, and pits.
 */
class Level implements Interface {
  private final BitMap pitMap, breezeMap, stenchMap;
  private Coordinate shotCoordinate = null;
  private Direction shotDirection = null;
  private final Coordinate wumpusCoord, goldCoord, agentCoord;
  private boolean hasWumpus = true, hasArrow = true, hasGold = true, hasEnded = false;
  private int score = 0;
  private End endType = null;
  private ActionType actionType;
  private Direction actionDirection;

  /**
   * Constructs a Level with the specified parameters.
   * Breeze generates from the neighbors of the pits.
   * Stench generates from the neighbors of the Wumpus.
   *
   * @param pitMap      The BitMap representing the pits.
   * @param wumpusCoord The coordinate of the Wumpus.
   * @param goldCoord   The coordinate of the gold.
   */
  public Level(BitMap pitMap, Coordinate wumpusCoord, Coordinate goldCoord) {
    this.agentCoord = new Coordinate();
    this.pitMap = pitMap;
    this.wumpusCoord = wumpusCoord;
    this.goldCoord = goldCoord;
    this.breezeMap = pitMap.getNeighbors();
    this.stenchMap = wumpusCoord.asBitMap().getNeighbors();
  }

  /**
   * Generates a random level with pits, a Wumpus, and gold.
   * Pits generate independently with probability 0.2.
   * The Wumpus and gold are placed in random empty coordinates.
   * Simply retries if the number of pits is too high.
   * (Probably will never happen, but just in case.)
   *
   * @return A new Level object.
   */
  public static Level generateLevel() {
    BitMap occupied = new BitMap(0b1);
    BitMap pitMap = occupied.not().and(BitMap.uniform(0.2));
    occupied.update(pitMap);
    if (occupied.size() > 14) return generateLevel(); // too many pits, retry
    Coordinate wumpusCoord = randEmpty(occupied);
    occupied.update(wumpusCoord);
    Coordinate goldCoord = randEmpty(occupied);
    return new Level(pitMap, wumpusCoord, goldCoord);
  }

  /**
   * Returns a random empty coordinate from the occupied BitMap.
   *
   * @param occupied The BitMap representing occupied coordinates.
   * @return A random empty coordinate.
   */
  public static Coordinate randEmpty(BitMap occupied) {
    Coordinate coordinate = new Coordinate();
    while (occupied.get(coordinate)) coordinate.randomize();
    return coordinate;
  }

  /**
   * Makes the agent move in the specified direction.
   * Also updates the score. Moving (not counting bumping into a wall) costs 1 point.
   * Dropping into a pit or getting captured by the Wumpus costs 1000 points and ends the game.
   * Bringing the gold back to the starting position gives 1000 points.
   * (Capturing the gold and capturing the Wumpus do not give points.)
   *
   * @param direction the direction to move in.
   * @return true if the move was successful, false otherwise.
   */
  public boolean move(Direction direction) {
    this.actionType = ActionType.MOVE;
    this.actionDirection = direction;
    if (!this.hasEnded && this.agentCoord.move(direction)) {
      this.score -= 1;
      if (this.pitMap.get(this.agentCoord)) {
        score -= 1000;
        this.hasEnded = true;
        this.endType = End.PIT;
      }
      if (this.agentCoord.equals(this.wumpusCoord) && this.hasWumpus) {
        score -= 1000;
        this.hasEnded = true;
        this.endType = End.WUMPUS;
      }
      if (this.agentCoord.equals(this.goldCoord) && this.hasGold) {
        this.hasGold = false;
      }
      if (this.agentCoord.equals(new Coordinate()) && !this.hasGold) {
        score += 1000;
        this.hasEnded = true;
        this.endType = End.WIN;
      }
      return true;
    }
    return false;
  }

  public boolean hasWumpus() {
    return this.hasWumpus;
  }

  public boolean hasArrow() {
    return this.hasArrow;
  }

  public boolean hasGold() {
    return this.hasGold;
  }

  public boolean detectsBreeze() {
    return this.breezeMap.get(this.agentCoord);
  }

  public boolean detectsStench() {
    return this.stenchMap.get(this.agentCoord);
  }

  public boolean detectsGlitter() {
    return this.goldCoord.equals(this.agentCoord);
  }

  public boolean shoot(Direction direction) {
    this.actionType = ActionType.SHOOT;
    this.actionDirection = direction;
    if (this.hasEnded || !hasArrow) return false;
    this.shotCoordinate = this.agentCoord.copy();
    this.shotDirection = direction;
    this.score -= 10;
    this.hasArrow = false;
    Coordinate arrow = agentCoord.copy();
    while (arrow.move(direction))
      if (arrow.equals(wumpusCoord))
        this.hasWumpus = false;
    return true;
  }

  public int getScore() {
    return score;
  }

  public boolean hasEnded() {
    return hasEnded;
  }

  public End endType() {
    return endType;
  }

  public Coordinate getAgentCoord() {
    return this.agentCoord;
  }

  /**
   * Returns a string representation of the Level.
   *
   * @return A string representation of the Level.
   */
  public String toString() {
    String string = "";
    string += "Agent: \n" + this.agentCoord.toString("A");
    string += "Wumpus: \n" + this.wumpusCoord.toString("W");
    string += "Stench: \n" + this.stenchMap.toString("S");
    string += "Gold: \n" + this.goldCoord.toString("G");
    string += "Pit: \n" + this.pitMap.toString("P");
    string += "Breeze: \n" + this.breezeMap.toString("B");
    string += "-------";
    return string;
  }

  /**
   * Returns the coordinate of the shot.
   *
   * @return The coordinate of the shot.
   */
  public Coordinate getShotCoordinate() {
    return this.shotCoordinate;
  }

  /**
   * Returns the direction of the shot.
   *
   * @return The direction of the shot.
   */
  public Direction getShotDirection() {
    return this.shotDirection;
  }

  /**
   * Returns the type of the last action performed, regardless of whether it was successful or not.
   *
   * @return The type of the last action performed.
   */
  public ActionType getActionType() {
    return this.actionType;
  }
  
  /**
   * Returns the direction of the last action performed, regardless of whether it was successful or not.
   *
   * @return The direction of the last action performed.
   */
  public Direction getActionDirection() {
    return this.actionDirection;
  }
}
