import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.*;

/**
 * BuyOrder is a type of order that gains hitcoins to the trader which created it when it reaches it target.
 */
public class BuyOrder extends Order {

  /**
   * Creates an order with the input variables.
   * @param t Trader which created this order
   * @param pos Initial position of the order
   * @param target  Target of the order
   * @param s Speed of the order
   * @param amount  Size of the order
   */
  public BuyOrder(Trader t, Vec2 pos, Vec2 target, double s, int amount) {
    super(t, pos, target, s, amount);
  }

  /**
   * Draws the BuyOrder as a Green ball with amount written on the inside. The initials of the trader is written above the order.
   * @param g2d Graphics object which the drawings are made.
   */

  @Override
  public void draw(Graphics2D g2d) {
    Font font = g2d.getFont();
    g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
    FontMetrics metrics = g2d.getFontMetrics();
    AffineTransform transform = g2d.getTransform();
    g2d.translate(pos.x, pos.y);
    g2d.setPaint(Color.GREEN);
    g2d.fillOval(-common.orderBallRadius/2, -common.orderBallRadius/2, common.orderBallRadius, common.orderBallRadius);
    g2d.drawString(trader.initials, -metrics.stringWidth(trader.initials)/2, -12);
    g2d.setPaint(Color.BLACK);
    g2d.drawString(Integer.toString(amount), -metrics.stringWidth(Integer.toString(amount))/2, 6);
    g2d.setTransform(transform);
    g2d.setFont(font);
  }
}
