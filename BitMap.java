import java.util.ArrayList;

/**
 * Represents a bitmap of coordinates.
 */
public class BitMap {
  private int value; // The value of the bitmap, represented as an integer.

  /**
   * Constructs an empty bitmap.
   */
  public BitMap() {
    this(0);
  }

  /**
   * Constructs a bitmap with the given value.
   *
   * @param value The value of the bitmap.
   */
  public BitMap(int value) {
    this.value = value;
  }

  /**
   * Constructs a bitmap based on a single coordinate.
   *
   * @param coordinate The coordinate to be represented in the bitmap.
   */
  public BitMap(Coordinate coordinate) {
    this(1 << coordinate.hashCode());
  }

  /**
   * Constructs a bitmap based on an ArrayList of coordinates.
   *
   * @param coordinates The ArrayList of coordinates to be represented in the bitmap.
   */
  public BitMap(ArrayList<Coordinate> coordinates) {
    this.value = 0;
    for (Coordinate coordinate : coordinates)
      this.value |= 1 << coordinate.hashCode();
  }

  /**
   * Updates with filtering a BitMap based on another BitMap.
   *
   * @param other The other BitMap to filter against.
   */
  public void filter(BitMap other) {
    this.value &= other.hashCode();
  }

  /**
   * Updates with filtering a BitMap based on a single coordinate.
   * Equivalent to filtering with a BitMap containing only that coordinate.
   *
   * @param other The coordinate to filter against.
   */
  public void filter(Coordinate other) {
    this.value &= 1 << other.hashCode();
  }

  /**
   * Computes the Bitwise AND of this BitMap with another BitMap.
   *
   * @param other The other BitMap to AND with.
   * @return A new BitMap representing the result of the AND operation.
   */
  public BitMap and(BitMap other) {
    return new BitMap(this.value & other.hashCode());
  }

  /**
   * Computes the Bitwise AND of this BitMap with a single coordinate.
   * Equivalent to ANDing with a BitMap containing only that coordinate.
   *
   * @param other The coordinate to AND with.
   * @return A new BitMap representing the result of the AND operation.
   */
  public BitMap and(Coordinate other) {
    return new BitMap(this.value & 1 << other.hashCode());
  }

  /**
   * Updates with the Bitwise OR of this BitMap with another BitMap.
   *
   * @param other The other BitMap to OR with.
   */
  public void update(BitMap other) {
    this.value |= other.hashCode();
  }

  /**
   * Updates with the Bitwise OR of this BitMap with a single coordinate.
   * Equivalent to ORing with a BitMap containing only that coordinate.
   *
   * @param other The coordinate to OR with.
   */
  public void update(Coordinate other) {
    this.value |= 1 << other.hashCode();
  }

  /**
   * Computes the Bitwise OR of this BitMap with another BitMap.
   *
   * @param other The other BitMap to OR with.
   * @return A new BitMap representing the result of the OR operation.
   */
  public BitMap or(BitMap other) {
    return new BitMap(this.value | other.hashCode());
  }

  /**
   * Computes the Bitwise OR of this BitMap with a single coordinate.
   * Equivalent to ORing with a BitMap containing only that coordinate.
   *
   * @param other The coordinate to OR with.
   * @return A new BitMap representing the result of the OR operation.
   */
  public BitMap or(Coordinate other) {
    return new BitMap(this.value | 1 << other.hashCode());
  }

  /**
   * Updates with the Bitwise REMOVE of this BitMap with another BitMap.
   * a REMOVE b = a & ~b.
   *
   * @param other The other BitMap to REMOVE with.
   */
  public void remove(BitMap other) {
    this.value &= ~other.hashCode();
  }

  /**
   * Updates with the Bitwise REMOVE of this BitMap with a single coordinate.
   * Equivalent to REMOVEing with a BitMap containing only that coordinate.
   *
   * @param other The coordinate to REMOVE with.
   */
  public void remove(Coordinate other) {
    this.value &= ~(1 << other.hashCode());
  }

  /**
   * Computes the Bitwise REMOVE of this BitMap with another BitMap.
   *
   * @param other The other BitMap to REMOVE with.
   * @return A new BitMap representing the result of the REMOVE operation.
   */
  public BitMap subtract(BitMap other) {
    return new BitMap(this.value & ~other.hashCode());
  }

  /**
   * Computes the Bitwise REMOVE of this BitMap with a single coordinate.
   * Equivalent to REMOVEing with a BitMap containing only that coordinate.
   *
   * @param other The coordinate to REMOVE with.
   * @return A new BitMap representing the result of the REMOVE operation.
   */
  public BitMap subtract(Coordinate other) {
    return new BitMap(this.value & ~(1 << other.hashCode()));
  }

  /**
   * Updates this BitMap with the Bitwise NOT operation.
   */
  public void invert() {
    // 0b1111011110111101111 is the fully occupied bitmap.
    this.value = ~this.value & 0b1111011110111101111;
  }

