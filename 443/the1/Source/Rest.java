import java.util.*;

/**
 * In this state the conman keeps its position of an amount of time. Time is determined by the timeout variable.
 */
public class Rest extends State {
  public int timeout;
  public Rest() {
    super();
    timeout = 60;
  }

  /**
   * updates the timeout variable.
   * @param conman The conman which is in this state.
   */
  @Override
  public void update(Conman conman) {
    timeout -= 1;
    if(timeout < 0) {
      conman.stateExpired = true;
    }
  }
}
