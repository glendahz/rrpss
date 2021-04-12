package Order;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Table.TableCtrl;
import Table.Table.TableStatus;
import util.Controller;

/**
 * A control class that mediates between {@code OrderInvoice} entity objects and the {@code InvoiceUI} boundary object.
 * This control class reads from and writes to a sales data file.
 * @author Glenda Hong Zixuan
 */
public class InvoiceCtrl extends Controller {
	
	/**
	 * The file which stores sales data.
	 */
	private static final File SALES_FILE = new File("data", "sales.txt");
	
	/**
	 * The GST tax as a decimal.
	 */
	private final static float GST = 0.07f;
	
	/**
	 * The service tax as a decimal.
	 */
	private final static float SERVICE_CHARGE = 0.1f;
	
	/**
	 * The name of the restaurant.
	 */
	private final static String RESTAURANT_NAME = "OODP Restaurant";
	
	/**
	 * The address of the restaurant.
	 */
	private final static String[] RESTAURANT_ADD = {"50 Nanyang Ave", "32 Block N4-B1B-11", "Singapore 639798"};
	
	/**
	 * The telephone number of the restaurant.
	 */
	private final static String RESTAURANT_TEL_NO = "6543 2109";
	
	/**
	 * The formatter used to format the bill invoice time stamp.
	 */
	private final static DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("EEE dd/MM/yyyy hh:mm a");
	
	/**
	 * The formatter used to format prices.
	 */
	private final static String PRICE_FORMAT = "$%.2f";
	
	/**
	 * The width of the bill invoice.
	 */
	private final static int WIDTH = 50;
	
	/**
	 * The length of the divider used to separate sections of the bill invoice.
	 */
	private final static int DIVIDER_LEN = (int) Math.round(0.8 * WIDTH);
	
	/**
	 * The length of the vertical margins surrounding the text of the bill invoice.
	 */
	private final static int SPACE_LEN = 2;
	
	/**
	 * The maximum width of text in the bill invoice.
	 */
	private final static int TEXT_BLOCK_LEN = DIVIDER_LEN - (2 * SPACE_LEN);
	
	/**
	 * The maximum length of each order item number in the bill invoice.
	 */
	private final static int ITEM_NUM_LEN = 2;
	
	/**
	 * The maximum length of each order item price in the bill invoice.
	 */
	private final static int ITEM_PRICE_LEN = 8;
	
	/**
	 * The maximum length of each order item name in the bill invoice.
	 */
	private final static int ITEM_NAME_LEN = TEXT_BLOCK_LEN - ITEM_NUM_LEN - ITEM_PRICE_LEN - (2 * SPACE_LEN);
	
	/**
	 * The control class object used to manage {@code Table} objects.
	 */
	private static TableCtrl tableCtrl;
	
	/**
	 * Sets the control class object used to manage {@code Table} objects.
	 * @param ctrl	The control class object used to manage {@code Table} objects.
	 */
	public void setTableCtrl(TableCtrl ctrl) {
		tableCtrl = ctrl;
	}
	
	/**
	 * Converts an {@code OrderInvoice} object to data to be written into the sales data file. The data is formatted like this:
	 * <br>&emsp;timestamp,totalPrice,itemNum1-itemName1,itemNum2-itemName2,...
	 * @param invoice	The {@code OrderInvoice} object to be converted to data.
	 * @return the converted {@code OrderInvoice} object data.
	 */
	private static String invoiceObjToData(OrderInvoice invoice) {
		// get timestamp & total price
		String timestamp = invoice.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		String totalPrice = Float.toString(invoice.getTotalPrice());
		
		// get items
		Order order = invoice.getOrder();
		String[] items = order.getAllItemNames();
		String name, num;
		for (int i=0; i<items.length; i++) {
			name = items[i];
			num = Integer.toString(order.getItemNum(name));
			items[i] = num + "-" + name;
		}
		String itemData = String.join(",", items);
		
		String data = String.join(",", timestamp, totalPrice);
		data = String.join(",", data, itemData);
		return data;
	}
	
