import javax.swing.table.AbstractTableModel;

/**
 * This class is used to represent the results of the SQL Query/Update executed.
 * Instances of this class can be used as model for a JTable.
 */
public class ResultTableModel extends AbstractTableModel {

  /**
   * Object received from server as the result of the SQL Query/Update.
   */
  private final SerializableResult serializableResult;

  /**
   * Creates a new ResultTableModel from as SerializeableResult.
   * @param serializableResult  A SerializableResult.
   */
  public ResultTableModel(SerializableResult serializableResult) {
    this.serializableResult = serializableResult;
  }

  /**
   * Gets the number of rows in the table representaion.
   * @return The number of rows in the table.
   */
  @Override
  public int getRowCount() {
    if(serializableResult.rows != null) {
      return serializableResult.rows.size();
    }
    return 1;
  }

  /**
   * Gets the number of Columns in the table representaion.
   * @return  number of cols in the table.
   */
  @Override
  public int getColumnCount() {
    if (serializableResult.columnNames != null) {
      return serializableResult.columnNames.size();
    }
    return 1;
  }

  /**
   * Gets the columns title, gets the title in the SQL Result at a given index. If the serializeable
   * result is created by an Update (not a Query) it returns a single Column name ("Update Return Value").
   * @param colIndex Index of the column
   * @return Title of the column
   */
  @Override
  public String getColumnName(int colIndex) {
    if(serializableResult.columnNames != null) {
      return serializableResult.columnNames.get(colIndex);
    }
    return "Update Return Value";
  }

  /**
   * Gets the Class of the column. This is useful for the different representations of the integers,
   * strings, floats etc. in the table.
   * @param colIndex  Column index
   * @return Class of the column.
   */
  @Override
  public Class getColumnClass(int colIndex) {
    if ((serializableResult.rows != null) && (serializableResult.rows.size() > 0)) {
      return getValueAt(0, colIndex).getClass();
    }
    if (colIndex == 0) {
      return Integer.class;
    }
    return Object.class;
  }

  /**
   * Returns true if a table cell is editable, false otherwise. Since our table is not editable this
   * always returns false.
   * @param rowIndex  Row index of the cell
   * @param colIndex  Column index of the cell
   * @return Whether cell is editable or not.
   */
  @Override
  public boolean isCellEditable(int rowIndex, int colIndex) {
    return false;
  }

  /**
   * Returns the Object at the given coordinates.
   * @param rowIndex Row index of the cell
   * @param colIndex  Column index of the cell.
   * @return  Object in the cell.
   */
  @Override
  public Object getValueAt(int rowIndex, int colIndex) {
    if (serializableResult.rows != null && serializableResult.columnNames != null) {
      return serializableResult.rows.get(rowIndex).get(colIndex);
    }
    return new Integer(serializableResult.updateReturnValue);
  }
}
