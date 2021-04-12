package Table;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Table.Table.TableStatus;
import util.Controller;

public class TableCtrl extends Controller {
	
	private static final String DELIMITER = ",";
	
	static Scanner sc = new Scanner(System.in);
	private ArrayList<Table> tables = new ArrayList<Table>();
	static int maxTableID = 1; // TODO: SET BY DATABASE		
	
	public TableCtrl() {
		readData("data/Tables.txt");
	}
	
//	public void createMultipleTables() {
//		int size = 0;
//		int numTable = 0;
//		while (true) {
//			System.out.print("Please enter table size or enter -1 to end table adding: ");
//			size = sc.nextInt();
//			if (size == -1) {
//				break;
//			} else {
//				System.out.print("Please enter number of tables of this size: ");
//				numTable = sc.nextInt();
//				for (int i = 0; i < numTable; ++i) {
//					addTable(size);
//				}
//			}
//		}
//	}
	
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
	
	// Return table IDs for those with equal or greater size
	public ArrayList<Integer> getAvaiTableIDsBySize(int size) {
		ArrayList<Integer> sizedTables = new ArrayList<Integer>();

		for(Table t : tables) {
			if(t.getStatus() == TableStatus.VACANT && t.getSize() >= size)
				sizedTables.add(t.getTableID());
		}
		
		return sizedTables;
	}
	
	public ArrayList<Integer> getAvaiTableIDsBySize() {
		int size = querySize();
		ArrayList<Integer> sizedTables = new ArrayList<Integer>();

		for(Table t : tables) {
			if(t.getStatus() == TableStatus.VACANT && t.getSize() >= size)
				sizedTables.add(t.getTableID());
		}
		
		if (sizedTables.isEmpty()) {
			System.out.println("There are no available tables of size " + size + "!");
		} else {
			System.out.println(sizedTables);
		}
		
		return sizedTables;
	}
	
	public void printAllTables() {
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
	
//	public void addTable() {
//		int size = querySize();
//		tables.add(new Table(size));
//	}
	
	private void addTable(int tableID, int size, TableStatus tableStatus) {
		tables.add(new Table(tableID, size, tableStatus));
	}
	
//	public void addTable(int size) {
//		tables.add(new Table(size));
//	}
	
//	public void removeTable() {
//		int tableID = queryTableID();
//		boolean found = false;
//		for (int i = 0; i < tables.size(); ++i) {
//			Table currTable = tables.get(i);
//			if (currTable.getTableID() == tableID) {
//				System.out.println("Table with ID " + tableID + " removed!");
//				tables.remove(i);
//				found = true;
//				break;
//			}
//		}
//		if (!found) {
//			System.out.println("There is no table with ID " + tableID + "!");
//		}
//	}
	
//	public void removeTable(int TableID) {
//		int tableID = queryTableID();
//		boolean found = false;
//		for (int i = 0; i < tables.size(); ++i) {
//			Table currTable = tables.get(i);
//			if (currTable.getTableID() == tableID) {
//				System.out.println("Table with ID " + tableID + " removed!");
//				tables.remove(i);
//				found = true;
//				break;
//			}
//		}
//		if (!found) {
//			System.out.println("There is no table with ID " + tableID + "!");
//		}
//	}
		
	public void assignTable() {
		int tableID = queryTableID();
		int tableIndex = getTableIndex(tableID);
		if (tableIndex == -1) {
			System.out.println("No such table!");
		} /* else if (tables.get(tableIndex - 1).getStatus() != TableStatus.VACANT || tables.get(tableIndex + 1).getStatus() != TableStatus.VACANT) {
			System.out.println("This table is unavailable as an adjacent table is occupied / reserved!");
		} */ else {
			System.out.println("Table with ID " + tableID + " set to occupied!");
			tables.get(tableIndex).setToOccupied();
		}
		writeData("data/Tables.txt");
	}
	
	public void assignTable(int tableID) {
		int tableIndex = getTableIndex(tableID);
		if (tableIndex == -1) {
			System.out.println("No such table!");
		} /* else if (tables.get(tableIndex - 1).getStatus() != TableStatus.VACANT || tables.get(tableIndex + 1).getStatus() != TableStatus.VACANT) {
			System.out.println("This table is unavailable as an adjacent table is occupied / reserved!");
		} */ else {
			System.out.println("Table with ID " + tableID + " set to occupied!");
			tables.get(tableIndex).setToOccupied();
		}
		writeData("data/Tables.txt");
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
		writeData("data/Tables.txt");
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
		writeData("data/Tables.txt");
	}
	
	public void vacateTable() {
		int tableID = queryTableID();
		int tableIndex = getTableIndex(tableID);
		if (tableIndex == -1) {
			System.out.println("No such table!");
		} else {
			System.out.println("Table with ID " + tableID + " set to vacant!");
			tables.get(tableIndex).setToVacant();
		}
		writeData("data/Tables.txt");
	}
	
	public void vacateTable(int tableID) {
		int tableIndex = getTableIndex(tableID);
		if (tableIndex == -1) {
			System.out.println("No such table!");
		} else {
			System.out.println("Table with ID " + tableID + " set to vacant!");
			tables.get(tableIndex).setToVacant();
		}
		writeData("data/Tables.txt");
	}
	
	public TableStatus getTableStatus() {
		int tableID = queryTableID();
		for (int i = 0; i < tables.size(); ++i) {
			Table currTable = tables.get(i);
			if (currTable.getTableID() == tableID) {
				System.out.println("Table " + tableID + " is " + currTable.getStatus());
				return currTable.getStatus();
			}
		}
		
		return null;
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
	
	private int getTableIndex(int tableID) {
		for (int i = 0; i < tables.size(); ++i) {
			Table currTable = tables.get(i);
			if (currTable.getTableID() == tableID) {
				return i;
			}
		}
		return -1;
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
	
	public static int queryTableID() {
		boolean validInput = false;
		int tableID = -1;
		System.out.print("Enter table ID: ");
		while (!validInput) {
			try {
				tableID = sc.nextInt();
				if (tableID > 0 && tableID < 31) {		// Possibly use maxTableID
					validInput = true;
				}
			} catch(InputMismatchException e) {
				System.out.print("Please enter a valid tableID: ");
				tableID = sc.nextInt();
			}
		}
		
		return tableID;
	}
	
	public void writeData(String fileName) {
		try {
			PrintWriter writer = new PrintWriter(fileName);
			for (int i = 0; i < tables.size(); ++i) {
				Table currTable = tables.get(i);
				Integer tableID = currTable.getTableID();
				Integer tableSize = currTable.getSize();
				String tableStatus = currTable.getStatus().toString();
				writer.write(String.join(DELIMITER, new String[] {tableID.toString(), tableSize.toString(), tableStatus}));
				writer.write("\n");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		}
	}
	
	public void readData(String fileName) {
		String line;
		tables.clear(); // Empty tables arraylist
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			while ((line = reader.readLine()) != null) {
				String[] items = line.split(DELIMITER);
				int tableID = Integer.parseInt(items[0]);
				int tableSize = Integer.parseInt(items[1]);
				TableStatus tableStatus = tableStatusFromString(items[2]);
				addTable(tableID, tableSize, tableStatus);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Input mistmatch!");
			e.printStackTrace();
		}
	}
	
	private TableStatus tableStatusFromString(String currStatus) {
		if (currStatus.equals("RESERVED")) {
			return TableStatus.RESERVED;
		} else if (currStatus.equals("OCCUPIED")) {
			return TableStatus.OCCUPIED;
		} else {
			return TableStatus.VACANT;
		}
	}
	
}
