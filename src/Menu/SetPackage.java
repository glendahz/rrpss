package Menu;

import java.util.ArrayList;

public class SetPackage {
	
	ArrayList<String> setList = new ArrayList<String>();
	int setIndex;
	double setPrice;
	
	public SetPackage(int SetIndex, ArrayList<String> setList, double Price)
	{
		this.setList = setList;
		this.setIndex = SetIndex;
		this.setPrice = Price;
	}

	public String getItemType() {
		return "setMenu";
	}
	
	public int getIndex() {
		return this.setIndex;
	}
	
	public double getPrice() {
		return this.setPrice;
	}
	
	public ArrayList<String> getSetList() {
		return this.setList;
	}
	
}