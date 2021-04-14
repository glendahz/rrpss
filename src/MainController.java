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
import util.Controller;

/**
 * Serves as an entry point to the entire application.
 * Here, all the necessary {@code UI} and {@code Controller} classes are created.
 * @author Wai Yar Aung
 *
 */
public class MainController extends Controller{
	private InvoiceUI invoiceUI;
	private MenuUI menuUI;
	private OrderUI orderUI;
	private ReservationUI reservationUI;
	private SalesReportUI salesReportUI;
	private StaffUI staffUI;
	private TableUI tableUI;

	private InvoiceCtrl invoiceCtrl;
//	private MenuCtrl menuCtrl;
	private OrderCtrl orderCtrl;
	private ReservationCtrl reservationCtrl;
	private SalesReportController salesReportCtrl;
	private StaffCtrl staffCtrl;
	private TableCtrl tableCtrl;
	
	/**
	 * The constructor creates the {@code Controller} classes as well
	 *  as set the other dependent controller classes.
	 */
	MainController() {
		invoiceCtrl = new InvoiceCtrl();
//		menuCtrl = new MenuCtrl();
		orderCtrl = new OrderCtrl();
		reservationCtrl = new ReservationCtrl();
		salesReportCtrl = new SalesReportController();
		staffCtrl = new StaffCtrl();
		tableCtrl = new TableCtrl();
		
		orderCtrl.setStaffCtrl(staffCtrl);
		orderCtrl.setTableCtrl(tableCtrl);
		
		reservationCtrl.setTableController(tableCtrl);
		invoiceCtrl.setTableCtrl(tableCtrl);
	}

	/**
	 * Creates a new {@code StaffUI} instance, sets an appropriate controller for it.
	 * Also, call {@code displayOptions()}.
	 */
	public void displayStaffUI() {
		staffUI = new StaffUI();
		staffUI.setController(staffCtrl);
		staffUI.displayOptions();
	}

	/**
	 * Creates a new {@code TableUI} instance, sets an appropriate controller for it.
	 * Also, call {@code displayOptions()}.
	 */
	public void displayTableUI() {
		tableUI = new TableUI();
		tableUI.setController(tableCtrl);
		tableUI.displayOptions();
	}

	/**
	 * Creates a new {@code MenuUI} instance, sets an appropriate controller for it.
	 * Also, call {@code displayOptions()}.
	 */
	public void displayMenuUI() {
		menuUI = new MenuUI();
		menuUI.displayOptions();
	}

	/**
	 * Creates a new {@code OrderUI} instance, sets an appropriate controller for it.
	 * Also, call {@code displayOptions()}.
	 */
	public void displayOrdersUI() {
		orderUI = new OrderUI();
		orderUI.setController(orderCtrl);
		orderUI.displayOptions();
	}

	/**
	 * Creates a new {@code ReservationUI} instance, sets an appropriate controller for it.
	 * Also, call {@code displayOptions()}.
	 */
	public void displayReservationUI() {
		reservationUI = new ReservationUI();
		reservationUI.setController(reservationCtrl);
		reservationUI.displayOptions();
	}

	/**
	 * Creates a new {@code InvoiceUI} instance, sets an appropriate controller for it.
	 * Also, call {@code displayOptions()}.
	 */
	public void displayInvoicesUI() {
		invoiceUI = new InvoiceUI();
		invoiceUI.setController(invoiceCtrl);
		invoiceUI.displayOptions();
	}

	/**
	 * Creates a new {@code SalesReportUI} instance, sets an appropriate controller for it.
	 * Also, call {@code displayOptions()}.
	 */
	public void displaySalesReportUI() {
		salesReportUI = new SalesReportUI();
		salesReportUI.setController(salesReportCtrl);
		salesReportUI.displayOptions();
	}
}
