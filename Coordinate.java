import java.util.ArrayList;

/**
 * Represents a coordinate in a 4x4 grid.
 */
public class Coordinate {
  private int row, col;
  public static ArrayList<Coordinate> allCoordinates = new ArrayList<>();

  static {
    // Initialize allCoordinates with all possible coordinates in a 4x4 grid.
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++)
        allCoordinates.add(new Coordinate(i, j));
    }
  }

  /**
   * Constructs a coordinate (0, 0) on the top left corner of the grid.
   */
  public Coordinate() {
    this(0, 0);
  }

  /**
   * Constructs a coordinate with the specified row and column.
   *
   * @param row The row of the coordinate.
   * @param col The column of the coordinate.
   */
  public Coordinate(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * Constructs a coordinate based on a BitMap.
   *
   * @param bitmap The BitMap to be converted to a coordinate.
   */
  public Coordinate(BitMap bitmap) {
    Coordinate that = bitmap.asCoordinate();
    this.row = that.getRow();
    this.col = that.getColumn();
  }

  /**
   * Returns the row of the coordinate.
   *
   * @return The row of the coordinate.
   */
  public int getRow() {
    return this.row;
  }

  /**
   * Returns the column of the coordinate.
   *
   * @return The column of the coordinate.
   */
  public int getColumn() {
    return this.col;
  }

  /**
   * Returns the hash code of the coordinate.
   *
   * @return The hash code of the coordinate.
   */
  public int hashCode() {
    return this.row * 5 + this.col;
  }

  /**
   * Returns a uniformly random coordinate in the grid.
   *
   * @return A random coordinate in the grid.
   */
  public static Coordinate random() {
    return new Coordinate((int) (Math.random() * 4), (int) (Math.random() * 4));
  }

  /**
   * Randomize the coordinate to a random position in the grid.
   */
  public void randomize() {
    this.row = (int) (Math.random() * 4);
    this.col = (int) (Math.random() * 4);
  }

  /**
   * Returns a BitMap representation of the coordinate.
   *
   * @return A BitMap representing the coordinate.
   */
  public BitMap asBitMap() {
    return new BitMap(this);
  }

  /**
   * Returns the neighbors of the coordinate as an ArrayList of Coordinates.
   *
   * @return An ArrayList of neighboring coordinates.
   */
  public ArrayList<Coordinate> getNeighbors() {
    return this.asBitMap().getNeighbors().asCoordinates();
  }

  /**
   * Moves the coordinate up by one row.
   *
   * @return true if the move was successful, false if it hit a wall.
   */
  public boolean moveUp() {
    if (this.row == 0) return false; // out of bounds
    this.row--;
    return true;
  }

  /**
   * Moves the coordinate down by one row.
   *
   * @return true if the move was successful, false if it hit a wall.
   */
  public boolean moveDown() {
    if (this.row == 3) return false; // out of bounds
    this.row++;
    return true;
  }

  /**
   * Moves the coordinate left by one column.
   *
   * @return true if the move was successful, false if it hit a wall.
   */
  public boolean moveLeft() {
    if (this.col == 0) return false; // out of bounds
    this.col--;
    return true;
  }

  /**
   * Moves the coordinate right by one column.
   *
   * @return true if the move was successful, false if it hit a wall.
   */
  public boolean moveRight() {
    if (this.col == 3) return false; // out of bounds
    this.col++;
    return true;
  }

  /**
   * Moves the coordinate in the specified direction.
   *
   * @param direction The direction to move in.
   * @return true if the move was successful, false if it hit a wall.
   */
  public boolean move(Direction direction) {
    switch (direction) {
      case UP:
        return moveUp();
      case DOWN:
        return moveDown();
      case LEFT:
        return moveLeft();
      case RIGHT:
        return moveRight();
      default:
        throw new IllegalArgumentException("Unidentifiable direction - what the fuck? ");
    }
  }

  /**
   * Returns a string representation of the coordinate.
   *
   * @return a string representation of the coordinate.
   */
  public String toString() {
    return "(" + this.row + ", " + this.col + ")";
  }

  /**
   * Calls the toString method of the corresponding BitMap.
   *
   * @param on the representation for an on bit in the corresponding BitMap
   * @return the String presentation of the corresponding BitMap
   * where "." represents an off bit and on represents an on bit.
   */
  public String toString(String on) {
    return this.asBitMap().toString(on);
  }

  /**
   * Returns a copy of this Coordinate.
   *
   * @return a copy of this Coordinate.
   */
  public Coordinate copy() {
    return new Coordinate(this.row, this.col);
  }

  /**
   * Checks if two Coordinates are equal based on their hash codes.
   *
   * @param other The other object to compare with.
   * @return true if the Coordinates are equal, false otherwise.
   */
  public boolean equals(Coordinate other) {
    return this.hashCode() == other.hashCode();
  }

  /**
   * Checks if two Coordinates are equal based on their hash codes.
   *
   * @param other The other object to compare with.
   * @return true if the Coordinates are equal, false otherwise.
   * Also false if the other object is null or not of the same class.
   */
  public boolean equals(Object other) {
    if (other == null || other.getClass() != this.getClass()) return false;
    Coordinate coordinate = (Coordinate) other;
    return this.equals(coordinate);
  }
}
