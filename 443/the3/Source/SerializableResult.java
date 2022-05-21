import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to hold the results created by a database query/update. It either takes the
 * return value (int) of the database call, or a ResultSet as the result of the database call.
 */
public class SerializableResult implements Serializable {

  /**
   * Return value of a database update call.
   */
  public int updateReturnValue;
  /**
   * Column names of the table from the result of a database query.
   */
  public List<String> columnNames;
  /**
   * Rows of the table from the result of a database query.
   */
  public List<List<Object>> rows;

  /**
   * Takes in the update value of the database call.
   * @param updateReturnValue Return value of an update query.
   */
  public SerializableResult(int updateReturnValue) {
    this.updateReturnValue = updateReturnValue;
    this.columnNames = null;
    this.rows = null;
  }

  /**
   * Takes in a ResultSet from a database query, and fills in the columnNames and rows from the data
   * accordingly.
   * @param resultSet
   * @throws Exception
   */
  public SerializableResult(ResultSet resultSet) throws Exception {
    this.updateReturnValue = -1;
    this.columnNames = new ArrayList<>();
    this.rows = new ArrayList<>();

    ResultSetMetaData metaData = resultSet.getMetaData();
    int columnCount = metaData.getColumnCount();
    for(int i=1; i<=columnCount; i++) {
      columnNames.add(metaData.getColumnName(i));
    }
    while (resultSet.next()) {
      ArrayList<Object> row = new ArrayList<>();
      for(int i=1; i<=columnCount; i++){
        row.add(resultSet.getObject(i));
;     }
      rows.add(row);
    }
  }
}
