package Staff;
enum Gender { MALE, FEMALE }
enum JobTitle { WAITER, MANAGER}

public class Staff {
	
	private String name;
	private Gender gender;
	private int employeeID;
	private JobTitle jobTitle;
	
	public Staff(String name, Gender gender, int employeeID, JobTitle jobTitle) {
		this.name = name;
		this.gender = gender;
		this.employeeID = employeeID;
		this.jobTitle = jobTitle;
	}
	
	public Staff(String name, Gender gender, int employeeID) {
		this.name = name;
		this.gender = gender;
		this.employeeID = employeeID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Gender getGender() {
		return this.gender;
	}
	
	public int getEmployeeID() {
		return this.employeeID;
	}
	
	public JobTitle getJobTitle() {
		return this.jobTitle;
	}
	
	public void setJobTitle(JobTitle jobTitle) {
		this.jobTitle = jobTitle;
	}
	
	public void checkWorkingHours() {
		System.out.println("123");
	}
	
}