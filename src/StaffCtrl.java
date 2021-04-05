import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class StaffCtrl {
	
	static int currEmpNum = 1;
	static private ArrayList<Staff> staffList = new ArrayList<Staff>();
	
	static Scanner sc = new Scanner(System.in);
	
	public void viewStaffList() {
		System.out.println("----Staff List----");
		if (staffList.size() == 0) {
			System.out.println("No staff in system");
		} else {
			for (int i = 0; i < staffList.size(); ++i) {
				Staff currStaff = staffList.get(i);
				System.out.println(currStaff.getEmployeeID() + " " + currStaff.getName() + " " + currStaff.getGender() + " " + currStaff.getJobTitle());
			}
		}
	}		
	
	public void addStaff() {
		
		boolean validInput = false;
		String name = "";
		System.out.print("Please enter staff name: ");
		while (!validInput) {
			try {
				name = sc.next();
				if (name instanceof String) {
					validInput = true;
				}
			} catch(InputMismatchException e) {
				System.out.print("Please enter a proper name: ");
				name = sc.next();
			}
		}
		
		validInput = false;
		String temp = "";
		Gender gender;
		System.out.print("Please enter staff gender (M/F): ");
		while (!validInput) {
			try {
				temp = sc.next();
				if (temp.equals("M") | temp.equals("F")) {
					validInput = true;
				}
			} catch(InputMismatchException e) {
				System.out.print("Please enter M for Male and F for Female: ");
				temp = sc.next();
			}
		}
		if (temp.equals("M")) {
			gender = Gender.MALE;
		} else {
			gender = Gender.FEMALE;
		}
		
		validInput = false;
		JobTitle jobTitle;
		System.out.print("Please enter staff role (Waiter/Manager): ");
		while (!validInput) {
			try {
				temp = sc.next();
				if (temp.equals("Manager") | temp.equals("Waiter")) {
					validInput = true;
				}
			} catch(InputMismatchException e) {
				System.out.print("Please enter a proper role: ");
				temp = sc.next();
			}
		}
		if (temp.equals("Manager")) {
			jobTitle = JobTitle.MANAGER;
		} else {
			jobTitle = JobTitle.WAITER;
		}
		
		staffList.add(new Staff(name, gender, currEmpNum, jobTitle));
		currEmpNum++;
		
	}
	
	public void removeStaff() {
		
		
		boolean validInput = false;
		int employeeID = -1;
		System.out.print("Enter employee ID of employee to be removed: ");
		while (!validInput) {
			try {
				employeeID = sc.nextInt();
				if (employeeID > 0 && employeeID < currEmpNum) {
					validInput = true;
				}
			} catch(InputMismatchException e) {
				System.out.print("Please enter a valid employee ID: ");
				employeeID = sc.nextInt();
			}
		}
		
		for (int i = 0; i < staffList.size(); ++i) {
			Staff currStaff = staffList.get(i);
			if (currStaff.getEmployeeID() == employeeID) {
				System.out.println("Employee " + currStaff.getName() + " with employee ID " + currStaff.getEmployeeID() + " removed!");
				staffList.remove(i);
			}
		}		
	}
	
}
