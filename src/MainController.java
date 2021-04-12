//import Menu.MenuCtrl;
import Menu.MenuUI;
import Order.InvoiceCtrl;
import Order.InvoiceUI;
import Order.OrderCtrl;
import Order.OrderUI;
import Reservation.ReservationCtrl;
import Reservation.ReservationUI;
import SalesReport.SalesReportController;
import SalesReport.SalesReportUI;
import Staff.StaffCtrl;
import Staff.StaffUI;
import Table.TableCtrl;
import Table.TableUI;

public class MainController {
	private InvoiceUI invoiceUI;
	private MenuUI menuUI;
	private OrderUI orderUI;
//	private ReservationUI reservationUI;
	private SalesReportUI salesReportUI;
	private StaffUI staffUI;
	private TableUI tableUI;

	private InvoiceCtrl invoiceCtrl;
//	private MenuCtrl menuCtrl;
	private OrderCtrl orderCtrl;
//	private ReservationCtrl reservationCtrl;
	private SalesReportController salesReportCtrl;
	private StaffCtrl staffCtrl;
	private TableCtrl tableCtrl;
	
	MainController() {
		invoiceCtrl = new InvoiceCtrl();
//		menuCtrl = new MenuCtrl();
		orderCtrl = new OrderCtrl();
//		reservationCtrl = new ReservationCtrl();
		salesReportCtrl = new SalesReportController();
		staffCtrl = new StaffCtrl();
		tableCtrl = new TableCtrl();
		
		orderCtrl.setStaffCtrl(staffCtrl);
		orderCtrl.setTableCtrl(tableCtrl);
		
//		reservationCtrl.setTableController(tableCtrl);
		invoiceCtrl.setTableCtrl(tableCtrl);
	}

	public void displayStaffUI() {
		staffUI = new StaffUI();
		staffUI.setController(staffCtrl);
		staffUI.displayOptions();
	}

	public void displayTableUI() {
		tableUI = new TableUI();
		tableUI.setController(tableCtrl);
		tableUI.displayOptions();
	}

	public void displayMenuUI() {
		menuUI = new MenuUI();
		menuUI.displayOptions();
	}

	public void displayOrdersUI() {
		orderUI = new OrderUI();
		orderUI.setController(orderCtrl);
		orderUI.displayOptions();
	}

//	public void displayReservationUI() {
//		reservationUI = new ReservationUI();
//		reservationUI.setController(reservationCtrl);
//		reservationUI.displayOptions();
//	}

	public void displayInvoicesUI() {
		// Invoice UI depends on OrderUI
		orderUI = new OrderUI();
		orderUI.setController(orderCtrl);
		
		invoiceUI = new InvoiceUI();
		invoiceUI.setController(invoiceCtrl);
		invoiceUI.displayOptions();
	}

	public void displaySalesReportUI() {
		salesReportUI = new SalesReportUI();
		salesReportUI.setController(salesReportCtrl);
		salesReportUI.displayOptions();
	}
}
