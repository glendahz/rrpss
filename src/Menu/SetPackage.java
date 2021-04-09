package Menu;

import java.util.ArrayList;

public class SetPackage {
	
//	private int input;
	ArrayList<String> setList = new ArrayList<String>();
	int setIndex;
	double setPrice;
	
	public SetPackage(int SetIndex, ArrayList<String> setList, double Price)
	{
		this.setList = setList;
		this.setIndex = SetIndex;
		this.setPrice = Price;
////		setpackage[0] = new ArrayList<SetPackage>();
//		while (true) {
//			Scanner scan = new Scanner(System.in);
//			System.out.print("Please enter number of items in setmenu, 0 to quit: ");
//			input = scan.nextInt();
//			if (input == 0) {
//				break;
//			} else {
//				for(int j = 0; j<input; j++) {
//					System.out.print("Please enter name of menuitem to add in: ");
//					String name  = scan.next();
//					for(int i = 0;i<counter;i++ ) {
//						if(menuitem[i] != null) {
//							if(menuitem[i].getName().equals(name)) {
//								setpackage.add(menuitem[i]);
//							}
//						}
//					}
//				}
//				}
//			}
//		
//		setpackage.add( new MenuItem("1", "1", "1", 1));
//		setpackage.add( new MenuItem("2", "2", "2", 2));
//		System.out.print(setpackage.get(1));
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