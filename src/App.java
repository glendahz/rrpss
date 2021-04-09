import java.util.Scanner;

public class App {

	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(System.in);
			// Display main menu
			MainUI ui = new MainUI();
			MainController mainController = new MainController();

			ui.setMainController(mainController);
			ui.displayMenu(sc);

			sc.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
