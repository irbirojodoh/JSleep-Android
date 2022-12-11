package com.RijalJSleepFN.request;
/**
 * A utility class for creating a Retrofit instance and getting a service for making API calls.
 */
public class UtilsApi {
    public static final String BASE_URL_API = "http://10.0.2.2:8080/";


    /**
     * Gets a service for making API calls using the specified base URL.
     *
     * @return A service for making API calls.
     */
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

}
