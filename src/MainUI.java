import java.util.InputMismatchException;
import java.util.Scanner;

import util.Controller;
import util.UI;

/**
 * Displays the options for the features of the application.
 * This is the landing/home page of the application.
 * @author Wai Yar Aung
 *
 */
public class MainUI extends UI {

	/**
	 * The {@code Controller} to handle inputs for the home page.
	 */
	private MainController mainController;
	
	/**
	 * A System.in scanner to take in user inputs.
	 * Static since reused and not closed as other parts of the program uses it.
	 */
	private static Scanner sc = new Scanner(System.in);

	@Override
	public void setController(Controller mainCtrl) {
		this.mainController = (MainController) mainCtrl;
	}
	
	@Override
	public void displayOptions() {
		int choice;
		System.out.println("Welcome to RRPSS\n");

		while (true) {
			// Print main options
			System.out.println();
			System.out.println("************************************");
			System.out.println("1. Access Staff Menu");
			System.out.println("2. Access Table");
			System.out.println("3. Access Menu");
			System.out.println("4. Access Orders");
			System.out.println("5. Access Reservation");
			System.out.println("6. Access Invoices");
			System.out.println("7. Access SalesReport");
			System.out.println("8. Quit.");
			System.out.println("*************************************");

			while (true) {
				System.out.print("\nOption: ");
				try {
					choice = sc.nextInt();
					break;
				} catch (InputMismatchException e) {
					System.out.println("Invalid choice. Please try again.");
					sc.next();
				}
			}

			if (choice < 1 || choice > 8)
				System.out.println("Invalid Choice!");
			else if (choice == 8) {
				System.out.println("Thank you for using RRPSS! Have a great day ahead!");
				break;
			} else {
				switch (choice) {
				case 1:
					mainController.displayStaffUI();
					break;

				case 2:
					mainController.displayTableUI();
					break;

				case 3:
					mainController.displayMenuUI();
					break;

				case 4:
					mainController.displayOrdersUI();
					break;

				case 5:
					mainController.displayReservationUI();
					break;

				case 6:
					mainController.displayInvoicesUI();
					break;

				case 7:
					mainController.displaySalesReportUI();
					break;

				default:
					break;

				}
			}
		}
	}
}
