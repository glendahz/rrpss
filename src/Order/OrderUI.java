package Order;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Table.Table.TableStatus;
import util.Controller;
import util.UI;

/**
 * A boundary class that interfaces with the application user for order related functions.
 * @author Glenda Hong Zixuan
 */
public class OrderUI extends UI {
	
	/**
	 * The control object that mediates between this boundary class and {@code Order} entity objects.
	 */
	private static OrderCtrl orderCtrl;
	
	/**
	 * The {@code Scanner} object to take in input from the application user.
	 */
	private static Scanner sc = new Scanner(System.in);

	/**
	 * Sets the control object that mediates between this boundary class and {@code Order} entity objects.
	 */
	@Override
	public void setController(Controller ctrl) {
		orderCtrl = (OrderCtrl) ctrl;
	}
	
	/**
	 * Displays the order related functions that can be performed 
	 * and gets a choice from the user about which function to perform. 
	 */
	@Override
	public void displayOptions() {
		boolean run=true;
		int choice;
		while(run) {
			System.out.println("\n======== Order ========\n"
					+ "1) Create order\n" 
					+ "2) View order\n"
					+ "3) Add to order\n"
					+ "4) Remove from order\n"
					+ "5) Return to main menu");
			try {
				System.out.print("\nSelect Option: ");
				choice = sc.nextInt();
				sc.nextLine(); // flush System.in
			}catch(NoSuchElementException e) {
				sc.nextLine(); // flush System.in
				System.out.println("Error: entry was not a valid choice\n"
						+ "Please enter an integer!\n");
				continue;
			}
			System.out.println();
			
			switch(choice) {
			case 1:
				createOrderUI();
				break;
			case 2:
				viewOrderUI();
				break;
			case 3:
				addOrderItemUI();
				break;
			case 4:
				removeOrderItemUI();
				break;
			case 5:
				run=false;
				System.out.println("Returning to main menu...\n");
				break;
			default:
				System.out.println("Error: '" + choice + "' is not a valid choice\n"
						+ "Please enter an integer between 1 to 5\n");
				break;
			}
		}
	}
	
	/**
	 * Gets information needed to create a new order from the user .
	 * Such information includes the table ID of the order,
	 * the employee ID of the staff taking the order,
	 * and the order items to be contained in the order.
	 * The table ID must correspond to a {@code Table} object that is set to {@code TableStatus.RESERVED}.
	 */
	private static void createOrderUI() {
		// get table ID & employee ID
		int tableID = getTableIDUI(TableStatus.RESERVED);
		String staffName = getEmployeeIDUI();
		
		// get order items
		boolean run = true;
		String itemName;
		int noOfItems;
		String[] itemNames = null;
		int[] itemNums = null;
		run = true;
		while(run) {
			System.out.println("Enter number of order items: ");
			try {
				noOfItems = sc.nextInt();
				sc.nextLine(); // flush System.in
				System.out.println();
				if (noOfItems > 0) run = false;
				else {
					System.out.println("Error: '" + noOfItems + "' is not valid\n"
						+ "Please enter a positive integer!\n");
					continue;
				}
			} catch(NoSuchElementException e) {
				System.out.println("\nError: entry was not valid\n"
						+ "Please enter an integer!\n");
				sc.nextLine(); // flush System.in
				continue;
			}
			itemNames = new String[noOfItems];
			itemNums = new int[noOfItems];
			for (int i=0; i<noOfItems; i++) {
				itemName = getOrderItemNameUI();
				itemNames[i] = itemName;
				itemNums[i] = getOrderItemNumUI(itemName);
			}
		}
		
		// write new order to data file
		try {
			orderCtrl.createOrder(tableID, staffName, itemNames, itemNums);
			System.out.println("Order successfully created!\n");
		} catch (Exception e) {
			System.out.println("Error: order could not be created\n"
					+ e.getMessage() + "\n");
		}
		
	}
	
	/**
	 * Gets a table ID from the user and displays the corresponding order information.
	 * The table ID must correspond to a {@code Table} object that is set to {@code TableStatus.OCCUPIED}.
	 */
	private static void viewOrderUI() {
		int tableID = getTableIDUI(TableStatus.OCCUPIED);;
		// print order
		try {
			System.out.println(orderCtrl.viewOrder(tableID) + "\n");
		} catch(Exception e) {
			System.out.println("Error: unable to print order\n" 
					+ e.getMessage() + "\n");
		}		
	}
	
	/**
	 * Gets information needed to add an order item to an existing order from the user.
	 * Such information includes the table ID of the target order and the name and number of the order item.
	 * The table ID must correspond to a {@code Table} object that is set to {@code TableStatus.OCCUPIED}. 
	 */
	private static void addOrderItemUI() {
		int tableID = getTableIDUI(TableStatus.OCCUPIED);
		String itemName = getOrderItemNameUI();
		int itemNum = getOrderItemNumUI(itemName);
		try {
			orderCtrl.addOrderItem(tableID, itemName, itemNum);
			System.out.println(itemName + " (x" + itemNum + ") successfully added to table " + tableID + " order!\n");
		} catch (Exception e) {
			System.out.println("Error: unable to add order item\n"
					+ e.getMessage() + "\n");
		}
	}
	
