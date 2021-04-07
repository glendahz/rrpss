import java.util.InputMismatchException;
import java.util.Scanner;

public class TableUI {
	
	static Scanner sc = new Scanner(System.in);
	private int choice = 0;
	private TableCtrl ctrl;
	
	public void getTableController(TableCtrl ctrl) {
		this.ctrl = ctrl;
	}
	
	public void displayOptions() {
		while (choice != 11) {
			Options();
			UserInput();
			MethodSelection(choice);
		}
	}
	
	public void Options() {
		
		System.out.println("-----------Tables-----------");
		System.out.println("Create Multiple Tables : 1");
		System.out.println("Get Available Tables : 2");
		System.out.println("Get All Tables : 3");
		System.out.println("Add Table : 4");
		System.out.println("Remove Table : 5");
		System.out.println("Assign Table : 6");
		System.out.println("Reserve Table : 7");
		System.out.println("Vacate Table : 8");
		System.out.println("Get All Tables : 9");
		System.out.println("Get Table Status : 10");
		System.out.println("Exit : 11");
		
	}
	
	public void UserInput() {
		
		boolean validInput = false;
		
		System.out.print("Please enter your choice from 1 to 11: ");
		while (!validInput) {
			try {
				choice = sc.nextInt();
				if (1 <= choice && choice <= 11) {
					validInput = true;
				}
			} catch(InputMismatchException e) {
				System.out.print("Please enter a number from 1 to 11: ");
				choice = sc.nextInt();
			}
		}
		
		return ;
	}
	
	public void MethodSelection(int methodIndex) {
		
		switch(methodIndex) {
		
			case 2:
				ctrl.getAvailableTables();
				break;
			
			case 4:
				ctrl.addTable();
				break;
				
			case 5:
				ctrl.removeTable();
				break;
			
			case 6:
				ctrl.assignTable();
				break;
			
			case 7:
				ctrl.reserveTable();
				break;
			
			case 8:
				ctrl.vacateTable();
				break;
				
			case 9:
				ctrl.getAllTables();
				break;
				
			case 10:
				ctrl.getTableStatus();
				break;
				
			case 11:
				System.out.println("Menu Exited");
				break;	
			
			default:
				break;
			
		}
		
	}
	
}
