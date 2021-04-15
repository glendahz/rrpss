package Order;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Menu.MenuCtrl;
import Menu.MenuItem;
import Menu.SetPackage;
import Staff.StaffCtrl;
import Table.Table.TableStatus;
import Table.TableCtrl;
import util.Controller;

/**
 * A control class that mediates between {@code Order} entity objects and the {@code OrderUI} boundary object.
 * This control class reads from and writes to an order data file.
 * @author Glenda Hong Zixuan
 */
public class OrderCtrl extends Controller {
	
	/**
	 * The file which stores order data.
	 */
	private static final File ORDER_FILE = new File("data", "order.txt");
	
	/**
	 * The delimiter that separates {@code Order} fields in the order data file.
	 */
	private static final String DELIMITER = ",";
	
	/**
	 * The delimiter that separates order item data in the order data file.
	 */
	private static final String SUB_DELIMITER = "-";
	
	/**
	 * The string used to format set package names.
	 */
	private static final String SET_PREFIX = "Set Package ";
	
	/**
	 * The control class object used to manage {@code Table} objects.
	 */
	private static TableCtrl tableCtrl;
	
	/**
	 * The control class object used to manage {@code Staff} objects.
	 */
	private static StaffCtrl staffCtrl;
	
	/**
	 * Sets the control class object used to manage {@code Table} objects.
	 * @param ctrl	The control class object used to manage {@code Table} objects.
	 */
	public void setTableCtrl(TableCtrl ctrl) {
		tableCtrl = ctrl;
	}
	
	/**
	 * Sets the control class object used to manage {@code Staff} objects.
	 * @param ctrl	The control class object used to manage {@code Staff} objects.
	 */
	public void setStaffCtrl(StaffCtrl ctrl) {
		staffCtrl = ctrl;
	}
	
	/**
	 * Converts an {@code Order} object fields into a string for printing.
	 * @param order	The {@code Order} object to be converted to string.
	 * @return the converted {@code Order} object string.
	 */
	private static String orderObjToStr(Order order) {
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("Table ID: " + order.getTableID());
		lines.add("Server: " + order.getStaffName());
		lines.add("Order Items:");
		String[][] items = order.getAllItemsArr();
		
		String format = "| %-4s | %-20s | %8s |";

		lines.add("+------+----------------------+----------+");
		lines.add("| No.  | Name                 | Price($) |");
		lines.add("+------+----------------------+----------+");
		
		for (int i=0; i<items.length; i++) {
			/*
			 * item[i][0] is the item number
			 * item[i][1] is the item name
			 * item[i][2] is the item price
			 */
//			lines.add("  " + items[i][0] + "  " + items[i][1] + " ($" + items[i][2] + ")");
			lines.add(String.format(format, items[i][0], items[i][1], items[i][2]));
		}
		lines.add("+------+----------------------+----------+");

		return String.join("\n", lines);
	}
	
	/**
	 * Converts an {@code Order} object to data to be written into the order data file. The data is formatted like this:
	 * <br>&emsp;tableID,staffName,itemNum1-itemName1-itemPrice1,itemNum2-itemName2-itemPrice2,...
	 * @param order	The {@code Order} object to be converted to data.
	 * @return the converted {@code Order} object data.
	 */
	private static String orderObjToData(Order order) {
		ArrayList<String> fields = new ArrayList<String>();
		
		// add tableID & staffName
		fields.add(String.valueOf(order.getTableID()));
		fields.add(order.getStaffName());

		
		// add items
		String itemData;
		String[][] items = order.getAllItemsArr();
		for(int i=0; i<items.length; i++) {
			//			 					  	  itemNum	   itemName		itemPrice
			itemData = String.join(SUB_DELIMITER, items[i][0], items[i][1], items[i][2]);
			fields.add(itemData);
		}
		
		return String.join(DELIMITER, fields);
	}
	
