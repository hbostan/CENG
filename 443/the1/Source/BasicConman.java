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
 * Basic version of the conman without any level badges.
 */
public class BasicConman extends Conman {
  public BufferedImage portrait;

  /**
   * Creates a basic conman
   * @param name  Name of the conman
   * @param portraitName Picture path for the portrait
   */
  public BasicConman(String name, String portraitName) {
    super(name);
    try{
      portrait = ImageIO.read(new File(portraitName));
    } catch (IOException e) {
      portrait = null;
      System.out.println("Cannot open '"+portraitName+"' image");
    }

  }

  /**
   * Draws the conman's portrait, money and current state.
   * @param g2d Graphics object the drawings are made.
   */
  @Override
  public void draw(Graphics2D g2d) {
    Font font = g2d.getFont();
    g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
    FontMetrics metrics = g2d.getFontMetrics();
    AffineTransform transform = g2d.getTransform();
    g2d.translate(pos.x, pos.y);
    g2d.drawImage(portrait, -common.conmanDim/2, -common.conmanDim/2, common.conmanDim, common.conmanDim, null);
    g2d.translate(0, -common.conmanDim/2);
    g2d.setPaint(Color.BLACK);
    g2d.drawString(name, -metrics.stringWidth(name)/2, 0);
    g2d.setPaint(Color.BLUE);
    String stateName = state.getClass().getSimpleName();
    g2d.drawString(stateName, -metrics.stringWidth(stateName)/2, common.conmanDim+metrics.getHeight());
    g2d.setPaint(Color.MAGENTA);
    String moneyString = Integer.toString(moneyStolen);
    g2d.drawString(moneyString, -metrics.stringWidth(moneyString)/2, common.conmanDim+2*metrics.getHeight());
    g2d.setFont(font);
    g2d.setTransform(transform);
  }
}
