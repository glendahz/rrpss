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

public class OrderCtrl extends Controller {
	private static final File ORDER_FILE = new File("data", "order.txt");
	private static final String DELIMITER = ",";
	private static final String SUB_DELIMITER = "-";
	private static final String SET_PREFIX = "Set Package ";
	private static TableCtrl tableCtrl;
	private static StaffCtrl staffCtrl;
	
	public void setTableCtrl(TableCtrl ctrl) {
		tableCtrl = ctrl;
	}
	
	public void setStaffCtrl(StaffCtrl ctrl) {
		staffCtrl = ctrl;
	}

	private static String orderObjToStr(Order order) {
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("Table ID: " + order.getTableID());
		lines.add("Server: " + order.getStaffName());
		lines.add("Order Items:");
		String[][] items = order.getAllItemsArr();
		
		
		for (int i=0; i<items.length; i++) {
			//				 itemNum			  itemName				itemPrice
			lines.add("  " + items[i][0] + "  " + items[i][1] + " ($" + items[i][2] + ")");
		}
		return String.join("\n", lines);
	}
	
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
			throw new Exception("OrderCtrl.editOrderData() error: Scanner cannot find order data file \n");
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
			throw new Exception("OrderCtrl.editOrderData() error: FileWriter cannot find order data file \n");
		}
	}

	private static void appendOrderData(Order newOrder) throws Exception {
		String data = "\n" + orderObjToData(newOrder);
		try {
			FileWriter fwa = new FileWriter(ORDER_FILE, true);
			fwa.write(data);
			fwa.close();
		} catch (IOException e) {
			throw new Exception("OrderCtrl.appendOrder() error: FileWriter could not find order data file");
		}
	}
	
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
			throw new Exception("OrderCtrl.deleteOrderData() error: Scanner cannot find order data file \n");
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
			throw new Exception("OrderCtrl.deleteOrderData() error: FileWriter cannot find order data file \n");
		}
	}
	
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
				} catch (IllegalArgumentException e) {
					fr.close();
					throw new Exception("OrderCtrl.getOrderObject() error:\n"
							+ e.getMessage());
				}
			}
			fr.close();
		} catch (FileNotFoundException e) {
			throw new Exception("OrderCtrl.getOrderObject() error: FileWriter could not find order data file");
		}
		return order;
	}
	
	private static float getOrderItemPrice(String itemName) {
		float itemPrice;
		if (itemName.matches(SET_PREFIX + "\\d+")) {
			itemName = itemName.replace(SET_PREFIX, "");
			int itemIndex = Integer.parseInt(itemName);
			itemPrice = (float) MenuCtrl.getSetItemPrice(itemIndex);			
		} else itemPrice = (float) MenuCtrl.getItemPrice(itemName);
		return itemPrice;
	}
	
	public boolean validTableID(int tableID, TableStatus status){
		TableStatus currStatus = tableCtrl.getTableStatus(tableID);
		if (currStatus == status) return true;
		else return false;
	}
	
	public String validEmployeeID(int employeeID) {
		return staffCtrl.getStaffName(employeeID);
	}
	
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
	
	public String viewOrder(int tableID) throws Exception {
		try {
			Order order = getOrderObject(tableID);
			return orderObjToStr(order);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
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