	/**
	 * Edits the the data of a specific order in the order data file.
	 * @param tableID	The table ID of the order to be edited.
	 * @param newOrder	The new {@code Order} object to be written into the order data file.
	 * @throws Exception if the {@code Scanner} object or {@code FileWriter} object cannot find the order data file,
	 * or if the data in the order data file is formatted differently from expected. 
	 */
	private static void editOrderData(int tableID, Order newOrder) throws Exception {
		ArrayList<String> newLines = new ArrayList<String>();
		String line;
		String[] splitLine;
		int currID;
		
		// retrieve data and edit it
		try {
			Scanner fr = new Scanner(ORDER_FILE);
			newLines.add(fr.nextLine()); // skip first line
			while (fr.hasNextLine()) {
				line = fr.nextLine();
				splitLine = line.split(DELIMITER);
				currID = Integer.parseInt(splitLine[0]);
				if (tableID == currID) newLines.add(orderObjToData(newOrder));
				else newLines.add(line); // copy all data that is not target data
			}
			fr.close();
		} catch (FileNotFoundException e) {
			throw new Exception("OrderCtrl.editOrderData() error: Scanner object cannot find order data file \n");
		} catch (NoSuchElementException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
			throw new Exception("OrderCtrl.editOrderData() error: data in order data file is formatted differently from expected\n");
		}
		
		// write data back to file
		String data = String.join("\n", newLines);
		try {
			FileWriter fw = new FileWriter(ORDER_FILE);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			throw new Exception("OrderCtrl.editOrderData() error: FileWriter object cannot find order data file \n");
		}
	}

	/**
	 * Appends data of a specific {@code Order} object to the end of the order data file.
	 * @param newOrder	The new {@code Order} object to be appended.
	 * @throws Exception if the {@code FileWriter} object cannot find the order data file.
	 */
	private static void appendOrderData(Order newOrder) throws Exception {
		String data = "\n" + orderObjToData(newOrder);
		try {
			FileWriter fwa = new FileWriter(ORDER_FILE, true);
			fwa.write(data);
			fwa.close();
		} catch (IOException e) {
			throw new Exception("OrderCtrl.appendOrder() error: FileWriter object could not find order data file");
		}
	}
	
	/**
	 * Deletes the data of a specific order in the order data file.
	 * @param tableID	The table ID of the order to be deleted.
	 * @throws Exception if the {@code Scanner} object or {@code FileWriter} object cannot find the order data file,
	 * or if the data in the order data file is formatted differently from expected.
	 */
	static void deleteOrderData(int tableID) throws Exception {
		ArrayList<String> newLines = new ArrayList<String>();
		String line;
		String[] splitLine;
		int currID;
		
		// retrieve data and edit it
		try {
			Scanner fr = new Scanner(ORDER_FILE);
			newLines.add(fr.nextLine()); // skip first line
			while (fr.hasNextLine()) {
				line = fr.nextLine();
				splitLine = line.split(DELIMITER);
				currID = Integer.parseInt(splitLine[0]);
				if (tableID == currID) continue;
				else newLines.add(line); // copy all data that is not target data
			}
			fr.close();
		} catch (FileNotFoundException e) {
			throw new Exception("OrderCtrl.deleteOrderData() error: Scanner object cannot find order data file \n");
		} catch (NoSuchElementException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
			throw new Exception("OrderCtrl.deleteOrderData() error: data in order data file is formatted differently from expected\n");
		}
		
		// write data back to file
		String data = String.join("\n", newLines);
		try {
			FileWriter fw = new FileWriter(ORDER_FILE);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			throw new Exception("OrderCtrl.deleteOrderData() error: FileWriter object cannot find order data file \n");
		}
	}
	
