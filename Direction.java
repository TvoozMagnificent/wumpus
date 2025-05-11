/**
 * Enumerates the orthogonal directions.
 */
public enum Direction {
  UP,
  DOWN,
  LEFT,
  RIGHT;

  /**
   * Returns a random direction.
   *
   * @return A random direction.
   */
  public static Direction random() {
    Direction[] directions = Direction.values();
    return directions[(int) (Math.random() * directions.length)];
  }
}
