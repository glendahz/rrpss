import java.util.Scanner;

public class MainUI {

	private MainController mainController;

	// take in MainController
	public void setMainController(MainController mainCtrl) {
		this.mainController = mainCtrl;
	}

	// display menu
	public void displayMenu(Scanner sc) {
		int choice;
		System.out.println("Welcome to RRPSS\n");

		while (true) {
			// print menu options
			System.out.println(
					"**************************************************************************************************");
			System.out.println("1. Access Staff Menu\n" + "2. Access Table\n" + "3. Access Menu\n"
					+ "4. Access Orders\n" + "5. Access Reservation\n" + "6. Access Invoices\n"
					+ "7. Access SalesReport\n" + "8. Quit.");
			System.out.println(
					"**************************************************************************************************");
			System.out.print("\nPlease choose an option from the menu: ");
			choice = sc.nextInt();

			if (choice < 0 || choice > 9)
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
