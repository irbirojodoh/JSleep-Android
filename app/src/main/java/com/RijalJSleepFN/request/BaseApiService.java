package com.RijalJSleepFN.request;

import com.RijalJSleepFN.model.*;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);

    @POST("account/login")
    Call<Account> login(@Query("email") String email, @Query("password") String password);

    @POST("account/register")
    Call<Account> register(@Query("name") String name, @Query("email") String email, @Query("password") String password);

    @POST("account/{id}/registerRenter")
    Call<Renter> renter(@Path("id") int id, @Query("username") String username, @Query("address") String address, @Query("phoneNumber") String phoneNumber);

    @POST("room/create")
    Call<Room> room(@Query("accountId") int accountId, @Query("name") String name, @Query("size") int size, @Query("price") int price, @Query("facility") ArrayList<Facility> facility, @Query("city") String city, @Query("address") String address, @Query("bedType") BedType bedType);

//    @GET("room/{id}")
//    Call<Room> getRoom (@Path("id") int id);

}