	/**
	 * Appends data of a specific {@code OrderInvoice} object to the end of the sales data file.
	 * @param invoice	The new {@code OrderInvoice} object to be appended.
	 * @throws Exception if the {@code FileWriter} object cannot find the order data file.
	 */
	private static void writeInvoiceData(OrderInvoice invoice) throws Exception {
		String data = "\n" + invoiceObjToData(invoice);
		try {
			FileWriter fwa = new FileWriter(SALES_FILE, true);
			fwa.write(data);
			fwa.close();
		} catch (IOException e) {
			throw new Exception("InvoiceCtrl.writeInvoiceData error: FileWriter object could not find sales report data file");
		}
	}
	
	/**
	 * Wraps a string that exceeds a specific length.
	 * @param str		The string to be wrapped.
	 * @param length	The maximum length of each line.
	 * @return the wrapped string.
	 */
	private static String wrapStr(String str, int length) {
		if (str.length() < length) return str;
		
		int start=0, end=length, breakIndex;
		String subStr, newStr="";
		while (end < str.length()) {
			subStr = str.substring(start, end);
			breakIndex = subStr.lastIndexOf(" ");
			if(breakIndex != -1) end = breakIndex;  
			if (!newStr.equals("")) newStr += "\n";
			newStr += str.substring(start, end);
			start = end;
			end += length;
		}
		try {
			newStr += "\n" + str.substring(start);
		} catch (StringIndexOutOfBoundsException e) {}
		return newStr;
	}
		
	/**
	 * Left aligns a string.
	 * @param str	The string to be left aligned.
	 * @return the left aligned string.
	 */
	private static String getLeftAlignedStr(String str) {
		String newStr = String.format("%-"+TEXT_BLOCK_LEN+"s", str);
		return getCenteredStr(newStr);
	}
	
	/**
	 * Right aligns a string
	 * @param str	The string to be right aligned.
	 * @return the right aligned string.
	 */
	private static String getRightAlignedStr(String str) {
		String newStr = String.format("%"+TEXT_BLOCK_LEN+"s", str);
		return getCenteredStr(newStr);
	}
	
	/**
	 * Centres a string.
	 * @param str	The string to be centred.
	 * @return the centred string.
	 */
	private static String getCenteredStr(String str) {
		int leftLen = (WIDTH - str.length()) / 2;
		int rightLen = WIDTH - str.length() - leftLen;
		String newStr = String.format("%"+leftLen+"s%s%"+rightLen+"s", "", str, "");
		newStr = "|" + newStr + "|";
		return newStr;
	} 
	
	/**
	 * Formats an order item's number, name and price for the bill invoice.
	 * @param itemNum		The number of the order item to be formatted.
	 * @param itemName		The name of the order item to be formatted.
	 * @param itemPrice		The price of the order item to be formatted.
	 * @return the order item's number, name and price formatted for the bill invoice.
	 */
	private static String getOrderItemStr(String itemNum, String itemName, String itemPrice) {
		// wrap item name string if needed
		String[] nameLines = wrapStr(itemName, ITEM_NAME_LEN).split("\n");
		
		// add first line
		String numStr = String.format("%"+ITEM_NUM_LEN+"s", itemNum);
		String nameStr = String.format("%-"+ITEM_NAME_LEN+"s", nameLines[0]); 
		String priceStr = String.format(PRICE_FORMAT, Float.parseFloat(itemPrice));
		priceStr = String.format("%"+ITEM_PRICE_LEN+"s", priceStr);
		String spaceStr = " ".repeat(SPACE_LEN);
		String str = getCenteredStr(numStr + spaceStr + nameStr + spaceStr + priceStr);
		
		// add additional lines if needed
		if (nameLines.length > 1) {
			numStr = String.format("%"+ITEM_NUM_LEN+"s", "");
			priceStr = String.format("%"+ITEM_PRICE_LEN+"s", "");
			for(int i=1; i<nameLines.length; i++) {
				nameStr = String.format("%-"+ITEM_NAME_LEN+"s", nameLines[i]); 
				str += getCenteredStr(numStr + spaceStr + nameStr + spaceStr + priceStr);
			} 
		}
		
		return str;
	}
	
