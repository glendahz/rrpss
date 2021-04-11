package Staff;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import util.Controller;

public class StaffCtrl extends Controller {
	
	private static final String DELIMITER = ",";
	
	static int currEmpNum;
	static private ArrayList<Staff> staffList = new ArrayList<Staff>();
	static Scanner sc = new Scanner(System.in);
	
	public StaffCtrl() {
		readData("data/Staff.txt");
	}
	
	public void viewStaffList() {
		System.out.println("\n========Staff List========");
		if (staffList.size() == 0) {
			System.out.println("No staff in system");
		} else {
			String leftAlignFormat = "| %-3d | %-18s | %-6s | %-7s |%n";

			System.out.format("+-----+--------------------+--------+---------+%n");
			System.out.format("| ID  | Name               | Gender | Title   |%n");
			System.out.format("+-----+--------------------+--------+---------+%n");
			
			for (int i = 0; i < staffList.size(); ++i) {
				Staff currStaff = staffList.get(i);
				System.out.printf(leftAlignFormat, currStaff.getEmployeeID(), currStaff.getName(), currStaff.getGender(), currStaff.getJobTitle());
//				System.out.println(currStaff.getEmployeeID() + " " + currStaff.getName() + " " + currStaff.getGender() + " " + currStaff.getJobTitle());
			}
			
			System.out.format("+-----+--------------------+--------+---------+%n");
		}
	}		
	
	public void addStaff(int employeeID, String name, Gender gender, JobTitle jobTitle) {
		staffList.add(new Staff(name, gender, employeeID, jobTitle));
		writeData("data/Staff.txt");
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
		
		writeData("data/Staff.txt");
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
		
		writeData("data/Staff.txt");
	}
	
	public String getStaffName(int employeeID) {
		for (int i = 0; i < staffList.size(); ++i) {
			Staff currStaff = staffList.get(i);
			if (currStaff.getEmployeeID() == employeeID) {
				return currStaff.getName();
			}
		}
		return null;
	}
	
	public void writeData(String fileName) {
		try {
			PrintWriter writer = new PrintWriter(fileName);
			Integer temp = (Integer) currEmpNum;
			writer.write(temp.toString());
			writer.write("\n");
			for (int i = 0; i < staffList.size(); ++i) {
				Staff currStaff = staffList.get(i);
				Integer employeeID = currStaff.getEmployeeID();
				String name = currStaff.getName();
				String gender = currStaff.getGender().toString();
				String jobTitle = currStaff.getJobTitle().toString();
				writer.write(String.join(DELIMITER, new String[] {employeeID.toString(), name, gender, jobTitle}));
				writer.write("\n");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		}
	}
	
	public void readData(String fileName) {
		String line;
		staffList.clear(); // Empty tables arraylist
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			currEmpNum = Integer.parseInt(reader.readLine());
			while ((line = reader.readLine()) != null) {
				String[] items = line.split(DELIMITER);
				int employeeID = Integer.parseInt(items[0]);
				String name = items[1];
				Gender gender = genderFromString(items[2]);
				JobTitle jobTitle = jobTitleFromString(items[3]);
				addStaff(employeeID, name, gender, jobTitle);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Input mistmatch!");
			e.printStackTrace();
		}
	}
	
	private Gender genderFromString(String currGender) {
		if (currGender.equals("MALE")) {
			return Gender.MALE;
		} else {
			return Gender.FEMALE;
		}
	}
	
	private JobTitle jobTitleFromString(String jobTitle) {
		if (jobTitle.equals("WAITER")) {
			return JobTitle.WAITER;
		} else {
			return JobTitle.MANAGER;
		}
	}
	
}
