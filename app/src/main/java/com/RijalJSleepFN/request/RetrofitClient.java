package com.RijalJSleepFN.request;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A class for creating a Retrofit instance.
 */
public class RetrofitClient     {
    private static Retrofit retrofit = null;

    /**
     * Gets a Retrofit instance with the specified base URL.
     * If an instance has already been created, it will be returned.
     *
     * @param baseUrl The base URL for the Retrofit instance.
     * @return A Retrofit instance.
     */  /**
     * Gets a Retrofit instance with the specified base URL.
     * If an instance has already been created, it will be returned.
     *
     * @param baseUrl The base URL for the Retrofit instance.
     * @return A Retrofit instance.
     */
        public static Retrofit getClient(String baseUrl) {
            if (retrofit==null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
}
