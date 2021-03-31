enum Gender { Male, Female }
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
	
	// To implement with order
	public void getOrder() {
		System.out.println(this.name + " got order from customer");
	}
	
}
