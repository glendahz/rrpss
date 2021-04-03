import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class MenuCtrl {
    public static int counter = 0;
    double ItemPrice = 0;
	boolean validInput = false;
	
	static MenuItem[] menuitem = new MenuItem[100];
	// ADD for set menu object also //
	
	public MenuItem[] createMenuItem(){
		// This function will Create item object
		
		Scanner scan = new Scanner(System.in);
		System.out.println("What item would you like to add to the menu?");
		System.out.print("Please enter Item type : ");
		String ItemType = scan.nextLine();   
		System.out.print("\nPlease enter name if Item : ");
		String ItemName = scan.nextLine();   
		System.out.print("\nPlease enter Item description : ");
		String ItemDesc = scan.nextLine();   
		System.out.print("\nPlease enter Item Price : $");
		while(!validInput) {
		    try {
		    	double ItemPrice = scan.nextDouble(); 
		        validInput = true;
		    } catch(InputMismatchException e) {
		        System.out.println("Please enter an number amount!");
		        scan.next();
		    }
		}
		
		
		
//		initiateItem(ItemType,ItemName,ItemDesc,ItemPrice);
		
		menuitem[counter] = new MenuItem(ItemType, ItemName, ItemDesc, ItemPrice);
		counter += 1;
		System.out.print("\nMenu item was created\n");
//		menuitem[1] = new MenuItem("Food", "ass", "weed", 420.00);
		
		return menuitem;
	}
		
	
	public static void createSetMenuItem() {
		// This function will Create set of item object
		
			
	}
	
	public static void removeMenuItem() {
		// Delete which one? (4)
		
		Scanner scan = new Scanner(System.in);
		
		
		int index = 1;
		System.out.println("\n --- Reference to current Menu --- ");
		
		for(int i = 0;i<counter;i++ ) {
			if(menuitem[i] != null) {
				System.out.print(index);
				System.out.print(")  ");
				System.out.println(menuitem[i].getItemType()); /// HERE TO GET METHODS TO PRINT SHIT.
				index++;
			}
		}
		
		System.out.print("Please enter Item type TO DELETE : ");
		String toDelete = scan.nextLine();
		
		for(int j = 0;j<counter;j++ ) {
			if(menuitem[j] != null) {
				if(menuitem[j].getItemType().equals(toDelete)) {
//					System.out.print("found");
					menuitem[j] = null;
				}
			}
			
		}
		
	}
	
	public static void removeSetItem() {
		
		
		
	}
	
	public static void viewMenu() {
		
		int index = 1;
		System.out.println("\n --- Current Menu --- ");
		
		for(int i = 0;i<counter;i++ ) {
			if(menuitem[i] != null) {
				System.out.print(index);
				System.out.print(")  ");
				System.out.println(menuitem[i].getItemType()); /// HERE TO GET METHODS TO PRINT SHIT.
				index++;
			}
			
		    
		}
		
	}
	
	public static void updateMenuItem() {
		// load menu from text file. read file
		// clear all current menu items to load from file
		for(int i = 0;i<counter;i++ ) {
			menuitem[i] = null;
		}
		
		try {
		      File myObj = new File("C://Users//kraji//Desktop//Codes//Java cx2002//trail_1.txt");
		      Scanner myReader = new Scanner(myObj);
		      myReader.nextLine(); 
		      while (myReader.hasNextLine()) {
		          String data = myReader.nextLine();
//		          System.out.println(data);
//		          String[] arrOfStr = str.split("@", 2);
		          String[] arrLinedata = data.split(" ", 2);
//		          System.out.println(arrLinedata[1]);
		          menuitem[counter] = new MenuItem(arrLinedata[1], arrLinedata[1], arrLinedata[1], 1);
		          counter += 1;
		      }
		      myReader.close();
		      System.out.print("\nMenu was loaded from text file\n");
	    } catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }
		
	}
	
	public static void convertMenuData() {
		// Current menu to text file. 
		
		int index = 1;
		try {
		      FileWriter myWriter = new FileWriter("C://Users//kraji//Desktop//Codes//Java cx2002//trail_1.txt");
		      myWriter.write("--- Saved Menu --- \n");
//		      myWriter.write("Files in Java might be tricky, but it is fun enough!");
//		      myWriter.write("\n fuck this shit...");
		      
				for(int i = 0;i<counter;i++ ) {
					if(menuitem[i] != null) {
						myWriter.write(String.valueOf(index));
						myWriter.write(") ");
						myWriter.write(menuitem[i].getItemType()); /// HERE TO GET METHODS TO PRINT SHIT.
						myWriter.write("\n");
						index++;
					}
				}
		      
		      myWriter.close();
		      System.out.println("\nSuccessfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}
	
}
