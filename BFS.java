import java.util.ArrayList;
import java.util.HashMap;

/**
 * Implements a breadth-first search algorithm to find the shortest path in a grid.
 * The BFS algorithm explores all possible paths from the starting coordinate to the target coordinate,
 * avoiding blocked coordinates.
 */
public class BFS {
  /**
   * Finds the shortest path between two coordinates in a grid, avoiding blocked coordinates.
   *
   * @param from    the starting coordinate.
   * @param to      the target coordinate.
   * @param blocked a BitMap representing blocked coordinates.
   * @return an ArrayList of Directions representing the path from 'from' to 'to', or null if no path exists.
   */
  public static ArrayList<Direction> directions(Coordinate from, Coordinate to, BitMap blocked) {
    if (from.equals(to)) return new ArrayList<>();
    BitMap visited = blocked.or(from);
    HashMap<Coordinate, Coordinate> fathers = new HashMap<>();
    HashMap<Coordinate, Direction> directions = new HashMap<>();
    ArrayList<Coordinate> queue = new ArrayList<>();
    int pointer = 0;
    queue.add(from);
    while (pointer < queue.size()) {
      Coordinate current = queue.get(pointer++);
      for (Direction direction : Direction.values()) {
        Coordinate copy = current.copy();
        if (copy.move(direction) && !visited.get(copy)) {
          fathers.put(copy, current);
          directions.put(copy, direction);
          if (copy.equals(to)) return backtrack(from, to, fathers, directions);
          visited.update(copy);
          queue.add(copy);
        }
      }
    }
    return null;
  }

  /**
   * Backtracks from the target coordinate to the starting coordinate using the fathers and directions maps.
   *
   * @param from       the starting coordinate.
   * @param to         the target coordinate.
   * @param fathers    a HashMap mapping each coordinate to its parent coordinate.
   * @param directions a HashMap mapping each coordinate to the direction taken to reach it from its parent.
   * @return an ArrayList of Directions representing the path from 'from' to 'to'.
   */
  public static ArrayList<Direction> backtrack(
      Coordinate from, Coordinate to, HashMap<Coordinate, Coordinate> fathers, HashMap<Coordinate, Direction> directions) {
    ArrayList<Direction> result = new ArrayList<>();
    while (!to.equals(from)) {
      result.add(0, directions.get(to));
      to = fathers.get(to);
    }
    return result;
  }
}
