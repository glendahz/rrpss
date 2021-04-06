import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Order {
	private int tableID;
	private String staffName;
	private Hashtable<String, Float[]> items = new Hashtable<String, Float[]>();
	
	public Order(int tableID, String staffName, String[] itemNames, int[] itemNums, float[] itemPrices) {
		this.tableID = tableID;
		this.staffName = staffName;
		Float[] itemInfo = new Float[2];
		if (itemNames != null) {
			if (itemNames.length != itemNums.length ) throw new IllegalArgumentException("Order() error: item names array and item numbers arrays must be the same length");
			else if (itemNames.length != itemPrices.length) throw new IllegalArgumentException("Order() error: item names array and item prices arrays must be the same length");
			else {
				for (int i=0; i<itemNames.length; i++) {
					itemInfo[0] = Float.valueOf(itemNums[i]);
					itemInfo[1] = Float.valueOf(itemPrices[i]);
					this.items.put(itemNames[i], itemInfo);
				}
			}
		}
	}
	
	public Order(int tableID, String staffName) {
		this(tableID, staffName, null, null, null);
	}
	
	// getter functions
	public int getTableID() {
		return this.tableID;
	}
	public String getStaffName() {
		return this.staffName;
	}
	public String[] getAllItemNames() {
		Enumeration<String> itemNamesEnum = this.items.keys();
		ArrayList<String> itemNamesArrList = new ArrayList<String>();
		while (itemNamesEnum.hasMoreElements()) itemNamesArrList.add(itemNamesEnum.nextElement());
		String[]  itemNamesArr = itemNamesArrList.toArray(new String[itemNamesArrList.size()]);
		return itemNamesArr;
	}
	public String[][] getAllItemsArr(){
		/*
		 * Example Array:
		 * [
		 * 	[num1, name1, price1],
		 * 	[num2, name2, price2],
		 * 	[num3, name3, price3]
		 * ]
		 */
		
		String[] itemNames = this.getAllItemNames();
		String[][] items = new String[itemNames.length][3];
		String itemName;
		int itemNum;
		Float itemPrice;
		for (int i=0; i<itemNames.length; i++) {
			itemName = itemNames[i];
			itemNum = this.items.get(itemName)[0].intValue();
			itemPrice = this.items.get(itemName)[1];
			items[i][0] = String.valueOf(itemNum);
			items[i][1] = itemName;
			items[i][2] = String.valueOf(itemPrice);
		}
		return items;
	}
	public int getItemNum(String itemName) {
		return this.items.get(itemName)[0].intValue();
	}
	public float getItemPrice(String itemName) {
		return this.items.get(itemName)[1].floatValue();
	}
	public boolean containsItem(String itemName) {
		return this.items.containsKey(itemName);
	}
	
	// setter functions
	public void setTableID(int tableID) {
		this.tableID = tableID;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public void setAllItems(Hashtable<String, Float[]> items) {
		this.items = items;
	}
	public void addItem(String itemName, int itemNum, float itemPrice) {
		Float[] itemInfo = new Float[2];
		itemInfo[0] = Float.valueOf(itemNum);
		itemInfo[1] = Float.valueOf(itemPrice);
		this.items.put(itemName, itemInfo);
	}
	public void addItem(String itemNum, String itemName, String itemPrice) {
		addItem(itemName, Integer.parseInt(itemNum), Float.parseFloat(itemPrice));
	}
	public void updateItemNum(String itemName, int itemNum) {
		Float[] itemInfo = this.items.get(itemName);
		itemInfo[0] = Float.valueOf(itemNum);
		this.items.replace(itemName, itemInfo);
	}
	public void updateItemPrice(String itemName, float itemPrice) {
		Float[] itemInfo = this.items.get(itemName);
		itemInfo[1] = Float.valueOf(itemPrice);
		this.items.replace(itemName, itemInfo);
	}
	public void removeItem(String itemName) {
		this.items.remove(itemName);
	}
	
	
}
