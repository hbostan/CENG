import java.util.*;

/**
 * Order entity, which is represented as a ball on the screen. It has a target, position speed, trader, and amount.
 */
public abstract class Order extends Entity {
  public Common common;
  public int amount;
  public Trader trader;
  public Vec2 target;
  public double speed;

  /**
   * Initializes an Order with given values
   * @param trader Trader which created this order
   * @param pos Position of the order
   * @param target Target of the order
   * @param speed Speed which the order moves
   * @param amount Order size.
   */
  public Order(Trader trader, Vec2 pos, Vec2 target, double speed, int amount) {
    this.trader = trader;
    this.pos = pos;
    this.target = target;
    this.speed = speed;
    this.amount = amount;
    this.common = Common.getInstance();
  }

  /**
   * Moves the order in the direction of the target with some speed.
   */
  @Override
  public void update() {
    Vec2 direction = target.sub(pos).normalize();
    double distance = pos.distanceTo(target);
    Vec2 moveVector = direction.multiply(speed);

    if( moveVector.length() > distance ) {
      moveVector = direction.multiply(distance);
    }
    if(Double.isFinite(moveVector.x)&& Double.isFinite(moveVector.y)) {
      pos.add(moveVector);
    }

  }
}
