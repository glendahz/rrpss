package Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class MenuCtrl {
    public static int counter = 0;
    double ItemPrice = 0;
	boolean validInput = false;
	public static int setcounter = 0;
	public static int setIndexCounter = 0;
	
	static private ArrayList<MenuItem> menuitem = new ArrayList<MenuItem>();
	static private ArrayList<SetPackage> setpackage = new ArrayList<SetPackage>();
//	static MenuItem[] menuitem = new MenuItem[100];
// 	static SetPackage[] setpackage = new SetPackage[100];

 	private static Scanner scan = new Scanner(System.in);
 	///
 	
 	public static ArrayList<SetPackage> getSetItemObject() {
 		updateMenuItem();
		return setpackage;
 	}
 	
 	public static ArrayList<MenuItem> getItemObject() {
 		updateMenuItem();
		return menuitem;
 	}
 	
	public static boolean checkItemName(String NameCheck) {
		updateMenuItem();
		for(int i = 0; i< menuitem.size(); i++) {
//			System.out.print(menuitem[i].getName());
//			if(menuitem[i] != null) {
				if(menuitem.get(i).getName().equals(NameCheck)) {
					return true;
				}
//			}
		}
		return false;
	}
	
	public static double getItemPrice(String NameOfItem) {
		updateMenuItem();
		for(int i = 0; i< menuitem.size(); i++) {
//			if(menuitem[i] != null) {
				if(menuitem.get(i).getName().equals(NameOfItem)) {
				
					return menuitem.get(i).getPrice();
				}
//			}
		}
		System.out.print("price not found.. returned 0");
		return 0;
	}
	
	public static boolean checkSetItemIndex(int IndexOfSet) {
		updateMenuItem();
		for(int i = 0; i< setIndexCounter; i++) {
//			System.out.print(menuitem[i].getName());
//			if(setpackage[i] != null) {
				if(setpackage.get(i).getIndex() == IndexOfSet) {
					return true;
				}
//			}
		}
		return false;
	}
	
	public static double getSetItemPrice(int IndexOfSet) {
		updateMenuItem();
		for(int i = 0; i< setIndexCounter; i++) {
//			if(setpackage[i] != null) {
				if(setpackage.get(i).getIndex() == IndexOfSet) {
					return setpackage.get(i).getPrice();
				}
//			}
		}
		return 0;
	}
 	///
	
	public ArrayList<MenuItem> createMenuItem(){
		// This function will Create item object
//		updateMenuItem();
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
		    	ItemPrice = scan.nextDouble(); 
		        validInput = true;
		    } catch(InputMismatchException e) {
		        System.out.println("Please enter an number amount!");
		        scan.next();
		    }
		}
		
		menuitem.add(new MenuItem(ItemType, ItemName, ItemDesc, ItemPrice));
		counter += 1;
		System.out.print("\nMenu item was created\n");
		convertMenuData();
		return menuitem;
	}
		
	
	public static void createSetMenuItem() {
		/// adds to setmenu text file
		boolean validInput = false;
		double SetPrice = 0;
		// This function will Create set of item object
		ArrayList<String> setLists = new ArrayList<String>();
        
		
		////

//		      FileWriter myWriter = new FileWriter("data", "SetMenu.txt", true);
	      System.out.println("What item would you like to add to set menu?");
	      System.out.print("Please enter number of items in set menu : ");
	      int SetSize = scan.nextInt();   
//		      myWriter.write(String.valueOf(setIndexCounter));
	      setIndexCounter += 1;
//		      myWriter.write(",");
	      for(int i =0; i<SetSize ;i++ ) {
	    	  if(i == 0) {
	    		  scan.nextLine(); 
	    	  }
	    	  
	    	  System.out.print("\nPlease enter name if Item :");
	    	  String SetNames = scan.nextLine();  
	    	  if(checkItemName(SetNames)) {
//		    		  myWriter.write(SetNames);
//			    	  myWriter.write(",");
		    	  setLists.add(SetNames);  
	    	  }
	    	  
	    	  else {
	    		  System.out.print("\nItem does not exist please enter an item from the menu :");
	    		  SetNames = scan.nextLine(); 
	    		  if(checkItemName(SetNames)) {
//			    		  myWriter.write(SetNames);
//				    	  myWriter.write(",");
			    	  setLists.add(SetNames);
		    	  }
	    		  else {
	    			  System.out.print("you entered wrongly again please check the menu before adding set items");
	    			  viewMenu();
//	    			  MenuUI.main(null);
	    		  }
	    	  }
	      }
	      System.out.print("\nPlease enter Item Price : $");
	      while(!validInput) {
	    	  try {
	    		  SetPrice = scan.nextDouble(); 
	    		  validInput = true;
			   	} catch(InputMismatchException e) {
			   		System.out.println("Please enter an number amount!");
			        scan.next();
			   	}
	      }
//				myWriter.write(StringPrice);
//				myWriter.write("\n");
			setcounter += 1;
	      
//		      myWriter.close();
	    System.out.println("\nSuccessfully wrote to the file.");
		setpackage.add(new SetPackage(setpackage.size(), setLists, SetPrice));
        setIndexCounter += 1; /// here have a setpackages already
        convertSetMenuData();
        viewMenu();
	}
	
	public static void removeMenuItem() {
		// Delete which one? (4)
	
		int index = 1;
		System.out.println("\n --- Reference to current Menu --- ");
		
		for(int i = 0;i<menuitem.size();i++ ) {
//			if(menuitem[i] != null) {
				System.out.print(index);
				System.out.print(")  ");
				System.out.print(menuitem.get(i).getItemType()); /// HERE TO GET METHODS TO PRINT SHIT.
				System.out.print(" ");
				System.out.print(menuitem.get(i).getName());
				System.out.print(" ");
				System.out.print(menuitem.get(i).getPrice());
				System.out.print(" ");
				System.out.println(menuitem.get(i).getDescription());
				index++;
//			}
		}
		
		System.out.print("Please enter Item name to DELETE : ");
		String toDelete = scan.nextLine();
		
		for(int j = 0;j<menuitem.size();j++ ) {
//			if(menuitem[j] != null) {
				if(menuitem.get(j).getName().equals(toDelete)) {
					menuitem.remove(j);
				}
//			}
			
		}
		convertMenuData();
		
	}
	
	public static void removeSetItem() {
		System.out.print("Please enter Item set Index to DELETE : ");
		int toDelete = scan.nextInt();
		
		for(int j = 0;j<setpackage.size();j++ ) {
//			if(setpackage[j] != null) {
				if(setpackage.get(j).getIndex() == toDelete) {
					setpackage.remove(j);
					
//				}
			}
			
		}
		convertSetMenuData();
	}
	
	public static void viewMenu() {
		// just to show menu
		updateMenuItem();
		System.out.println("\n=========== Menu ===========");
		
		String menuFormat = "| %-3d | %-20s | %-39s | %8.2f |%n";

		System.out.format("+-----+----------------------+-----------------------------------------+----------+%n");
		System.out.format("| ID  | Name                 | Description                             | Price($) |%n");
		System.out.format("+-----+----------------------+-----------------------------------------+----------+%n");
		
		for(int i = 0;i<menuitem.size();i++ ) {
//			if(menuitem[i] != null) {
				MenuItem item = menuitem.get(i);
				System.out.printf(menuFormat, i + 1, item.getName(), item.getDescription(), item.getPrice());
//			}
		}
		System.out.format("+-----+----------------------+-----------------------------------------+----------+%n");
		
		System.out.println("\n=========== Set Menu ===========");
		
		String setFormat = "| %-3d | %-60s | %8.2f |%n";

		System.out.format("+-----+--------------------------------------------------------------+----------+%n");
		System.out.format("| ID  | Set Package                                                  | Price($) |%n");
		System.out.format("+-----+--------------------------------------------------------------+----------+%n");
		
		for(int j = 0; j<setpackage.size(); j++) {
//			if(setpackage[j] != null) {
				SetPackage set = setpackage.get(j);
				System.out.printf(setFormat, setpackage.get(j).getIndex(), String.join(" + ", set.getSetList()), set.getPrice());
//			}
		}
		System.out.format("+-----+--------------------------------------------------------------+----------+%n");
	}
	
	public static void updateMenuItem() {
		// load menu from text file. read file
		// clear all current menu items to load from file
//		for(int i = 0;i<counter;i++ ) {
//			menuitem[i] = null;
//		}
		boolean itemloaded = false;
		boolean setloaded = false;
		
		try {
		      File myObj = new File("data", "Menu.txt");
		      Scanner myReader = new Scanner(myObj);
		      myReader.nextLine(); 
		      while (myReader.hasNextLine()) {
		          String data = myReader.nextLine();
		          String[] arrLinedata = data.split(",", 7);

		          for(int i=0; i< menuitem.size(); i++) {
		        	  if(menuitem.get(i).getName().equals(arrLinedata[2]) ) {
		        		  itemloaded = true;
		        	  }
		          }
		          if(itemloaded == false) {
			          menuitem.add(new MenuItem(arrLinedata[1], arrLinedata[2], arrLinedata[3], Double.parseDouble(arrLinedata[4])));
			          counter += 1;
		          }

		      }
		      myReader.close();
		      System.out.print("\nMenu was loaded from Menu Item text file\n");
	    } catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }
		
		/// load set menu from text file
	 	

		
