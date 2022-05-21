import java.util.*;

/**
 * A High level factory class to be used with BuyOrderFactory and SellOrderFactory
 */
public abstract class OrderFactory {
  public OrderFactory() {}
  public abstract Order createOrder(Trader t);
}
