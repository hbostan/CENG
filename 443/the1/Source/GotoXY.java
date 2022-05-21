import java.util.*;

/**
 * This state determines a random point in the area where conmans can travel and goes to this target
 * point with a random speed. Expires when the destination is reached.
 */
public class GotoXY extends State {
  public Vec2 target;
  public double speed;

  /**
   * Determines a random target point and a random speed.
   */
  public GotoXY() {
    super();
    target = selectTarget();
    speed = 3 + random.nextDouble()*5;
  }

  /**
   * Moves the conman in the direction of the target with some speed.
   * @param conman Conman whose position is updated.
   */
  @Override
  public void update(Conman conman) {
    Vec2 direction = target.sub(conman.pos).normalize();
    double distance = conman.pos.distanceTo(target);
    Vec2 moveVector = direction.multiply(speed);

    if( moveVector.length() > distance ) {
      moveVector = direction.multiply(distance);
    }
    if(Double.isFinite(moveVector.x)&& Double.isFinite(moveVector.y)) {
      conman.pos.add(moveVector);
    }
    if(conman.pos.distanceTo(target) < 2.0) {
      conman.stateExpired = true;
    }
  }
}