//		for(int i = 0;i<100;i++ ) {
//			setpackage[i] = null;
//		}
		setpackage.removeAll(setpackage);
		try {
		      File myObj = new File("data", "SetMenu.txt");
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		    	  ArrayList<String> setList = new ArrayList<String>();
		          String data = myReader.nextLine();
		          String[] arrLinedata = data.split(",", 100);
		          for(int i = 1 ; i < arrLinedata.length-1; i++) {        	  
		        	  setList.add(arrLinedata[i]);
		          }
	        	  for(int i=0; i< setpackage.size(); i++) {
	        		  if(setpackage.get(i).getIndex() == Integer.parseInt(arrLinedata[0])) {
	        			  setloaded = true;					          
	        		  }
	        	  }
	        	  if(setloaded == false) {
		        	  setpackage.add(new SetPackage(Integer.parseInt(arrLinedata[0]), setList, Double.parseDouble(arrLinedata[arrLinedata.length-1])));
			          setIndexCounter += 1; /// here have a setpackages already
	        	  }
		      }
		      myReader.close();
		      System.out.print("\nMenu was loaded from Set Menu Item text file\n");
	    } catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }
	}
	
	public static void convertMenuData() {
		// Current menu to text file. 
		
		int index = 1;
		try {
			  File myObj = new File("data", "Menu.txt");
		      FileWriter myWriter = new FileWriter(myObj);
		      myWriter.write("--- Saved Menu --- \n");
		      
				for(int i = 0;i<menuitem.size();i++ ) {
//					if(menuitem[i] != null) {
						myWriter.write(String.valueOf(index));
						myWriter.write("),");
						myWriter.write(menuitem.get(i).getItemType()); /// HERE TO GET METHODS TO PRINT SHIT.
						myWriter.write(",");
						myWriter.write(menuitem.get(i).getName()); /// HERE TO GET METHODS TO PRINT SHIT.
						myWriter.write(",");
						myWriter.write(menuitem.get(i).getDescription()); /// HERE TO GET METHODS TO PRINT SHIT.
						myWriter.write(",");
						myWriter.write(String.valueOf(menuitem.get(i).getPrice())); /// HERE TO GET METHODS TO PRINT SHIT.
						myWriter.write("\n");
						index++;
//					}
				}
		      
		      myWriter.close();
		      System.out.println("\nSuccessfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}
	
	public static void convertSetMenuData() {
		// Current menu to text file. 
		
		int index = 1;
		try {
			  File myObj = new File("data", "SetMenu.txt");
		      FileWriter myWriter = new FileWriter(myObj);
		      
				for(int i = 0;i<setpackage.size();i++ ) {
//					if(setpackage[i] != null) {
						myWriter.write(String.valueOf(index));
						myWriter.write(",");
//						myWriter.write(setpackage.get(i).getItemType()); /// HERE TO GET METHODS TO PRINT SHIT.
//						myWriter.write(",");
						for(int l = 0; l < setpackage.get(i).getSetList().size();l++) {
							myWriter.write(setpackage.get(i).getSetList().get(l));
							myWriter.write(",");
						}
						myWriter.write(String.valueOf(setpackage.get(i).getPrice())); /// HERE TO GET METHODS TO PRINT SHIT.
						myWriter.write("\n");
						index++;
//					}
				}
		      
		      myWriter.close();
		      System.out.println("\nSuccessfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}
	

	
}