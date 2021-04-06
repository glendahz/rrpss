import java.util.Scanner;

public class MainMenuUI {
	
	static Scanner sc = new Scanner(System.in);
	private MainController mainCtrl;
	
	public MainMenuUI() {
	}
	
	// take in MainController
	public void setMainController(MainController mainCtrl) {
		this.mainCtrl = mainCtrl;
	}
	
	// display menu
	public void displayMenu() {
		int choice;
		System.out.println("Welcome to RRPSS\n");
		do {
			//print menu options
			System.out.println("**************************************************************************************************");
			System.out.println("1. Access Staff Menu\n"
					+ "2. Access Table Menu\n"
					+ "3. Access Menu\n"
					+ "4. Access Orders\n"
					+ "5. Access Reservation\n"
					+ "6. Access Invoices\n"
					+ "7. Access SalesReport\n"
					+ "8. Quit.");
			System.out.println("**************************************************************************************************");
			System.out.println("\nPlease choose an option from the menu:");
			choice = sc.nextInt();
		} while (mainCtrl.chooseFunction(choice) == -1); // pass choice to MainController
													 	// loop if invalid input return -1
	}
}
