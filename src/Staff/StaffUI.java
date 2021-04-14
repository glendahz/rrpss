package Staff;

import java.util.Scanner;
import java.util.InputMismatchException;

import util.*;

/**
 * Boundary class to get user input to control system action for Staff
 * @author david
 * @version 1.0
 * @since 2021-04-07
 */

public class StaffUI extends UI {
	
	/**
	 * Scanner for getting inputs from the user
	 */
	static Scanner sc = new Scanner(System.in);
	/**
	 * Variable used to store user input
	 */
	private int choice = 0;
	/**
	 * Staff Ctrl that deals with retrieving and storing staff information
	 */
	private StaffCtrl ctrl;
	
	/**
	 * Assigns the staff controller in the class
	 */
	@Override
	public void setController(Controller ctrl) {
		this.ctrl = (StaffCtrl) ctrl;
	}
	
	/**
	 * Loop that allows users to enter their choices
	 */
	public void displayOptions() {
		while (choice != 4) {
			Options();
			UserInput();
			MethodSelection(choice);
		}
	}
	
	/**
	 * Prints the available options to the user at the current UI page
	 */
	public void Options() {
		System.out.println("\n======= Staff =======");
		System.out.println("1. View Current Staff");
		System.out.println("2. Add Staff");
		System.out.println("3. Remove Staff");
		System.out.println("4. Exit\n");
		
	}
	
	/**
	 * Get user input between 1 to 4
	 */
	public void UserInput() {
		
		boolean validInput = false;
		
		System.out.print("Please enter your choice from 1 to 4: ");
		while (!validInput) {
			try {
				choice = sc.nextInt();
				if (1 <= choice && choice <= 4) {
					validInput = true;
				}
			} catch(InputMismatchException e) {
				System.out.print("Please enter a number from 1 to 4: ");
				choice = sc.nextInt();
			}
		}
		
		return ;
	}
	
	/**
	 * Get the staffCtrl to execute a method that corresponds to the user's input
	 * @param methodIndex
	 */
	public void MethodSelection(int methodIndex) {
		
		switch(methodIndex) {
		
			case 1:
				ctrl.viewStaffList();
				break;
			
			case 2:
				ctrl.addStaff();
				break;
				
			case 3:
				ctrl.removeStaff();
				break;
			
			case 4:
				System.out.println("Menu Exited");
				break;
				
			default:
				break;
			
		}
		
	}
}
