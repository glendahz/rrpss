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

    public Reservation(LocalDate date, LocalTime arrTime, int pax,
    String name, int contactNumber, int tableID){
        this.date = date;
        this.arrTime = arrTime;
        this.pax = pax;
        this.name = name;
        this.contactNumber = contactNumber;
        this.tableID = tableID;
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
        System.out.println("Contact number: "+contactNumber
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
}
