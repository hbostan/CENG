import java.util.*;

/**
 * This <b>singleton</b> class holds the common variables which all classes may need and also holds all the existing
 * entities in the program. It also updates all the entities.
 */
public class Common {

  public final int hitcoinIconDim = 200;
  public final int tickerHeight = 30;
  public final int traderDim = 200;
  public final int conmanDim = 100;
  public final int orderBallRadius = 20;
  public final int windowWidth = 1200;
  public final int windowHeight = 1000;
  public final int traderPadded = 236;
  public PricePlot pricePlot;
  public Ticker ticker;
  public List<Trader> traders;
  public List<Conman> conmen;
  public List<Order> orders;
  public OrderFactory[] orderFactories;
  private static Common instance;

  /**
   * Makes sure that there are no preexisting entities and creates the OrderFactories for Traders to use.
   */
  private Common() {
    pricePlot = null;
    ticker = null;
    traders = null;
    conmen = null;
    orders = null;
    instance = this;
    orderFactories = new OrderFactory[2];
    orderFactories[0] = new BuyOrderFactory();
    orderFactories[1] = new SellOrderFactory();
  }

  /**
   * Gets the reference of the instance of this class. If it is not created yet, creates one.
   * @return Reference to the single instance of this class.
   */
  public static Common getInstance() {
    if(instance != null) {
      return instance;
    }
    return new Common();
  }

  /**
   * Updates all the existing entities and checks for collisions/conman levels.
   */
  public synchronized void updateAll() {
    if(pricePlot!=null) {
      pricePlot.update();
    }
    if(ticker!=null) {
      ticker.update();
    }
    if(traders != null && traders.size() > 0) {
      for(Trader t : traders) {
        t.update();
      }
    }
    if(orders != null && orders.size() > 0) {
      Iterator<Order> it = orders.iterator();
      while(it.hasNext()) {
        Order o = it.next();
        o.update();
        if( o.pos.distanceTo(o.target) < 2.0) {
          int targetX = ((int)o.target.x) - hitcoinIconDim;
          if(targetX < 0) targetX = 0;
          if(targetX > pricePlot.data.size()-1) targetX = pricePlot.data.size()-1;
          int targetPrice = pricePlot.getPriceAt(targetX)*o.amount;
          if(o instanceof BuyOrder) {
            o.trader.cash -= targetPrice;
            o.trader.coins += o.amount;
          } else if (o instanceof SellOrder) {
            o.trader.cash += targetPrice;
            o.trader.coins -= o.amount;
          }
          it.remove();
        }
      }
    }

    if(conmen != null && conmen.size() > 0) {
      for(Conman c: conmen) {
        c.update();
        Iterator<Order> it = orders.iterator();
        while(it.hasNext()){
          Order order = it.next();
          if(c.pos.distanceTo(order.pos) < conmanDim/2 -5) {
            int stolen = order.amount * pricePlot.getLastValue();
            c.moneyStolen += stolen;
            if(order instanceof BuyOrder) {
              order.trader.cash -= stolen;
            }
            if(order instanceof SellOrder) {
              order.trader.coins -= order.amount;
            }
            it.remove();
          }
        }
        if(c.moneyStolen > 1999 && c.moneyStolen < 3000 && !(c instanceof Novice)){
          int index = conmen.indexOf(c);
          conmen.set(index, new Novice(c));
        }
        if(c.moneyStolen > 2999 && c.moneyStolen < 5000 && !(c instanceof Master)){
          int index = conmen.indexOf(c);
          conmen.set(index, new Master(c));
        }
        if(c.moneyStolen > 4999 && !(c instanceof Expert)){
          int index = conmen.indexOf(c);
          conmen.set(index, new Expert(c));
        }
      }
    }
  }
}
