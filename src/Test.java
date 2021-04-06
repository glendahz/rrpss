import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class Test {

	public static void main(String[] args) {
		
		TableCtrl tableCtrl = new TableCtrl();
		for (int i=0; i<10; i++) tableCtrl.addTable(5);
		for (int i=1; i<=2; i++) tableCtrl.assignTable(i);
		
		StaffCtrl staffCtrl = new StaffCtrl();
		for (int i=1; i<=2; i++) staffCtrl.addStaff();
		
		OrderCtrl orderCtrl = new OrderCtrl();
		orderCtrl.setTableCtrl(tableCtrl);
		orderCtrl.setStaffCtrl(staffCtrl);
		
		OrderUI orderUI = new OrderUI();
		orderUI.setOrderCtrl(orderCtrl);
		orderUI.displayOptions();
		
		InvoiceCtrl invoiceCtrl = new InvoiceCtrl();
		invoiceCtrl.setTableCtrl(tableCtrl);
		
		InvoiceUI invoiceUI = new InvoiceUI();
		invoiceUI.setInvoiceCtrl(invoiceCtrl);
		invoiceUI.displayOptions();
		
		
	}

}