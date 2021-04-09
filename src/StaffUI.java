import java.util.Scanner;
import java.util.InputMismatchException;

public class StaffUI {
	
	static Scanner sc = new Scanner(System.in);
	private int choice = 0;
	private StaffCtrl ctrl;
	
	public void getStaffController(StaffCtrl ctrl) {
		this.ctrl = ctrl;
	}
	
	public void displayOptions() {
		while (choice != 4) {
			Options();
			UserInput();
			MethodSelection(choice);
		}
	}
	
	public void Options() {
		
		System.out.println("-----Staff-----");
		System.out.println("View Current Staff : 1");
		System.out.println("Add Staff : 2");
		System.out.println("Remove Staff : 3");
		System.out.println("Exit : 4");
		
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
