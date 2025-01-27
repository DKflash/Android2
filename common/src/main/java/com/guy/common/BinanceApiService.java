package com.guy.common;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BinanceApiService {

    @GET("api/v3/ticker/price")
    Call<PriceResponse> getPrice(@Query("symbol") String symbol);
}
