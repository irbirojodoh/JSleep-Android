package com.RijalJSleepFN.model;



import com.RijalJSleepFN.MainActivity;

import java.util.Calendar;
import java.util.Date;
import java.text.*;


public class Payment extends Invoice{
    public Date to;
    public Date from;
    public int roomId;

    /**overloading constructor*/
    public Payment(Account buyer, Renter renter, int roomId,Date from,Date to){
        super(buyer,renter);
        this.roomId=roomId;
        //SimpleDateFormat format =
        this.from=from;
        this.to=to;
        //to.add(Calendar.DATE,2);
    }

    public Payment(int buyerId, int renterId, int roomId,Date from, Date to){
        super(buyerId,renterId);
        this.roomId=roomId;
        this.from= from;
        this.to=to;
        //to.add(Calendar.DATE,2);
    }
    /**method overriding dengan parent*/
    public String print(){
        String constfrom = "From :";
        SimpleDateFormat arrange = new SimpleDateFormat(" dd/mm/yyyy");
        String formattedTo = arrange.format(to.getTime());
        String formattedFrom = arrange.format(from.getTime());
        //String space = "  ";
        String tmp = constfrom + this.from +"\nFrom: "+ formattedFrom + "\nTo:" + formattedTo+ this.roomId;
        return tmp;
    }

    /** getter*/
    public int getRoomId(){
        return this.roomId;
    }

    // public String getTime(){
    //     SimpleDateFormat arrange = new SimpleDateFormat(" dd MMMM yyyy ");
    //    String currTime = arrange.format(time.getTime());
    //    return currTime;
    // }

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
