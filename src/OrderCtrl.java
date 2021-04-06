import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class OrderCtrl {
	private static final File ORDER_FILE = new File("data", "order.txt");
	private static final int ORDER_LINES = 4; // number of lines for each order in the data file
	private static final String DELIMITER = ",";
	//private static final File STAFF_FILE = new File("data", "staff.txt");
	//private static final int STAFF_LINES = 4;
	private TableSystem tableSystem;
	
	public OrderCtrl(TableSystem tableSystem) {
		this.tableSystem = tableSystem;
	}
	
	private static String orderObjToStr(Order order) throws Exception {
		String str = "Order ID: " + order.getOrderID() + "\n"
				+ "Table ID: " + order.getTableID() + "\n"
				+ "Server: " + order.getStaffName() + "\n"
				+ "Order Items:";
		String[][] items = order.getAllItemsArr();
		for (int i=0; i<items.length; i++) {
			//				itemNum				 itemName			  itemPrice
			str += "\n  " + items[i][1] + "  " + items[i][0] + " ($" + items[i][2] + ")";
		}
		return str;
	}
	
	private static String orderObjToData(Order order) {
		String line;
		ArrayList<String> lines = new ArrayList<String>();
		
		// line 1 of data
		String orderID = String.valueOf(order.getOrderID());
		String tableID = String.valueOf(order.getTableID());
		String staffName = order.getStaffName();
		line = String.join(DELIMITER, orderID, tableID, staffName);
		lines.add(line);
		
		// lines 2, 3 & 4 of data
		String[][] items = order.getAllItemsSepArr();
		for(int i=0; i<=2; i++) {
			line = String.join(DELIMITER, items[i]);
			lines.add(line);
		}
		
		// data
		String data = String.join("\n", lines);
		return data;
	}

	private static int createOrderID() throws Exception {
		int orderID = -1;
		try {
			// read from file
			Scanner fr = new Scanner(ORDER_FILE);
			try {
				orderID = fr.nextInt();
				fr.nextLine();
				String data = String.valueOf(orderID + 1) + "\n";
				while(fr.hasNextLine()) data += fr.nextLine() + "\n";
				fr.close();
				// write to file
				FileWriter fw = new FileWriter(ORDER_FILE);
				fw.write(data);
				fw.close();
			}catch(NoSuchElementException e){
				fr.close();
				throw new Exception("OrderCtrl.createOrderID() error: data in order data file is formatted differently from expected\n");				
			}catch (IOException e) {
				throw new Exception("OrderCtrl.createOrderID() error: FileWriter cannot find order data file\n");
			}
		} catch (FileNotFoundException e) {
			throw new Exception("OrderCtrl.createOrderID() error: Scanner cannot find order data file \n");
		} 
		return orderID;
	}
	
	private static void editOrderData(int orderID, Order newOrder) throws Exception {
		String data = "", line;
		String[] splitLine;
		int currID;
		
		// retrieve data and edit it
		try {
			Scanner fr = new Scanner(ORDER_FILE);
			data += fr.nextLine(); // skip first line
			while (fr.hasNextLine()) {
				line = fr.nextLine();
				splitLine = line.split(DELIMITER);
				currID = Integer.parseInt(splitLine[0]);
				if (orderID == currID) {
					data += "\n" + orderObjToData(newOrder);
					for (int i=0; i<ORDER_LINES-1; i++) fr.nextLine(); // skip over target order data
					break;
				} 
				// copy all data before target data
				data += "\n" + line;
				for (int i=0; i<ORDER_LINES-1; i++) data += "\n" + fr.nextLine();
			}
			while (fr.hasNextLine()) data += "\n" + fr.nextLine(); // copy all data after target data
			fr.close();
		} catch (FileNotFoundException e) {
			throw new Exception("OrderCtrl.editOrderData() error: Scanner cannot find order data file \n");
		} catch (NoSuchElementException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
			throw new Exception("OrderCtrl.editOrderData() error: data in order data file is formatted differently from expected\n");
		}
		
		// write data back to file
		try {
			FileWriter fw = new FileWriter(ORDER_FILE);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			throw new Exception("OrderCtrl.editOrderData() error: FileWriter cannot find order data file \n");
		}
	}

	private static void appendOrderData(Order newOrder) throws Exception {
		String data = orderObjToData(newOrder);
		try {
			FileWriter fwa = new FileWriter(ORDER_FILE, true);
			fwa.write(data);
			fwa.close();
		} catch (IOException e) {
			throw new Exception("OrderCtrl.appendOrder() error: FileWriter could not find order data file");
		}
	}
	
	protected static void deleteOrderData(int orderID) throws Exception {
		String data = "", line;
		String[] splitLine;
		int currID;
		
		// retrieve data and edit it
		try {
			Scanner fr = new Scanner(ORDER_FILE);
			data += fr.nextLine(); // skip first line
			while (fr.hasNextLine()) {
				line = fr.nextLine();
				splitLine = line.split(DELIMITER);
				currID = Integer.parseInt(splitLine[0]);
				if (orderID == currID) {
					for (int i=0; i<ORDER_LINES-1; i++) fr.nextLine(); // skip over target order data
					break;
				} 
				// copy all data before target data
				data += "\n" + line;
				for (int i=0; i<ORDER_LINES-1; i++) data += "\n" + fr.nextLine();
			}
			while (fr.hasNextLine()) data += "\n" + fr.nextLine(); // copy all data after target data
			fr.close();
		} catch (FileNotFoundException e) {
			throw new Exception("OrderCtrl.deleteOrderData() error: Scanner cannot find order data file \n");
		} catch (NoSuchElementException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
			throw new Exception("OrderCtrl.deleteOrderData() error: data in order data file is formatted differently from expected\n");
		}
		
		// write data back to file
		try {
			FileWriter fw = new FileWriter(ORDER_FILE);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			throw new Exception("OrderCtrl.deleteOrderData() error: FileWriter cannot find order data file \n");
		}
	}
	
	protected static Order getOrderObject(int orderID) throws Exception {
		Order order = null;
		int currID, tableID;
		String staffName;
		String[] line, itemNames, itemNums, itemPrices;
		try {
			Scanner fr = new Scanner(ORDER_FILE);
			fr.nextLine(); // skips first line
			while (fr.hasNextLine()) {
				try {
					line = fr.nextLine().split(DELIMITER);
					currID = Integer.parseInt(line[0]);
					if (orderID == currID) {
						tableID = Integer.parseInt(line[1]);
						staffName = line[2];
						order = new Order(orderID, tableID, staffName);
						// add items to order
						itemNames = fr.nextLine().split(DELIMITER);
						itemNums = fr.nextLine().split(DELIMITER);
						itemPrices = fr.nextLine().split(DELIMITER);
						for(int i=0; i<itemNames.length; i++) order.addItem(itemNames[i], itemNums[i], itemPrices[i]);
						break;
					}
					for (int i=0; i<ORDER_LINES-1; i++) fr.nextLine(); // skip to next order ID
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
	
	// TODO decide if needed
	public static boolean validOrderID(int orderID) throws Exception {
		boolean valid = false;
		String[] line;
		int currID;
		try {
			Scanner fr = new Scanner(ORDER_FILE);
			fr.nextLine(); // skips first line
			while (fr.hasNextLine()) {
				try {
					line = fr.nextLine().split(DELIMITER);
					currID = Integer.parseInt(line[0]);
					if (orderID == currID) {
						valid = true;
						break;
					}
					for (int i=0; i<ORDER_LINES-1; i++) fr.nextLine(); // skip over all other data in each staff object
				}catch(NoSuchElementException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
					fr.close();
					throw new Exception("OrderCtrl.validOrderID() error: data in order data file is formatted differently from expected");
				}
			}
			fr.close();
		} catch (FileNotFoundException e) {
			throw new Exception("OrderCtrl.validOrderID() error: Scanner cannot find order data file");
		}
		return valid;
	}
	
	// TODO integrate with MenuCtrl
	public void createOrder(int tableID, String staffName, String[] itemNames, int[] itemNums) throws Exception {
		try {
			// create order object
			int orderID = createOrderID();
			float[] itemPrices = new float[itemNames.length];
			for (int i=0; i<itemNames.length; i++) itemPrices[i] = 4; //itemPrices[i] = MenuCtrl.getPrice(itemNames[i]);
			Order order = new Order(orderID, tableID, staffName, itemNames, itemNums, itemPrices);
			// write data to file
			appendOrderData(order);
			// change table status
			tableSystem.assignTable(tableID);
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
	
	// TODO integrate with StaffCtrl
	public void createOrder(int tableID, int employeeID, String[] itemNames, int[] itemNums) throws Exception {
		//String staffName = StaffCtrl.getStaffName(employeeID);
		try {
			createOrder(tableID, "Ben", itemNames, itemNums);
		} catch (Exception e) {
			throw e;
		}
	}
	
	// TODO integrate with MenuCtrl
	public void addOrderItem(int orderID, String newItemName, int newItemNum) throws Exception {
		try {
			Order order = getOrderObject(orderID);
			float newItemPrice = 10; //float newItemPrice = MenuCtr.getPrice(itemName); 
			order.addItem(newItemName, newItemNum, newItemPrice);
			editOrderData(orderID, order);
		} catch (Exception e) {
			throw new Exception("OrderCtr.addOrderItem() error:\n\t"
					+ e.getMessage());
		}
	}
	
	public boolean removeOrderItem(int orderID, String itemName) throws Exception {
		try {
			Order order = getOrderObject(orderID);
			if (!order.containsItem(itemName)) return false;
			order.removeItem(itemName);
			editOrderData(orderID, order);
		} catch (Exception e) {
			throw new Exception("OrderCtr.removeOrderItem() error:\n\t"
					+ e.getMessage());
		}
		return true;
	}

	
	/*public boolean validEmployeeID(int employeeID) throws Exception {
		boolean valid = false;
		try {
			Scanner fr = new Scanner(STAFF_FILE);
			while (fr.hasNextLine()) {
				try {
					if (employeeID == fr.nextInt()) {
						valid = true;
						break;
					}
					for (int i=0; i<STAFF_LINES; i++) fr.nextLine(); // skip over all other data in each staff object
				}catch(NoSuchElementException e) {
					fr.close();
					throw new Exception("OrderCtrl.validEmployeeID() error: data in staff data file is formatted differently from expected");
				}
			}
			fr.close();
		} catch (FileNotFoundException e) {
			throw new Exception("OrderCtrl.validEmployeeID() error: Scanner cannot find staff data file");
		}
		return valid;
	}
	
	private static Staff getStaffObject(int employeeID) throws Exception {
		Staff staff = null;
		try {
			Scanner fr = new Scanner(STAFF_FILE);
			for(int i=0; i<employeeID*STAFF_LINES; i++) fr.nextLine(); // skip data before target staff data
			fr.nextLine(); // skip the employee ID since it is already passed into the function
			try {
				String name = fr.nextLine(); 
				Gender gender = Gender.valueOf(fr.nextLine());
				JobTitle jobTitle = JobTitle.valueOf(fr.nextLine());
				staff = new Staff(employeeID, name, gender, jobTitle);
			} catch (IllegalArgumentException e) {
				fr.close();
				throw new Exception("OrderCtrl.getStaffObject() error: data in staff data file was not formatted as expected");
			}
			fr.close();
		} catch (FileNotFoundException e) {
			throw new Exception("OrderCtrl.getStaffObject() error: Scanner cannot find staff data file");
		} catch (NoSuchElementException e) {}
		return staff;
	}*/
}
