import java.time.LocalDateTime;

enum PaymentMethod { CASH, CREDIT_CARD, NETS, NETS_FLASHPAY, PAYLAH, APLIPAY };

public class OrderInvoice {
	private Order order;
	private PaymentMethod paymentMethod;
	private LocalDateTime timestamp;
	
	OrderInvoice(Order order, PaymentMethod paymentMethod, LocalDateTime timestamp){
		this.order = order;
		this.paymentMethod = paymentMethod;
		this.timestamp = timestamp;
	}
	OrderInvoice(Order order, String paymentMethod, LocalDateTime timestamp){
		this(order, PaymentMethod.valueOf(paymentMethod), timestamp);
	}
	
	// getters
	public Order getOrder() {
		return this.order;
	}
	public PaymentMethod getPaymentMethod() {
		return this.paymentMethod;
	}	
	public LocalDateTime getTimestamp() {
		return this.timestamp;
	}
	public float getTotalPrice() {
		float totalPrice = 0;
		String[] itemNames = this.order.getAllItemNames();
		for (int i=0; i<itemNames.length; i++) {
			totalPrice += this.order.getItemPrice(itemNames[i]) * this.order.getItemNum(itemNames[i]);
		}
		return totalPrice;
	}
	public float[] getTaxDetails(float gst, float serviceCharge) {
		float total = getTotalPrice();
		float totalTax = 1 + gst + serviceCharge;
		float subTotal = total / totalTax;
		float gstTax = subTotal * gst;
		float serviceTax = subTotal * serviceCharge;
		float[] taxDetails = {subTotal, gstTax, serviceTax, total};
		return taxDetails;
	}
	
	// setters
	public void setOrder(Order order) {
		this.order = order;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		setPaymentMethod(PaymentMethod.valueOf(paymentMethod));
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
}
