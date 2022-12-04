package com.RijalJSleepFN.model;

import java.util.Date;

public class Invoice extends Serializable{
    public int buyerId;
    public int renterId;
    //public Date time;
    public PaymentStatus status = PaymentStatus.WAITING;
    public RoomRating rating = RoomRating.NONE;

    public enum PaymentStatus
    {
        FAILED, WAITING, SUCCESS
    }

    public enum RoomRating
    {
        NONE, BAD, NEUTRAL, GOOD
    }

    protected Invoice(int buyerId,int renterId){

        this.buyerId=buyerId;
        this.renterId=renterId;
        //this.time=new Date();
        //this.buyerID=buyerID;
    }
    public Invoice( Account buyer, Renter renter){
        //super(id);
        this.buyerId=getClosingId(buyer.getClass());
        this.renterId=getClosingId(renter.getClass());
        //this.time=new Date();
    }

    public String print(){
        //String buyID = (String)buyerID;
        String space = " ";
        String tmp = "Buyer ID :" + this.buyerId +"\nRenter ID :" +this.renterId+ "\nTime :" ;
        return tmp;
    }
}

