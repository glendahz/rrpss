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

/**
 * Control class that controls the retrieval and storing of tables
 * @author david
 * @version 1.0
 * @since 2021-04-07
 */

public class TableCtrl extends Controller {
	
	/**
	 * Delimiter for file reading and writing - CSV
	 */
	private static final String DELIMITER = ",";
	
	/**
	 * Scanner for getting inputs from the user
	 */
	static Scanner sc = new Scanner(System.in);
	/**
	 * Dynamic list to store all tables belonging to the restaurant
	 */
	private ArrayList<Table> tables = new ArrayList<Table>();
	/**
	 * Next table ID that has yet been assigned
	 */
	static int maxTableID = 1; // TODO: SET BY DATABASE		
	
	/**
	 * Creates the TableCtrl that imports all tables from the text file storing the staff information
	 */
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
	
	/**
	 * Prints the tables that are VACANT of a certain user-inputted size
	 */
	public void getAvailableTables() {
		int size = querySize();
		ArrayList<Table> availableTables = new ArrayList<Table>();
		for (int i = 0; i < tables.size(); ++i) {
			Table currTable = tables.get(i);
			if (currTable.getStatus() == TableStatus.VACANT && currTable.getSize() == size) {
				availableTables.add(currTable);
			}
		}
		if (availableTables.isEmpty()) {
			System.out.println("\nThere are no available tables of size " + size + "!");
		} else {
			System.out.printf("\n=== Size: %d, Status: %s ===%n", size, TableStatus.VACANT.name());
			printTablesFormatted(availableTables);
		}
		
	}
	
	/**
	 * Returns a list of table IDs of tables that are vacant and also of size that is of at least the parameter
	 * @param size
	 * @return list of table ID fulfilling the condition
	 */
	// Return table IDs for those with equal or greater size
	public ArrayList<Integer> getAvaiTableIDsBySize(int size) {
		ArrayList<Integer> sizedTables = new ArrayList<Integer>();

		for(Table t : tables) {
			if(t.getStatus() == TableStatus.VACANT && t.getSize() >= size)
				sizedTables.add(t.getTableID());
		}
		
		return sizedTables;
	}
	
	/**
	 * Returns a list of table IDs of tables that are vacant and also of size that is of at least of the user input
	 * @return list of table ID fulfilling the condition
	 */
	public void getAvaiTableIDsBySize() {
		int size = querySize();
		ArrayList<Table> sizedTables = new ArrayList<Table>();

		for(Table t : tables) {
			if(t.getStatus() == TableStatus.VACANT && t.getSize() >= size)
				sizedTables.add(t);
		}
		
		if (sizedTables.isEmpty()) {
			System.out.println("\nThere are no available tables of size " + size + "!");
		} else {
			System.out.printf("\n== Size: >=%d, Status: %s ==%n", size, TableStatus.VACANT.name());
			printTablesFormatted(sizedTables);
		}
	}
	
	/**
	 * Print all the table IDs of all the tables in the restaurant
	 */
	public void printAllTables() {
		
		if(tables.size() == 0) {
			System.out.println("\nThere are no tables!");
			return;
		}
		
		System.out.println("\n========== All Tables ==========");
		
		printTablesFormatted(tables);
	}
	
//	public void addTable() {
//		int size = querySize();
//		tables.add(new Table(size));
//	}
	
	/**
	 * Add a table with specified tableID, size and tableStatus
	 * @param tableID
	 * @param size
	 * @param tableStatus
	 */
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
	
	/**
	 * Get the controller to set the table status for the user input table ID to OCCUPIED
	 */
	public void assignTable() {
		int tableID = queryTableID();
		int tableIndex = getTableIndex(tableID);
		System.out.println();
		if (tableIndex == -1) {
			System.out.println("No such table!");
		} else if (tables.get(tableIndex).getStatus() == TableStatus.OCCUPIED) {
			System.out.println("Table is already occupied!");
		} else if (tables.get(Math.max(1, tableIndex - 1)).getStatus() != TableStatus.VACANT || tables.get(Math.min(30, tableIndex + 1)).getStatus() != TableStatus.VACANT) {
			System.out.println("This table is unavailable as an adjacent table is occupied / reserved!");
		} else {
			System.out.println("Table with ID " + tableID + " set to occupied!");
			tables.get(tableIndex).setToOccupied();
		}
		writeData("data/Tables.txt");
	}
	
