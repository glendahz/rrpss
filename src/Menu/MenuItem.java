package Menu;

public class MenuItem
{

//	public static enum ItemType
//	{
//		MainCourse("Main Course"),
//		Drink("Drink"),
//		Dessert("Dessert"),
//		SetPackage("Set Package");
//		
//		private final String value;
//		  
//		private ItemType(String value) {
//			this.value = value;
//		}
//
//		public String toStrValue() {
//			return value;
//		}
//	}

	String itemType;
	String name;
	String desc;
	double price;
	

	public MenuItem(String string, String name, String desc, double price)
	{
		this.itemType = string;
		this.name = name;
		this.desc = desc;
		this.price = price;
	}
	

	public String getItemType() {
		return this.itemType;
	}
	
//	public String setItemType() {
//		return this.itemType;
//	}
	

//	public void setItemType(ItemType newItemType) {
//		_itemType = newItemType;
//	}
//	
//	/**
//	 * Gets the name of this menu item
//	 * 
//	 * @return This menu item's name
//	 */
	public String getName() {
		return this.name;
	}
	
	
	public String getDescription() {
		return this.desc;
	}
	
	public double getPrice() {
		return this.price;
	}
//	
//	/**
//	 * Changes the name of this menu item
//	 * 
//	 * @param newName This menu item's new name
//	 */
//	public void setName(String newName) {
//		_name = newName;
//	}
//	
//	/**
//	 * Gets the description of this menu item
//	 * 
//	 * @return This menu item's description
//	 */
//	public String getDesc() {
//		return _desc;
//	}
//	
//	/**
//	 * Changes the description of this menu item
//	 * 
//	 * @param newDesc This menu item's new description
//	 */
//	public void setDesc(String newDesc) {
//		_desc = newDesc;
//	}
//	
//	/**
//	 * Gets the price of this menu item
//	 * 
//	 * @return This menu item's price
//	 */
//	public double getPrice() {
//		return _price;
//	}
//	
//	/**
//	 * Changes the price of this menu item
//	 * 
//	 * @param newPrice This menu item's new price
//	 */
//	public void setPrice(double newPrice) {
//		_price = newPrice;
//	}
//	
//	/**
//	 * Display the details of this item with proper formatting<br>
//	 * Displayed information include menu item's name, price and description
//	 */
//	public void displayItemDetails() {
//		System.out.printf("%-30s", getName());
//		System.out.printf("%20s%n",
//				new DecimalFormat("$###,##0.00").format(getPrice()));
//		System.out.println("\"" + getDesc() + "\"");
//	}
//	
//	/**
//	 * Display item name, item type and the item price in a single line<br>
//	 * Provides a summary of this menu item
//	 */
//	public void displayItemSummary() {
//		System.out.printf("%-30s", getName());
//		System.out.printf("%-20s", "[" + getItemType().toStrValue() + "]");
//		System.out.printf("%12s%n",
//				new DecimalFormat("$###,##0.00").format(getPrice()));
//	}
//	

}