	/**
	 * Gets information needed to remove an existing order item from an existing order from the user.
	 * Such information includes the table ID of the target order and the name of the target order item.
	 * The table ID must correspond to a {@code Table} object that is set to {@code TableStatus.OCCUPIED}.
	 */
	private static void removeOrderItemUI() {
		boolean run = true;
		int tableID = getTableIDUI(TableStatus.OCCUPIED);
		String itemName;
		while(run) {
			try {
				itemName = getOrderItemNameUI(tableID);
				if (orderCtrl.removeOrderItem(tableID, itemName)) {
					System.out.println(itemName + " successfully removed from table " + tableID + " order!\n");
					run = false;
				}
				else {
					System.out.println("Error: " + itemName + " is not in table " + tableID + " order\n" 
							+ "Please enter an order item that is in table " + tableID + " order!\n");
				}
			} catch (Exception e) {
				System.out.println("Error: unable to remove order item\n"
						+ e.getMessage() + "\n");
			}
		}
	}
	
	/**
	 * Gets the table ID of a specific {@code Table} object that is set to a specific {@code TableStatus}.
	 * @param status	The {@code TableStatus} value that the target {@code Table} object should be set to.
	 * @return the table ID retrieved from the user.
	 */
	private static int getTableIDUI(TableStatus status) {
		boolean run = true;
		int tableID = -1;
		while(run) {
			System.out.println("Enter table ID: ");
			// check if entry is an integer
			try {
				tableID = sc.nextInt();
				sc.nextLine(); // flush System.in
				System.out.println();
			} catch(NoSuchElementException e) {
				System.out.println("\nError: entry was not a valid table ID\n"
						+ "Please enter an integer!\n");
				sc.nextLine(); // flush System.in
				continue;
			}
			if (orderCtrl.validTableID(tableID, status)) run = false;
			else System.out.println("Error: entry was not a valid table ID\n");
		}
		return tableID;
	}

	/**
	 * Gets the name of an order item.
	 * @param tableID	If {@code tableID} is set to {@code -1} 
	 * 					the {@code getOrderItemNameUI} method will get the name of any order item.
	 * 					<br>Otherwise, the {@code getOrderItemNameUI} method will get the name of an existing order item
	 * 					contained in the {@code Order} object corresponding to {@code tableID}.
	 * @return the name of the order item retrieved from the user.
	 */
	private static String getOrderItemNameUI(int tableID) {
		boolean run = true;
		int choice=0;
		String[] itemNames=null;
		if (tableID == -1) itemNames = orderCtrl.getAllOrderItemNames();
		else {
			try {
				itemNames = orderCtrl.getOrderItemNames(tableID);
			} catch (Exception e) {
				System.out.println("\nError: unable to get order item names"
						+ "\t" + e.getMessage());
			}
		}
		
		String itemName = "";
		while (run) {
			try {
				System.out.println("Select order item: ");
				for (int i=0; i<itemNames.length; i++) System.out.println((i+1) + ". " + itemNames[i]);
				choice = sc.nextInt();
				sc.nextLine(); // flush System.in
				System.out.println();
			} catch(NoSuchElementException e) {
				sc.nextLine();
				System.out.println("\nError: entry was not a valid choice\n"
						+ "Please enter an integer!\n");
				continue;
			}
			
			if (0 < choice & choice <= itemNames.length) {
				itemName = itemNames[choice-1];
				run = false;
			} else {
				System.out.println("Error: '" + choice + "' was not a valid choice\n"
						+ "Please enter an integer between 1 and " + itemNames.length + "!\n");
			}
		}
		return itemName;
	}
	
	/**
	 * Gets the name of an order item.
	 * @return the name of the order item retrieved from the user.
	 */
	private static String getOrderItemNameUI() {
		return getOrderItemNameUI(-1);
	}
	
	/**
	 * Gets the number of an order item.
	 * @param itemName	The name of the order item.
	 * @return the number of the order item retrieved from the user.
	 */
	private static int getOrderItemNumUI(String itemName) {
		boolean run = true;
		int num = 0;
		while(run) {
			System.out.println("Enter number of " + itemName + ":");
			try {
				num = sc.nextInt();
				sc.nextLine(); // flush System.in
				System.out.println();
				if (num > 0) run = false;
				else {
					System.out.println("Error: '" + num + "' is not valid\n" 
							+ "Please enter a positive integer!\n");
				}
			} catch (NoSuchElementException e) {
				System.out.println("\nError: entry was not valid\n"
						+ "Please enter an integer!\n");
				sc.nextLine();
			}
		}
		return num;
	}

	/**
	 * Gets the employee ID of a staff member.
	 * @return the employee ID of the staff member retrieved from the user.
	 */
	private static String getEmployeeIDUI() {
		boolean run = true;
		int employeeID;
		String staffName="";
		while(run) {
			System.out.println("Enter employee ID: ");
			try {
				employeeID = sc.nextInt();
				sc.nextLine(); // flush System.in
				System.out.println();
			} catch(NoSuchElementException e) {
				System.out.println("\nError: entry was not a valid employee ID\n"
						+ "Please enter an integer!\n");
				sc.nextLine(); // flush System.in
				continue;
			}
			staffName = orderCtrl.validEmployeeID(employeeID);
			if (staffName == null) {
				System.out.println("Error: entry was not a valid employee ID\n");
			}
			else {
				run = false;
			}
		}
		return staffName;
	}
}
