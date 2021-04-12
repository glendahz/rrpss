
package Staff;
enum Gender { MALE, FEMALE }
enum JobTitle { WAITER, MANAGER}

/**
 * Represents a staff working in the restaurant
 * @author david
 * @version 1.0
 * @since 2021-04-07
 */

public class Staff {
	
	/**
	 * The name of the employee
	 */
	private String name;
	/**
	 * Gender of the employee - Male or Female
	 */
	private Gender gender;
	/**
	 * Employee ID
	 */
	private int employeeID;
	/**
	 * Job Title of the employee - Waiter or Manager
	 */
	private JobTitle jobTitle;
	
	/**
	 * Constructor that creates a staff with the job title specified
	 * @param name
	 * @param gender
	 * @param employeeID
	 * @param jobTitle
	 */
	public Staff(String name, Gender gender, int employeeID, JobTitle jobTitle) {
		this.name = name;
		this.gender = gender;
		this.employeeID = employeeID;
		this.jobTitle = jobTitle;
	}
	
	/**
	 * Constructor that creates a staff without the job title specified
	 * @param name
	 * @param gender
	 * @param employeeID
	 */
	public Staff(String name, Gender gender, int employeeID) {
		this.name = name;
		this.gender = gender;
		this.employeeID = employeeID;
	}
	
	/**
	 * Gets the name of this staff
	 * @return this staff's name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the gender of this staff
	 * @return this staff's gender
	 */
	public Gender getGender() {
		return this.gender;
	}
	
	/**
	 * Gets the employee ID of this staff
	 * @return this staff's employee ID
	 */
	public int getEmployeeID() {
		return this.employeeID;
	}
	
	/**
	 * Gets the job title of this staff
	 * @return this staff's job title
	 */
	public JobTitle getJobTitle() {
		return this.jobTitle;
	}
	
	/**
	 * Set the jobtitle for this staff
	 * @param jobTitle
	 */
	public void setJobTitle(JobTitle jobTitle) {
		this.jobTitle = jobTitle;
	}
	
	/**
	 * Method for checking working hours
	 */
	public void checkWorkingHours() {}
	
}