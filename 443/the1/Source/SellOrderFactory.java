import java.util.*;

/**
 * This class is responsible for the creation of the SellOrders. It gives them an appropriate initial
 * position, target, speed and amount.
 */
public class SellOrderFactory extends OrderFactory {

  Random random;
  Common common;

  /**
   * Initializes the factory.
   */
  public SellOrderFactory() {
    random = new Random();
    common = Common.getInstance();
  }

  /**
   * Creates a SellOrder for the passed in trader.
   * @param t Trader that wants to create a SellOrder
   * @return  Created SellOrder object
   */
  public Order createOrder(Trader t) {
    int amount = random.nextInt(6) + 1;
    double speed = random.nextInt(4) + 6;
    Vec2 target = new Vec2(random.nextInt(common.windowWidth - common.hitcoinIconDim + 1) + common.hitcoinIconDim, common.hitcoinIconDim);
    Vec2 origin = new Vec2(t.pos.x + common.traderPadded/2, t.pos.y);
    return new SellOrder(t, origin, target, speed, amount);
  }
}
