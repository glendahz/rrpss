import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		TableCtrl tableCtrl = new TableCtrl();
		int i1=1, i2=3, n=10;
		for (int i=0; i<n; i++) tableCtrl.addTable(5);
		for (int i=i1; i<=i2; i++) tableCtrl.assignTable(i);
		for (int i=i2+1; i<=n; i++) tableCtrl.reserveTable(i);
		
		StaffCtrl staffCtrl = new StaffCtrl();
		staffCtrl.addStaff();
		
		OrderCtrl orderCtrl = new OrderCtrl();
		orderCtrl.setTableCtrl(tableCtrl);
		orderCtrl.setStaffCtrl(staffCtrl);
		
		OrderUI orderUI = new OrderUI();
		orderUI.setOrderCtrl(orderCtrl);
		orderUI.displayOptions(sc);
		
		InvoiceCtrl invoiceCtrl = new InvoiceCtrl();
		invoiceCtrl.setTableCtrl(tableCtrl);
		
		InvoiceUI invoiceUI = new InvoiceUI();
		invoiceUI.setInvoiceCtrl(invoiceCtrl);
		invoiceUI.displayOptions(sc);
		
		sc.close();
	}

}