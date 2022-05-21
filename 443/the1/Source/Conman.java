import java.util.*;

/**
 * Entities that try to collect Orders before they reach their destinations. It holds a state inside and
 * switches it when the state is expired.
 */
public abstract class Conman extends Entity {
  public String name;
  public boolean stateExpired;
  public int moneyStolen;
  public State state;
  public Common common;
  public Random random;

  /**
   * Sets the name, state and position of the conman
   * @param name Name of the conman
   */
  public Conman(String name) {
    this.common = Common.getInstance();
    this.random = new Random();
    this.name = name;
    this.state = getRandomState();
    this.pos = state.selectTarget();
    this.stateExpired = false;
    this.moneyStolen = 0;
  }

  /**
   * Calls the current state's update method.
   */
  public void update() {
    state.update(this);
    if(stateExpired) {
      state = getRandomState();
      stateExpired = false;
    }
  }

  /**
   * Returns a new random state. Called when the current state expires.
   * @return A new random State.
   */
  public State getRandomState() {
    int rnd =random.nextInt(100);
    if(rnd < 25) {
      return new Rest();
    } else if (rnd < 50) {
      return new GotoXY();
    } else if (rnd < 75) {
      return new Shake();
    } else {
      return new ChaseClosest();
    }
  }
}
