package Staff;

public class Waiter extends Staff {

	public Waiter(String name, Gender gender, int employeeID) {
		super(name, gender, employeeID);
		this.setJobTitle(JobTitle.WAITER);
	}
	
	public void takeTemperature() {
		System.out.println("Temperature taken!");
	}
	
	public void clearRubbish() {
		System.out.println("Rubbish removed!");
	}
	
	@Override
	public void checkWorkingHours() {
		System.out.println("Waiter works from 10 AM To 3 PM");
	}
	
}
