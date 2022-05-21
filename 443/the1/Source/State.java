import java.util.*;

/**
 * Base class for all the states.
 */
public abstract class State {
  public Common common;
  public Random random;

  /**
   * Initializes the Common reference and the random object.
   */
  public State() {
    common = Common.getInstance();
    random = new Random();
  }

  /**
   * Selects a random target in the area where conmans can move.
   * @return
   */
  public Vec2 selectTarget() {
    int lowX = common.conmanDim/2;
    int highX = common.windowWidth - common.conmanDim/2;
    int lowY = common.hitcoinIconDim + common.conmanDim/2;
    int highY = common.windowHeight - common.tickerHeight - common.traderDim - 120 - common.conmanDim/2;
    int randomX = random.nextInt(highX - lowX + 1) + lowX;
    int randomY = random.nextInt(highY - lowY + 1) + lowY;

    return new Vec2(randomX, randomY);
  }

  public abstract void update(Conman conman);
}
