import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * RMIServer used to access a database on the server.
 */
public class RMIServer {
  private static String codebase = "http://localhost/"; // An HTTP/FTP/NFS/FS codebase
  private static String name = "RemoteDatabaseServer"; // Name to be registered in RMI Registry

  /**
   * Creates the stub (RMIInterface), exports and registers it to the registry under the name of 'name'
   * And creates a window with the Exit button.
   * @param args Commandline arguments
   * @throws Exception
   */
  public static void main(String args[]) throws Exception {
    if (args.length > 0) {
      codebase = args[0];
    }
    if (args.length > 1) {
      name = args[1];
    }

    RMIImplementation rmiImplementation = new RMIImplementation();
    RMIInterface stub = (RMIInterface) UnicastRemoteObject.exportObject(rmiImplementation, 0);
    Registry registry = LocateRegistry.getRegistry();
    registry.rebind(name, stub);
    System.out.println("Stub is bound as " + name);

    JFrame frame = new JFrame("Server");
    JButton button = new JButton("Exit");
    ActionListener listener =
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            System.exit(0);
          }
        };

    button.addActionListener(listener);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationByPlatform(true);
    frame.add(button);
    frame.setSize(450, 150);

    SwingUtilities.invokeLater(
        new Runnable() {
          @Override
          public void run() {
            frame.setVisible(true);
          }
        });
  }
}
