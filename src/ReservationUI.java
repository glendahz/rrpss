import java.util.InputMismatchException;
import java.util.Scanner;

public class ReservationUI {
	
	static Scanner sc = new Scanner(System.in);
	static int choice = 0;
	static ResservationCtrl ctrl = new ReservationCtrl();
	
	public static void main(String[] args) {
		
		while (choice != 4) {
			Options();
			UserInput();
			MethodSelection(choice);
		}
		
	}
	
	public static void Options() {
		
		System.out.println("-----Reservation-----");
		System.out.println("Add Reservation : 1");
		System.out.println("Remove Reservation : 2");
		System.out.println("Get All Reservation : 3");
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
		
		return ;
	}
	
	public static void MethodSelection(int methodIndex) {
		
		switch(methodIndex) {
		
			case 1:
				ctrl.makeReservation();
				break;
			
			case 2:
				ctrl.removeReservation();
				break;
				
			case 3:
				ctrl.getAllReservation();
				break;
			
			case 4:
				System.out.println("Menu Exited");
				break;	
			
			default:
				break;
			
		}
		
	}
	
}