  /**
   * Computes the Bitwise NOT of this BitMap.
   *
   * @return A new BitMap representing the result of the NOT operation.
   */
  public BitMap not() {
    return new BitMap(~this.value);
  }

  /**
   * Given a BitMap, outputs a BitMap featuring all the neighbors of all the bits in the original BitMap,
   * specific to a 4x4 array.
   *
   * @return A new BitMap representing the neighbors of the original BitMap.
   */
  public BitMap getNeighbors() {
    // 0b1111011110111101111 is the fully occupied bitmap.
    // shifts by 5 as up or down and shifts by 1 as left and right.
    return new BitMap((value << 1 | value >> 1 | value << 5 | value >>> 5) & 0b1111011110111101111);
  }

  /**
   * Checks if a specific coordinate is set in the BitMap.
   *
   * @param coordinate The coordinate to check.
   * @return true if the coordinate is set, false otherwise.
   */
  public boolean get(Coordinate coordinate) {
    return (value >> (coordinate.hashCode()) & 1) == 1;
  }

  /**
   * Returns the value of the BitMap.
   *
   * @return The value of the BitMap.
   */
  public int hashCode() {
    return value;
  }

  /**
   * Generates a uniformly random BitMap.
   *
   * @return A uniformly random BitMap.
   */
  public static BitMap random() {
    // 0b1111011110111101111 is the fully occupied bitmap.
    return new BitMap((int) (Math.random() * 1048576) & 0b1111011110111101111);
  }

  /**
   * Randomizes the BitMap value.
   */
  public void randomize() {
    // 0b1111011110111101111 is the fully occupied bitmap.
    this.value = (int) (Math.random() * 1048576) & 0b1111011110111101111;
  }

  /**
   * Generates a random BitMap with a specified density.
   *
   * @param density The density of the BitMap.
   * @return A uniformly random BitMap with the specified density.
   */
  public static BitMap uniform(double density) {
    int value = 0;
    for (int bit = 0; bit < 20; bit++) {
      value *= 2;
      if (Math.random() < density) value++;
    }
    // 0b1111011110111101111 is the fully occupied bitmap.
    return new BitMap(value & 0b1111011110111101111);
  }

  /**
   * Returns an ArrayList of coordinates in the BitMap.
   *
   * @return An ArrayList of coordinates in the BitMap.
   */
  public ArrayList<Coordinate> asCoordinates() {
    ArrayList<Coordinate> coordinates = new ArrayList<>();
    for (Coordinate coordinate : Coordinate.allCoordinates)
      if (this.get(coordinate)) coordinates.add(coordinate);
    return coordinates;
  }

  /**
   * Returns the size of the BitMap. (The number of bits set to 1).
   *
   * @return The size of the BitMap.
   */
  public int size() {
    return this.asCoordinates().size();
  }

  /**
   * Returns a single coordinate from the BitMap.
   *
   * @return A single coordinate from the BitMap.
   * @throws IllegalArgumentException if the BitMap does not contain exactly one coordinate.
   */
  public Coordinate asCoordinate() {
    if (this.size() != 1)
      throw new IllegalArgumentException("Expected one coordinate with hashmap " + value + ". ");
    return this.asCoordinates().get(0);
  }

  /**
   * Returns a string representation of the BitMap. Bits are represented as "." for off and "#" for on.
   *
   * @return The string representation of the BitMap.
   */
  public String toString() {
    return this.toString(".", "#");
  }

  /**
   * Returns a string representation of the BitMap. Bits are represented as "." for off and the specified string for on.
   *
   * @param on The string representation for on bits.
   * @return The string representation of the BitMap.
   */
  public String toString(String on) {
    return this.toString(".", on);
  }

  /**
   * Returns a string representation of the BitMap. Bits are represented as the specified strings for off and on.
   *
   * @param off The string representation for off bits.
   * @param on  The string representation for on bits.
   * @return The string representation of the BitMap.
   */
  public String toString(String off, String on) {
    StringBuilder string = new StringBuilder(this.value + ": \n");
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++)
        string.append(this.get(new Coordinate(row, col)) ? on : off).append(" ");
      string.append("\n");
    }
    return string.toString();
  }

  /**
   * Returns a copy of the BitMap.
   *
   * @return A new BitMap with the same value.
   */
  public BitMap copy() {
    return new BitMap(this.value);
  }

  /**
   * Checks if two BitMaps are equal based on their hash codes.
   *
   * @param other The other BitMap to compare with.
   * @return true if the BitMaps are equal, false otherwise.
   */
  public boolean equals(BitMap other) {
    return this.hashCode() == other.hashCode();
  }

  /**
   * Checks if two BitMaps are equal based on their hash codes.
   *
   * @param other The other object to compare with.
   * @return true if the BitMaps are equal, false otherwise.
   * Also false if the other object is null or not of the same class.
   */
  public boolean equals(Object other) {
    if (other == null || other.getClass() != this.getClass()) return false;
    BitMap bitmap = (BitMap) other;
    return this.equals(bitmap);
  }
}
