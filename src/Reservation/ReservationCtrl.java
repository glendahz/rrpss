package Reservation;

import java.io.BufferedReader;
import java.io.File;
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
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TimerTask;

import Table.TableCtrl;
import Table.Table.TableStatus;
import util.Controller;

/**
 * A control class that mediates between {@code Reservation} entity objects and the {@code ReservationUI} boundary object.
 * This control class reads from and writes to a reservations data file.
 * @author Hoang Huu Quoc Huy
 */
public class ReservationCtrl extends Controller {

	/**
	 * The file which stores reservations data.
	 */
	private static final File RSV_FILE = new File("data", "Reservations.txt");

	/**
	 * The delimiter between values in each line of data file.
	 */
	private static final String DELIMITER = ",";

	/**
	 * The control class object used to manage {@code Table} objects.
	 */
	private TableCtrl tabCtrl = new TableCtrl();

	/**
	 * The ArrayList used to store pending {@code Reservation} objects.
	 */
	private ArrayList<Reservation> reservations = new ArrayList<Reservation>();

	/**
	 * The {@code Scanner} object to take in input from the application user.
	 */
	private static Scanner sc = new Scanner(System.in);

	/**
	 * Sets the control class object used to manage {@code Table} objects.
	 * @param ctrl	The control class object used to manage {@code Table} objects.
	 */
	public void setTableController(TableCtrl ctrl) {
		this.tabCtrl = ctrl;
	}

	/**
	 * The constructor of {@code ReservationCtrl} objects.
	 */
	public ReservationCtrl (){
		readData(RSV_FILE);
	}

	/**
	 * Create a new {@code Reservation}.
	 */
	public void makeReservation() {

		/**
		 * Contact number query.
		 * Return void if there is another pending reservation registered with the same contact number.
		 */
		int contactNumber;
		outloop:
		while(true){
			try{
				System.out.println("Please enter contact number (number only): ");
				contactNumber = sc.nextInt();
				for (Reservation rsv : reservations) {
					if (rsv.getContactNumber() == contactNumber) {
						System.out.println("Alreaady make reservation!");
						continue outloop;
					}
				}
				break;
			}catch(InputMismatchException e){
				System.out.println("Please enter valid number.");
				sc.nextLine();
			}
		}

		/**
		 * Arrival date query.
		 */
		LocalDate date = queryDate();

		/**
		 * Arrival time query.
		 */
		LocalTime arrTime = queryTime();

		LocalDateTime dt = LocalDateTime.of(date, arrTime);

		/**
		 * Number of person query
		 */
		int pax;
		while(true){
			try {
				System.out.println("Please enter number of people: ");
				pax = sc.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Please enter valid input.");
				sc.nextLine();
			}
		}

		ArrayList<Integer> tableIDs = tabCtrl.getAvaiTableIDsBySize(pax);

		ArrayList<Reservation>rsvUnavai = new ArrayList<>();

		/**
		 * Filter unavailable tableIDs
		 * rsvUnavai used to avoid ConcurrentModificationException.
		 */
		for (Reservation rsv : reservations) {
			if (tableIDs.contains(rsv.getTableID()))
				if (rsv.timeClash(dt))
					rsvUnavai.add(rsv);
		}

		for (Reservation rsv : rsvUnavai) {
			tableIDs.remove(Integer.valueOf(rsv.getTableID()));
		}

		if (tableIDs.isEmpty()) System.out.println("No available table to reserve.");
		else {
			/**
			 * If (validInput) creat new {@code Reservation},
			 * add converted data to data file,
			 * add newly created {@code Reservation} object to the pending {@code Reservation} ArrayList,
			 * and set up a schedule to set {@code table} to {@code TableStatus.RESERVED}.
			 */
			System.out.println("Please enter customer name:");
			String name = sc.next();
			Reservation rsv = new Reservation(contactNumber, name, date, arrTime, pax, tableIDs.get(0));
			writeData(RSV_FILE, rsv);
			reservations.add(rsv);
			setReservedTimer(rsv);
			System.out.println("Reservation successfully made to tableID "+rsv.getTableID());
		}
	}

