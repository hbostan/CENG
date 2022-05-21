import java.util.*;

/**
 * In this state the conman 'shakes' randomly for an amount of time.
 * Time is controlled by timeout variable. It moves to a +/-3 pixel position on each update.
 */
public class Shake extends State {
  public int timeout;

  /**
   * Initializes the state
   */
  public Shake() {
    super();
    timeout = 60;
  }

  /**
   * Updates the postion of he conman randomly.
   * @param conman Conman which is in this state
   */
  @Override
  public void update(Conman conman) {
    int randX = random.nextInt(5)-2;
    int randY = random.nextInt(5)-2;
    conman.pos.add(new Vec2(randX, randY));
    timeout -= 1;
    if(timeout<0) {
      conman.stateExpired = true;
    }
  }
}
