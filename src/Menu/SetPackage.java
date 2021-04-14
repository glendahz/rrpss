package Menu;

import java.util.ArrayList;

/**
 * Represents 1 Set menu item object.
 * Attributes = int SetIndex, ArrayList<String> setList, double Price.
 * @author Craigdon Lee
 * @version 1.0
 *
 */
public class SetPackage {
	
	ArrayList<String> setList = new ArrayList<String>();
	int setIndex;
	double setPrice;
	
	/**
	 * @param SetIndex		Sets the index of a specific setpackage
	 * @param setList		Sets the array list of menu items
	 * @param Price			Sets the price of the Set menu item.
	 */
	public SetPackage(int SetIndex, ArrayList<String> setList, double Price)
	{
		this.setList = setList;
		this.setIndex = SetIndex;
		this.setPrice = Price;
	}

	/**
	 * @return item type of set menu
	 */
	public String getItemType() {
		return "setMenu";
	}
	
	/**
	 * @return returns the index that represents the specific set menu item
	 */
	public int getIndex() {
		return this.setIndex;
	}
	
	/**
	 * @return Price of specific set menu item
	 */
	public double getPrice() {
		return this.setPrice;
	}
	
	/**
	 * @return Array list of menu items.
	 */
	public ArrayList<String> getSetList() {
		return this.setList;
	}
	
}