package Reservation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {
    
    private LocalDate date;
    private LocalTime arrTime;
    private int pax;
    private String name;
    private int contactNumber;
    private int tableID;
    private static  int n = 0;//total number of rsv, for rsvID
    private int rsvID;

    public Reservation(int contactNumber, String name, LocalDate date, LocalTime arrTime, int pax, int tableID){
        this.date = date;
        this.arrTime = arrTime;
        this.pax = pax;
        this.name = name;
        this.contactNumber = contactNumber;
        this.tableID = tableID;
        n++;
        this.rsvID = n;
    }

    //restore rsvID when read from file
    public Reservation(int contactNumber, String name, LocalDate date, LocalTime arrTime, int pax, int tableID, int rsvID){
        this.date = date;
        this.arrTime = arrTime;
        this.pax = pax;
        this.name = name;
        this.contactNumber = contactNumber;
        this.tableID = tableID;
        this.rsvID = rsvID;
    }

    public LocalDate getDate(){
        return this.date;
    }

    public LocalTime getArrTime(){
        return this.arrTime;
    }

    public int getContactNumber(){
        return this.contactNumber;
    }

    public String getName(){
        return this.name;
    }

    public int getPax(){
        return this.pax;
    }

    public int getTableID(){
        return this.tableID;
    }

    public void display(){
        System.out.println("\nReservation ID: " +rsvID
                            +"\nContact number: "+contactNumber
                            +"\nName: "+name
                            +"\nDate: "+date
                            +"\nArrive time: "+arrTime
                            +"\nTableID: "+tableID);
    }

    public boolean TimeClash(LocalDateTime time){
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

    public int getRsvID(){
        return rsvID;
    }

    //used for assigning rsvID correctly after read from file (new Reservation() has rsvID=0 otherwise)
    public static void setTotal(int n){
        Reservation.n = n;
    }

}
