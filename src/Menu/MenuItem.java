package Menu;

/**
 * Represents 1 menu item.
 * Attributes
 * @author Craigdon Lee
 * @version 1.0
 *
 */
public class MenuItem
{


	/**
	 *  Item type Drink, Main Course, Desert
	 */
	String itemType;
	/**
	 * Name of the menu item
	 */
	String name;
	/**
	 * Description of the menu item
	 */
	String desc;
	/**
	 * Price of the menu item
	 */
	double price;
	

	/**
	 * @param string	Setting menu item type
	 * @param name		Setting name of menu item
	 * @param desc		Setting description of menu item
	 * @param price		Setting Price of menu item
	 */
	public MenuItem(String string, String name, String desc, double price)
	{
		this.itemType = string;
		this.name = name;
		this.desc = desc;
		this.price = price;
	}
	

	/**
	 * @return menu item type string
	 */
	public String getItemType() {
		return this.itemType;
	}
	
	/**
	 * @return Name of menu item string
	 */
	public String getName() {
		return this.name;
	}
	
	
	/**
	 * @return description of menu item string
	 */
	public String getDescription() {
		return this.desc;
	}
	
	/**
	 * @return price of menu item double
	 */
	public double getPrice() {
		return this.price;
	}


}