import java.util.ArrayList;
import java.util.Scanner;

public class TableSystem {
	
	Scanner sc = new Scanner(System.in);
	
	private ArrayList<Table> tables = new ArrayList<Table>();
	
	public TableSystem() {
		int size = 0;
		int numTable = 0;
		while (true) {
			System.out.print("Please enter table size or enter -1 to end table adding: ");
			size = sc.nextInt();
			if (size == -1) {
				break;
			} else {
				System.out.print("Please enter number of tables of this size: ");
				numTable = sc.nextInt();
				for (int i = 0; i < numTable; ++i) {
					addTable(size);
				}
			}
		}
	}
	
	public void getAvailableTables(int size) {
		ArrayList<Integer> availableTables = new ArrayList<Integer>();
		for (int i = 0; i < tables.size(); ++i) {
			Table currTable = tables.get(i);
			if (currTable.getStatus() == TableStatus.VACANT && currTable.getSize() == size) {
				availableTables.add((Integer) i + 1);
			}
		}
		System.out.println(availableTables);
	}
	
	public void addTable(int size) {
		tables.add(new Table(size));
	}
	
	public void addTable(int size, TableStatus status) {
		tables.add(new Table(size, status));
	}
		
	public void assignTable(int tableID) {
		tables.get(tableID).setToOccupied();
	}
	
	public void reserveTable(int tableID) {
		tables.get(tableID).setToReserved();
	}
	
	public void vacateTable(int tableID) {
		tables.get(tableID).setToVacant();
	}
	
}
