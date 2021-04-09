package Order;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Table.TableCtrl;
import util.Controller;

public class InvoiceCtrl extends Controller {
	private static final File SALES_FILE = new File("data", "sales.txt");
	private final static float GST = 0.07f;
	private final static float SERVICE_CHARGE = 0.1f;
	private final static String RESTAURANT_NAME = "OODP Restaurant";
	private final static String[] RESTAURANT_ADD = {"50 Nanyang Ave", "32 Block N4-B1B-11", "Singapore 639798"};
	private final static String RESTAURANT_TEL_NO = "6543 2109";
	private final static DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("EEE dd/MM/yyyy hh:mm a");
	private final static String PRICE_FORMAT = "$%.2f";
	private final static int WIDTH = 50;
	private final static int DIVIDER_LEN = (int) Math.round(0.8 * WIDTH);
	private final static int SPACE_LEN = 2;
	private final static int TEXT_BLOCK_LEN = DIVIDER_LEN - (2 * SPACE_LEN);
	private final static int ITEM_NUM_LEN = 2;
	private final static int ITEM_PRICE_LEN = 8;
	private final static int ITEM_NAME_LEN = TEXT_BLOCK_LEN - ITEM_NUM_LEN - ITEM_PRICE_LEN - (2 * SPACE_LEN);
	
	private static TableCtrl tableCtrl;
	
	public void setTableCtrl(TableCtrl ctrl) {
		tableCtrl = ctrl;
	}
	
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
	
	private static void writeInvoiceData(OrderInvoice invoice) throws Exception {
		String data = "\n" + invoiceObjToData(invoice);
		try {
			FileWriter fwa = new FileWriter(SALES_FILE, true);
			fwa.write(data);
			fwa.close();
		} catch (IOException e) {
			throw new Exception("InvoiceCtrl.writeInvoiceData error: FileWriter could not find sales report data file");
		}
	}
	
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
		
	private static String getLeftAlignedStr(String str) {
		String newStr = String.format("%-"+TEXT_BLOCK_LEN+"s", str);
		return getCenteredStr(newStr);
	}
	
	private static String getRightAlignedStr(String str) {
		String newStr = String.format("%"+TEXT_BLOCK_LEN+"s", str);
		return getCenteredStr(newStr);
	}
	
	private static String getCenteredStr(String str) {
		int leftLen = (WIDTH - str.length()) / 2;
		int rightLen = WIDTH - str.length() - leftLen;
		String newStr = String.format("%"+leftLen+"s%s%"+rightLen+"s", "", str, "");
		newStr = "|" + newStr + "|";
		return newStr;
	} 
	
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
	
	// TODO change table status
	// TODO delete order from order file
	public String createInvoice(int tableID, String payMthd) throws Exception {
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
