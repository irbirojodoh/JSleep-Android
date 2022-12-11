package com.RijalJSleepFN.model;

import android.widget.Toast;

import com.RijalJSleepFN.model.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class represents an account in a rental system. It contains information about the account
 * owner, such as their name, email, and password, as well as their current balance and any
 * associated renter information.
 *
 * @author Ibrahim Rijal
 * @version 1.0
 */
public class Account extends Serializable {
    public String name;
    public String email;

    public double balance = 0;
    public String password;

    public Renter renter;

    public static final String REGEX_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
    public static final String REGEX_EMAIL = "^[a-zA_Z0-9]+@[a-zA-Z.]+.[a-zA-Z.]+[a-zA-Z]$";

    /**
     * Constructs a new `Account` object with the specified name, email, and password.
     *
     * @param name The name of the account owner.
     * @param email The email of the account owner.
     * @param password The password of the account.
     */
    public Account(String name, String email, String password){
       // super(id);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Validates the email and password of the account using the regex patterns.
     *
     * @return `true` if the email and password are valid, `false` otherwise.
     */
    public boolean validate(){
        return this.email.matches(REGEX_EMAIL) && this.password.matches(REGEX_PASSWORD);
    }

    /**
     * @deprecated
     */
    public boolean read(String xxx){
        return true;
    }

    //@Override
    /**
     * @deprecated
     */
    public Object write(){
        return null;
    }


    /**
     * Returns a string representation of the account.
     * @return A string representation of the account.
     */
    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                ", password='" + password + '\'' +
                ", renter=" + renter +
                '}';
    }


}


