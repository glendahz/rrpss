import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class OrderCtrl {
	private static final File ORDER_FILE = new File("data", "order.txt");
	private static final String DELIMITER = ",";
	private static final String SUB_DELIMITER = "-";
	private static TableCtrl tableCtrl;
	private static StaffCtrl staffCtrl;
	private static MenuCtrl menuCtrl;
	
	public void setTableCtrl(TableCtrl ctrl) {
		tableCtrl = ctrl;
	}
	
	public void setStaffCtrl(StaffCtrl ctrl) {
		staffCtrl = ctrl;
	}
	
	public void setMenuCtrl(MenuCtrl ctrl) {
		menuCtrl = ctrl;
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
	
	protected static void deleteOrderData(int tableID) throws Exception {
		ArrayList<String> newLines = new ArrayList<String>();
		String line;
		String[] splitLine;
		int currID;
		
		// retrieve data and edit it
		try {
			Scanner fr = new Scanner(ORDER_FILE);
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
	
	protected static Order getOrderObject(int tableID) throws Exception {
		Order order = null;
		int currID;
		String staffName;
		String[] line, itemData;
		try {
			Scanner fr = new Scanner(ORDER_FILE);
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
	
	public boolean validTableID(int tableID, TableStatus status){
		TableStatus currStatus = tableCtrl.getTableStatus(tableID);
		if (status == null) {
			if (currStatus != null) return true;
			else return false;
		}
		else {
			if (currStatus == status) return true;
			else return false;
		}
	}
	
	public boolean validTableID(int tableID) {
		return validTableID(tableID, null);
	}
	
	public String validEmployeeID(int employeeID) {
		return staffCtrl.getStaffName(employeeID);
	}
	
	// TODO integrate with MenuCtrl
	public void createOrder(int tableID, String staffName, String[] itemNames, int[] itemNums) throws Exception {
		try {
			// create order object
			float[] itemPrices = new float[itemNames.length];
			for (int i=0; i<itemNames.length; i++) itemPrices[i] = 4;			
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
	
	public String viewOrder(int orderID) throws Exception {
		try {
			Order order = getOrderObject(orderID);
			return orderObjToStr(order);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	// TODO integrate with MenuCtrl
	public void addOrderItem(int tableID, String newItemName, int newItemNum) throws Exception {
		try {
			Order order = getOrderObject(tableID);
			float newItemPrice = 10; //float newItemPrice = MenuCtr.getPrice(itemName); 
			order.addItem(newItemName, newItemNum, newItemPrice);
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
