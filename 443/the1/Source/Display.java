import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * This class extends JPanes and is responsible for the drawing of all the <code>Entity</code> s to the window.
 * It calls the draw method of all entries when appropriate.
 */
public class Display extends JPanel {

  private final BufferedImage hitcoinImage;
  private Common common;

  /**
   * Constructor, loads the Hitcoin symbol to the memory and clears the background.
   */
  public Display() {
    BufferedImage readImage = null;
    try {
      readImage = ImageIO.read(new File("Hitcoin.gif"));
    } catch (IOException e) {
      System.out.println("Problem reading 'Hitcoin.gif'");
    } finally{
      hitcoinImage = readImage;
      common = Common.getInstance();
      setBackground(Color.WHITE);
    }
  }

  /**
   * Returns the preffered size for the opened window.
   * @return  Preffered size.
   * @see Dimension
   */
  public Dimension getPreferredSize() {
    return new Dimension(common.windowWidth, common.windowHeight);
  }

  /**
   * This function calls the draw methods of the existing entities. A Graphics object is passed to all draw functions
   * @param g Graphics object that all entities draw on.
   */
  public final void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2d.drawImage(hitcoinImage, 0, 0, common.hitcoinIconDim, common.hitcoinIconDim, null);
    synchronized (common) {
      if(common.pricePlot != null) {
        common.pricePlot.draw(g2d);
      }
      if(common.ticker != null) {
        common.ticker.draw(g2d);
      }
      if(common.traders != null && common.traders.size() > 0) {
        for(Trader t : common.traders) {
          t.draw(g2d);
        }
      }
      if(common.orders != null && common.orders.size() > 0) {
        for(Order o : common.orders) {
          o.draw(g2d);
        }
      }
      if(common.conmen != null && common.conmen.size() > 0) {
        for(Conman c: common.conmen) {
          c.draw(g2d);
        }
      }
    } //end synchronized
  }
}
