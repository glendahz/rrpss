import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SalesReport {
	private String type;
	private LocalDate startDate;
	private List<OrderInvoice> invoices = new ArrayList<OrderInvoice>();
	private HashMap<Integer, Float> dailyTotals = new HashMap<Integer, Float>();
	private float sessionTotal;

	public void setType(String type) throws Exception {
		if(type == "DAY" || type == "MONTH")
			this.type = type;
		else
			throw new Exception("SalesReport: Invalid type");
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setStartDate(LocalDate date) {
		this.startDate = date; // Since LocalDate is immutable
	}
	
	public LocalDate getStartDate() {
		return this.startDate;
	}
	
	public void addInvoice(OrderInvoice invoice) {
		invoices.add(invoice);
	}
	
	public List<OrderInvoice> getInvoices() {
		return this.invoices;
	}
	
	public void addDailyTotals(int day, float total) {
		if(this.dailyTotals.containsKey(day)) {
			float prevValue = this.dailyTotals.get(day);
			this.dailyTotals.put(day, prevValue + total);
		} else {
			this.dailyTotals.put(day, total);
		}
	}
	
	public HashMap<Integer, Float> getDailyTotals() {
		return this.dailyTotals;
	}
	
	public float getSessionTotal() {
		return this.sessionTotal;
	}
	
	public void setSessionTotal(float total) {
		this.sessionTotal = total;
	}
	
	public void resetSession() {
		startDate = null;
		invoices = null;
		sessionTotal = 0;
	}
}
