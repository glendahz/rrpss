package Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import util.Controller;
import util.UI;

public class MenuUI extends UI {

	static int choice = 0;
	static int counter = 0;
	static int exit = 0;

	static Scanner scan = new Scanner(System.in);

	@Override
	public void displayOptions() {
		scan = new Scanner(System.in);
		while (exit != 8) {
			MainMenu();
			choice = menuChoice();
			MethodSelection(choice);
			exit = choice;
		}
	}

	@Override
	public void setController(Controller ctrl) {
		// Do nothing
	}

	public static int menuChoice() {
		int choice = 0;
		boolean validInput = false;

		System.out.print("Please enter your choice of 1-8 : ");
		while (!validInput) {
			try {
				choice = scan.nextInt();
				if (choice >= 1 && choice <= 8)
					validInput = true;
			} catch (InputMismatchException e) {
				System.out.print("Please enter a number within 1-8! :");
			}
		}

		return choice;
	}

	public static void MainMenu() {

		System.out.println("\n-----Menu-----");
		System.out.println("View Current Menu : 1");
		System.out.println("Create Menu Item : 2");
		System.out.println("Create Set Menu Item : 3");
		System.out.println("Remove Menu Item : 4");
		System.out.println("Remove Set Menu Item : 5");
		System.out.println("update Menu Item : 6");
		System.out.println("Convert Menu Data : 7");
		System.out.println("Exit : 8\n");

	}

	public static void MethodSelection(int methodIndex) {

		switch (methodIndex) {

		case 1:
			MenuCtrl.viewMenu();
			break;

		case 2:
//			System.out.print("menu item = ");
//			System.out.print(array[0].itemType);
			break;

		case 3:
			MenuCtrl.createSetMenuItem();
			break;

		case 4:
			MenuCtrl.removeMenuItem();
			break;

		case 5:
			MenuCtrl.removeSetItem();
			break;

		case 6:
			MenuCtrl.updateMenuItem();
			break;

		case 7:
			MenuCtrl.convertMenuData();
			break;

		case 8:
			System.out.println("\nMenu Exited\n");
			break;

		default:
			break;

		}

	}
}
