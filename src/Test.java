public class Test {

	public static void main(String[] args) {

		TableSystem tables = new TableSystem();
		tables.addTable(4);
		tables.assignTable(3);
		tables.getAvailableTables(4);
		tables.reserveTable(5);
		tables.getAvailableTables(4);
		tables.addTable(5);
		tables.getAvailableTables(4);
		
		Staff Mary = new Staff("Mary", Gender.Female, 1, JobTitle.WAITER);
		Mary.getOrder();
		
	}

}
