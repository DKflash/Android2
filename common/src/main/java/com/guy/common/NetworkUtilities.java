package com.guy.common;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtilities {

    private static Retrofit retrofitInstance;
    private static Retrofit retrofitInstanceTrades;

    public static Retrofit getRetrofitInstance() {
        if (retrofitInstance == null) {
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl("https://api.binance.com/") // Ensure this is the correct base URL
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }

    public static Retrofit getRetrofitInstanceTrades() {
        if (retrofitInstanceTrades == null) {
            retrofitInstanceTrades = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitInstanceTrades;
    }
}
