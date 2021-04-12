package Staff;

public class Manager extends Staff {
	
	public Manager(String name, Gender gender, int employeeID) {
		super(name, gender, employeeID);
		this.setJobTitle(JobTitle.MANAGER);
	}
	
	public void planRoster() {
		System.out.println("Planning the roster for future shifts!");
	}
	
	public void checkQualityOfFood() {
		System.out.println("Check the quality of the food!");
	}
	
	public void reviseMenuItems() {
		System.out.println("Revise the menu!");
	}
	
	public void checkWorkingHours() {
		System.out.println("10AM To 10PM");
	}
	
}
