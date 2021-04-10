package SalesReport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Order.OrderInvoice;

/**
 * Represents a session of SalesReport.
 * Once the type and startDate is set, it can be generated.
 * @author Wai Yar Aung
 *
 */
public class SalesReport {
	/**
	 * Whether its a monthly or daily report
	 */
	private String type;
	
	/**
	 * The date of the report if type == "DAY" 
	 * otherwise, the first day of the month for type == "MONTH"
	 */
	private LocalDate startDate;
	
	/**
	 * List of invoices for order for a day,
	 * used only when type == "DAY"
	 */
	private List<OrderInvoice> invoices = new ArrayList<OrderInvoice>();
	
	/**
	 * A HashMap of the total cash for each day,
	 * used only when type == "MONTH"
	 */
	private HashMap<Integer, Float> dailyTotals = new HashMap<Integer, Float>();
	
	/**
	 * The total sum of the entire session
	 */
	private float sessionTotal;

	/**
	 * Sets the type of SalesReport. The type is either "MONTH" or "DAY".
	 * 
	 * @param type "MONTH" or "DAY"
	 * @throws Exception If type is not "MONTH" or "DAY"
	 */
	public void setType(String type) throws Exception {
		if (type == "DAY" || type == "MONTH")
			this.type = type;
		else
			throw new Exception("SalesReport: Invalid type");
	}

	/**
	 * Returns the type of SalesReport
	 * @return
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Sets the starting date of SalesReport
	 * @param date
	 */
	public void setStartDate(LocalDate date) {
		this.startDate = date; // Since LocalDate is immutable
	}

	/**
	 * Return starting date of SalesReport
	 * @return LocalDate starting date
	 */
	public LocalDate getStartDate() {
		return this.startDate;
	}

	
	/**
	 * Adds an OrderInvoice object to the invoices list
	 * @param invoice an OrderInvoice object to be added
	 */
	public void addInvoice(OrderInvoice invoice) {
		invoices.add(invoice);
	}

	/**
	 * 
	 * @return the list of invoices from the SalesReport
	 */
	public List<OrderInvoice> getInvoices() {
		return this.invoices;
	}

	/**
	 * Adds an entry to the HashTable dailyTotals with day as key and total as value
	 * @param day the day of month
	 * @param total the total price for the day
	 */
	public void addDailyTotals(int day, float total) {
		if (this.dailyTotals.containsKey(day)) {
			float prevValue = this.dailyTotals.get(day);
			this.dailyTotals.put(day, prevValue + total);
		} else {
			this.dailyTotals.put(day, total);
		}
	}

	/**
	 * 
	 * @return a HashMap of daily totals
	 */
	public HashMap<Integer, Float> getDailyTotals() {
		return this.dailyTotals;
	}

	/**
	 * The total amount of the session
	 * @return total float invoices/dailyTotals
	 */
	public float getSessionTotal() {
		return this.sessionTotal;
	}

	/**
	 * Sets the total value for session
	 * @param total sum of invoices/dailyTotals
	 */
	public void setSessionTotal(float total) {
		this.sessionTotal = total;
	}
}
