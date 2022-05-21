import java.awt.Graphics2D;
import java.util.*;

/**
 * Base class for all animation entities.
 */
public abstract class Entity {

  public Vec2 pos;

  public abstract void draw(Graphics2D g2d);

  public abstract void update();
}
