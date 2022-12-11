package com.RijalJSleepFN.model;

import java.util.Date;


/**
 * This class represents an invoice for a transaction when customer rents a room
 *
 * The class contains fields for the buyer and renter IDs, the payment status, and the room rating.
 * It also contains two inner enums for `PaymentStatus` and `RoomRating`.
 *
 * The class has two constructors: one that takes in the buyer and renter IDs, and another that takes in
 * `Account` and `Renter` objects and extracts the IDs from them.
 * @author Ibrahim Rijal
 * @version 1.0
 * The class also has a `print` method that returns a string representation of the invoice.
 */
public class Invoice extends Serializable{
    public int buyerId;
    public int renterId;
    //public Date time;
    public PaymentStatus status = PaymentStatus.WAITING;
    public RoomRating rating = RoomRating.NONE;


    /**
     * This enum represents the possible payment statuses for an invoice.
     */
    public enum PaymentStatus
    {
        FAILED, WAITING, SUCCESS
    }


    /**
     * This enum represents the possible room ratings for an invoice.
     */
    public enum RoomRating
    {
        NONE, BAD, NEUTRAL, GOOD
    }

    /**
     * Constructs an `Invoice` object with the given buyer and renter IDs.
     *
     * @param buyerId the ID of the buyer
     * @param renterId the ID of the renter
     */
    protected Invoice(int buyerId,int renterId){

        this.buyerId=buyerId;
        this.renterId=renterId;
        //this.time=new Date();
        //this.buyerID=buyerID;
    }

    /**
     * Constructs an `Invoice` object with the given `Account` and `Renter` objects.
     * Extracts the buyer and renter IDs from the objects.
     *
     * @param buyer the buyer's account
     * @param renter the renter
     */
    public Invoice( Account buyer, Renter renter){
        //super(id);
        this.buyerId=getClosingId(buyer.getClass());
        this.renterId=getClosingId(renter.getClass());
        //this.time=new Date();
    }

    /**
     * Returns a string representation of the invoice.
     * @return A string representation of the invoice.
     */
    public String print(){
        //String buyID = (String)buyerID;
        String space = " ";
        String tmp = "Buyer ID :" + this.buyerId +"\nRenter ID :" +this.renterId+ "\nTime :" ;
        return tmp;
    }
}

