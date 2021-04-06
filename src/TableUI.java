import java.util.InputMismatchException;
import java.util.Scanner;

public class TableUI {
	
	static Scanner sc = new Scanner(System.in);
	static int choice = 0;
	static TableCtrl ctrl = new TableCtrl();
	
	public static void main(String[] args) {
		
		while (choice != 7) {
			Options();
			UserInput();
			MethodSelection(choice);
		}
		
	}
	
	public static void Options() {
		
		System.out.println("-----Tables-----");
		System.out.println("Get Available Tables : 1");
		System.out.println("Add Table : 2");
		System.out.println("Remove Table : 3");
		System.out.println("Assign Table : 4");
		System.out.println("Reserve Table : 5");
		System.out.println("Vacate Table : 6");
		System.out.println("Get All Tables : 7");
		System.out.println("Exit : 8");
		
	}
	
	public static void UserInput() {
		
		boolean validInput = false;
		
		System.out.print("Please enter your choice from 1 to 8: ");
		while (!validInput) {
			try {
				choice = sc.nextInt();
				if (1 <= choice && choice <= 8) {
					validInput = true;
				}
			} catch(InputMismatchException e) {
				System.out.print("Please enter a number from 1 to 8: ");
				choice = sc.nextInt();
			}
		}
		
		return ;
	}
	
	public static void MethodSelection(int methodIndex) {
		
		switch(methodIndex) {
		
			case 1:
				ctrl.getAvailableTables();
				break;
			
			case 2:
				ctrl.addTable();
				break;
				
			case 3:
				ctrl.removeTable();
				break;
			
			case 4:
				ctrl.assignTable();
				break;
			
			case 5:
				ctrl.reserveTable();
				break;
			
			case 6:
				ctrl.vacateTable();
				break;
				
			case 7:
				ctrl.getAllTables();
				break;
				
			case 8:
				System.out.println("Menu Exited");
				break;	
			
			default:
				break;
			
		}
		
	}
	
}
