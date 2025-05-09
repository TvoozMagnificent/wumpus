import java.util.HashMap;

/**
 * Debugging methods.
 */
public class Utility {
  /**
   * Prints the contents of a HashMap.
   *
   * @param hashmap The HashMap to print.
   * @param <K>     Key type.
   * @param <V>     Value type.
   */
  public static <K, V> void printHashMap(HashMap<K, V> hashmap) {
    for (K key : hashmap.keySet()) System.out.println("| " + key + ": " + hashmap.get(key));
  }
}
