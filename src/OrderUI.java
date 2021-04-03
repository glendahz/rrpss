import java.util.NoSuchElementException;
import java.util.Scanner;

public class OrderUI {
	private static OrderCtrl ctrl;
	private static TableSystem tblSys;
	
	public static void main(String[] args) {
		TableSystem tables = new TableSystem();
		tables.addTable(4);
		tables.addTable(5);
		mainUI(tables);
	}
	
	public static void mainUI(Scanner sc, TableSystem tableSystem) {
		tblSys = tableSystem;
		ctrl = new OrderCtrl(tblSys);
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
	
	public static void mainUI(TableSystem tableSystem) {
		Scanner sc = new Scanner(System.in);
		mainUI(sc, tableSystem);
		sc.close();
	}
	
	private static void createOrderUI(Scanner sc) {
		// get table ID & employee ID
		int tableID = getTableIDUI(sc);
		int employeeID = getEmployeeIDUI(sc);
		
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
			} catch(NoSuchElementException e) {
				System.out.println("Error: entry was not valid\n"
						+ "Please enter an integer!\n");
				sc.nextLine(); // flush System.in
				continue;
			}
			itemNames = new String[noOfItems];
			itemNums = new int[noOfItems];
			run = false;
			for (int i=0; i<noOfItems; i++) {
				itemName = getOrderItemNameUI(sc);
				itemNames[i] = itemName;
				itemNums[i] = getOrderItemNumUI(sc, itemName);
			}
		}
		
		// write new order to data file
		try {
			ctrl.createOrder(tableID, employeeID, itemNames, itemNums);
			System.out.println("Order successfully created!");
		} catch (Exception e) {
			System.out.println("Error: order could not be created\n"
					+ e.getMessage() + "\n");
		}
		
	}
	
	private static void viewOrderUI(Scanner sc) {
		int orderID = getOrderIDUI(sc);;
		// print order
		try {
			System.out.println(ctrl.orderObjToStr(orderID) + "\n");
		} catch(Exception e) {
			System.out.println("Error: unable to print order\n" 
					+ e.getMessage() + "\n");
		}		
	}
	
	private static void addOrderItemUI(Scanner sc) {
		int orderID = getOrderIDUI(sc);
		String itemName = getOrderItemNameUI(sc);
		int itemNum = getOrderItemNumUI(sc, itemName);
		try {
			ctrl.addOrderItem(orderID, itemName, itemNum);
			System.out.println(itemName + " (x" + itemNum + ") successfully added to order " + orderID + "!\n");
		} catch (Exception e) {
			System.out.println("Error: unable to add order item\n"
					+ e.getMessage() + "\n");
		}
	}
	
	private static void removeOrderItemUI(Scanner sc) {
		boolean run = true;
		int orderID = getOrderIDUI(sc);
		String itemName;
		while(run) {
			try {
				itemName = getOrderItemNameUI(sc);
				if (ctrl.removeOrderItem(orderID, itemName)) {
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
	
	protected static int getOrderIDUI(Scanner sc) {
		boolean run = true;
		int orderID=-1;
		
		// get order ID
		while (run) {
			System.out.println("Enter order ID: ");
			// check if entry is an integer
			try {
				orderID = sc.nextInt();
				sc.nextLine(); // flush System.in
				System.out.println();
			} catch(NoSuchElementException e) {
				System.out.println("Error: entry was not a valid order ID\n"
						+ "Please enter an integer!\n");
				continue;
			}
			// check if entry is a valid order ID
			try {
				if (OrderCtrl.validOrderID(orderID)) run = false;
				else {
					System.out.println("Error: " + orderID + " is not a valid order ID\n"
							+ "Please enter a valid order ID!\n");
				}
			} catch (Exception e) {
				System.out.println("Error: unable to determine if order ID is valid\n" 
						+ e.getMessage() + "\n");
			}
		}
		
		return orderID;
	}

	// TODO: valid order item check (MenuCtrl)
	private static String getOrderItemNameUI(Scanner sc) {
		boolean run = true;
		String itemName = "";
		while (run) {
			System.out.println("Enter name of order item: ");
			itemName = sc.nextLine();
			System.out.println();
			run = false; // TODO delete when checks are implemented
			/*if (MenuCtrl.validOrderItemName(itemName)) run = false;
			else {
				System.out.println("Error: '" + itemName + "' is not a valid order item name\n");
			}*/
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
				System.out.println("Error: entry was not valid\n"
						+ "Please enter an integer!\n");
			}
		}
		return num;
	}

	// TODO valid table ID check (TableSystem)
	// TODO reserved table status check (TableSystem)
	private static int getTableIDUI(Scanner sc) {
		boolean run = true;
		int tableID = -1;
		while(run) {
			System.out.println("Enter table ID: ");
			// check if entry is an integer
			try {
				tableID = sc.nextInt();
				sc.nextLine(); // flush System.in
				System.out.println();
				run = false; // TODO delete when checks are implemented
			} catch(NoSuchElementException e) {
				System.out.println("Error: entry was not a valid table ID\n"
						+ "Please enter an integer!\n");
				sc.nextLine(); // flush System.in
				continue;
			}
			/* check if table is reserved
			if (TableSystem.getTableStatus(tableID) == TableStatus.RESERVED) run = false;
			else {
				System.out.println("Error: table " + tableID + " is not reserved\n"
						+ "Only reserved tables can make orders!\n");
			}*/
		}
		return tableID;
	}

	// TODO valid employee ID check (StaffCtrl)
	private static int getEmployeeIDUI(Scanner sc) {
		boolean run = true;
		int employeeID = -1;
		while(run) {
			System.out.println("Enter employee ID: ");
			try {
				employeeID = sc.nextInt();
				sc.nextLine(); // flush System.in
				System.out.println();
				run = false; // TODO delete when checks are implemented
			} catch(NoSuchElementException e) {
				System.out.println("Error: entry was not a valid employee ID\n"
						+ "Please enter an integer!\n");
				sc.nextLine(); // flush System.in
				continue;
			}
			/*try {
				if (OrderCtrl.validEmployeeID(employeeID)) run = false;
				else {
					System.out.println("Error: " + employeeID + " is not a valid employee ID\n"
							+ "Please enter a valid employee ID!\n");
				}
			} catch (Exception e) {
				System.out.println("Error: unable to determine if employee ID is valid\n" 
						+ e.getMessage() + "\n");
			}*/
		}
		return employeeID;
	}
}
