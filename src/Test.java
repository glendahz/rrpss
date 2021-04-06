public class Test {

	public static void main(String[] args) {
		TableCtrl tables = new TableCtrl();
		tables.addTable(4);
		tables.addTable(5);
		tables.addTable(6);
		tables.assignTable(1);
		tables.assignTable(2);
		tables.reserveTable(3);
		StaffCtrl staffs = new StaffCtrl();
		staffs.addStaff();
		OrderUI ui = new OrderUI();
		ui.setCtrl(new OrderCtrl(tables, staffs));
		ui.mainUI();
	}

}