import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ReservationCtrl {
    private TableCtrl tabCtrl;
    private ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    static Scanner sc = new Scanner(System.in);

    public void makeReservation(){
        System.out.println("Please enter contact number: ");
        int contactNumber = sc.nextInt();
        for (Reservation rsv : reservations) {
            if(rsv.getContactNumber()==contactNumber){
                System.out.println("Alreaady make reservation!");
                return;
            }
        }
        System.out.println("Please enter date (MMdd):");
        LocalDate date = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("MMdd"));
        System.out.println("Please enter arrival time(HHmm): ");
        LocalTime arrTime = LocalTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("HHmm"));
        int pax = tabCtrl.querySize();
        ArrayList<Integer> tableIDs = tabCtrl.getTableBySize(pax);

        for (Reservation rsv : reservations) {
            if(tableIDs.contains(rsv.getTableID()))
                if(rsv.TimeClash(date.atTime(arrTime)))
                    tableIDs.remove(rsv.getTableID());
        }
        if(tableIDs.isEmpty())System.out.println("Unavailable");
        else{
            System.out.println("Please enter customer name:");
            String name = sc.nextLine();
            reservations.add(new Reservation(date, arrTime, pax, name, contactNumber, tableIDs.get(0)));
            System.out.println("Reservation successfully made.");
        }
    }

    //called when constructor is called
    //assuming system starts at the beginning of each session
    //and each session is 8AM:2PM and 3PM:9PM
    private void setReservation(){
        for (Reservation rsv : reservations) {

            //remove outdated rsv
            if(rsv.getDate().atTime(rsv.getArrTime()).compareTo(LocalDateTime.now())<0){
                reservations.remove(rsv);
            }
            if(rsv.getDate()==LocalDate.now()){
                //if session is AM, ignore reservation past 2PM
                //if session is PM, already remove AM reservation
                //assuming you can't make reservation for the same session bcuz it's only called at the start of each session
                if(rsv.getArrTime().compareTo(LocalTime.now().plusHours(6))<0){
                    tabCtrl.reserveTable(rsv.getTableID());
                    setExpired(rsv.getTableID(),rsv.getArrTime());
                }
            }
        }
    }

    private void setExpired(int tableID, LocalTime time){
        new Timer().schedule(new TimerTask(){
            public void run(){
                if(tabCtrl.getTableStatus(tableID)==TableStatus.RESERVED){
                    tabCtrl.vacateTable(tableID);
                }
            }
        }, Date.from(LocalDate.now().atTime(time).atZone(ZoneId.systemDefault())
        .toInstant()));
    }

    public Reservation reservationQueryByContact(){
        Scanner sc = new Scanner(System.in);
        int input;
        boolean validInput = false;
        Reservation temp = null;
        while(!validInput){
            System.out.print("Enter contact number: ");
            try{
                input = sc.nextInt();
                sc.nextLine();
                for (Reservation rsv : reservations) {
                    if(rsv.getContactNumber()==input){
                        temp = rsv;
                        validInput = true;
                        break;
                    }
                }
            } catch(Exception e){
                sc.nextLine();
                System.out.println("Invalid input! Try again.");
                continue;           
            }
        }
        return temp;
    }

    public void displayByContactNumber(){
        Reservation rsv = reservationQueryByContact();
        if(rsv!=null)rsv.display();
    }

    public void displayByTableID(){
        int tableID = tabCtrl.queryTableID();
        ArrayList<Reservation> rsvList = new ArrayList<Reservation>();
        for (Reservation rsv : reservations) 
            if(rsv.getTableID()==tableID)
                rsvList.add(rsv);
        if(rsvList.isEmpty())System.out.println("No reservation made for table ID"+tableID);
        else for(Reservation rsv: rsvList)rsv.display();

    }

    public void removeReservationByContact(){
        Reservation rsv = reservationQueryByContact();
        reservations.remove(rsv);
    }

    public void removeReservationByTableID(){
        int tableID = tabCtrl.queryTableID();
        for (Reservation rsv : reservations)
            if(rsv.getTableID()==tableID)
                reservations.remove(rsv);
    }

    public void displayAllReservation(){
        for (Reservation rsv : reservations) {
            rsv.display();
        }
    }

}
