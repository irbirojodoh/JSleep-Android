package com.RijalJSleepFN.model;

import com.RijalJSleepFN.model.Serializable;

import java.lang.reflect.Array;
import java.util.*;

/**
 This class represents a room that is available for booking.
 @author Ibrahim Rijal
 */
public class Room extends Serializable {
    public int size;
    public String name;
    public String address;
    public ArrayList<Facility> facility;
    public Price price;
    public BedType bedType;
    public City city;

    public int accountId;
    public ArrayList<Date> booked;
    /**
     Constructs a new Room object with the specified parameters.
     @param accountId The ID of the account that owns the room
     @param name The name of the room
     @param size The size of the room in square feet
     @param price The price of the room
     @param facility The list of facilities available in the room
     @param city The city where the room is located
     @param address The address of the room
     @param bedType The type of bed in the room
     */
    public Room(int accountId, String name, int size, Price price, ArrayList<Facility> facility, City city, String address, BedType bedType) {
        this.accountId = accountId;
        this.name = name;
        this.size = size;
        this.price = price;
        this.facility.addAll(facility);
        this.bedType = bedType;
        this.city = city;
        this.address = address;
        this.booked = new ArrayList<>();
    }
    /**
     Returns a string representation of the Room object.
     @return A string representation of the Room object
     */
    @Override
    public String toString() {
        return "Room{" +
                "size=" + size +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", facility=" + facility +
                ", price=" + price +
                ", bedType=" + bedType +
                ", city=" + city +
                ", accountId=" + accountId +
                ", booked=" + booked +
                '}';
    }

    /**
     *
     * @param xxx
     * @return
     * @deprecated
     */
    // @Override
    public boolean read(String xxx){
        return true;
    }

    /**

     * @return
     * @deprecated
     */
    //@Override
    public Object write(){
        return null;
    }

    
}
