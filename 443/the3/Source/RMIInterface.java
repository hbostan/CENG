import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {
  public SerializableResult processSQL(String sql, boolean isQuery)
      throws RemoteException; // If no ResultSet expected, set isQuery to false
}