	/**
	 * Filter pending {@code Reservation} objects by contact number.
	 * @return queried {@code Reservation} object.
	 */
	public Reservation reservationQueryByContact() {
		int input;
		Reservation temp = null;
		while (true) {
			System.out.print("Enter contact number: ");
			try {
				input = sc.nextInt();
				sc.nextLine();
				for (Reservation rsv : reservations) {
					if (rsv.getContactNumber() == input) {
						temp = rsv;
						break;
					}
				}
				break;
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("Please enter valid number");
				continue;
			}
		}
		return temp;
	}

	/**Display {@code Reservation} by Reservation.contactNumber */
	public void displayByContactNumber() {
		Reservation rsv = reservationQueryByContact();
		if (rsv != null)
			rsv.display();
		else System.out.println("No reservation registered.");
	}

	/**Display {@code Reservation} by Reservation.tableID */
	public void displayByTableID() {
		int tableID = TableCtrl.queryTableID(); //Reuse {@code TableCtrl} class's method.
		ArrayList<Reservation> rsvList = new ArrayList<Reservation>();
		for (Reservation rsv : reservations)
			if (rsv.getTableID() == tableID)
				rsvList.add(rsv);
		if (rsvList.isEmpty())
			System.out.println("No reservation made for table ID " + tableID);
		else
			for (Reservation rsv : rsvList)
				rsv.display();

	}

	/**Remove {@code Reservation} object by Reservation.contactNumber */
	public void removeReservationByContact() {
		Reservation rsv = reservationQueryByContact();
		if(rsv==null)System.out.println("No reservation found.");
		else{
			removeData(RSV_FILE, rsv);
			rsv.removeTimer();
			reservations.remove(rsv);
			System.out.println("Remove successfully.");
		}
	}

	/**Remove {@code Reservation} object by Reservation.tableID */
	public void removeReservationByTableID() {
		int tableID = TableCtrl.queryTableID();
		ArrayList<Reservation>rsvToRemove=new ArrayList<>();
		for (Reservation rsv : reservations){
			if (rsv.getTableID() == tableID){
				rsvToRemove.add(rsv);
			}
		}
		if(rsvToRemove.isEmpty()){
			System.out.println("No reservation found");
			return;
		}
		for (Reservation rsv : rsvToRemove) {
			removeData(RSV_FILE, rsv);
			rsv.removeTimer();
			reservations.remove(rsv);
		}
		System.out.println("Remove successfully.");
	}

	/**Display all pending {@code Reservation} objects */
	public void displayAllReservation() {
		if(reservations.isEmpty()){
			System.out.println("No reservation to display");
			return;
		}
		for (Reservation rsv : reservations) {
			rsv.display();
		}
	}

