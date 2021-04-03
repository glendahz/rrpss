import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Scanner;

public class SalesReportController {
	private SalesReport model;
	private SalesReportUI view;
	
	SalesReportController(SalesReport model, SalesReportUI view) {
		this.model = model;
		this.view = view;
	}
	
	public void printReport() {
		generateSalesReport("sales.txt");
		float total = model.getSessionTotal();
		
		if(model.getType() == "DAY")
			view.printByDay(model.getInvoices(), total);
		else
			view.printByMonth(model.getDailyTotals(), total);
	}
	
	private void generateSalesReport(String path) {
		float total = 0;
		String type = model.getType();
		
		if(type == "MONTH") {
			try {
				File file = new File(path);
				Scanner sc = new Scanner(file);
				LocalDate date = model.getStartDate();
				YearMonth searchYM = YearMonth.from(date); 
				
				sc.nextLine(); // headers
				while(sc.hasNextLine()) {
					String line = sc.nextLine();
					String[] row = line.split(",");
					LocalDateTime rowDateTime = LocalDateTime.parse(row[0]);
					YearMonth rowYM = YearMonth.from(rowDateTime);
					
					if(rowYM.isBefore(searchYM)) {
						continue;
					} else if(rowYM.equals(searchYM)) {
						float price = Float.parseFloat(row[1]);
						
						model.addDailyTotals(rowDateTime.getDayOfMonth(), price);
						total += price;
					} else {
						break;
					}
				}
				sc.close(); 
			} catch (FileNotFoundException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		} else if(type == "DAY") {
			try {
				File file = new File(path);
				Scanner sc = new Scanner(file);
				sc.nextLine();
				while(sc.hasNextLine()) {
					String line = sc.nextLine();
					String[] row = line.split(",");
					LocalDateTime rowDateTime = LocalDateTime.parse(row[0]);
					LocalDate rowDate = rowDateTime.toLocalDate();
					LocalDate date = model.getStartDate();
					
					if(rowDate.isBefore(date)) {
						continue;
					} else if(rowDate.equals(date)) {
						// Since items are separated by commas
						String[] items = Arrays.copyOfRange(row, 2, row.length);
						float price = Float.parseFloat(row[1]);
						
						OrderInvoice invoice = new OrderInvoice(rowDateTime, items, price);
						
						total += price;
						model.addInvoice(invoice);
					} else {
						break;
					}
				}
				sc.close(); 
			} catch (FileNotFoundException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
		
		
		
		model.setSessionTotal(total);
	}
}
