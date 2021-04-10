package Order;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Table.Table.TableStatus;
import util.Controller;
import util.UI;

public class InvoiceUI extends UI {
	private static InvoiceCtrl invoiceCtrl;
		
	@Override
	public void setController(Controller ctrl) {
		InvoiceUI.invoiceCtrl = (InvoiceCtrl) ctrl;
	}
	
	public void displayOptions(Scanner sc) {
		boolean run=true;
		int choice;
		while(run) {
			System.out.println("-----Invoice-----\n"
					+ "Select option:\n" 
					+ "1. Print invoice\n" 
					+ "2. Return to main menu");
			try {
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
				printInvoiceUI(sc);
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
	
	public void displayOptions() {
		Scanner sc = new Scanner(System.in);
		displayOptions(sc);
	}

	private static void printInvoiceUI(Scanner sc) {
		int orderID = OrderUI.getTableIDUI(sc, TableStatus.OCCUPIED);
		
		// get payment method
		PaymentMethod payMthd = getPaymentMethodUI(sc);
		
		// create invoice
		try {
			System.out.println(invoiceCtrl.createInvoice(orderID, payMthd) + "\n");
		} catch (Exception e) {
			System.out.println("Error: unable to create invoice\n"
					+ e.getMessage() + "\n");
		}
	}
	
	private static PaymentMethod getPaymentMethodUI(Scanner sc) {
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
						+ "Please enter an integer between 1 to 6\n");
				continue;
			}
			// break out of loop
			run = false;
		}
		return payMthd;
	}
}
