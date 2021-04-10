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

public class SalesReportUI extends UI {

	private SalesReport model;
	private SalesReportController controller;
	private static Scanner sc = new Scanner(System.in);

	public SalesReportUI() {
		this.model = new SalesReport();
	}

	@Override
	public void setController(Controller ctrl) {
		this.controller = (SalesReportController) ctrl;
	}

	public void displayOptions() {
		System.out.println("=================SalesReport=================");
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
			this.model.setType(sessionType);
			this.model.setStartDate(date);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		controller.printReport(this.model, this);
	}

	public void printByMonth(HashMap<Integer, Float> dailyTotals, float total) {
		String leftAlignFormat = "| %-7s | %9.2f |%n";

		System.out.format("+---------+-----------+%n");
		System.out.format("| Day     | Amount($) |%n");
		System.out.format("+---------+-----------+%n");

		dailyTotals.forEach((day, amount) -> {
			System.out.format(leftAlignFormat, day, amount);
		});

		System.out.format("+---------------------+%n");
		System.out.format("| Total   | %9.2f |%n", total);
		System.out.format("+---------+-----------+%n");

		System.out.println();
	}

	public void printByDay(List<OrderInvoice> invoices, float total) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

		String leftAlignFormat = "| %-8s | %8.2f | %56s |%n";

		System.out.format("+----------+----------+----------------------------------------------------------+%n");
		System.out.format("| Time     | Price($) |                                                   Orders |%n");
		System.out.format("+----------+----------+----------------------------------------------------------+%n");

		invoices.forEach(invoice -> {
			LocalDateTime ts = invoice.getTimestamp();
			String[][] orders = invoice.getOrder().getAllItemsArr();
			String orderStr = "";
			for (String[] item : orders) {
				orderStr = orderStr.concat(String.format("%sx %s, ", item[1], item[0])); // Concat with NumberxName
																							// (e.g. 1xChicken)
			}
			System.out.format(leftAlignFormat, ts.format(dtf), invoice.getTotalPrice(), orderStr);
		});

		System.out.format("+----------+----------+----------------------------------------------+-----------+%n");
		System.out.format("| Total                                                              | %9.2f |%n", total);
		System.out.format("+--------------------------------------------------------------------+-----------+%n");

		System.out.println();
	}
}
