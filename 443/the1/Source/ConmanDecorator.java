import java.awt.Graphics2D;
import java.util.*;

/**
 * Parent class of Conman decorations.
 */
public abstract class ConmanDecorator extends Conman {
  public Conman conman;

  /**
   * Copies the status of the passed in conman to itself.
   * @param conman Conman whose status is copied.
   */
  public ConmanDecorator(Conman conman) {
    super(conman.name);
    this.conman = conman;
    this.stateExpired = conman.stateExpired;
    this.state = conman.state;
    this.common = conman.common;
    this.moneyStolen = conman.moneyStolen;
    this.random = conman.random;
    this.pos = conman.pos;
  }
  public abstract void draw(Graphics2D g2d);
}
