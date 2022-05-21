
import java.util.*;

/**
 * This state checks the nearest Orders and tries to move to the position of the closest one for some time.
 * The time is determined by the timeout variable.
 */
public class ChaseClosest extends State {
  public int timeout;
  public double speed;

  /**
   * Sets the timeout and a random speed between 3 and 8.
   */
  public ChaseClosest() {
    super();
    timeout = 60;
    speed = 3 + random.nextDouble()*5;
  }

  /**
   * Scans all the orders and finds the closest one to the passed in Conman. Then updates the conman's
   * position towards the closest Order.
   * @param conman The conman which is in this state.
   */
  @Override
  public void update(Conman conman) {
    Vec2 target = conman.pos;
    double minimumDistance = Double.MAX_VALUE;
    Iterator<Order> it = common.orders.iterator();
    while(it.hasNext()){
      Order current = it.next();
      double distance = conman.pos.distanceTo(current.pos);
      if(distance < minimumDistance) {
        minimumDistance = distance;
        target = current.pos;
      }
    }
    Vec2 direction = target.sub(conman.pos).normalize();
    double distance = conman.pos.distanceTo(target);
    Vec2 moveVector = direction.multiply(speed);

    if( moveVector.length() > distance ) {
      moveVector = direction.multiply(distance);
    }
    if(Double.isFinite(moveVector.x)&& Double.isFinite(moveVector.y)) {
      conman.pos.add(moveVector);
    }

    timeout -= 1;
    if(timeout < 0) {
      conman.stateExpired = true;
    }
  }
}