	/**
	 * Gets a specific {@code Order} object from the order data file.
	 * @param tableID	The table ID of the target {@code Order} object.
	 * @return the target {@code Order} object
	 * @throws Exception if the {@code Scanner} object cannot find the order data file,
	 * or if the data in the order data file is formatted differently from expected.
	 */
	static Order getOrderObject(int tableID) throws Exception {
		Order order = null;
		int currID;
		String staffName;
		String[] line, itemData;
		try {
			Scanner fr = new Scanner(ORDER_FILE);
			fr.nextLine(); // skip first line
			while (fr.hasNextLine()) {
				try {
					line = fr.nextLine().split(DELIMITER);
					currID = Integer.parseInt(line[0]);
					if (tableID == currID) {
						staffName = line[1];
						order = new Order(tableID, staffName);
						// add items to order
						for(int i=2; i<line.length; i++) {
							itemData = line[i].split(SUB_DELIMITER);
							order.addItem(itemData[0], itemData[1], itemData[2]);
						}
						break;
					}
				} catch (NoSuchElementException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
					fr.close();
					throw new Exception("OrderCtrl.getOrderObject() error: data in order data file is formatted differently from expected");
				}
			}
			fr.close();
		} catch (FileNotFoundException e) {
			throw new Exception("OrderCtrl.getOrderObject() error: Scanner object could not find order data file");
		}
		return order;
	}
	
	/**
	 * Gets the price of a specific order item.
	 * @param itemName	The name of the target order item.
	 * @return the price of the target order item.
	 */
	private static float getOrderItemPrice(String itemName) {
		float itemPrice;
		if (itemName.matches(SET_PREFIX + "\\d+")) {
			itemName = itemName.replace(SET_PREFIX, "");
			int itemIndex = Integer.parseInt(itemName);
			itemPrice = (float) MenuCtrl.getSetItemPrice(itemIndex);			
		} else itemPrice = (float) MenuCtrl.getItemPrice(itemName);
		return itemPrice;
	}
	
	/**
	 * Checks whether a table ID is valid 
	 * and whether the corresponding {@code Table} object is set to a specific {@code TableStatus}
	 * @param tableID	The table ID to be checked.
	 * @param status	The {@code TableStatus} value that the {@code Table} object should be set to.
	 * @return {@code true} if the table ID is valid and the corresponding {@code Table} object is set to {@code status},
	 * otherwise {@code false}.
	 */
	public boolean validTableID(int tableID, TableStatus status){
		TableStatus currStatus = tableCtrl.getTableStatus(tableID);
		
		// just check if table ID is valid if status is null
		if (status == null) {
			if (currStatus != null) return true;
			else return false;
		} else {
			if (currStatus == status) return true;
			else return false;
		}
		
	}
	
	/**
	 * Checks whether an employee ID is valid.
	 * @param employeeID	The employee ID to be checked
	 * @return {@code true} if the employee ID is valid,
	 * otherwise false.
	 */
	public String validEmployeeID(int employeeID) {
		return staffCtrl.getStaffName(employeeID);
	}
	
	/**
	 * Gets the names of all order items.
	 * @return the names of all order items.
	 */
	public String[] getAllOrderItemNames() {
		ArrayList<MenuItem> menuItems = MenuCtrl.getItemObject();
		ArrayList<SetPackage> setItems = MenuCtrl.getSetItemObject();
		int noOfMenuItems = menuItems.size();
		int noOfSetItems = setItems.size();
		String[] itemNames = new String[noOfMenuItems + noOfSetItems];
		int i=0;
		for (int j=0; j<noOfMenuItems; j++, i++)  {
			itemNames[i] = menuItems.get(j).getName();
		}
		for (int j=0; j<noOfSetItems; j++, i++)  {
			itemNames[i] = SET_PREFIX + setItems.get(j).getIndex();
		}
				
		return itemNames;
	}
	
	/**
	 * Gets the names of order items contained in a specific {@code Order} object.
	 * @param tableID	The table ID of the target {@code Order} object.
	 * @return the names of order items contained in the target {@code Order} object.
	 * @throws Exception if the method ({@code getOrderObject}) called throws an exception.
	 */
	public String[] getOrderItemNames(int tableID) throws Exception {
		String[] itemNames = null;
		try {
			Order order = getOrderObject(tableID);
			itemNames = order.getAllItemNames();
		} catch (Exception e) {
			throw new Exception("OrderCtrl.getOrderItemNames() error:\n"
					+ "\t" + e.getMessage());
		}
		return itemNames;
	}
	
