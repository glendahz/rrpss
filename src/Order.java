import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Order {
	private int orderID;
	private int tableID;
	private String staffName;
	private Hashtable<String, Integer> itemNums = new Hashtable<String, Integer>();
	private Hashtable<String, Float> itemPrices = new Hashtable<String, Float>();
	
	public Order(int orderID, int tableID, String staffName, String[] itemNames, int[] itemNums, float[] itemPrices) {
		this.orderID = orderID;
		this.tableID = tableID;
		this.staffName = staffName;
		if (itemNames != null) {
			if (itemNames.length != itemNums.length ) throw new IllegalArgumentException("Order() error: item names array and item numbers arrays must be the same length");
			else if (itemNames.length != itemPrices.length) throw new IllegalArgumentException("Order() error: item names array and item prices arrays must be the same length");
			else {
				for (int i=0; i<itemNames.length; i++) {
					this.itemNums.put(itemNames[i], Integer.valueOf(itemNums[i]));
					this.itemPrices.put(itemNames[i], Float.valueOf(itemPrices[i]));
				}
			}
		}
	}
	
	public Order(int orderID, int tableID, String staffName) {
		this(orderID, tableID, staffName, null, null, null);
	}
	
	// getter functions
	public int getOrderID() {
		return this.orderID;
	}
	public int getTableID() {
		return this.tableID;
	}
	public String getStaffName() {
		return this.staffName;
	}
	public ArrayList<String> getAllItemNames() {
		Enumeration<String> itemNamesEnum = this.itemNums.keys();
		ArrayList<String> itemNamesArrList = new ArrayList<String>();
		while (itemNamesEnum.hasMoreElements()) itemNamesArrList.add(itemNamesEnum.nextElement());
		return itemNamesArrList;
	}
	public Hashtable<String, Integer> getAllItemNums(){
		return this.itemNums;
	}
	public Hashtable<String, Float> getAllItemPrices(){
		return this.itemPrices;
	}
	public ArrayList<ArrayList<String>> getAllItems(){
		ArrayList<ArrayList<String>> items = new ArrayList<ArrayList<String>>();
		ArrayList<String> itemNames = getAllItemNames();
		ArrayList<String> item;
		String itemName;
		for (int i=0; i<itemNames.size(); i++) {
			item = new ArrayList<String>();
			itemName = itemNames.get(i);
			item.add(itemName);
			item.add(String.valueOf(this.itemNums.get(itemName)));
			item.add(String.valueOf(this.itemPrices.get(itemName)));
			items.add(item);
		}
		return items;
	}
	public ArrayList<ArrayList<String>> getAllItemsSepArr(){
		ArrayList<ArrayList<String>> items = new ArrayList<ArrayList<String>>();
		ArrayList<String> itemNames = getAllItemNames();
		ArrayList<String> itemNums = new ArrayList<String>();
		ArrayList<String> itemPrices = new ArrayList<String>();
		String itemName;
		Integer itemNum; 
		Float itemPrice;
		for (int i=0; i<itemNames.size(); i++) {
			itemName = itemNames.get(i);
			itemNum = this.itemNums.get(itemName);
			itemNums.add(String.valueOf(itemNum));
			itemPrice = this.itemPrices.get(itemName);
			itemPrices.add(String.valueOf(itemPrice));
		}
		items.add(itemNames);
		items.add(itemNums);
		items.add(itemPrices);
		return items;
	}
	public int getItemNum(String itemName) {
		return getItemNumInteger(itemName).intValue();
	}
	public Integer getItemNumInteger(String itemName) {
		return this.itemNums.get(itemName);
	}
	public float getItemPrice(String itemName) {
		return this.getItemPriceFloat(itemName).floatValue();
	}
	public Float getItemPriceFloat(String itemName) {
		return this.itemPrices.get(itemName);
	}
	public boolean containsItem(String itemName) {
		return this.itemNums.containsKey(itemName);
	}
	
	// setter functions
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public void setTableID(int tableID) {
		this.tableID = tableID;
	}
	public void setStaff(String staffName) {
		this.staffName = staffName;
	}
	public void setAllItemNums(Hashtable<String, Integer> itemNums) {
		this.itemNums = itemNums;
	}
	public void setAllItemPrices(Hashtable<String, Float> itemPrices) {
		this.itemPrices = itemPrices;
	}
	public void addItem(String itemName, Integer itemNum, Float itemPrice) {
		this.itemNums.put(itemName, itemNum);
		this.itemPrices.put(itemName, itemPrice);
	}
	public void addItem(String itemName, int itemNum, float itemPrice) {
		addItem(itemName, Integer.valueOf(itemNum), Float.valueOf(itemPrice));
	}
	public void addItem(String itemName, String itemNum, String itemPrice) {
		addItem(itemName, Integer.valueOf(itemNum), Float.valueOf(itemPrice));
	}
	public void updateItemNum(String itemName, Integer itemNum) {
		this.itemNums.replace(itemName, itemNum);
	}
	public void updateItemNum(String itemName, int itemNum) {
		updateItemNum(itemName, Integer.valueOf(itemNum));
	}
	public void updateItemPrice(String itemName, Float itemPrice) {
		this.itemPrices.replace(itemName, itemPrice);
	}
	public void updateItemPrice(String itemName, float itemPrice) {
		updateItemPrice(itemName, Float.valueOf(itemPrice));
	}
	public void removeItem(String itemName) {
		this.itemNums.remove(itemName);
		this.itemPrices.remove(itemName);
	}
	
	
}
