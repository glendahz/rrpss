package Reservation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import Table.TableCtrl;
import Table.Table.TableStatus;
import util.Controller;

public class ReservationCtrl extends Controller {

	private static final String DELIMITER = ",";

	private TableCtrl tabCtrl;
	private ArrayList<Reservation> reservations = new ArrayList<Reservation>();
	private Scanner sc = new Scanner(System.in);

	public void setTableController(TableCtrl ctrl) {
		this.tabCtrl = ctrl;
	}

	public ReservationCtrl (){
		readData("data/Reservations.txt");
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
		LocalDate date = queryDate();

		LocalTime arrTime = queryTime();

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
			Reservation rsv = new Reservation(contactNumber, name, date, arrTime, pax, tableIDs.get(0));
			writeData("data/Reservations.txt", rsv);
			reservations.add(rsv);
			setReserved(rsv);
			System.out.println("Reservation successfully made to tableID "+rsv.getTableID());
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
		removeData("data/Reservations.txt", rsv);
		reservations.remove(rsv);
	}

	public void removeReservationByTableID() {
		int tableID = TableCtrl.queryTableID();
		ArrayList<Reservation>rsvToRemove=new ArrayList<>();
		for (Reservation rsv : reservations){
			if (rsv.getTableID() == tableID){
				rsvToRemove.add(rsv);
			}
		}
		for (Reservation rsv : rsvToRemove) {
			removeData("data/Reservations.txt", rsv);
		}
	}

	public void displayAllReservation() {
		for (Reservation rsv : reservations) {
			rsv.display();
		}
	}

	public void writeData(String fileName, Reservation rsv){
		try {
			FileWriter writer = new FileWriter(fileName, true);
			Integer contactNumber = rsv.getContactNumber();
			String name = rsv.getName();
			LocalDateTime dt = LocalDateTime.of(rsv.getDate(), rsv.getArrTime());
			Integer pax = rsv.getPax();
			Integer tableID = rsv.getTableID();
			Integer rsvID = rsv.getRsvID();
			writer.write(String.join(DELIMITER, new String[] {contactNumber.toString(), name, dt.toString(), pax.toString(), tableID.toString(), rsvID.toString()}));
			writer.write("\n");
			writer.close();
		} catch (Exception e) {
			System.out.println("File not found!");
			e.printStackTrace();
		}
	}

	public void readData(String fileName){
		String line;
		Reservation rsv;
		reservations.clear(); // Empty reservations arraylist
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			while ((line = reader.readLine()) != null) {
				String[] items = line.split(DELIMITER);
				LocalDateTime dt = LocalDateTime.parse(items[2]);
				if(dt.compareTo(LocalDateTime.now())<0)continue;//ignore outdated rsv
				rsv = new Reservation(Integer.valueOf(items[0]),//contact
										items[1],//name
										dt.toLocalDate() , dt.toLocalTime(),
										Integer.valueOf(items[3]),//pax
										Integer.valueOf(items[4]),//tableID
										Integer.valueOf(items[5]));//rsvID
				reservations.add(rsv);
				setReserved(rsv);
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

	private void setReserved(Reservation rsv){
		LocalTime actTime;

		if(rsv.getArrTime().compareTo(LocalTime.parse("13:00"))<0)actTime = LocalTime.parse("07:00");
		else actTime = LocalTime.parse("14:00");

		new Timer().schedule(new TimerTask(){
			@Override
			public void run(){
				tabCtrl.reserveTable(rsv.getTableID());
				setExpired(rsv);
			}
		}, Date.from(LocalDateTime.of(rsv.getDate(), actTime)
		.atZone(ZoneId.systemDefault()).toInstant()));
	}

	private void setExpired(Reservation rsv){
		int tableID = rsv.getTableID();
		new Timer().schedule(new TimerTask(){
			@Override
			public void run(){
				if(tabCtrl.getTableStatus(tableID)==TableStatus.RESERVED)tabCtrl.vacateTable(tableID);
			}
		}, Date.from(LocalDate.now().atTime(rsv.getArrTime()).plusMinutes(30)
		.atZone(ZoneId.systemDefault()).toInstant()));
	}

	public LocalDate queryDate(){
		sc = new Scanner(System.in);
		boolean validInput = false;
		LocalDate date = LocalDate.now();
		while(!validInput){
				System.out.println("Please enter date (dd-MM-yyyy): ");
				date = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				if(date.compareTo(LocalDate.now().plusDays(30))<0) validInput = true;
				else System.out.println("Only accept reservation for the next 30 days");
		}
		return date;
	}

	public LocalTime queryTime(){
		sc = new Scanner(System.in);
		boolean validInput = false;
		LocalTime time = LocalTime.now();
		while(!validInput){
			try{
				System.out.println("Please enter arrival time (hh:mm) between 07:00-12:30 or 14:00-19:30 : ");
				time = LocalTime.parse(sc.nextLine());
				if(((time.compareTo(LocalTime.parse("06:59"))>0)&&(time.compareTo(LocalTime.parse("12:31"))<0))
				||((time.compareTo(LocalTime.parse("13:59"))>0)&&(time.compareTo(LocalTime.parse("19:31"))<0)))
					validInput = true;
				else System.out.println("Only accept reservation between 07:00-12:30 or 14:00-19:30");
			}catch(Exception e){
				System.out.println("Invalid input!");
			}
		}
		return time;
	}

	private void removeData(String fileName, Reservation rsv){
		String line;
		ArrayList<String> newData = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			while ((line = reader.readLine()) != null) {
				String[] items = line.split(DELIMITER);
				if(Integer.valueOf(items[-1])==rsv.getRsvID())continue;
				else newData.add(line);
			}
			reader.close();
		}catch(Exception e){
			System.out.println("ReservationCtrl.removeData() error: cannot find file");
		}

		try {
			PrintWriter wr = new PrintWriter(fileName);
			for (String newLine : newData) {
				wr.write(newLine); wr.write("\n");
				wr.close();
			}
		}catch(Exception e){
			System.out.println("ReservationCtrl.removeData() error: cannot find file");
		}
	}
}
