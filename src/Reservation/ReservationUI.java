package Reservation;
import java.util.InputMismatchException;
import java.util.Scanner;

import util.Controller;
import util.UI;

public class ReservationUI extends UI {
	
	static Scanner sc = new Scanner(System.in);
	static int choice = 0;
	ReservationCtrl ctrl;
	static int disChoice = 0;
	static int rmvChoice = 0;
	
	@Override
	public void displayOptions() {
		while (choice != 4) {
			Options();
			UserInput();
			MethodSelection(choice);
		}
		choice = 0; // Reset
	}

	@Override
	public void setController(Controller ctrl) {
		this.ctrl = (ReservationCtrl) ctrl;
	}
	
	public static void Options() {
		
		System.out.println("\n-----Reservation-----");
		System.out.println("Make Reservation : 1");
		System.out.println("Display Reservation : 2");
		System.out.println("Remove Reservation : 3");
		System.out.println("Exit : 4");
		
	}
	
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
	
	public void MethodSelection(int methodIndex) {
		
		switch(methodIndex) {
		
			case 1:
				ctrl.makeReservation();
				break;

			case 2:
				while(disChoice!=4){
					disOptions();
					disUserInput();
					disMethodSelection(disChoice);
				}
				break;

			case 3:
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
	
	public static void disOptions() {
		System.out.println("\n-----Display Reservation Options-----");
		System.out.println("By Contact Number : 1");
		System.out.println("By Table ID : 2");
		System.out.println("Display All : 3");
		System.out.println("Exit : 4");
	}

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

	public static void rmvOptions(){
		System.out.println("\n-----Remove Reservation Options-----");
		System.out.println("By Contact Number : 1");
		System.out.println("By Table ID : 2");
		System.out.println("Exit : 3");
	}

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
