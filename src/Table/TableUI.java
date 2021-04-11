package Table;

import java.util.InputMismatchException;
import java.util.Scanner;

import util.Controller;
import util.UI;

public class TableUI extends UI {

	static Scanner sc = new Scanner(System.in);
	private int choice = 0;
	private TableCtrl ctrl;

	@Override
	public void setController(Controller ctrl) {
		this.ctrl = (TableCtrl) ctrl;
	}

	@Override
	public void displayOptions() {
		while (choice != 8) {
			Options();
			UserInput();
			MethodSelection(choice);
		}
	}

	public void Options() {

		System.out.println("-----------Tables-----------");
		System.out.println("Get All Available Tables : 1");
		System.out.println("Get All Available Tables Bigger Than Size : 2");
		System.out.println("Get All Tables : 3");
		System.out.println("Assign Table : 4");
		System.out.println("Reserve Table : 5");
		System.out.println("Vacate Table : 6");
		System.out.println("Get Table Status : 7");
		System.out.println("Exit : 8");


	}

	public void UserInput() {

		boolean validInput = false;

		System.out.print("Please enter your choice from 1 to 8: ");
		while (!validInput) {
			try {
				choice = sc.nextInt();
				if (1 <= choice && choice <= 8) {
					validInput = true;
				}
			} catch (InputMismatchException e) {
				System.out.print("Please enter a number from 1 to 8: ");
				choice = sc.nextInt();
			}
		}

		return;
	}

	public void MethodSelection(int methodIndex) {

		switch (methodIndex) {

		case 1:
			ctrl.getAvailableTables();
			break;

		case 2:
			ctrl.getAvaiTableIDsBySize();
			break;
			
		case 3:
			ctrl.printAllTables();
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
			ctrl.getTableStatus();
			break;

		case 8:
			System.out.println("Menu Exited");
			break;

		default:
			break;

		}

	}

}
