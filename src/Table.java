enum TableStatus { RESERVED, OCCUPIED, VACANT }

public class Table {
	
	private int size;
	private TableStatus status = TableStatus.VACANT; // Set to empty when initializing
	private int tableID;
	
	public Table(int size) {
		this.size = size;
		this.tableID = TableCtrl.currTableID;
		TableCtrl.currTableID++;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public TableStatus getStatus() {
		return this.status;
	}
	
	public int getTableID() {
		return this.tableID;
	}
	
	public void setToOccupied() {
		this.status = TableStatus.OCCUPIED;
	}
	
	public void setToReserved() {
		this.status = TableStatus.RESERVED;
	}
	
	public void setToVacant() {
		this.status = TableStatus.VACANT;
	}
	
}

