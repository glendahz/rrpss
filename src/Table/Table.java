package Table;

/**
* Represents a table in the restaurant
 * @author david
 * @version 1.0
 * @since 2021-04-07
 */

public class Table {
	
	/**
	 * The three available states of a table - Reserved, Occupied, Vacant
	 */
	public enum TableStatus { RESERVED, OCCUPIED, VACANT }
	/**
	 * Size of this table
	 */
	private int size;
	/**
	 * Table status for this table
	 */
	private TableStatus status = TableStatus.VACANT; // Set to empty when initializing
	/**
	 * Table ID for this table
	 */
	private int tableID;
	
	/**
	 * Creates a table of a given size
	 * @param size
	 */
	public Table(int size) {
		this.size = size;
		this.tableID = TableCtrl.maxTableID;
		TableCtrl.maxTableID++;
	}
	
	/**
	 * Creates a table with a specific ID, size and status
	 * @param tableID
	 * @param size
	 * @param status
	 */
	public Table(int tableID, int size, TableStatus status) {
		this.tableID = tableID;
		this.size = size;
		this.status = status;
	}
	
	/**
	 * Get the size of this table
	 * @return this table's size
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Get the table status of this table
	 * @return this table's table status
	 */
	public TableStatus getStatus() {
		return this.status;
	}
	
	/**
	 * Get the table ID for this table
	 * @return this table's table ID
	 */
	public int getTableID() {
		return this.tableID;
	}
	
	/**
	 * Change the table status of this table to OCCUPIED
	 */
	public void setToOccupied() {
		this.status = TableStatus.OCCUPIED;
	}
	
	/**
	 * Change the table status of this table to RESERVED
	 */
	public void setToReserved() {
		this.status = TableStatus.RESERVED;
	}
	
	/**
	 * Change the table status of this table to VACANT
	 */
	public void setToVacant() {
		this.status = TableStatus.VACANT;
	}
	
}

