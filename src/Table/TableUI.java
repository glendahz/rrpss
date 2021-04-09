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
		while (choice != 11) {
			Options();
			UserInput();
			MethodSelection(choice);
		}
	}

	public void Options() {

		System.out.println("-----------Tables-----------");
		System.out.println("1) Create Multiple Tables");
		System.out.println("2) Get Available Tables");
//		System.out.println("3) Get All Tables");
		System.out.println("4) Add Table");
		System.out.println("5) Remove Table");
		System.out.println("6) Assign Table");
		System.out.println("7) Reserve Table");
		System.out.println("8) Vacate Table");
		System.out.println("9) Get All Tables");
		System.out.println("10) Get Table Status");
		System.out.println("11) Exit");

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
			} catch (InputMismatchException e) {
				System.out.print("Please enter a number from 1 to 11: ");
				choice = sc.nextInt();
			}
		}

		return;
	}

	public void MethodSelection(int methodIndex) {

		switch (methodIndex) {
		case 1:
			ctrl.createMultipleTables();
			break;

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
			ctrl.printAllTables();
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
