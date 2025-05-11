import java.util.ArrayList;

/**
 * A bot that safely returns to the start position once it has the gold.
 */
public class SafeReturnBot extends RandomBot implements BotInterface {
  private BitMap nonWumpusSpaces, nonPitSpaces, mustPitSpaces, possibleWumpusSpaces;

  /**
   * Constructs a RandomBot with the specified BackEnd.
   *
   * @param backEnd The BackEnd associated with the bot.
   */
  public SafeReturnBot(BackEnd backEnd) {
    super(backEnd);
  }

  /**
   * Makes a random action.
   */
  public void action() {
    if (!this.getBackEnd().getLevel().hasGold()) this.safeReturnAction();
    else super.action();
  }

  /**
   * Makes a safe return action to the start position.
   */
  private void safeReturnAction() {
    Coordinate start = new Coordinate();
    Coordinate current = this.getBackEnd().getLevel().getAgentCoord();
    BitMap safeSquares = this.getSafeSquares();
    ArrayList<Direction> directions = BFS.directions(current, start, safeSquares);
    if (directions == null) throw new IllegalStateException("No safe path found - shouldn't happen. ");
    if (directions.isEmpty()) throw new IllegalStateException("We should've already won? ");
    this.getBackEnd().getLevel().move(directions.get(0));
  }

  /**
   * Updates the internal state of the bot.
   * This method updates the nonWumpusSpaces, nonPitSpaces, mustPitSpaces, and possibleWumpusSpaces BitMaps.
   * It filters the nonWumpusSpaces and nonPitSpaces based on the current observations of stench and breeze.
   * It also identifies mustPitSpaces based on the breeze observations.
   * Finally, if there is only one possible Wumpus space, it updates the nonPitSpaces accordingly.
   */
  public void update() {
    this.nonWumpusSpaces = this.getBackEnd().getLoaded().copy();
    this.nonWumpusSpaces.update(this.getBackEnd().getLoaded().subtract(this.getBackEnd().getStench()).getNeighbors());
    this.possibleWumpusSpaces = this.nonWumpusSpaces.not();
    for (Coordinate coordinate : this.getBackEnd().getStench().asCoordinates())
      this.possibleWumpusSpaces.filter(coordinate.asBitMap().getNeighbors());
    if (this.possibleWumpusSpaces.size() == 1) this.nonWumpusSpaces = this.possibleWumpusSpaces.not();
    this.nonPitSpaces = this.getBackEnd().getLoaded().copy();
    this.nonPitSpaces.update(this.getBackEnd().getLoaded().subtract(this.getBackEnd().getBreeze()).getNeighbors());
    if (this.possibleWumpusSpaces.size() == 1) this.nonPitSpaces.update(this.possibleWumpusSpaces);
    this.mustPitSpaces = new BitMap();
    for (Coordinate coordinate : this.getBackEnd().getBreeze().asCoordinates()) {
      BitMap pitCandidates = coordinate.asBitMap().getNeighbors().subtract(this.nonPitSpaces);
      if (pitCandidates.size() == 1) {
        this.mustPitSpaces.update(pitCandidates);
        this.possibleWumpusSpaces.subtract(pitCandidates);
        this.nonWumpusSpaces.update(pitCandidates);
      }
    }
  }

  /**
   * Returns a BitMap representing the safe squares.
   *
   * @return A BitMap representing the safe squares.
   */
  public BitMap getSafeSquares() {
    this.update();
    return this.nonWumpusSpaces.and(this.nonPitSpaces);
  }

  /**
   * Returns nonWumpusSpaces.
   *
   * @return nonWumpusSpaces.
   */
  public BitMap getNonWumpusSpaces() {
    return this.nonWumpusSpaces;
  }

  /**
   * Returns nonPitSpaces.
   *
   * @return nonPitSpaces.
   */
  public BitMap getNonPitSpaces() {
    return this.nonPitSpaces;
  }

  /**
   * Returns mustPitSpaces.
   *
   * @return mustPitSpaces.
   */
  public BitMap getMustPitSpaces() {
    return this.mustPitSpaces;
  }

  /**
   * Returns possibleWumpusSpaces.
   *
   * @return possibleWumpusSpaces.
   */
  public BitMap getPossibleWumpusSpaces() {
    return this.possibleWumpusSpaces;
  }
}