	/**
	 * Formats an {@code OrderInvoice} object into a bill invoice to be printed.
	 * @param invoice	The {@code OrderInvoice} object to be formatted.
	 * @return the formatted {@code OrderInvoice} object.
	 */
	private static String getInvoiceStr(OrderInvoice invoice) {
		Order order = invoice.getOrder();
		String horiBorder = "-".repeat(WIDTH+2);
		String blankLine = "|" + " ".repeat(WIDTH) + "|";
		String divider = getCenteredStr("-".repeat(DIVIDER_LEN));
		ArrayList<String> lines = new ArrayList<String>();
		
		// add top of receipt & restaurant name
		lines.add(horiBorder);
		lines.add(blankLine);
		lines.add(getCenteredStr(RESTAURANT_NAME));
		lines.add(divider);
		
		// add restaurant address & telephone number
		for(int i=0; i<RESTAURANT_ADD.length; i++) lines.add(getCenteredStr(RESTAURANT_ADD[i]));
		lines.add(getCenteredStr("Tel: " + RESTAURANT_TEL_NO));
		lines.add(divider);
		
		// add table ID, server, datetime
		lines.add(getLeftAlignedStr("Table: " + order.getTableID()));
		lines.add(getLeftAlignedStr("Server: " + order.getStaffName()));
		lines.add(getLeftAlignedStr(invoice.getTimestamp().format(DATETIME_FORMAT)));
		lines.add(divider);
		lines.add(blankLine);
		
		// add order items
		String[][] items = order.getAllItemsArr();
		for (int i=0; i<items.length; i++) lines.add(getOrderItemStr(items[i][0], items[i][1], items[i][2]));
		lines.add(blankLine);
		lines.add(divider);
		
		// add tax details
		float[] taxDetails = invoice.getTaxDetails(GST, SERVICE_CHARGE);
		String[] description = {"Sub-Total", "GST Tax", "Service Tax", "Total"};
		String priceStr;
		for (int i=0; i<description.length; i++) {
			if (i == 3) lines.add(divider);
			priceStr = String.format(PRICE_FORMAT, taxDetails[i]);
			priceStr = String.format("%"+ITEM_PRICE_LEN+"s", priceStr);
			lines.add(getRightAlignedStr(description[i] + ":" + " ".repeat(SPACE_LEN) + priceStr));
		}
		lines.add(blankLine);
		
		// add bottom of receipt
		lines.add(divider);
		lines.add(getCenteredStr("Thank you for dining with us!"));
		lines.add(divider);
		lines.add(blankLine);
		lines.add(horiBorder);

		return String.join("\n", lines);
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
		if (currStatus == status) return true;
		else return false;
	}

	/**
	 * Creates a new {@code OrderInvoice} object, 
	 * writes its data into the sales data file,
	 * and then returns the bill invoice to be printed.
	 * Once a new order invoice is created, 
	 * the corresponding {@code Table} object is set to {@code TableStatus.VACATED}
	 * and the corresponding {@code Order} object data is deleted from the order data file.
	 * @param tableID	The table ID of the corresponding {@code Order} object.
	 * @param payMthd	The payment method of the new {@code OrderInvoice} object.
	 * @return the bill invoice to be printed.
	 * @throws Exception if any of the methods 
	 * ({@code writeInvoiceData}, {@code OrderCtrl.getOrderObject} and {@code OrderCtrl.deleteOrderData}) 
	 * called throws an exception
	 */
	public String createInvoice(int tableID, PaymentMethod payMthd) throws Exception {
		String invoiceStr = "";
		try {
			Order order = OrderCtrl.getOrderObject(tableID);
			OrderInvoice invoice = new OrderInvoice(order, payMthd, LocalDateTime.now());
			invoiceStr = getInvoiceStr(invoice);
			tableCtrl.vacateTable(order.getTableID());
			writeInvoiceData(invoice);
			OrderCtrl.deleteOrderData(tableID);
		} catch (Exception e) {
			throw new Exception("InvoiceCtrl.createInvoice() error:\n"
					+ "\t" + e.getMessage());
		} 	
		return invoiceStr;
	}
}