	/**
	 * Appends data of a specific {@code Reservation} object to the end of the reservation data file.
	 * @param file the destination {@code File} to append object data in.
	 * @param rsv the {@code Reservation} object to be appended.
	 * @throws Exception if the {@code FileWriter} object cannot find the reservation data file.
	 */
	public void writeData(File file, Reservation rsv){
		try {
			FileWriter writer = new FileWriter(file, true);
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

	/**
	 * Convert data lines from {@code File} object to {@code Reservation} which then to be added to the
	 * reservations ArrayList.
	 * @param file the {@code File} object that data is converted from.
	 */
	public void readData(File file){
		String line;
		ArrayList<String> pending = new ArrayList<>();
		Reservation rsv;
		reservations.clear(); // Empty reservations ArrayList
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while ((line = reader.readLine()) != null) {
				String[] items = line.split(DELIMITER);
				LocalDateTime dt = LocalDateTime.parse(items[2]);

				/**
				 * Not add outdated {@code Reservation} object into the reservations ArrayList
				 */
				if(dt.compareTo(LocalDateTime.now())<0)continue;
				pending.add(line);

				rsv = new Reservation(Integer.valueOf(items[0]),//contact
										items[1],//name
										dt.toLocalDate() , dt.toLocalTime(),
										Integer.valueOf(items[3]),//pax
										Integer.valueOf(items[4]),//tableID
										Integer.valueOf(items[5]));//rsvID
				reservations.add(rsv);
				setReservedTimer(rsv);

				/**
				 * Use the biggest rsvID available in the reservations ArrayList
				 * to set next rsvID of later made {@code Reservation} objects
				 */
				Reservation.setNextID(rsv.getRsvID());
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Input mistmatch!");
			e.printStackTrace();
		}

		try {
			PrintWriter wr = new PrintWriter(file);
			for (String string : pending) {
				wr.write(string);
				wr.write("\n");
			}
			wr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Use {@code Timer} object inside {@code Reservation} objects to set the TableStatus==RESERVED
	 * at the appointed Reservation.date and Reservation.arrTime.
	 * @param rsv the {@code Reservation} object.
	 */
	private void setReservedTimer(Reservation rsv){

		/**
		 * Get the time the {@code Timer} go off depending on which session
		 * rsv.arrTime falls on, 07:00-12:30 or 14:00-19:30
		 */
		LocalTime actTime;
		if(rsv.getArrTime().compareTo(LocalTime.parse("13:00"))<0)actTime = LocalTime.parse("07:00");
		else actTime = LocalTime.parse("14:00");

		rsv.getTimer().schedule(new TimerTask(){
			@Override
			public void run(){
				tabCtrl.reserveTable(rsv.getTableID());

				/**Set TableStatus==VACANT if rsv expires. */
				setExpiredTimer(rsv);
			}
		}, Date.from(LocalDateTime.of(rsv.getDate(), actTime)
		.atZone(ZoneId.systemDefault()).toInstant()));
	}

	/**
	 * Expired timer goes off 30 minutes after SetReservedTimer started.
	 * If Reservation.removeTimer() has not been called and TableStatus==RESERVED,
	 * set TableStatus==VACATE.
	 * @param rsv the {@code Reservation} object.
	 */
	private void setExpiredTimer(Reservation rsv){
		int tableID = rsv.getTableID();
		rsv.getTimer().schedule(new TimerTask(){
			@Override
			public void run(){
				if(tabCtrl.getTableStatus(tableID)==TableStatus.RESERVED)tabCtrl.vacateTable(tableID);
			}
		}, Date.from(LocalDate.now().atTime(rsv.getArrTime()).plusMinutes(30)
		.atZone(ZoneId.systemDefault()).toInstant()));
	}

	/**
	 * Get a valid arrival date from the user
	 * @return a valid date
	 */
	public LocalDate queryDate(){
		sc = new Scanner(System.in);
		boolean validInput = false;
		LocalDate date = LocalDate.now();
		while(!validInput){
				try{System.out.println("Please enter date (dd-MM-yyyy): ");
				date = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				if((date.compareTo(LocalDate.now().plusDays(30))<0)&&(date.compareTo(LocalDate.now())>0)) validInput = true;
				else System.out.println("Only accept reservation for the next 30 days");}
				catch(DateTimeParseException e){
					System.out.println("Please enter valid date.");
				}
		}
		return date;
	}

	/**
	 * Get a valid arrival time from the user
	 * @return a valid arrTime
	 */
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
			}catch(DateTimeParseException e){
				System.out.println("Please enter valid time.");
			}
		}
		return time;
	}

	/**
	 * Remove data from the {@code File} object that represents the {@code Reservation} object
	 * by filter out target data then rewrite the {@code File} object.
	 * @param File The {@code File} object the data is removed from.
	 * @param rsv The {@code Reservation} object whose data need to be removed.
	 */
	private void removeData(File file, Reservation rsv){
		String line;
		ArrayList<String> newData = new ArrayList<>();
		try {
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()) {
				line = reader.nextLine();
				String[] items = line.split(DELIMITER);
				if(Integer.valueOf(items[5])==rsv.getRsvID())continue;
				else newData.add(line);
			}
			reader.close();
		}catch(FileNotFoundException e){
			System.out.println("ReservationCtrl.removeData() error: cannot find file");
		}

		/**
		 * Rewrite the {@code File} object
		 */
		try {
			PrintWriter wr = new PrintWriter(file);
			for (String newLine : newData) {
				wr.write(newLine); wr.write("\n");
			}
			wr.close();
		}catch(Exception e){
			System.out.println("ReservationCtrl.removeData() error: cannot find file");
		}
	}
}
