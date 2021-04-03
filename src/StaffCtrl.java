import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class StaffCtrl {
	
	private ArrayList<Staff> staffList = new ArrayList<Staff>();
	// ADD for set menu object also //
	
	public void viewStaffList() {
		System.out.println("----Staff List----");
		for (int i = 0; i < staffList.size(); ++i) {
			Staff currStaff = staffList.get(i);
			System.out.println(currStaff);
		}
	}		
	
	public static void addStaff() {		
			
	}
	
	public static void removeStaff() {
		
	}
	
}