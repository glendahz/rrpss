package Staff;

/**
 * Represents a manager working in the restaurant
 * @author david
 * @version 1.0
 * @since 2021-04-07
 */

public class Manager extends Staff {
	
	/**
	 * Creates a manager working in the restaurant
	 * @param name
	 * @param gender
	 * @param employeeID
	 */
	public Manager(String name, Gender gender, int employeeID) {
		super(name, gender, employeeID);
		this.setJobTitle(JobTitle.MANAGER);
	}
	
	/**
	 * Method for planning the roster - only available to managers
	 */
	public void planRoster() {
		System.out.println("Planning the roster for future shifts!");
	}
	
	/**
	 * Method for checking quality of food - only available to managers
	 */
	public void checkQualityOfFood() {
		System.out.println("Check the quality of the food!");
	}
	
	/**
	 * Method for revising menu items - only available to managers
	 */
	public void reviseMenuItems() {
		System.out.println("Revise the menu!");
	}
	
	/**
	 * Override method in Staff to return manager's working hours
	 */
	@Override
	public void checkWorkingHours() {
		System.out.println("Manager works from 10AM To 10PM");
	}
	
}
