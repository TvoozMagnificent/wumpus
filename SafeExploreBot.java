import java.util.ArrayList;
import java.util.Comparator;

public class SafeExploreBot extends SafeReturnBot {
  /**
   * Constructs a SafeExploreBot with the specified BackEnd.
   *
   * @param backEnd The BackEnd associated with the bot.
   */
  public SafeExploreBot(BackEnd backEnd) {
    super(backEnd);
  }

  /**
   * Makes a safe exploration action.
   */
  public void action() {
    if (!this.getBackEnd().getLevel().hasGold()) super.action();
    else {
      Coordinate current = this.getBackEnd().getLevel().getAgentCoord();
      BitMap exploreSquares = this.getSafeSquares().subtract(this.getBackEnd().getLoaded());
      if (exploreSquares.size() == 0) super.action();
      else {
        ArrayList<Coordinate> sortedSquares = exploreSquares.asCoordinates();
        sortedSquares.sort(Comparator.comparingDouble(a -> -BFS.distance(new Coordinate(), a, this.getSafeSquares())));
        sortedSquares.sort(Comparator.comparingDouble(a -> -Math.max(Math.abs(a.getRow() - 1.5), Math.abs(a.getColumn() - 1.5))));
        sortedSquares.sort(Comparator.comparingDouble(a -> BFS.distance(current, a, this.getSafeSquares())));
        ArrayList<Direction> directions = BFS.directions(current, sortedSquares.get(0), this.getSafeSquares());
        if (directions == null) super.action(); // just default back to super.action()
        else if (directions.isEmpty())
          throw new IllegalStateException("We are on an unexplored square? - something needs updating. ");
        else this.getBackEnd().getLevel().move(directions.get(0));
      }
    }
  }
}
