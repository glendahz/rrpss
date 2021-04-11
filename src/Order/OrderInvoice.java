package Order;
import java.time.LocalDateTime;

/**
 * Represents the payment methods that a customer can use.
 * @author Glenda Hong Zixuan
 */
enum PaymentMethod { 
	
	/**
	 * Payment by cash.
	 */
	CASH, 
	
	/**
	 * Payment by credit card.
	 */
	CREDIT_CARD, 
	
	/**
	 * Payment by NETS.
	 */
	NETS, 
	
	/**
	 * Payment by NETS Flashpay.
	 */
	NETS_FLASHPAY, 
	
	/**
	 * Payment by Paylah!.
	 */
	PAYLAH, 
	
	/**
	 * Payment by Alipay.
	 */
	ALIPAY 
};

/**
 * Represents an order invoice of an order.
 * Each {@code OrderInvoice} is created from an {@code Order}.
 * @author Glenda Hong Zixuan
 *
 */
public class OrderInvoice {
	
	/**
	 * The {@code Order} that this {@code OrderInvoice} was created from.
	 */
	private Order order;
	
	/**
	 * The total price of all the order items in this {@code OrderInvoice}.
	 */
	private float totalPrice;
	
	/**
	 * The payment method that the customer(s) used to pay.
	 */
	private PaymentMethod paymentMethod;
	
	/**
	 * The time stamp of when this {@code OrderInvoice} was created.
	 */
	private LocalDateTime timestamp;
	
	/**
	 * Creates a new {@code OrderInvoice}.
	 * @param order			The {@code Order} of this {@code OrderInvoice}.
	 * @param paymentMethod	The payment method of this {@code OrderInvoice}
	 * @param timestamp		The time stamp of this {@code OrderInvoice}.
	 */
	public OrderInvoice(Order order, PaymentMethod paymentMethod, LocalDateTime timestamp){
		this.order = order;
		this.paymentMethod = paymentMethod;
		this.timestamp = timestamp;
		
		// get total price
		this.totalPrice = this.getTotalPrice(true);
	}
	
	/**
	 * Creates a new {@code OrderInvoice}.
	* @param order			The {@code Order} of this {@code OrderInvoice}.
	 * @param paymentMethod	The payment method of this {@code OrderInvoice}
	 * @param timestamp		The time stamp of this {@code OrderInvoice}.
	 */
	public OrderInvoice(Order order, String paymentMethod, LocalDateTime timestamp){
		this(order, PaymentMethod.valueOf(paymentMethod), timestamp);
	}
	
	/**
	 * Creates a new {@code OrderInvoice}.
	 * @param order			The {@code Order} of this {@code OrderInvoice}.
	 * @param timestamp		The time stamp of this {@code OrderInvoice}.
	 * @param totalPrice	The total price of this {@code OrderInvoice}.
	 */
	public OrderInvoice(Order order, LocalDateTime timestamp, float totalPrice){
		this.order = order;
		this.timestamp = timestamp;
		this.totalPrice = totalPrice;
		this.paymentMethod = null;
	}
	
	// getters
	
	/**
	 * Gets the {@code Order} of this {@code OrderInvoice}.
	 * @return the {@code Order} of this {@code OrderInvoice}.
	 */
	public Order getOrder() {
		return this.order;
	}
	
	/**
	 * Gets the payment method of this {@code OrderInvoice}.
	 * @return the payment method of this order.
	 */
	public PaymentMethod getPaymentMethod() {
		return this.paymentMethod;
	}	
	
	/**
	 * Gets the time stamp of this {@code OrderInvoice}.
	 * @return the time stamp of this {@code OrderInvoice}.
	 */
	public LocalDateTime getTimestamp() {
		return this.timestamp;
	}
	
	/**
	 * Gets the total price of this {@code OrderInvoice}.
	 * @param calc	If {@code calc} is {@code true}, the {@code getTotalPrice} method will recalculate the total price,
	 * 				set the {@code totalPrice} field of this {@code OrderInvoice} to the recalculated price,
	 * 				and then return the recalculated price.
	 * 				<br>Otherwise, the {@code getTotalPrice} returns the value stored 
	 * 				in the {@code totalPrice} field of this {@code OrderInvoice}.
	 * @return the total price of this {@code OrderInvoice}.
	 */
	public float getTotalPrice(boolean calc) {
		if (calc) {
			// re-calculate total price
			this.totalPrice = 0;
			String[] itemNames = this.order.getAllItemNames();
			for (int i=0; i<itemNames.length; i++) {
				this.totalPrice += this.order.getItemPrice(itemNames[i]) * this.order.getItemNum(itemNames[i]);
			}
			this.totalPrice = (float) (Math.round(this.totalPrice * 100.0) / 100.0); // rounds off total price to 2 d.p.
		}
		return this.totalPrice;
	}
	
	/**
	 * Gets the total price of this {@code OrderInvoice}.
	 * @return the total price of this {@code OrderInvoice}.
	 */
	public float getTotalPrice() {
		return this.getTotalPrice(false);
	}
	
	/**
	 * Gets the tax details of this {@code OrderInvoice}.
	 * @param gst			The GST tax as a decimal value.
	 * @param serviceCharge	The service tax as a decimal value. 
	 * @return the tax details of this {@code OrderInvoice} in a {@code float} array of size 4.
	 * <br>&emsp;The first element is the total price without taxes.
	 * <br>&emsp;The second element is the total price including GST tax.
	 * <br>&emsp;The third element is the total price including service tax.
	 * <br>&emsp;The fourth element is the total price with taxes.
	 */
	public float[] getTaxDetails(float gst, float serviceCharge) {
		float total = this.getTotalPrice();
		float totalTax = 1 + gst + serviceCharge;
		float subTotal = total / totalTax;
		float gstTax = subTotal * gst;
		float serviceTax = subTotal * serviceCharge;
		float[] taxDetails = {subTotal, gstTax, serviceTax, total};
		return taxDetails;
	}
	
	// setters
	
	/**
	 * Sets the {@code Order} of this {@code OrderInvoice}.
	 * @param order	The {@code Order} of this {@code OrderInvoice}.
	 */
	public void setOrder(Order order) {
		this.order = order;
		this.getTotalPrice(true);
	}
	
	/**
	 * Sets the payment method of this {@code OrderInvoice}.
	 * @param paymentMethod	The payment method of this {@code OrderInvoice}.
	 */
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	/**
	 * Sets the payment method of this {@code OrderInvoice}.
	 * @param paymentMethod	The payment method of this {@code OrderInvoice}.
	 */
	public void setPaymentMethod(String paymentMethod) {
		setPaymentMethod(PaymentMethod.valueOf(paymentMethod));
	}
	
	/**
	 * Sets the time stamp of this {@code OrderInvoice}.
	 * @param timestamp	The time stamp of this {@code OrderInvoice}.
	 */
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
}
