package com.RijalJSleepFN.model;



import com.RijalJSleepFN.MainActivity;

import java.util.Calendar;
import java.util.Date;
import java.text.*;


/**

 Payment class that extends Invoice and contains information about a payment for a specific room.
 @author Ibrahim Rijal
 @version 1.0
 */

public class Payment extends Invoice{
    public Date to;
    public Date from;
    public int roomId;

    /**
     Overloading constructor that creates a Payment object with the given buyer, renter, roomId, and date range.
     @param buyer the Account of the buyer
     @param renter the Renter of the room
     @param roomId the id of the room
     @param from the starting date of the booking
     @param to the ending date of the booking
     */

    public Payment(Account buyer, Renter renter, int roomId,Date from,Date to){
        super(buyer,renter);
        this.roomId=roomId;
        //SimpleDateFormat format =
        this.from=from;
        this.to=to;
        //to.add(Calendar.DATE,2);
    }
    /**
     Overloading constructor that creates a Payment object with the given buyer id, renter id, roomId, and date range.
     @param buyerId the id of the buyer
     @param renterId the id of the renter
     @param roomId the id of the room
     @param from the starting date of the booking
     @param to the ending date of the booking
     */
    public Payment(int buyerId, int renterId, int roomId,Date from, Date to){
        super(buyerId,renterId);
        this.roomId=roomId;
        this.from= from;
        this.to=to;
        //to.add(Calendar.DATE,2);
    }
    /**
     Overrides the print method in the parent class to return a String containing information about the Payment object.
     @return a String containing information about the Payment object
     */
    public String print(){
        String constfrom = "From :";
        SimpleDateFormat arrange = new SimpleDateFormat(" dd/mm/yyyy");
        String formattedTo = arrange.format(to.getTime());
        String formattedFrom = arrange.format(from.getTime());
        //String space = "  ";
        String tmp = constfrom + this.from +"\nFrom: "+ formattedFrom + "\nTo:" + formattedTo+ this.roomId;
        return tmp;
    }


    /**
     * Getter for roomId
     * @return
     */
    public int getRoomId(){
        return this.roomId;
    }

    // public String getTime(){
    //     SimpleDateFormat arrange = new SimpleDateFormat(" dd MMMM yyyy ");
    //    String currTime = arrange.format(time.getTime());
    //    return currTime;
    // }

    /**
     * Availibility method that checks if the room is available for the given date range.
     * @param from
     * @param to
     * @param room
     * @return
     */
    public static boolean availability(Date from, Date to, Room room){
        //int y;
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(from);
        end.setTime(to);
        if(start.after(end)||start.equals(end)){
            return false;
        }
        for(Date date= start.getTime();start.before(end);start.add(Calendar.DATE,1),date=start.getTime()) {
            if(room.booked.contains(date)){
                return false;
            }
        }
        return true;
    }

    /**
     * Make booking method that creates a booking for the given date range and room.
     * @param from
     * @param to
     * @param room
     * @return
     */
    public static boolean makeBooking(Date from, Date to, Room room){
        if(availability(from, to, room)){
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            start.setTime(from);
            end.setTime(to);
            for(Date date= start.getTime();start.before(end);start.add(Calendar.DATE,1),date=start.getTime()) {
                room.booked.add(date);
            }
            return true;
        }
        return false;
    }



}
