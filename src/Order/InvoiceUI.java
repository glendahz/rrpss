package Order;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Table.Table.TableStatus;
import util.Controller;
import util.UI;

/**
 * A boundary class that interfaces with the application user for invoice related functions.
 * @author Glenda Hong Zixuan
 */
public class InvoiceUI extends UI {
	
	/**
	 * The control object that mediates between this boundary class and {@code OrderInvoice} entity objects.
	 */
	private static InvoiceCtrl invoiceCtrl;
	
	/**
	 * The {@code Scanner} object to take in input from the application user.
	 */
	private static Scanner sc = new Scanner(System.in);
	
	/**
	 * Sets the control object that mediates between this boundary class and {@code OrderInvoice} entity objects.
	 */
	@Override
	public void setController(Controller ctrl) {
		InvoiceUI.invoiceCtrl = (InvoiceCtrl) ctrl;
	}
	
	/**
	 * Displays the invoice related functions that can be performed 
	 * and gets a choice from the user about which function to perform. 
	 */
	@Override
	public void displayOptions() {
		boolean run=true;
		int choice;
		while(run) {
			System.out.println("\n====== Invoice ======\n"
					+ "1) Print invoice\n" 
					+ "2) Return to main menu");
			try {
				System.out.print("Select Option: ");
				choice = sc.nextInt();
				sc.nextLine(); // flush System.in
			}catch(NoSuchElementException e) {
				sc.nextLine(); // flush System.in
				System.out.println("\nError: entry was not a valid choice\n"
						+ "Please enter an integer!\n");
				continue;
			}
			System.out.println();
			

			switch(choice) {
			case 1:
				printInvoiceUI();
				break;
			case 2:
				run=false;
				System.out.println("Returning to main menu...\n");
				break;
			default:
				System.out.println("Error: '" + choice + "' is not a valid choice\n"
						+ "Please enter an integer between 1 to 2\n");
				break;
			}
		}
	}

	/**
	 * Gets a table ID from the user and prints the corresponding bill invoice.
	 * The table ID must correspond to a {@code Table} object that is set to {@code TableStatus.OCCUPIED}.
	 */
	private static void printInvoiceUI() {
		int tableID = getTableIDUI(TableStatus.OCCUPIED);
		
		// get payment method
		PaymentMethod payMthd = getPaymentMethodUI();
		
		// create invoice
		try {
			System.out.println(invoiceCtrl.createInvoice(tableID, payMthd) + "\n");
		} catch (Exception e) {
			System.out.println("Error: unable to create invoice\n"
					+ e.getMessage() + "\n");
		}
	}
	
	/**
	 * Gets a payment method.
	 * @return the payment method retrieved from the user.
	 */
	private static PaymentMethod getPaymentMethodUI() {
		boolean run = true;
		int choice;
		PaymentMethod payMthd = null;
		while (run) {
			System.out.println("Select payment method:\n"
					+ "1. Cash\n"
					+ "2. Credit Card\n"
					+ "3. NETS\n"
					+ "4. NETS FlashPay\n"
					+ "5. DBS Paylah!\n"
					+ "6. AliPay");
			// check if entry is an integer
			try {
				choice = sc.nextInt();
				sc.nextLine(); // flush System.in
				System.out.println();
			} catch (NoSuchElementException e) {
				sc.nextLine(); // flush System.in
				System.out.println("\nError: entry was not a valid choice\n"
						+ "Please enter an integer!\n");
				continue;
			}
			// check if entry is a valid choice
			switch(choice) {
			case 1:
				payMthd = PaymentMethod.CASH;
				break;
			case 2:
				payMthd = PaymentMethod.CREDIT_CARD;
				break;
			case 3:
				payMthd = PaymentMethod.NETS;
				break;
			case 4:
				payMthd = PaymentMethod.NETS_FLASHPAY;
				break;
			case 5:
				payMthd = PaymentMethod.PAYLAH;
				break;
			case 6:
				payMthd = PaymentMethod.ALIPAY;
				break;
			default:
				System.out.println("Error: '" + choice + "' is not a valid choice\n"
						+ "Please enter an integer between 1 to 2\n");
				continue;
			}
			// break out of loop
			run = false;
		}
		return payMthd;
	}
	
	/**
	 * Gets the table ID of a specific {@code Table} object that is set to a specific {@code TableStatus}.
	 * @param status	The {@code TableStatus} value that the target {@code Table} object should be set to.
	 * @return the table ID retrieved from the user.
	 */
	private static int getTableIDUI(TableStatus status) {
		boolean run = true;
		int tableID = -1;
		while(run) {
			System.out.println("Enter table ID: ");
			// check if entry is an integer
			try {
				tableID = sc.nextInt();
				sc.nextLine(); // flush System.in
				System.out.println();
			} catch(NoSuchElementException e) {
				System.out.println("\nError: entry was not a valid table ID\n"
						+ "Please enter an integer!\n");
				sc.nextLine(); // flush System.in
				continue;
			}
			if (invoiceCtrl.validTableID(tableID, status)) run = false;
			else System.out.println("Error: entry was not a valid table ID\n");
		}
		return tableID;
	}
}
