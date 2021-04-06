import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InvoiceCtrl {
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
	
	private TableSystem tableSystem;
	
	public static void main(String[] args) {
		try {
			Order order = OrderCtrl.getOrderObject(12);
			OrderInvoice invoice = new OrderInvoice(order, PaymentMethod.valueOf("CASH"), LocalDateTime.now());
			String str = getInvoiceStr(invoice);
			System.out.println(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public InvoiceCtrl(TableSystem tableSystem) {
		this.tableSystem = tableSystem;
	}
	
	
	private static void writeInvoiceData(OrderInvoice invoice) {
		// TODO
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
	
	private static String getHoriBorderStr() {
		return "-".repeat(WIDTH+2);
	}
	
	private static String getBorderedStr(String str) {
		return "|" + str + "|";
	}
	
	private static String getBlankStr() {
		String newStr = " ".repeat(WIDTH);
		newStr = getBorderedStr(newStr);
		return newStr;
	}
		
	private static String getDividerStr() {
		return getCenteredStr("-".repeat(DIVIDER_LEN));
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
		newStr = getBorderedStr(newStr);
		return newStr;
	} 
	
	private static String getOrderItemStr(String itemName, String itemNum, String itemPrice) {
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
		
		// add top of receipt & restaurant name
		String invoiceStr = getHoriBorderStr()
				+ "\n" + getBlankStr() 
				+ "\n" + getCenteredStr(RESTAURANT_NAME) 
				+ "\n" + getDividerStr();
		
		// add restaurant address & telephone number
		for(int i=0; i<RESTAURANT_ADD.length; i++) {
			invoiceStr += "\n" + getCenteredStr(RESTAURANT_ADD[i]);
		}
		
		invoiceStr += "\n" + getCenteredStr("Tel: " + RESTAURANT_TEL_NO) 
				+ "\n" + getDividerStr(); 
		
		// add table ID, server, datetime
		invoiceStr += "\n" + getLeftAlignedStr("Table: " + order.getTableID())
				+ "\n" + getLeftAlignedStr("Server: " + order.getStaffName())
				+ "\n" + getLeftAlignedStr(invoice.getTimestamp().format(DATETIME_FORMAT))
				+ "\n" + getDividerStr()
				+ "\n" + getBlankStr();
		
		// add order items
		String[][] items = order.getAllItemsArr();
		for (int i=0; i<items.length; i++) invoiceStr += "\n" + getOrderItemStr(items[i][0], items[i][1], items[i][2]);
		invoiceStr += "\n" + getBlankStr() 
				+ "\n" + getDividerStr();
		
		// add tax details
		float[] taxDetails = invoice.getTaxDetails(GST, SERVICE_CHARGE);
		String[] description = {"Sub-Total", "GST Tax", "Service Tax", "Total"};
		String priceStr;
		for (int i=0; i<description.length; i++) {
			if (i == 3) invoiceStr += "\n" + getDividerStr();
			priceStr = String.format(PRICE_FORMAT, taxDetails[i]);
			priceStr = String.format("%"+ITEM_PRICE_LEN+"s", priceStr);
			invoiceStr += "\n" + getRightAlignedStr(description[i] + ":" + " ".repeat(SPACE_LEN) + priceStr);
		}
		invoiceStr += "\n" + getBlankStr();
		
		// add bottom of receipt
		invoiceStr += "\n" + getDividerStr()
				+ "\n" + getCenteredStr("Thank you for dining with us!")
				+ "\n" + getDividerStr()
				+ "\n" + getBlankStr()
				+ "\n" + getHoriBorderStr();
		return invoiceStr;
	}
	
	// TODO change table status
	// TODO delete order from order file
	public String createInvoice(int orderID, String payMthd) throws Exception {
		String invoiceStr = "";
		try {
			Order order = OrderCtrl.getOrderObject(orderID);
			OrderInvoice invoice = new OrderInvoice(order, PaymentMethod.valueOf(payMthd), LocalDateTime.now());
			invoiceStr = getInvoiceStr(invoice);
			tableSystem.vacateTable(order.getTableID());
			writeInvoiceData(invoice);
			OrderCtrl.deleteOrderData(orderID);
		} catch (IllegalArgumentException e) { 
			throw new Exception("InvoiceCtrl.createInvoice() error: invalid PaymentMethod string");
		} catch (Exception e) {
			throw new Exception("InvoiceCtrl.createInvoice() error:\n"
					+ "\t" + e.getMessage());
		} 	
		return invoiceStr;
	}
}
