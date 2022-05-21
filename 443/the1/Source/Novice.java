import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.*;

/**
 * Novice decoration for the Conman. It places a Blue square to the top right corner of the Conman
 */
public class Novice extends ConmanDecorator {
  /**
   *
   * @param conman Conman to be decorated
   */
  public Novice(Conman conman) {
    super(conman);
  }
  /**
   * First calls the conman's draw fuction and then draws a orange square to the top left of it.
   * @param g2d Graphics object on which the drawings are made
   */
  public void draw(Graphics2D g2d) {
    conman.pos = this.pos;
    conman.state =this.state;
    conman.stateExpired = this.stateExpired;
    conman.moneyStolen = this.moneyStolen;
    conman.random = this.random;
    conman.common = this.common;
    conman.name = this.name;
    conman.draw(g2d);
    AffineTransform transform = g2d.getTransform();
    g2d.translate(pos.x + 10, pos.y);
    g2d.setPaint(Color.ORANGE);
    g2d.fillRect(-common.conmanDim/2, -common.conmanDim/2 - 35, 20, 20);
    g2d.setTransform(transform);
  }
}