	/**
	 * Get the controller to set the table status for the parameter table ID to OCCUPIED
	 * @param tableID
	 */
	public void assignTable(int tableID) {
		int tableIndex = getTableIndex(tableID);
		if (tableIndex == -1) {
			System.out.println("No such table!");
		} else if (tables.get(tableIndex).getStatus() == TableStatus.OCCUPIED) {
			System.out.println("Table is already occupied!");
		} else if (tables.get(Math.max(1, tableIndex - 1)).getStatus() != TableStatus.VACANT || tables.get(Math.min(30, tableIndex + 1)).getStatus() != TableStatus.VACANT) {
			System.out.println("This table is unavailable as an adjacent table is occupied / reserved!");
		} else {
			System.out.println("Table with ID " + tableID + " set to occupied!");
			tables.get(tableIndex).setToOccupied();
		}
		writeData("data/Tables.txt");
	}
	
	/**
	 * Get the controller to set the table status for the user input table ID to RESERVED
	 */
	public void reserveTable() {
		int tableID = queryTableID();
		int tableIndex = getTableIndex(tableID);
		if (tableIndex == -1) {
			System.out.println("No such table!");
		} else {
			System.out.println("Table with ID " + tableID + " set to reserved!");
			tables.get(tableIndex).setToReserved();
		}
		writeData("data/Tables.txt");
	}
	
	/**
	 * Get the controller to set the table status for the parameter table ID to RESERVED
	 * @param tableID
	 */
	public void reserveTable(int tableID) {
		int tableIndex = getTableIndex(tableID);
		if (tableIndex == -1) {
			System.out.println("No such table!");
		} else {
			System.out.println("Table with ID " + tableID + " set to reserved!");
			tables.get(tableIndex).setToReserved();
		}
		writeData("data/Tables.txt");
	}
	
	/**
	 * Get the controller to set the table status for the user input table ID to VACANT
	 */
	public void vacateTable() {
		int tableID = queryTableID();
		int tableIndex = getTableIndex(tableID);

		System.out.println();
		if (tableIndex == -1) {
			System.out.println("No such table!");
		} else {
			System.out.println("Table with ID " + tableID + " set to vacant!");
			tables.get(tableIndex).setToVacant();
		}
		writeData("data/Tables.txt");
	}
	
	/**
	 * Get the controller to set the table status for the parameter table ID to VACANT
	 * @param tableID
	 */
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
	
	/**
	 * Get the controller to retrieve the table status for the user input table ID
	 * @return this table's table status
	 */
	public TableStatus getTableStatus() {
		int tableID = queryTableID();
		for (int i = 0; i < tables.size(); ++i) {
			Table currTable = tables.get(i);
			if (currTable.getTableID() == tableID) {
				System.out.println("\nTable " + tableID + " is " + currTable.getStatus());
				return currTable.getStatus();
			}
		}
		
		return null;
	}
	
	/**
	 * Get the controller to retrieve the table status for the parameter table ID
	 * @return this table's table status
	 */
	public TableStatus getTableStatus(int tableID) {
		for (int i = 0; i < tables.size(); ++i) {
			Table currTable = tables.get(i);
			if (currTable.getTableID() == tableID) {
				return currTable.getStatus();
			}
		}
		return null;
	}
	
	/**
	 * Find the table index for a given table ID
	 * @param tableID
	 * @return this tables' table index
	 */
	private int getTableIndex(int tableID) {
		for (int i = 0; i < tables.size(); ++i) {
			Table currTable = tables.get(i);
			if (currTable.getTableID() == tableID) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Get a valid table size from the user
	 * @return a valid table size
	 */
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
	
	/**
	 * Get a valid table ID from the user
	 * @return a valid tableID
	 */
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
	
	/**
	 * Store all the tables currently in the restaurant into the text file in CSV format
	 * @param fileName
	 */
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
	
	/**
	 * Retrieve all the tables from the given file and stores to tables list
	 * @param fileName
	 */
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
	
	/**
	 * Get table status from string to TableStatus - RESERVED, OCCUPIED, VACANT
	 * @param currStatus
	 * @return enumeration TableStatus
	 */
	private TableStatus tableStatusFromString(String currStatus) {
		if (currStatus.equals("RESERVED")) {
			return TableStatus.RESERVED;
		} else if (currStatus.equals("OCCUPIED")) {
			return TableStatus.OCCUPIED;
		} else {
			return TableStatus.VACANT;
		}
	}
	
	/**
	 * Utility function to print out tables in a nice format.
	 * @param tables List of tables to be printed.
	 */
	private void printTablesFormatted(ArrayList<Table> tables) {
		String format = "| %-3d | %-8d | %-10s |%n";

		System.out.format("+-----+----------+------------+%n");
		System.out.format("| ID  | Size     | Status     |%n");
		System.out.format("+-----+----------+------------+%n");
		
		for(Table t : tables) {
			System.out.format(format, t.getTableID(), t.getSize(), t.getStatus().name());
		}
		System.out.format("+-----+----------+------------+%n");
	}
	
}
