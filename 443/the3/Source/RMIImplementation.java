import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class responsible for Database communication. Implements RMIInterface so it can be exported.
 */
public class RMIImplementation implements RMIInterface {

  /**
   * Database connector's class name
   */
  private final String driver;
  /**
   * Url of the database server
   */
  private final String url;
  /**
   * Name of the database to be used.
   */
  private final String database;
  /**
   * Username to access the database.
   */
  private final String username;
  /**
   * Password used to access the database
   */
  private final String password;
  /**
   * Database connection created. Kept until the object is destroyed.
   */
  private Connection connection;
  /**
   * Used to execute SQL.
   */
  private Statement statement;

  /**
   * Makes preparations to create a db connection. Then calls connect() to create the connection and statement.
   * @throws Exception
   */
  public RMIImplementation() throws Exception {
    this.driver = "com.mysql.jdbc.Driver";
    this.url = "jdbc:mysql://localhost:3306/";
    this.database = "Ceng443";
    this.username = "root";
    this.password = "";
    this.connection = null;
    this.statement = null;

    connect();
  }

  /**
   * Makes preparations to create a db connection. Then calls connect() to create the connection and statement.
   * @param driver  Database connector's class name
   * @param url Url of the database server
   * @param database  Name of the database to be used.
   * @param username  Username to access the database.
   * @param password  Database connection created. Kept until the object is destroyed.
   * @throws Exception
   */
  public RMIImplementation(
      String driver, String url, String database, String username, String password)
      throws Exception {
    this.driver = driver;
    this.url = url;
    this.database = database;
    this.username = username;
    this.password = password;
    this.connection = null;
    this.statement = null;

    connect();
  }

  /**
   * Loads the Driver for the database connection incase it is not loaded already. Then creates the
   * connection to the database. And creases a Statement object from the connection.
   * @throws Exception
   */
  private void connect() throws Exception {
    Class.forName(driver);
    connection = DriverManager.getConnection(url+database, username, password);
    statement = connection.createStatement();
  }

  /**
   * Closes the connection when the object is destored.
   * @throws Throwable
   */
  @Override
  protected void finalize() throws Throwable {
    if (connection != null || !connection.isClosed()) {
      connection.close();
    }
  }

  /**
   * Executes the SQL Query/Update on the database and returns the result as SerializableResult.
   * @param sql SQL Query/Update string
   * @param isQuery Whether SQL string is query or not.
   * @return SerializableResult as the result of the sql query.
   * @throws RemoteException
   */
  @Override
  public SerializableResult processSQL(String sql, boolean isQuery) throws RemoteException {
    System.out.println("Got request: "+sql+", Is Query? "+isQuery);
    SerializableResult serializableResult = null;
    try{
      if(isQuery) {
        ResultSet resultSet = statement.executeQuery(sql);
        serializableResult = new SerializableResult(resultSet);
      } else {
        int result = statement.executeUpdate(sql);
        serializableResult = new SerializableResult(result);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return serializableResult;
  }
}
