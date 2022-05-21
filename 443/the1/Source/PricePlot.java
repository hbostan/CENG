import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.*;

/**
 * Price plot entity on top of the screen. Shows the current value and the history of the hitcoin price.
 * It holds the prices as a linked list and updates the linked list on each update.
 */
public class PricePlot extends Entity {
  public LinkedList<Integer> data;
  private Random random;
  public final int width, height;
  public Common common;

  /**
   * Initializes the data with random values.
   */
  public PricePlot() {
    common = Common.getInstance();
    random = new Random();
    pos = new Vec2(common.hitcoinIconDim,0);
    width = common.windowWidth - common.hitcoinIconDim;
    height= common.hitcoinIconDim;
    data = new LinkedList<>();
    data.add(height/2);
    for(int i = 1; i < width; i++) {
      data.add(getNextValue(data.get(i-1)));
    }
  }

  /**
   * Creates a new random value depending on the current value of the hitcoin.
   * @param current Current value of hitcoin
   * @return Next value of hitcoin
   */
  public int getNextValue(int current) {
    int next = current + random.nextInt(51) - 25;
    if(next <= 0) {
      next = 1;
    }
    if(next >= height) {
      next = height-1;
    }
    return next;
  }

  /**
   * Gets the current value of the hitcoin.
   * @return Current value of hitcoin
   */
  public int getLastValue() {
    return data.getLast();
  }

  /**
   * Returns the value of the hitcoin at a given time in the stored history.
   * @param index Index of the linked list node.
   * @return  Value of the linked list node.
   */
  public int getPriceAt(int index) {
    return data.get(index);
  }

  /**
   * Draws lines between the current and the previous elemnt of the linked list to create a line graph.
   * @param g2d Graphics object on which the drawings are made.
   */
  @Override
  public void draw(Graphics2D g2d) {
    Composite composite = g2d.getComposite();
    AffineTransform transform = g2d.getTransform();
    Font font = g2d.getFont();
    g2d.translate(pos.x, pos.y);
    g2d.setPaint(Color.DARK_GRAY);
    g2d.drawLine(0, 0, width, 0);
    g2d.drawLine(0, height, width, height);
    g2d.setPaint(Color.LIGHT_GRAY);
    for(int i=0; i<height; i+=20) {
      g2d.drawLine(0, i, width, i);
    }
    for(int i=0; i<width; i+=20) {
      g2d.drawLine(i, 0, i, height);
    }
    for(int i=1; i<data.size(); i++) {
      int start = common.hitcoinIconDim - data.get(i-1);
      int end = common.hitcoinIconDim - data.get(i);
      g2d.setPaint(Color.BLUE);
      g2d.drawLine(i-1, start, i, end);
    }

    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
    g2d.setPaint(Color.BLACK);
    g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 60));
    g2d.drawString("Hitcoin Price", width/2 - g2d.getFontMetrics().stringWidth("Hitcoin Price")/2, height/2+g2d.getFontMetrics().getHeight()/3);
    g2d.setTransform(transform);
    g2d.setFont(font);
    g2d.setComposite(composite);
  }

  /**
   * Removes the first element of the linked list and generates a new value and places it to the end.
   */
  @Override
  public void update() {
    int last = getLastValue();
    data.add(getNextValue(last));
    data.remove(0);
  }
}
