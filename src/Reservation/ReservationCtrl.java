package Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import Table.TableCtrl;
import util.Controller;

public class ReservationCtrl extends Controller {
	private TableCtrl tabCtrl;
	private ArrayList<Reservation> reservations = new ArrayList<Reservation>();
	static Scanner sc = new Scanner(System.in);

	public void setTableController(TableCtrl ctrl) {
		this.tabCtrl = ctrl;
	}

	public void makeReservation() {
		System.out.println("Please enter contact number: ");
		int contactNumber = sc.nextInt();
		for (Reservation rsv : reservations) {
			if (rsv.getContactNumber() == contactNumber) {
				System.out.println("Alreaady make reservation!");
				return;
			}
		}
		System.out.println("Please enter date (MM-dd):");
		MonthDay md = MonthDay.parse(sc.next(), DateTimeFormatter.ofPattern("MM-dd"));
		LocalDate date = md.atYear(LocalDate.now().getYear());

		System.out.println("Please enter arrival time(HH:mm): ");
		LocalTime arrTime = LocalTime.parse(sc.next(), DateTimeFormatter.ofPattern("HH:mm"));

		System.out.println("Please enter number of people: ");
		int pax = sc.nextInt();

		ArrayList<Integer> tableIDs = tabCtrl.getTableIDsBySize(pax);

		LocalDateTime dt = LocalDateTime.of(date, arrTime);

		for (Reservation rsv : reservations) {
			if (tableIDs.contains(rsv.getTableID()))
				if (rsv.TimeClash(dt))
					tableIDs.remove(rsv.getTableID());
		}
		
		if (tableIDs.isEmpty())
			System.out.println("Unavailable");
		else {
			System.out.println("Please enter customer name:");
			String name = sc.next();
			reservations.add(new Reservation(date, arrTime, pax, name, contactNumber, tableIDs.get(0)));
			System.out.println("Reservation successfully made.");
		}
	}

	public Reservation reservationQueryByContact() {
		int input;
		boolean validInput = false;
		Reservation temp = null;
		while (!validInput) {
			System.out.print("Enter contact number: ");
			try {
				input = sc.nextInt();
				sc.nextLine();
				for (Reservation rsv : reservations) {
					if (rsv.getContactNumber() == input) {
						temp = rsv;
						validInput = true;
						break;
					}
				}
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("Invalid input! Try again.");
				continue;
			}
		}
		return temp;
	}

	public void displayByContactNumber() {
		Reservation rsv = reservationQueryByContact();
		if (rsv != null)
			rsv.display();
	}

	public void displayByTableID() {
		int tableID = TableCtrl.queryTableID();

		ArrayList<Reservation> rsvList = new ArrayList<Reservation>();
		for (Reservation rsv : reservations)
			if (rsv.getTableID() == tableID)
				rsvList.add(rsv);
		if (rsvList.isEmpty())
			System.out.println("No reservation made for table ID" + tableID);
		else
			for (Reservation rsv : rsvList)
				rsv.display();

	}

	public void removeReservationByContact() {
		Reservation rsv = reservationQueryByContact();
		reservations.remove(rsv);
	}

	public void removeReservationByTableID() {
		int tableID = TableCtrl.queryTableID();
		for (Reservation rsv : reservations)
			if (rsv.getTableID() == tableID)
				reservations.remove(rsv);
	}

	public void displayAllReservation() {
		for (Reservation rsv : reservations) {
			rsv.display();
		}
	}

}
