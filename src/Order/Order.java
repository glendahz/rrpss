package Order;
import java.util.HashMap;
import java.util.Set;

/**
 * Represents an order made by customers (represented by tables) in the restaurant.
 * Each table (group of customers) can only make one order .
 * Each order is taken by one staff.
 * An order can have multiple order items.
 * Once the customers make payment, the Order object is stored into an OrderInvoice object.
 * @author Glenda Hong Zixuan
 */

public class Order {
	/**
	 * The ID number of the table which made this Order
	 */
	private int tableID;
	
	/**
	 * The name of the staff member who took this Order. 
	 * The name can be full name, first name or last name.
	 */
	private String staffName;
	
	/**
	 * The order items contained in this Order
	 * Key:		The name of the order item.
	 * Value:	The first element of the Float array is the number of the corresponding order item.
	 * 			The second element of the Float array is the price of the corresponding order item.
	 */
	private HashMap<String, Float[]> items = new HashMap<String, Float[]>();
	
	/**
	 * Creates a new Order.
	 * @param tableID 		The ID number of the table which made this Order.
	 * @param staffName 	The name of the staff member who took this Order. 
	 * 						The name can be full name, first name or last name.
	 * @param itemNames 	The names of the order items contained in this Order.
	 * @param itemNums 		The number of each order item contained in this order
	 * 						The numbers should be arranged according to their corresponding item names in <code>itemNames</code>.
	 * @param itemPrices	The price of each order item contained in this order
	 * 						The prices should be arranged according to their corresponding item names in <code>itemNames</code>.
	 */
	public Order(int tableID, String staffName, String[] itemNames, int[] itemNums, float[] itemPrices) {
		this.tableID = tableID;
		this.staffName = staffName;
		Float[] itemInfo;

		if (itemNames != null) {
			if (itemNames.length != itemNums.length ) throw new IllegalArgumentException("Order() error: item names array and item numbers arrays must be the same length");
			else if (itemNames.length != itemPrices.length) throw new IllegalArgumentException("Order() error: item names array and item prices arrays must be the same length");
			else {
				for (int i=0; i<itemNames.length; i++) {
					itemInfo = new Float[2];
					itemInfo[0] = Float.valueOf(itemNums[i]);
					itemInfo[1] = Float.valueOf(itemPrices[i]);
					this.items.put(itemNames[i], itemInfo);
				}
			}
		}
	}
	
	/**
	 * Creates a new Order.
	 * @param tableID 		The ID number of the table which made this Order.
	 * @param staffName 	The name of the staff member who took this Order. 
	 * 						The name can be full name, first name or last name.
	 */
	public Order(int tableID, String staffName) {
		this(tableID, staffName, null, null, null);
	}
	
	// getter functions
	
	/**
	 * Gets the ID number of the table which made this Order.
	 * @return the ID number of the table which made this Order.
	 */
	public int getTableID() {
		return this.tableID;
	}
	
	/**
	 * Gets the name of the staff member who took this Order.
	 * @return the name of the staff member who took this Order.
	 */
	public String getStaffName() {
		return this.staffName;
	}
	
	/**
	 * Gets the names of all order items contained in this Order.
	 * @return the names of all order items contained in this Order.
	 * */
	public String[] getAllItemNames() {
		Set<String> itemNamesSet = this.items.keySet();
		String[] itemNamesArr = new String[itemNamesSet.size()];
		int i=0;
		for (String name: itemNamesSet) itemNamesArr[i++] = name;
		return itemNamesArr;
	}
	
	/**
	 * Gets the numbers, names and prices of all order items contained in this Order.
	 * @return the numbers, names and prices of all order items contained in this Order. The nested array will be formatted like this:
	 * <br><pre>
	 * [
	 * 	[num1, name1, price1],
	 * 	[num2, name2, price2],
	 * 	[num3, name3, price3]
	 * ]
	 * </pre>
	 */
	public String[][] getAllItemsArr(){		
		String[] itemNames = this.getAllItemNames();
		String[][] items = new String[itemNames.length][3];
		String itemName;
		int itemNum;
		Float itemPrice;
		for (int i=0; i<itemNames.length; i++) {
			itemName = itemNames[i];
			itemNum = this.getItemNum(itemName);			
			itemPrice = this.getItemPrice(itemName);
			items[i][0] = String.valueOf(itemNum);
			items[i][1] = itemName;
			items[i][2] = String.valueOf(itemPrice);
		}
		
		return items;
	}
	
