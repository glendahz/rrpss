import java.util.HashMap;
import java.util.Set;

public class Order {
	private int tableID;
	private String staffName;
	private HashMap<String, Float[]> items = new HashMap<String, Float[]>();
	
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
		Set<String> itemNamesSet = this.items.keySet();
		String[] itemNamesArr = new String[itemNamesSet.size()];
		int i=0;
		for (String name: itemNamesSet) itemNamesArr[i++] = name;
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
			itemNum = this.getItemNum(itemName);			
			itemPrice = this.getItemPrice(itemName);
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
	public void setAllItems(HashMap<String, Float[]> items) {
		this.items = items;
	}
	public void addItem(int itemNum, String itemName, float itemPrice) {
		Float[] itemInfo = new Float[2];
		itemInfo[0] = Float.valueOf(itemNum);
		itemInfo[1] = Float.valueOf(itemPrice);
		this.items.put(itemName, itemInfo);
	}
	public void addItem(String itemNum, String itemName, String itemPrice) {
		addItem(Integer.parseInt(itemNum), itemName, Float.parseFloat(itemPrice));
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
