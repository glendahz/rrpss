package Order;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Table.Table.TableStatus;
import util.Controller;
import util.UI;

public class OrderUI extends UI {
	private static OrderCtrl orderCtrl;
	private static Scanner sc = new Scanner(System.in);

	public void setController(Controller ctrl) {
		orderCtrl = (OrderCtrl) ctrl;
	}
	
	public void displayOptions(Scanner sc) {
		boolean run=true;
		int choice;
		while(run) {
			System.out.println("-----Order-----\n"
					+ "Select option:\n" 
					+ "1. Create order\n" 
					+ "2. View order\n"
					+ "3. Add to order\n"
					+ "4. Remove from order\n"
					+ "5. Return to main menu");
			try {
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
				createOrderUI(sc);
				break;
			case 2:
				viewOrderUI(sc);
				break;
			case 3:
				addOrderItemUI(sc);
				break;
			case 4:
				removeOrderItemUI(sc);
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
	
	public void displayOptions() {
		displayOptions(sc);
	}
	
	private static void createOrderUI(Scanner sc) {
		// get table ID & employee ID
		int tableID = getTableIDUI(sc, TableStatus.RESERVED);
		String staffName = getEmployeeIDUI(sc);
		
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
				itemName = getOrderItemNameUI(sc);
				itemNames[i] = itemName;
				itemNums[i] = getOrderItemNumUI(sc, itemName);
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
	
	private static void viewOrderUI(Scanner sc) {
		int tableID = getTableIDUI(sc, TableStatus.OCCUPIED);;
		// print order
		try {
			System.out.println(orderCtrl.viewOrder(tableID) + "\n");
		} catch(Exception e) {
			System.out.println("Error: unable to print order\n" 
					+ e.getMessage() + "\n");
		}		
	}
	
	private static void addOrderItemUI(Scanner sc) {
		int tableID = getTableIDUI(sc, TableStatus.OCCUPIED);
		String itemName = getOrderItemNameUI(sc);
		int itemNum = getOrderItemNumUI(sc, itemName);
		try {
			orderCtrl.addOrderItem(tableID, itemName, itemNum);
			System.out.println(itemName + " (x" + itemNum + ") successfully added to order " + tableID + "!\n");
		} catch (Exception e) {
			System.out.println("Error: unable to add order item\n"
					+ e.getMessage() + "\n");
		}
	}
	
	private static void removeOrderItemUI(Scanner sc) {
		boolean run = true;
		int orderID = getTableIDUI(sc, TableStatus.OCCUPIED);
		String itemName;
		while(run) {
			try {
				itemName = getOrderItemNameUI(sc);
				if (orderCtrl.removeOrderItem(orderID, itemName)) {
					System.out.println(itemName + " successfully removed from order " + orderID + "!\n");
					run = false;
				}
				else {
					System.out.println("Error: " + itemName + " is not in order " + orderID + "\n" 
							+ "Please enter an order item that is in order " + orderID + "!\n");
				}
			} catch (Exception e) {
				System.out.println("Error: unable to remove order item\n"
						+ e.getMessage() + "\n");
			}
		}
	}
	
	protected static int getTableIDUI(Scanner sc, TableStatus status) {
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

	private static String getOrderItemNameUI(Scanner sc) {
		boolean run = true;
		int choice=0;
		String[] itemNames = orderCtrl.getAllOrderItemNames();
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
	
	private static int getOrderItemNumUI(Scanner sc, String itemName) {
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

	private static String getEmployeeIDUI(Scanner sc) {
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
