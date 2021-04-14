package Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import util.Controller;
import util.UI;

/**
 * A boundary class that interfaces with the application user for Menu related functions.
 * @author Craigdon Lee
 * @version 1.0
 *
 */
public class MenuUI extends UI {

	/**
	 * Choice of menu option
	 */
	static int choice = 0;
	/**
	 * When exit = 8 it will breakout and go back to main menu
	 */
	static int exit = 0;

	static Scanner scan = new Scanner(System.in);

	/**
	 *	Displays Menu's UI to choose each method
	 */
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

	/**
	 *	Sets the controller
	 */
	@Override
	public void setController(Controller ctrl) {
		// Do nothing
	}

	/**
	 * Gets user input for menu choise and makes sure input corresponds to a valid option.
	 * @return
	 */
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

	/**
	 * Displays main menu
	 */
	public static void MainMenu() {

		System.out.println("\n========= Menu =========");
		System.out.println("1) View Current Menu");
		System.out.println("2) Create Menu Item");
		System.out.println("3) Create Set Menu Item");
		System.out.println("4) Remove Menu Item");
		System.out.println("5) Remove Set Menu Item");
		System.out.println("6) Edit Menu Data");
		System.out.println("7) Convert Menu Data");
		System.out.println("8) Exit\n");

	}

	/**
	 * Switch case for each option/method available.
	 * @param methodIndex input option by user
	 */
	public static void MethodSelection(int methodIndex) {
		MenuCtrl ctrl = new MenuCtrl();
		switch (methodIndex) {

		case 1:
			MenuCtrl.viewMenu();
			break;

		case 2:
//			System.out.print("menu item = ");
//			System.out.print(array[0].itemType);
			ctrl.createMenuItem();
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
			MenuCtrl.editMenuItem(); // change here
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
