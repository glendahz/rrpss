enum TableStatus { RESERVED, OCCUPIED, VACANT }

public class Table {
	
	private int size;
	private TableStatus status = TableStatus.VACANT; // Set to empty when initializing
	
	public Table(int size) {
		this.size = size;
	}
	
	public Table(int size, TableStatus status) {
		this.size = size;
		this.status = status;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public TableStatus getStatus() {
		return this.status;
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
