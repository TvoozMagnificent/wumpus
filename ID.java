/**
 * Represents an ID connected to an observed level state.
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
 * We represent an arrow that is not shot as an arrow shot upwards from (0, 0)
 * that hits the Wumpus, since this is otherwise impossible.
 */
public class ID {
  private final BitMap loaded, breeze, stench;
  private final Coordinate shotCoordinate;
  private final Direction shotDirection;
  private final boolean arrowHit, goldStatus;

  /**
   * Constructor for ID.
   */
  public ID(BitMap loaded, BitMap breeze, BitMap stench,
            Coordinate shotCoordinate, Direction shotDirection,
            boolean arrowHit, boolean goldStatus) {
    this.loaded = loaded;
    this.breeze = breeze;
    this.stench = stench;
    this.shotCoordinate = shotCoordinate;
    this.shotDirection = shotDirection;
    this.arrowHit = arrowHit;
    this.goldStatus = goldStatus;
  }

  /**
   * Returns whether the two IDs are equal.
   *
   * @param other The other ID to compare with.
   * @return true if the IDs are equal, false otherwise.
   */
  public boolean equals(ID other) {
    return this.loaded.equals(other.loaded) &&
        this.breeze.equals(other.breeze) &&
        this.stench.equals(other.stench) &&
        this.shotCoordinate.equals(other.shotCoordinate) &&
        this.shotDirection == other.shotDirection &&
        this.arrowHit == other.arrowHit &&
        this.goldStatus == other.goldStatus;
  }

  /**
   * Returns whether the two IDs are equal.
   *
   * @param other The other ID to compare with.
   * @return true if the IDs are equal, false otherwise.
   */
  public boolean equals(Object other) {
    if (other instanceof ID) return this.equals((ID) other);
    return false;
  }

  /**
   * Returns the hash code of the ID.
   *
   * @return The hash code of the ID.
   */
  public int hashCode() {
    return this.loaded.hashCode() << 10 | this.breeze.hashCode() << 8 |
        this.stench.hashCode() << 6 | this.shotCoordinate.hashCode() << 4 |
        this.shotDirection.hashCode() + (this.arrowHit ? 2 : 0) +
            (this.goldStatus ? 1 : 0);
  }
}