	/**
	 * Creates a new {@code Order} object and writes its data into the order data file.
	 * Once a new order is created, the corresponding {@code Table} object is set to {@code TableStatus.OCCUPIED}. 
	 * @param tableID	The table ID of the new {@code Order} object.
	 * @param staffName	The staff name of the new {@code Order} object.
	 * @param itemNames	The names of the order items to be contained in the new {@code Order} object.
	 * @param itemNums	The numbers of the order items to be contained in the new {@code Order} object.
	 * @throws Exception if either of the methods ({@code getOrderItemPrice} and {@code appendOrderData}) 
	 * called throws an exception.
	 */
	public void createOrder(int tableID, String staffName, String[] itemNames, int[] itemNums) throws Exception {
		try {
			// create order object
			float[] itemPrices = new float[itemNames.length];
			for (int i=0; i<itemNames.length; i++) itemPrices[i] = getOrderItemPrice(itemNames[i]); 
			Order order = new Order(tableID, staffName, itemNames, itemNums, itemPrices);
			
			// write data to file
			appendOrderData(order);
			
			// change table status
			tableCtrl.assignTable(tableID);
		} catch (Exception e) {
			throw new Exception("OrderCtrl.createOrder() error:\n"
					+ "\t" + e.getMessage());
		} 
	}
	
	/**
	 * Returns a specific {@code Order} object as a string to be printed.
	 * @param tableID	The table ID of the target {@code Order} object.
	 * @return the target {@code Order} object string.
	 * @throws Exception if the method ({@code getOrderObject}) called throws an exception.
	 */
	public String viewOrder(int tableID) throws Exception {
		try {
			Order order = getOrderObject(tableID);
			return orderObjToStr(order);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	/**
	 * Add an order item to a specific {@code Order} object and writes the data of the new object into the order data file.
	 * @param tableID		The table ID of the target {@code Order} object.
	 * @param newItemName	The name of the order item to be added.
	 * @param newItemNum	The number of the order item to be added.
	 * @throws Exception if either of the methods ({@code getOrderObject} and {@code editOrderData}) called throws an exception.
	 */
	public void addOrderItem(int tableID, String newItemName, int newItemNum) throws Exception {
		try {
			Order order = getOrderObject(tableID);
			if (order.containsItem(newItemName)) { // order item already exists in order, add to existing order item number
				newItemNum += order.getItemNum(newItemName);
				order.updateItemNum(newItemName, newItemNum);
			} else { // order item does not exist in order, add new order item
				float newItemPrice = getOrderItemPrice(newItemName);
				order.addItem(newItemNum, newItemName, newItemPrice);
			}
			editOrderData(tableID, order);
		} catch (Exception e) {
			throw new Exception("OrderCtr.addOrderItem() error:\n\t"
					+ e.getMessage());
		}
	}
	
	/**
	 * Removes a specific order item from a specific {@code Order} object
	 * and writes the data of the new object into the order data file.
	 * @param tableID	The table ID of the target {@code Order} object.
	 * @param itemName	The name of the target order item.
	 * @return {@code true} if the target order item is removed from the target {@code Order} object
	 * and the data of the new object is written into the order data file,
	 * otherwise {@code false}.
	 * @throws Exception if either of the methods ({@code getOrderObject} and {@code editOrderData}) called throws an exception. 
	 */
	public boolean removeOrderItem(int tableID, String itemName) throws Exception {
		try {
			Order order = getOrderObject(tableID);
			if (!order.containsItem(itemName)) return false;
			order.removeItem(itemName);
			editOrderData(tableID, order);
		} catch (Exception e) {
			throw new Exception("OrderCtr.removeOrderItem() error:\n\t"
					+ e.getMessage());
		}
		return true;
	}

}
