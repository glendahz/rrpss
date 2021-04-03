import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SalesReportUI {
	
	SalesReportUI(SalesReport model) {
		System.out.print("=================SalesReport=================\n"
				+ "1) Print sales by day\n"
				+ "2) Print sales by month\n"
				+ "0) Exit\n");
		
		System.out.print("Input: ");
		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();
		
		while(input < 0 || input > 2) {
			System.out.println("Invalid option! Try again.");
			System.out.print("Input: ");
			input = sc.nextInt();
		}
		
		if(input == 0) {
			sc.close();
			return;
		}
		
		String sessionType;
		LocalDate date;
		if(input == 1) {
			sessionType = "DAY";
			while(true) {
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
			while(true) {
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
			model.setType(sessionType);
			model.setStartDate(date);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		sc.close();
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
	}
	
	public void printByDay(List<OrderInvoice> invoices, float total) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		
		String leftAlignFormat = "| %-8s | %8.2f | %20s |%n";
		
		System.out.format("+----------+----------+----------------------+%n");
		System.out.format("| Time     | Price($) | Orders               |%n");
		System.out.format("+----------+----------+----------------------+%n");
		
		invoices.forEach(invoice -> {
			System.out.format(leftAlignFormat, invoice.datetime.format(dtf), invoice.price, String.join(",", invoice.items));
		});
		
		
		System.out.format("+----------+----------+----------+-----------+%n");
		System.out.format("| Total                          | %9.2f |%n", total);
		System.out.format("+--------------------------------+-----------+%n");
	}
}
