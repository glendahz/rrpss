package Staff;

import java.util.Scanner;
import java.util.InputMismatchException;

import util.*;

public class StaffUI extends UI {
	
	static Scanner sc = new Scanner(System.in);
	private int choice = 0;
	private StaffCtrl ctrl;
	
	@Override
	public void setController(Controller ctrl) {
		this.ctrl = (StaffCtrl) ctrl;
	}
	
	public void displayOptions() {
		while (choice != 4) {
			Options();
			UserInput();
			MethodSelection(choice);
		}
	}
	
	public void Options() {
		System.out.println("\n========Staff========");
		System.out.println("1. View Current Staff");
		System.out.println("2. Add Staff");
		System.out.println("3. Remove Staff");
		System.out.println("4. Exit\n");
		
	}
	
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
