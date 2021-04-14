package SalesReport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Order.OrderInvoice;
import util.Controller;
import util.UI;

/**
 * To display data related to Sales Report.
 * @author Wai Yar Aung
 *
 */
public class SalesReportUI extends UI {

	/**
	 * The SalesReport model to display
	 */
	private SalesReport model;
	
	/**
	 * The SalesReportController to interface with the model.
	 */
	private SalesReportController controller;
	
	/**
	 * A System.in scanner to take in user inputs.
	 * Static since reused and not closed as other parts of the program uses it.
	 */
	private static Scanner sc = new Scanner(System.in);

	@Override
	public void setController(Controller ctrl) {
		this.controller = (SalesReportController) ctrl;
	}

	@Override
	public void displayOptions() {
		System.out.println("\n======== SalesReport ========");
		System.out.println("1) Print sales by day");
		System.out.println("2) Print sales by month");
		System.out.println("0) Exit\n");

		int input = 0;
		while (true) {
			System.out.print("Choice: ");
			try {
				input = sc.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid choice.");
				sc.next(); // Consume input
			}
		}

		while (input < 0 || input > 2) {
			System.out.println("Invalid option! Try again.");
			System.out.print("Input: ");
			input = sc.nextInt();
		}

		if (input == 0) {
			return;
		}

		String sessionType;
		LocalDate date;
		if (input == 1) {
			sessionType = "DAY";
			while (true) {
				System.out.print("Enter date(yyyy-mm-dd): ");

				try {
					date = LocalDate.parse(sc.next());
					break;
				} catch (DateTimeParseException e) {
					System.out.println(e.getParsedString() + " is not a valid format.");
				}
			}

		} else {
			sessionType = "MONTH";
			while (true) {
				System.out.print("Enter month(yyyy-mm): ");

				try {
					// Set date to start date of input month;
					date = YearMonth.parse(sc.next()).atDay(1);
					break;
				} catch (DateTimeParseException e) {
					System.out.println(e.getParsedString() + " is not a valid format.");
				}
			}
		}

		try {
			this.model = new SalesReport();
			this.model.setType(sessionType);
			this.model.setStartDate(date);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		controller.printReport(this.model, this);
	}

	/**
	 * Called when the model's type == "MONTH".
	 * Prints a daily total for the entire month selected.
	 * @param dailyTotals This HashMap has the date for key and total for value.
	 * @param total The session's total
	 */
	public void printByMonth(HashMap<Integer, Float> dailyTotals, float total) {
		String tableFormat = "| %-7s | %9.2f |%n";

		System.out.format("+---------+-----------+%n");
		System.out.format("| Day     | Amount($) |%n");
		System.out.format("+---------+-----------+%n");

		dailyTotals.forEach((day, amount) -> {
			System.out.format(tableFormat, day, amount);
		});

		System.out.format("+---------------------+%n");
		System.out.format("| Total   | %9.2f |%n", total);
		System.out.format("+---------+-----------+%n");

		System.out.println();
	}

	/**
	 * Called when the model's type == "DAY".
	 * Prints the invoices for the entire day.
	 * Each invoice will have their time, price and orders printed.
	 * @param invoices The invoices for the day
	 * @param total The session's total
	 */
	public void printByDay(List<OrderInvoice> invoices, float total) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

		String tableFormat = "| %-8s | %8.2f | %56s |%n";

		System.out.format("+----------+----------+----------------------------------------------------------+%n");
		System.out.format("| Time     | Price($) |                                                   Orders |%n");
		System.out.format("+----------+----------+----------------------------------------------------------+%n");

		invoices.forEach(invoice -> {
			LocalDateTime ts = invoice.getTimestamp();
			String[][] orders = invoice.getOrder().getAllItemsArr();
			String orderStr = "";
			for (String[] item : orders) {
				// Display as Number x Name
				// (e.g. 1 x Chicken)
				orderStr = orderStr.concat(String.format("%s x %s, ", item[0], item[1]));
			}
			System.out.format(tableFormat, ts.format(dtf), invoice.getTotalPrice(), orderStr);
		});

		System.out.format("+----------+----------+----------------------------------------------+-----------+%n");
		System.out.format("| Total                                                              | %9.2f |%n", total);
		System.out.format("+--------------------------------------------------------------------+-----------+%n");

		System.out.println();
	}
}
