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

/**
 * Control class for Staff to retrieve and store staff data
 * @author david
 * @version 1.0
 * @since 2021-04-07
 */

public class StaffCtrl extends Controller {
	
	/**
	 * Delimiter for file reading and writing - CSV
	 */
	private static final String DELIMITER = ",";
	
	/**
	 * Next employee number that is not yet assigned before
	 */
	static int currEmpNum;
	/**
	 * Dynamic list to store all staff belonging to the restaurant
	 */
	static private ArrayList<Staff> staffList = new ArrayList<Staff>();
	/**
	 * Scanner for getting inputs from the user
	 */
	static Scanner sc = new Scanner(System.in);
	
	/**
	 * Creates the StaffCtrl that imports all staff from the text file storing the staff information
	 */
	public StaffCtrl() {
		readData("data/Staff.txt");
	}
	
	/**
	 * Prints all the staff and their information - ID, Name, Gender, Title
	 */
	public void viewStaffList() {
		System.out.println("\n================= Staff List =================");
		if (staffList.size() == 0) {
			System.out.println("No staff in system");
		} else {
			String format = "| %-3d | %-18s | %-6s | %-7s |%n";
			
			System.out.format("+-----+--------------------+--------+---------+%n");
			System.out.format("| ID  | Name               | Gender | Title   |%n");
			System.out.format("+-----+--------------------+--------+---------+%n");
			
			for (int i = 0; i < staffList.size(); ++i) {
				Staff currStaff = staffList.get(i);
				System.out.printf(format, currStaff.getEmployeeID(), currStaff.getName(), currStaff.getGender(), currStaff.getJobTitle());
//				System.out.println(currStaff.getEmployeeID() + " " + currStaff.getName() + " " + currStaff.getGender() + " " + currStaff.getJobTitle());
			}
			
			System.out.format("+-----+--------------------+--------+---------+%n");
		}
	}		
	
	/**
	 * Adds a staff with their employee ID, name, gender and job title
	 * @param employeeID
	 * @param name
	 * @param gender
	 * @param jobTitle
	 */
	public void addStaff(int employeeID, String name, Gender gender, JobTitle jobTitle) {
		staffList.add(new Staff(name, gender, employeeID, jobTitle));
		writeData("data/Staff.txt");
	}
	
	/**
	 * Add a staff by having users input the staff information
	 */
	public void addStaff() {
		
		boolean validInput = false;
		String name = "";
		System.out.print("Please enter staff name: ");
		while (!validInput) {
			try {
				name = sc.next();
				if (name instanceof String) {
					validInput = true;
					break;
				}
				System.out.print("Please enter a proper name: ");
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
					break;
				}
				System.out.print("Please enter M for Male and F for Female: ");
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
		System.out.print("Please enter staff role (Waiter/Manager): ");
		while (!validInput) {
			try {
				temp = sc.next();
				if (temp.equals("Manager") | temp.equals("Waiter")) {
					validInput = true;
					break;
				}
				System.out.print("Please enter a proper role: ");
			} catch(InputMismatchException e) {
				System.out.print("Please enter a proper role: ");
				temp = sc.next();
			}
		}
		if (temp.equals("Manager")) {
			Manager manager = new Manager(name, gender, currEmpNum);
			staffList.add((Staff) manager);
		} else {
			Waiter waiter = new Waiter(name, gender, currEmpNum);
			staffList.add((Staff) waiter);
		}
	
		currEmpNum++;
		
		writeData("data/Staff.txt");
	}
	
	/**
	 * Remove a staff from the restaurant
	 */
	public void removeStaff() {
		
		boolean validInput = false;
		int employeeID = -1;
		System.out.print("Enter employee ID of employee to be removed: ");
		while (!validInput) {
			try {
				employeeID = sc.nextInt();
				if (employeeID == -1) {
					break;
				}
				if (employeeID > 0 && employeeID < currEmpNum) {
					for (int i = 0; i < staffList.size(); ++i) {
						Staff currStaff = staffList.get(i);
						if (currStaff.getEmployeeID() == employeeID) {
							validInput = true;
							break;
						}
					}
				}
				System.out.print("Please enter a valid employee ID or -1 to return to previous menu: ");
			} catch(InputMismatchException e) {
				System.out.print("Please enter a valid employee ID or -1 to return to previous menu: ");
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
	
	/**
	 * Returns the staff name for an employee
	 * @param employeeID
	 * @return
	 */
	public String getStaffName(int employeeID) {
		for (int i = 0; i < staffList.size(); ++i) {
			Staff currStaff = staffList.get(i);
			if (currStaff.getEmployeeID() == employeeID) {
				return currStaff.getName();
			}
		}
		return null;
	}
	
	/**
	 * Store all the staff currently in the restaurant into the text file in CSV format
	 * @param fileName
	 */
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
	
	/**
	 * Retrieve all the staff from the given file and stores to staff list
	 * @param fileName
	 */
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
	
	/**
	 * Convert users' input for gender into the enumeration - Gender
	 * @param currGender
	 * @return
	 */
	private Gender genderFromString(String currGender) {
		if (currGender.equals("MALE")) {
			return Gender.MALE;
		} else {
			return Gender.FEMALE;
		}
	}
	
	/**
	 * Covert users' input for job title into the enumeration - JobTitle
	 * @param jobTitle
	 * @return
	 */
	private JobTitle jobTitleFromString(String jobTitle) {
		if (jobTitle.equals("WAITER")) {
			return JobTitle.WAITER;
		} else {
			return JobTitle.MANAGER;
		}
	}
	
}
