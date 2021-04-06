import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Scanner;

import jdk.javadoc.internal.doclets.toolkit.taglets.ReturnTaglet;

public class ReservationCtrl {
    private TableCtrl tabCtrl;
    private ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    static Scanner sc = new Scanner(System.in);

    public void makeReservation(){
        System.out.println("Please enter contact number: ");
        int contactNumber = sc.nextInt();
        for (Reservation reservation : reservations) {
            if(reservation.getContactNumber()==contactNumber){
                System.out.println("Alreaady make reservation!");
                return;
            }
        }
        System.out.println("Please enter date (MMdd):");
        int fdate = sc.nextInt();
        LocalDate date = LocalDate.of(2021,Month.of((int)fdate/100),fdate%100);
        System.out.println("Please enter arrival time(hhmm): ");
        int ftime = sc.nextInt();
        LocalTime arrTime = LocalTime.of((int)Math.floor(ftime/100), ftime%100);
        System.out.println("Please enter number of person: ");
        int pax = sc.nextInt();

        //tabCtrl.getAvailableTables();
        
        //if unavailable, return

        //tabCtrl.reserveTable();

        //Enter the rest of details
    }

    public void expireReservation(){//called at the end of each session
        for (Reservation reservation : reservations) {
            if(reservation.getDate().compareTo(LocalDate.now())<0)
                reservations.remove(reservation);
            else if(reservation.getDate().compareTo(LocalDate.now())==0){
                if(reservation.getArrTime().compareTo(LocalTime.now())<0)
                reservations.remove(reservation);
            }
        }
    }

    private Reservation find(int contactNumber){
        boolean found = false;
        int temp = 0;
        while(!found){
            if(reservations.get(temp).getContactNumber()==contactNumber){
                found = true;
                return reservations.get(temp);
            }
        if(temp++>reservations.size()-1)System.out.println("No reservtion!");
        }
    }

    public void removeReservation(){
        System.out.println("Enter contact number: ");
        int contactNumber = sc.nextInt();
        Reservation rsv = find(contactNumber);
        reservations.remove(rsv);
    }

    public void getAllReservation(){
        for (Reservation rsv : reservations) {
            System.out.printf("Contact number: %d\n name: %s\n date and time: %s %s\n pax: %d\n table: %d\n",rsv.getContactNumber(), rsv.getName(), rsv.getDate(),rsv.getArrTime(), rsv.getPax(), rsv.getTable().getTableID());
        }
    }

}
