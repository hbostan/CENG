import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.*;

/**
 * The ticker at the bottom of the screen, it shows 100 companies from BIST in a random order each time
 * the program is started. It also randomly assigns status arrows next of them showing the status.
 * A greem up arrow
 * A red down arrow
 * A gray right arrow
 */
public class Ticker extends Entity {

  private final String[] oompanyNames = { "AEFES", "AFYON", "AKBNK", "AKENR", "AKSA", "AKSEN", "ALARK", "ALCTL", "ALGYO", "ALKIM", "ANACM", "ANELE", "ARCLK", "ASELS", "AYGAZ", "BAGFS", "BANVT", "BIMAS", "BIZIM", "BJKAS", "BRISA", "CCOLA", "CEMTS", "CLEBI", "CRFSA", "DEVA", "DOAS", "DOHOL", "ECILC", "EGEEN", "EKGYO", "ENKAI", "ERBOS", "EREGL", "FENER", "FROTO", "GARAN", "GLYHO", "GOLTS", "GOODY", "GOZDE", "GSDHO", "GSRAY", "GUBRF", "HALKB", "HLGYO", "ICBCT", "IHLAS", "IPEKE", "ISCTR", "ISGYO", "IZMDC", "KARSN", "KARTN", "KCHOL", "KIPA", "KLGYO", "KONYA", "KORDS", "KOZAA", "KOZAL", "KRDMD", "MAVI", "METRO", "MGROS", "NETAS", "NTTUR", "ODAS", "OTKAR", "PETKM", "PGSUS", "PRKME", "SAHOL", "SASA", "SISE", "SKBNK", "SODA", "TATGD", "TAVHL", "TCELL", "THYAO", "TKFEN", "TKNSA", "TMSN", "TOASO", "TRCAS", "TRKCM", "TSKB", "TSPOR", "TTKOM", "TTRAK", "TUPRS", "ULKER", "VAKBN", "VESTL", "VKGYO", "YATAS", "YAZIC", "YKBNK", "ZOREN" };
  private final int[] downArrowX = { 7, 14, 10, 10, 4, 4, 0 };
  private final int[] downArrowY = { 0, 7, 7, 14, 14, 7, 7 };
  private final int[] rightArrowX = { 0, 7, 7, 14, 7, 7, 0 };
  private final int[] rightArrowY = { 4, 4, 0, 7, 14, 10, 10 };
  private final int[] upArrowX = { 4, 10, 10, 14, 7, 0, 4 };
  private final int[] upArrowY = { 0, 0, 7, 7, 14, 7, 7 };
  private Common common;
  private ArrayList<String> companies;
  private ArrayList<Integer> status;
  private Random random;
  private int offset;
  private int cyclicIndex;

  /**
   * Shuffles the companies and generates random statuses for each
   */
  public Ticker() {
    common = Common.getInstance();
    random = new Random();
    pos = new Vec2(0, common.windowHeight - 2 - common.tickerHeight);
    companies = new ArrayList<String>(Arrays.asList(oompanyNames));
    status = new ArrayList<>();
    Collections.shuffle(companies);
    for(int i = 0 ; i < companies.size(); i++) {
      status.add(Integer.valueOf(random.nextInt(3)));
    }
    offset = 0;
    cyclicIndex = 0;
  }

  /**
   * Draws the company name and the status arrow for the current screen width. It starts drawing from
   * the (-offset, 0). This way we get the sliding text effect. When current company is totally out of
   * view we reset the offset and move to the next company.
   * @param g2d Graphics object on which the drawings are made.
   */
  @Override
  public void draw(Graphics2D g2d) {
    Font font = g2d.getFont();
    AffineTransform transform = g2d.getTransform();
    g2d.setFont(new Font("Sans Serif", Font.BOLD, 18));
    FontMetrics metrics = g2d.getFontMetrics();
    g2d.translate(pos.x, pos.y);
    g2d.setPaint(Color.LIGHT_GRAY);
    g2d.fillRect(0, 0, common.windowWidth, common.tickerHeight);
    if( offset > metrics.stringWidth(companies.get(cyclicIndex))+39) {
      cyclicIndex = (cyclicIndex+1) % companies.size();
      offset = 0;
    }
    g2d.translate(-offset, 0);
    for(int i = 0, j = cyclicIndex; i<common.windowWidth + offset; i+=40) {
      String companyName = companies.get(j);
      int companyStatus = status.get(j);
      j = (j+1) % companies.size();
      g2d.setPaint(Color.BLACK);
      g2d.drawString(companyName, 0, metrics.getHeight());
      g2d.translate(metrics.stringWidth(companyName) + 5, 10);
      switch (companyStatus) {
        case 0:
          g2d.setPaint(Color.GREEN);
          g2d.fillPolygon(downArrowX, downArrowY, 7);
          break;
      case 1:
        g2d.setPaint(Color.DARK_GRAY);
        g2d.fillPolygon(rightArrowX, rightArrowY, 7);
        break;
      case 2:
        g2d.setPaint(Color.RED);
        g2d.fillPolygon(upArrowX, upArrowY, 7);
        break;
      }
      g2d.translate(35, -10);
      i += metrics.stringWidth(companyName);
    }
    g2d.setFont(font);
    g2d.setTransform(transform);
  }

  /**
   * Increases the offset variable.
   */
  @Override
  public void update() {
    offset += 1;
  }
}
