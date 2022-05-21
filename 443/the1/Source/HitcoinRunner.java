import javax.swing.*;
import java.util.*;

/**
 * <h1>Hitcoin Runner</h1>
 * This class sets-up all the components and runs the hitcoin simulation.
 * @author Hakan Bostan
 */
public class HitcoinRunner {
  public JFrame window;
  public Display display;
  public Common common;

  /**
   * All the setup is done in the constructor, it gets the reference of Sigleton Class <code>Common</code>
   * and fills up the necessary fields. It also creates a window and runs it on a separate thread.
   * @see Common
   * @see JFrame
   * @see Display
   */
  public HitcoinRunner() {
    window = new JFrame("HitcoinRunner");
    common = Common.getInstance();
    display = new Display();
    window.add(display);
    window.setLocationByPlatform(true);
    window.setResizable(false);
    window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    window.pack();
    common.pricePlot = new PricePlot();
    common.ticker = new Ticker();
    common.traders = new ArrayList<>();
    int traderHeight = common.windowHeight - common.tickerHeight - common.traderDim - 110;
    int traderWidth = common.traderPadded;
    common.traders.add(new Trader("Bill Gates", "Hell Gates", "BG", 500, 50000, "BillGates.gif", new Vec2(10, traderHeight)));
    common.traders.add(new Trader("Warren Buffet", "Warum Buffet", "WB", 500, 50000, "WarrenBuffet.gif", new Vec2(traderWidth+10, traderHeight)));
    common.traders.add(new Trader("Selim Temizer", "Trade King", "ST", 500, 50000, "SelimTemizer.gif", new Vec2(traderWidth*2+10, traderHeight)));
    common.traders.add(new Trader("Nick Leeson", "Rogue Trader", "NL", 500, 50000, "NickLeeson.gif", new Vec2(traderWidth*3+10, traderHeight)));
    common.traders.add(new Trader("George Soros", "Doom Bringer", "GS", 500, 50000, "GeorgeSoros.gif", new Vec2(traderWidth*4+10, traderHeight)));
    common.orders = new ArrayList<>();

    common.conmen = new ArrayList<>();
    common.conmen.add(new BasicConman("Yalçın", "BankerYalcin.gif"));
    common.conmen.add(new BasicConman("Parsadan", "SelcukParsadan.gif"));
    common.conmen.add(new BasicConman("Sülün", "SulunOsman.gif"));
    common.conmen.add(new BasicConman("Tosun", "TosunMehmet.gif"));

    class RunnableArg implements Runnable {
      HitcoinRunner runner;

      RunnableArg(HitcoinRunner runner) {
        this.runner = runner;
      }

      @Override
      public void run() {
        runner.window.setVisible(true);
      }
    }
    SwingUtilities.invokeLater(new RunnableArg(this));
  }

  /**
   * Main method of the program. It creates an instance of <code>HitcoinRunner</code>. And updates and draws
   * all elements in an infinite loop. Step time (sleep time after a single pass of the loop) is 50ms.
   * @param args Unused.
   */
  public static void main(String[] args) {
    HitcoinRunner hitcoinRunner = new HitcoinRunner();
    while (true) {
      hitcoinRunner.common.updateAll();
      hitcoinRunner.display.repaint();
      try{
        Thread.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
