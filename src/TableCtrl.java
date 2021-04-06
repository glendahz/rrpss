import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TableCtrl {
	
	static int currTableID = 1;	
	
	static Scanner sc = new Scanner(System.in);
	
	private ArrayList<Table> tables = new ArrayList<Table>();
	
	public TableCtrl() {
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
	
	private int querySize() {
		boolean validInput = false;
		int size = -1;
		System.out.print("Enter size of table to query: ");
		while (!validInput) {
			try {
				size = sc.nextInt();
				if (size > 0) {
					validInput = true;
				}
			} catch(InputMismatchException e) {
				System.out.print("Please enter a size: ");
				size = sc.nextInt();
			}
		}
		
		return size;		
	}
	
	private int queryTableID() {
		boolean validInput = false;
		int tableID = -1;
		System.out.print("Enter table ID: ");
		while (!validInput) {
			try {
				tableID = sc.nextInt();
				if (tableID > 0 && tableID < currTableID) {
					validInput = true;
				}
			} catch(InputMismatchException e) {
				System.out.print("Please enter a valid tableID: ");
				tableID = sc.nextInt();
			}
		}
		
		return tableID;
	}
	
	public TableStatus getTableStatus(int tableID) {
		for (int i = 0; i < tables.size(); ++i) {
			Table currTable = tables.get(i);
			if (currTable.getTableID() == tableID) {
				return currTable.getStatus();
			}
		}
		return null;
	}
	
	public void getAvailableTables() {
		int size = querySize();
		ArrayList<Integer> availableTables = new ArrayList<Integer>();
		for (int i = 0; i < tables.size(); ++i) {
			Table currTable = tables.get(i);
			if (currTable.getStatus() == TableStatus.VACANT && currTable.getSize() == size) {
				availableTables.add((Integer) i + 1);
			}
		}
		if (availableTables.isEmpty()) {
			System.out.println("There are no available tables of size " + size + "!");
		} else {
			System.out.println(availableTables);
		}
	}
	
	public void getAllTables() {
		ArrayList<Integer> availableTables = new ArrayList<Integer>();
		for (int i = 0; i < tables.size(); ++i) {
			availableTables.add((Integer) tables.get(i).getTableID());
		}
		if (availableTables.isEmpty()) {
			System.out.println("There are no tables!");
		} else {
			System.out.println(availableTables);
		}
	}
	
	public void addTable() {
		int size = querySize();
		tables.add(new Table(size));
	}
	
	public void addTable(int size) {
		tables.add(new Table(size));
	}
	
	public void removeTable() {
		int tableID = queryTableID();
		boolean found = false;
		for (int i = 0; i < tables.size(); ++i) {
			Table currTable = tables.get(i);
			if (currTable.getTableID() == tableID) {
				System.out.println("Table with ID " + tableID + " removed!");
				tables.remove(i);
				found = true;
				break;
			}
		}
		if (!found) {
			System.out.println("There is no table with ID " + tableID + "!");
		}
	}
	
	public void removeTable(int TableID) {
		int tableID = queryTableID();
		boolean found = false;
		for (int i = 0; i < tables.size(); ++i) {
			Table currTable = tables.get(i);
			if (currTable.getTableID() == tableID) {
				System.out.println("Table with ID " + tableID + " removed!");
				tables.remove(i);
				found = true;
				break;
			}
		}
		if (!found) {
			System.out.println("There is no table with ID " + tableID + "!");
		}
	}
		
	public void assignTable() {
		int tableID = queryTableID();
		int tableIndex = getTableIndex(tableID);
		if (tableIndex == -1) {
			System.out.println("No such table!");
		} else {
			System.out.println("Table with ID " + tableID + " set to occupied!");
			tables.get(tableIndex).setToOccupied();
		}
	}
	
	public void assignTable(int tableID) {
		int tableIndex = getTableIndex(tableID);
		if (tableIndex == -1) {
			System.out.println("No such table!");
		} else {
			System.out.println("Table with ID " + tableID + " set to occupied!");
			tables.get(tableIndex).setToOccupied();
		}
	}
	
	public void reserveTable() {
		int tableID = queryTableID();
		int tableIndex = getTableIndex(tableID);
		if (tableIndex == -1) {
			System.out.println("No such table!");
		}
		else {
			System.out.println("Table with ID " + tableID + " set to reserved!");
			tables.get(tableIndex).setToReserved();
		}
	}
	
	public void reserveTable(int tableID) {
		int tableIndex = getTableIndex(tableID);
		if (tableIndex == -1) {
			System.out.println("No such table!");
		}
		else {
			System.out.println("Table with ID " + tableID + " set to reserved!");
			tables.get(tableIndex).setToReserved();
		}
	}
	
	public void vacateTable() {
		int tableID = queryTableID();
		int tableIndex = getTableIndex(tableID);
		if (tableIndex == -1) {
			System.out.println("No such table!");
		}else {
			System.out.println("Table with ID " + tableID + " set to vacant!");
			tables.get(tableIndex).setToVacant();
		}
	}
	
	public void vacateTable(int tableID) {
		int tableIndex = getTableIndex(tableID);
		if (tableIndex == -1) {
			System.out.println("No such table!");
		}else {
			System.out.println("Table with ID " + tableID + " set to vacant!");
			tables.get(tableIndex).setToVacant();
		}
	}
	
	public int getTableIndex(int tableID) {
		for (int i = 0; i < tables.size(); ++i) {
			Table currTable = tables.get(i);
			if (currTable.getTableID() == tableID) {
				return i;
			}
		}
		return -1;
	}
	
}
