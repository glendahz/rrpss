public class Test {

	public static void main(String[] args) {
//		TableCtrl tables = new TableCtrl();
//		tables.addTable(4);
//		tables.addTable(5);
//		tables.addTable(6);
//		tables.assignTable(1);
//		tables.assignTable(2);
//		tables.reserveTable(3);
//		tables.getAllTables();
//		tables.writeData("Tables.txt");
//		tables.readData("Tables.txt");
//		tables.getAllTables();
		
		StaffCtrl staffs = new StaffCtrl();
		staffs.addStaff();
		staffs.addStaff();
		staffs.viewStaffList();
		staffs.writeData("Staff.txt");
		staffs.readData("Staff.txt");
		staffs.viewStaffList();
		
//		OrderUI ui = new OrderUI();
//		ui.setCtrl(new OrderCtrl(tables, staffs));
//		ui.mainUI();
	}

}