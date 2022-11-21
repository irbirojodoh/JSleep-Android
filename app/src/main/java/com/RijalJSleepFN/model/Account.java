package com.RijalJSleepFN.model;

import android.widget.Toast;

import com.RijalJSleepFN.model.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Account extends Serializable {
    public String name;
    public String email;

    public double balance = 0;
    public String password;

    public Renter renter;

    public static final String REGEX_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
    public static final String REGEX_EMAIL = "^[a-zA_Z0-9]+@[a-zA-Z.]+.[a-zA-Z.]+[a-zA-Z]$";

    /** */
    public Account(String name, String email, String password){
       // super(id);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean validate(){
        return this.email.matches(REGEX_EMAIL) && this.password.matches(REGEX_PASSWORD);
    }

    //@Override
    public boolean read(String xxx){
        return true;
    }

    //@Override
    public Object write(){
        return null;
    }

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


