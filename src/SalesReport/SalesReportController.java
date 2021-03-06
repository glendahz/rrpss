package SalesReport;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Scanner;

import Order.Order;
import Order.OrderInvoice;
import util.Controller;

/**
 * Interfaces between the SalesReportUI and SalesReport model.
 * @author Wai Yar Aung
 *
 */
public class SalesReportController extends Controller {
	/**
	 * Holds the SalesReport currently being shown by the UI.
	 */
	private SalesReport model = null;
	
	/**
	 * Fills up the model with sales data from text file.
	 * Calls respective print function in UI.
	 * @param model SalesReport to print
	 * @param view The UI to print in
	 */
	public void printReport(SalesReport model, SalesReportUI view) {
		this.model = model;
		generateSalesReport("data/Sales.txt");
		float total = model.getSessionTotal();
		
		if(model.getType() == "DAY")
			view.printByDay(model.getInvoices(), total);
		else
			view.printByMonth(model.getDailyTotals(), total);
		
		view.displayOptions();
	}
	
	/**
	 * Reads the sales.txt data file and adds it to model if the timestamp fits.
	 * If the type == "MONTH", finds all date for the month chosen.
	 * If the type == "DAY", finds all timestamp for the date chosen.
	 * Assumes that the data file is sorted by ascending timestamp.
	 * Ends early once the next date/month is reached.
	 * @param path The path for sales.txt data file
	 */
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
						float price = Float.parseFloat(row[1]);
						
						//TODO: IF CONSTRUCTOR CHANGE, CAN REMOVE
						String[] items = Arrays.copyOfRange(row, 2, row.length); // Since items are separated by commas
						String[] itemNames = new String[items.length];
						int[] itemNums = new int[items.length];
						
						float[] itemPrices = new float[items.length]; // TO BE REMOVED
						
						for(int i = 0; i < items.length; i++) {
							String[] temp = items[i].split("-");
							itemNames[i] = temp[1];
							itemNums[i] = Integer.parseInt(temp[0]);
							
							// TO BE REMOVED
							itemPrices[i] = 0;
						}

						Order order = new Order(1, "test", itemNames, itemNums, itemPrices);
						OrderInvoice invoice = new OrderInvoice(order, rowDateTime, price);
						
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
