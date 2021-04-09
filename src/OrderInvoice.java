import java.time.LocalDateTime;

enum PaymentMethod { CASH, CREDIT_CARD, NETS, NETS_FLASHPAY, PAYLAH, APLIPAY };

public class OrderInvoice {
	private Order order;
	private float totalPrice;
	private PaymentMethod paymentMethod;
	private LocalDateTime timestamp;
	
	OrderInvoice(Order order, PaymentMethod paymentMethod, LocalDateTime timestamp){
		this.order = order;
		this.paymentMethod = paymentMethod;
		this.timestamp = timestamp;
		
		// get total price
		this.totalPrice = 0;
		String[] itemNames = this.order.getAllItemNames();
		for (int i=0; i<itemNames.length; i++) {
			this.totalPrice += this.order.getItemPrice(itemNames[i]) * this.order.getItemNum(itemNames[i]);
		}
		this.totalPrice = (float) (Math.round(this.totalPrice * 100.0) / 100.0); // rounds off total price to 2 d.p.
	}
	OrderInvoice(Order order, String paymentMethod, LocalDateTime timestamp){
		this(order, PaymentMethod.valueOf(paymentMethod), timestamp);
	}
	OrderInvoice(Order order, LocalDateTime timestamp, float totalPrice){
		this.order = order;
		this.timestamp = timestamp;
		this.totalPrice = totalPrice;
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
		return this.totalPrice;
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
