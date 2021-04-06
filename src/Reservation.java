import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    
    private LocalDate date;
    private LocalTime arrTime;
    private int pax;
    private String name;
    private int contactNumber;
    private Table table;

    public Reservation(LocalDate date, LocalTime arrTime, int pax,
    String name, int contactNumber, Table table){
        this.date = date;
        this.arrTime = arrTime;
        this.pax = pax;
        this.name = name;
        this.contactNumber = contactNumber;
        this.table = table;
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

    public Table getTable(){
        return this.table;
    }
}
