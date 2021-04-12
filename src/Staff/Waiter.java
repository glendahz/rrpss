package Staff;

/**
 * Represents a waiter working in the restaurant
 * @author david
 * @version 1.0
 * @since 2021-04-07
 */

public class Waiter extends Staff {

	/**
	 * Creates a waiter that works in the restaurant
	 * @param name
	 * @param gender
	 * @param employeeID
	 */
	public Waiter(String name, Gender gender, int employeeID) {
		super(name, gender, employeeID);
		this.setJobTitle(JobTitle.WAITER);
	}
	
	/**
	 * Allows waiter to take temperature for customers - only available to waiters
	 */
	public void takeTemperature() {
		System.out.println("Temperature taken!");
	}
	
	/**
	 * Allows waiter to throw out the rubbish - only available to waiters
	 */
	public void clearRubbish() {
		System.out.println("Rubbish removed!");
	}
	
	/**
	 * Override method in Staff to return manager's working hours
	 */
	@Override
	public void checkWorkingHours() {
		System.out.println("Waiter works from 10 AM To 3 PM");
	}
	
}
