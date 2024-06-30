package com.guy.common;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TradeApiService {
    @POST("/trade")
    Call<Trade> saveTrade(@Body Trade trade);

    @GET("/trades")
    Call<List<Trade>> getTrades();
}
