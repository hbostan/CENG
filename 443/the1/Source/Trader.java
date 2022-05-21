import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

/**
 * Trader entities at the near the bottom of the screen. They spawn Buy/Sell orders to buy/sell hitcoins.
 * The have an amount of coins and cash. Their net worth changes with the price of the hitcoin. They are
 * represented by their portraits, names and nicknames.
 */
public class Trader extends Entity {
  public String name;
  public String nickname;
  public String initials;
  public int coins;
  public int cash;
  public BufferedImage portrait;
  private Common common;
  private Random random;

  /**
   * Initializes a trader with given parameters.
   * @param name Name of the trader
   * @param nickname Nickname of the trader
   * @param initials Initials of the trader to be written on the orders of this trader
   * @param coins Coins that thus trader has
   * @param cash Cash that this trader has
   * @param portrait Path to the portrait image
   * @param pos Position on the screen
   */
  public Trader(String name, String nickname, String initials, int coins, int cash, String portrait, Vec2 pos) {
    common = Common.getInstance();
    random = new Random();
    this.name = name;
    this.nickname = nickname;
    this.initials = initials;
    this.coins = coins;
    this.cash = cash;
    this.pos = pos;
    try{
      this.portrait = ImageIO.read(new File(portrait));
    } catch (IOException e) {
      this.portrait = null;
      System.out.println("Cannot find " + portrait+ " image.");
    }
  }

  /**
   * Draws the trader portrait to its position and alse writes the info assciated with it below the portrait.
   * @param g2d
   */
  @Override
  public void draw(Graphics2D g2d) {
    Font font = g2d.getFont();
    g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
    FontMetrics metrics = g2d.getFontMetrics();
    AffineTransform transform = g2d.getTransform();
    g2d.translate(pos.x +common.traderPadded/2, pos.y);
    g2d.drawImage(portrait, -common.traderDim/2, 0, common.traderDim, common.traderDim, null);
    g2d.translate(0, common.traderDim);
    g2d.setPaint(Color.BLACK);
    g2d.drawString(name, -metrics.stringWidth(name)/2, metrics.getHeight());
    g2d.setPaint(Color.MAGENTA);
    g2d.drawString("' "+nickname+" '", -metrics.stringWidth("' "+nickname+" '")/2, metrics.getHeight()*2);
    g2d.setPaint(Color.BLUE);
    g2d.drawString(coins + " coins", -metrics.stringWidth(coins + " coins")/2, metrics.getHeight()*3);
    g2d.setPaint(Color.GREEN);
    g2d.drawString(cash + " cash", -metrics.stringWidth(cash + " cash")/2, metrics.getHeight()*4);
    g2d.setPaint(Color.BLACK);
    int total = cash + coins * (common.pricePlot.getLastValue());
    g2d.drawString("Net: "+ total, -metrics.stringWidth("Net: "+ total)/2, metrics.getHeight()*5);
    g2d.setTransform(transform);
    g2d.setFont(font);
  }

  /**
   * Generates a random order with 1/25 chance on each update.
   */
  @Override
  public void update() {
    if(random.nextInt(25) == 0) {
      common.orders.add(common.orderFactories[random.nextInt(2)].createOrder(this));
    }
    //if(!common.spawned) {
    // common.orders.add(common.orderFactories[random.nextInt(2)].createOrder(this));
    //  common.spawned = true;
    //}
  }
}
