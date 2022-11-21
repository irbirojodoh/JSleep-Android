package com.RijalJSleepFN.request;

import com.RijalJSleepFN.model.*;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BaseApiService {
    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);

//    @GET("room/{id}")
//    Call<Room> getRoom (@Path("id") int id);

}
