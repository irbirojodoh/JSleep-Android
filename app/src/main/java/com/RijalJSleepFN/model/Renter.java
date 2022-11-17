package com.RijalJSleepFN.model;
import com.RijalJSleepFN.model.Serializable;

public class Renter extends Serializable {
    
    public  String phoneNumber;
    public String username;
    public String address;

    // regex for username minimum 4 character maximum 20 character


    public static final String REGEX_NAME = "^[A-Z][a-zA-Z0-9_]{4,20}$";
    public static final String REGEX_PHONE = "^[0-9]{9,12}$";
   
   
/*    public Renter(int id, String username){
        this.username = username;
    }
*/
public Renter(String username, String phoneNumber, String address){

    this.username = username;
    this.phoneNumber = phoneNumber;
    this.address = address;
}
    /*
    public Renter(int id, String username, String phoneNumber){
        this.username = username;
        this.phoneNumber = phoneNumber;
    }

    public Renter(int id, String username, String phoneNumber, String address){

        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

     */

    public boolean validate(){
        return this.username.matches(REGEX_NAME) && this.phoneNumber.matches(REGEX_PHONE);
    }
}