	/**
	 * Gets the number of a specific order item contained in this Order.
	 * @param	itemName The name of the order item contained in this Order.
	 * @return the number corresponding to <code>itemName</code>.
	 */
	public int getItemNum(String itemName) {
		return this.items.get(itemName)[0].intValue();
	}
	
	/**
	 * Gets the price of a specific order item contained in this Order.
	 * @param	itemName The name of the order item contained in this Order.
	 * @return the price corresponding to <code>itemName</code>.
	 */
	public float getItemPrice(String itemName) {
		return this.items.get(itemName)[1].floatValue();
	}
	
	/**
	 * Checks if this Order contains a specific order item.
	 * @param	itemName The name of the order item to check.
	 * @return <code>true</code> if the order item is contained in this Order, otherwise <code>false</code>.
	 * */
	public boolean containsItem(String itemName) {
		return this.items.containsKey(itemName);
	}
	
	// setter functions
	
	/**
	 * Sets the ID number of the table which made this Order.
	 * @param	tableID The ID number of the table which made this Order.
	 */
	public void setTableID(int tableID) {
		this.tableID = tableID;
	}
	
	/**
	 * Sets the name of the staff member who took this Order.
	 * @param	staffName the name of the staff member who took this Order.
	 */
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
	/**
	 * Sets the order items contained in this Order.
	 * @param items 	Key:	The name of the order item.
	 * 					Value:	The first element of the Float array is the number of the corresponding order item.
	 * 							The second element of the Float array is the price of the corresponding order item.
	 */
	public void setAllItems(HashMap<String, Float[]> items) {
		this.items = items;
	}
	
	/**
	 * Add an order item to this Order.
	 * @param itemNum 	The number of the order item to be added.
	 * @param itemName	The name of the order item to be added.
	 * @param itemPrice	The price of the order item to be added.
	 */
	public void addItem(int itemNum, String itemName, float itemPrice) {
		Float[] itemInfo = new Float[2];
		itemInfo[0] = Float.valueOf(itemNum);
		itemInfo[1] = Float.valueOf(itemPrice);
		this.items.put(itemName, itemInfo);
	}
	
	/**
	 * Add an order item to this Order.
	 * @param itemNum 	The number of the order item to be added.
	 * @param itemName	The name of the order item to be added.
	 * @param itemPrice	The price of the order item to be added.
	 */
	public void addItem(String itemNum, String itemName, String itemPrice) {
		addItem(Integer.parseInt(itemNum), itemName, Float.parseFloat(itemPrice));
	}
	
	/**
	 * Update the number of an order item contained in this Order.
	 * @param itemName	The name of the order item to be updated.
	 * @param itemNum 	The updated number of the order item.
	 */
	public void updateItemNum(String itemName, int itemNum) {
		Float[] itemInfo = this.items.get(itemName);
		itemInfo[0] = Float.valueOf(itemNum);
		this.items.replace(itemName, itemInfo);
	}
	
	/**
	 * Update the price of an order item contained in this Order.
	 * @param itemName	The name of the order item to be updated.
	 * @param itemPrice	The updated price of the order item.
	 */
	public void updateItemPrice(String itemName, float itemPrice) {
		Float[] itemInfo = this.items.get(itemName);
		itemInfo[1] = Float.valueOf(itemPrice);
		this.items.replace(itemName, itemInfo);
	}
	
	/**
	 * Remove an order item contained in this Order.
	 * @param itemName	The name of the order item to be removed.
	 * */
	public void removeItem(String itemName) {
		this.items.remove(itemName);
	}
	
	
}
