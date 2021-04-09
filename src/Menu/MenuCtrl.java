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
	
	static MenuItem[] menuitem = new MenuItem[100];
 	static SetPackage[] setpackage = new SetPackage[100];

 	///
	public static boolean checkItemName(String NameCheck) {
		updateMenuItem();
		for(int i = 0; i< counter; i++) {
//			System.out.print(menuitem[i].getName());
			if(menuitem[i] != null) {
				if(menuitem[i].getName().equals(NameCheck)) {
					
					return true;
				}
			}
		}
		return false;
	}
	
	public static double getItemPrice(String NameOfItem) {
		updateMenuItem();
		for(int i = 0; i< counter; i++) {
			if(menuitem[i] != null) {
				if(menuitem[i].getName().equals(NameOfItem)) {
				
					return menuitem[i].getPrice();
				}
			}
		}
		System.out.print("price not found.. returned 0");
		return 0;
	}
	
	public static boolean checkSetItemIndex(int IndexOfSet) {
		updateMenuItem();
		for(int i = 0; i< setIndexCounter; i++) {
//			System.out.print(menuitem[i].getName());
			if(setpackage[i] != null) {
				if(setpackage[i].getIndex() == IndexOfSet) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static double getSetItemPrice(int IndexOfSet) {
		updateMenuItem();
		for(int i = 0; i< setIndexCounter; i++) {
			if(setpackage[i] != null) {
				if(setpackage[i].getIndex() == IndexOfSet) {
					return setpackage[i].getPrice();
				}
			}
		}
		return 0;
	}
 	///
	
	public MenuItem[] createMenuItem(){
		// This function will Create item object
//		updateMenuItem();
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
		    	ItemPrice = scan.nextDouble(); 
		        validInput = true;
		    } catch(InputMismatchException e) {
		        System.out.println("Please enter an number amount!");
		        scan.next();
		    }
		}
		
		scan.close();
		
		menuitem[counter] = new MenuItem(ItemType, ItemName, ItemDesc, ItemPrice);
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
	      Scanner scan = new Scanner(System.in);
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
		setpackage[setIndexCounter] = new SetPackage(setIndexCounter, setLists, SetPrice);
        setIndexCounter += 1; /// here have a setpackages already
        convertSetMenuData();
		
        scan.close();
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
				System.out.print(menuitem[i].getItemType()); /// HERE TO GET METHODS TO PRINT SHIT.
				System.out.print(" ");
				System.out.print(menuitem[i].getName());
				System.out.print(" ");
				System.out.print(menuitem[i].getPrice());
				System.out.print(" ");
				System.out.println(menuitem[i].getDescription());
				index++;
			}
		}
		
		System.out.print("Please enter Item name to DELETE : ");
		String toDelete = scan.nextLine();
		
		for(int j = 0;j<counter;j++ ) {
			if(menuitem[j] != null) {
				if(menuitem[j].getName().equals(toDelete)) {
					menuitem[j] = null;
				}
			}
			
		}
		convertMenuData();
		
		scan.close();
	}
	
	public static void removeSetItem() {
		Scanner scan = new Scanner(System.in);
		System.out.print("Please enter Item set Index to DELETE : ");
		int toDelete = scan.nextInt();
		
		for(int j = 0;j<100;j++ ) {
			if(setpackage[j] != null) {
				if(setpackage[j].getIndex() == toDelete) {
					setpackage[j] = null;
					
				}
			}
			
		}
		convertSetMenuData();
		scan.close();
	}
	
	public static void viewMenu() {
		// just to show menu
		updateMenuItem();
		int index = 1;
		int setindex =1;
		System.out.println("\n --- Current Menu --- ");
		
		for(int i = 0;i<counter;i++ ) {
			if(menuitem[i] != null) {
				System.out.print(index);
				System.out.print(")  ");
				System.out.print(menuitem[i].getItemType()); /// HERE TO GET METHODS TO PRINT SHIT.
				System.out.print(" ");
				System.out.print(menuitem[i].getName());
				System.out.print(" ");
				System.out.print(menuitem[i].getPrice());
				System.out.print(" ");
				System.out.println(menuitem[i].getDescription());
				index++;
			}
		}
		System.out.println("\n --- Current Set Menu --- ");
		for(int j = 0; j<setIndexCounter; j++) {
			if(setpackage[j] != null) {
				System.out.print(setindex + ") ");
				setindex += 1;
				System.out.print(setpackage[j].getItemType());
				for(int l = 0; l < setpackage[j].getSetList().size();l++) {
					System.out.print(" ");
					System.out.print(setpackage[j].getSetList().get(l));
				}
				System.out.print(" ");
				System.out.print(setpackage[j].getPrice());
				System.out.print(" \n");
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
		      File myObj = new File("data", "Menu.txt");
		      Scanner myReader = new Scanner(myObj);
		      myReader.nextLine(); 
		      while (myReader.hasNextLine()) {
		          String data = myReader.nextLine();
		          String[] arrLinedata = data.split(",", 5);
		          menuitem[counter] = new MenuItem(arrLinedata[1], arrLinedata[2], arrLinedata[3], Double.parseDouble(arrLinedata[4]));
		          counter += 1;
		      }
		      myReader.close();
		      System.out.print("\nMenu was loaded from Menu Item text file\n");
	    } catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }
		
		/// load set menu from text file
	 	

		
		for(int i = 0;i<100;i++ ) {
			setpackage[i] = null;
		}
		
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

		          setpackage[setIndexCounter] = new SetPackage(Integer.parseInt(arrLinedata[0]), setList, Double.parseDouble(arrLinedata[arrLinedata.length-1]));
		          setIndexCounter += 1; /// here have a setpackages already
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
		      FileWriter myWriter = new FileWriter("../Data/Menu.txt");
		      myWriter.write("--- Saved Menu --- \n");
		      
				for(int i = 0;i<counter;i++ ) {
					if(menuitem[i] != null) {
						myWriter.write(String.valueOf(index));
						myWriter.write("),");
						myWriter.write(menuitem[i].getItemType()); /// HERE TO GET METHODS TO PRINT SHIT.
						myWriter.write(",");
						myWriter.write(menuitem[i].getName()); /// HERE TO GET METHODS TO PRINT SHIT.
						myWriter.write(",");
						myWriter.write(menuitem[i].getDescription()); /// HERE TO GET METHODS TO PRINT SHIT.
						myWriter.write(",");
						myWriter.write(String.valueOf(menuitem[i].getPrice())); /// HERE TO GET METHODS TO PRINT SHIT.
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
	
	public static void convertSetMenuData() {
		// Current menu to text file. 
		
		int index = 1;
		try {
		      FileWriter myWriter = new FileWriter("../Data/SetMenu.txt");
		      
				for(int i = 0;i<setIndexCounter;i++ ) {
					if(setpackage[i] != null) {
						myWriter.write(String.valueOf(index));
						myWriter.write(",");
						myWriter.write(setpackage[i].getItemType()); /// HERE TO GET METHODS TO PRINT SHIT.
						myWriter.write(",");
						for(int l = 0; l < setpackage[i].getSetList().size();l++) {
							myWriter.write(setpackage[i].getSetList().get(l));
							myWriter.write(",");
						}
						myWriter.write(String.valueOf(setpackage[i].getPrice())); /// HERE TO GET METHODS TO PRINT SHIT.
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
