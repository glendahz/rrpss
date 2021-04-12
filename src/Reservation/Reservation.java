package Reservation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Timer;

/**
 * Represents a reservation made by customers.
 * Each registered contact number can only make one reservation
 * @author Hoang Huu Quoc Huy
 */

public class Reservation {
    
    /**
     * Arrival date
     */
    private LocalDate date;

    /**
     * Arrival time
     */
    private LocalTime arrTime;

    /**
     * Number of customers
     */
    private int pax;

    /**
     * Name of customer
     */
    private String name;

    /**
     * Customer's contact numbber
     */
    private int contactNumber;

    /**
     * table ID, representing the table assigned to the customer
     */
    private int tableID;

    /**
     * Used for making new Reservation ID
     */
    private static  int n = 0;

    /**
     * Unique number assigned to each Reservation made
     */
    private int rsvID;

    /**
     * Used to set the reservation at the appointed date and time
     */
    private Timer timer;

    /**
     * Create new {@code Reservation} object.
     * @param contactNumber
     * @param name
     * @param date
     * @param arrTime
     * @param pax
     * @param tableID
     */ 
    public Reservation(int contactNumber, String name, LocalDate date, LocalTime arrTime, int pax, int tableID){
        this.date = date;
        this.arrTime = arrTime;
        this.pax = pax;
        this.name = name;
        this.contactNumber = contactNumber;
        this.tableID = tableID;
        n++;
        this.rsvID = n;
        this.timer = new Timer();
    }

    /**
     * Recreate {@code Reservation} object from reservation data file.
     * Needed to preserve reservation ID
     * @param contactNumber
     * @param name
     * @param date
     * @param arrTime
     * @param pax
     * @param tableID
     * @param rsvID
     */
    public Reservation(int contactNumber, String name, LocalDate date, LocalTime arrTime, int pax, int tableID, int rsvID){
        this.date = date;
        this.arrTime = arrTime;
        this.pax = pax;
        this.name = name;
        this.contactNumber = contactNumber;
        this.tableID = tableID;
        this.rsvID = rsvID;
    }

    /**
     * @return arrival date of this {@code Reservation}.
     */
    public LocalDate getDate(){
        return this.date;
    }

    /**
     * @return arrival time of this {@code Reservation}.
     */
    public LocalTime getArrTime(){
        return this.arrTime;
    }

    /**
     * @return contact number registered in this {@code Reservation}.
     */
    public int getContactNumber(){
        return this.contactNumber;
    }

    
    /**
     * @return customer name registered in this {@code Reservation}.
     */
    public String getName(){
        return this.name;
    }

    /**
     * @return number of customers of this {@code Reservation}.
     */
    public int getPax(){
        return this.pax;
    }

    /**
     * @return the table ID of this {@code Reservation}.
     */
    public int getTableID(){
        return this.tableID;
    }

    /**
     * Displays details of this {@code Reservation}.
     */
    public void display(){
        System.out.println("\nReservation ID: " +rsvID
                            +"\nContact number: "+contactNumber
                            +"\nName: "+name
                            +"\nDate: "+date
                            +"\nArrive time: "+arrTime
                            +"\nTableID: "+tableID);
    }

    /**
     * Determines whether an arrival time clashes with an existing {@code Reservation} object.
     * @param time the arrival time being queried when making reservation
     * @return true if arrival time clashes with the {@code Reservation} object, and vice versa.
     */
    public boolean timeClash(LocalDateTime time){
        boolean cl = false;
        if(time.toLocalDate().compareTo(date)==0){
            if(time.toLocalTime().compareTo(LocalTime.parse("14:00"))<0){
                if(arrTime.compareTo(LocalTime.parse("14:00"))<0){
                    cl = true;
                }
            }else{
                if(arrTime.compareTo(LocalTime.parse("19:00"))<0){
                    cl = true;
                }
            }
        }
        return cl;
    }

    /**
     * @return the reservation ID of this {@code Reservation}.
     */
    public int getRsvID(){
        return rsvID;
    }

    /**
     * @return the timer of this {@code Reservation}.
     */
    public Timer getTimer(){
        return this.timer;
    }

    /**
     * Cancel the timer of this {@code Reservation}.
     */
    public void removeTimer(){
        this.timer.cancel();
    }

    /**
     * Used for assigning reservation ID correctly after read from file,
     * new Reservation() has rsvID=0 otherwise.
     * @param n represents the reservation ID of existing {@code Reservation}.
     */
    public static void setNextID(int n){
        if(Reservation.n<n)
        Reservation.n = n;
    }

}
