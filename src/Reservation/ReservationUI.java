package Reservation;
import java.util.InputMismatchException;
import java.util.Scanner;

import util.Controller;
import util.UI;

/**
 * A boundary class that interfaces with the user for reservation related functions.
 * @author Hoang Huu Quoc Huy
 */

public class ReservationUI extends UI {
	
	/**
	 * The {@code Scanner} object to take in input from the application user.
	 */
	static Scanner sc = new Scanner(System.in);

	/**
	 * Variables used to store user input.
	 */
	/**
	 * Main Options
	 */
	static int choice = 0;
	/**
	 * Display Options
	 */
	static int disChoice = 0;
	/**
	 * Remove Options
	 */
	static int rmvChoice = 0;
	
	/**
	 * The control object that mediates between this boundary class and {@code Reservation} entity objects.
	 */	
	ReservationCtrl ctrl;

	/**
	 * Sets the control object that mediates between this boundary class and {@code Order} entity objects.
	 */
	@Override
	public void setController(Controller ctrl) {
		this.ctrl = (ReservationCtrl) ctrl;
	}

	/**
	 * Loop that allows users to enter their choices
	 */
	@Override
	public void displayOptions() {
		while (choice != 4) {
			Options();
			UserInput();
			MethodSelection(choice);
		}
		choice = 0; // Reset
	}

	/**
	 * Prints the available Main Options to the user at the current UI page
	 */
	public static void Options() {
		
		System.out.println("\n-----Reservation-----");
		System.out.println("Make Reservation : 1");
		System.out.println("Display Reservation : 2");
		System.out.println("Remove Reservation : 3");
		System.out.println("Exit : 4");
		
	}
	
	/**
	 * Get user input between 1 to 4
	 */
	public static void UserInput() {
		
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
	}

	/**
	 * Get the reservationCtrl to execute a method that corresponds to the user's input
	 * @param methodIndex
	 */
	public void MethodSelection(int methodIndex) {
		
		switch(methodIndex) {
		
			case 1:
				ctrl.makeReservation();
				break;

			case 2:
				disChoice = 0;			//reset
				while(disChoice!=4){
					disOptions();
					disUserInput();
					disMethodSelection(disChoice);
				}
				break;

			case 3:
				rmvChoice = 0;			//reset
				while(rmvChoice!=3){
					rmvOptions();
					rmvUserInput();
					rmvMethodSelection(rmvChoice);
				}
				break;
			
			case 4:
				System.out.println("Reservation Exited");
				break;
			
		}
		
	}

	/**
	 * Prints the available Display Options to the user at the current UI page
	 */
	public static void disOptions() {
		System.out.println("\n-----Display Reservation Options-----");
		System.out.println("By Contact Number : 1");
		System.out.println("By Table ID : 2");
		System.out.println("Display All : 3");
		System.out.println("Exit : 4");
	}
	
	/**
	 * Get user input between 1 to 4
	 */
	public static void disUserInput(){
		boolean validInput = false;
		
		System.out.print("Please enter your choice from 1 to 4: ");
		while (!validInput) {
			try {
				disChoice = sc.nextInt();
				if (1 <= disChoice && disChoice <= 4) {
					validInput = true;
				}
			} catch(InputMismatchException e) {
				System.out.print("Please enter a number from 1 to 4: ");
				disChoice = sc.nextInt();
			}
		}
	}

	/**
	 * Get the reservationCtrl to execute a method that corresponds to the user's input
	 * @param methodIndex
	 */
	public void disMethodSelection(int methodIndex){
		switch (methodIndex) {
			case 1:
				ctrl.displayByContactNumber();
				break;
		
			case 2:
				ctrl.displayByTableID();
				break;

			case 3:
				ctrl.displayAllReservation();
				break;

			case 4:
				System.out.println("Display Options Exited");
				break;
		}
	}

	/**
	 * Prints the available Remove Options to the user at the current UI page
	 */
	public static void rmvOptions(){
		System.out.println("\n-----Remove Reservation Options-----");
		System.out.println("By Contact Number : 1");
		System.out.println("By Table ID : 2");
		System.out.println("Exit : 3");
	}
	
	/**
	 * Get user input between 1 to 3
	 */
	public static void rmvUserInput(){
		boolean validInput = false;
		
		System.out.print("Please enter your choice from 1 to 3: ");
		while (!validInput) {
			try {
				rmvChoice = sc.nextInt();
				if (1 <= rmvChoice && rmvChoice <= 3) {
					validInput = true;
				}
			} catch(InputMismatchException e) {
				System.out.print("Please enter a number from 1 to 3: ");
				rmvChoice = sc.nextInt();
			}
		}
	}

	/**
	 * Get the reservationCtrl to execute a method that corresponds to the user's input
	 * @param methodIndex
	 */
	public void rmvMethodSelection(int methodIndex){
		switch (methodIndex) {
			case 1:
				ctrl.removeReservationByContact();
				break;
			
			case 2:
				ctrl.removeReservationByTableID();
				break;

			case 3:
				System.out.println("Remove Options Exited");
				break;
		}
	}
}
