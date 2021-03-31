import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;  // Import the File class

public class MenuUI {

	static int choise = 0;
	static int counter = 0;
	static int exit = 0;
	
	public static void main(String[] args){
		while(exit != 8) {
			MainMenu();
	        choise = MenuChoise();
	        MethodSelection(choise);
	        exit = choise;
		}
	}
	
	public static int MenuChoise() {
		int choise = 0;
		boolean validInput = false;

		Scanner scan = new Scanner(System.in);

        System.out.print("Please Enter your choise of 1-8 : ");
        while(!validInput) {
            try {
            	choise = scan.nextInt();
                validInput = true;
            } catch(InputMismatchException e) {
                System.out.print("Please enter an 1-8! :");
                scan.next();
            }
        }
//        System.out.print(choise);
		while(true) {        
	        if(!(choise < 9 && choise > 0)) {
	        	System.out.print("Invalid entery Input (1-8) only\nPlease Enter your choise of 1-8 : ");
	            choise = scan.nextInt();
//	            System.out.printf("input = %d\n" , choise);
	        } 	
	        
	        else {
	        	return choise;
	        }
	        
		}

	}
	public static void MainMenu() {
		
		System.out.println("\n-----Menu-----");
		System.out.println("View Current Menu : 1");
		System.out.println("Create Menu Item : 2");
		System.out.println("Create Set Menu Item : 3");
		System.out.println("Remove Menu Item : 4");
		System.out.println("Remove Set Menu Item : 5");
		System.out.println("update Menu Iem : 6");
		System.out.println("Convert Menu Data : 7");
		System.out.println("Exit : 8\n");
		
	}
	
	public static void MethodSelection(int methodIndex) {
		MenuCtrl ctrl = new MenuCtrl();
		
		switch(methodIndex) {
		
		case 1:
			ctrl.viewMenu();
			break;
		
		case 2:
			MenuItem[] array = ctrl.createMenuItem();
//			System.out.print("menu item = ");
//			System.out.print(array[0].itemType);
			break;
			
		case 3:
			ctrl.createSetMenuItem();
			break;
			
		case 4:
			ctrl.removeMenuItem();
			break;
			
		case 5:
			ctrl.removeSetItem();
			break;
			
		case 6:
			ctrl.updateMenuItem();
			break;
			
		case 7:
			ctrl.convertMenuData();
			break;
		
		case 8:
			System.out.println("\nMenu Exited\n");
			break;
			
		default:
			break;
			
		}
		
	}
	
	
	
//	public static void initiateItem(String ItemType, String) {
//		
//	
//	}
//	
}
