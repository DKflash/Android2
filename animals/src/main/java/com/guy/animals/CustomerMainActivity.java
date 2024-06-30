package com.guy.animals;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.guy.common.Activity_PanelBase;
import com.guy.common.BinanceApiService;
import com.guy.common.NetworkUtilities;
import com.guy.common.PriceResponse;
import com.guy.common.Trade;
import com.guy.common.TradeAdapter;
import com.guy.common.TradeApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerMainActivity extends Activity_PanelBase {

    private RecyclerView portfolioRecyclerView;
    private TextView portfolioValueText;
    private TradeApiService tradeApiService;
    private TradeAdapter tradeAdapter;
    private List<Trade> tradeList = new ArrayList<>();
    private Handler handler = new Handler(Looper.getMainLooper());
    private static final String TAG = "CustomerMainActivity";
    private static final int REFRESH_INTERVAL = 1000;
    private static final double INITIAL_BALANCE = 100000.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        portfolioValueText = findViewById(R.id.portfolioValueText);
        portfolioRecyclerView = findViewById(R.id.portfolioRecyclerView);
        portfolioRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tradeAdapter = new TradeAdapter(tradeList);
        portfolioRecyclerView.setAdapter(tradeAdapter);

        tradeApiService = NetworkUtilities.getRetrofitInstanceTrades().create(TradeApiService.class);

        startPortfolioUpdates();
        loadTrades();
    }

    private void startPortfolioUpdates() {
        handler.postDelayed(() -> {
            loadPortfolio();
            handler.postDelayed(this::startPortfolioUpdates, REFRESH_INTERVAL);
        }, REFRESH_INTERVAL);
    }

    private void loadPortfolio() {
        tradeApiService.getTrades().enqueue(new Callback<List<Trade>>() {
            @Override
            public void onResponse(Call<List<Trade>> call, Response<List<Trade>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Trade> trades = response.body();
                    double portfolioValue = INITIAL_BALANCE + trades.stream()
                            .mapToDouble(trade -> trade.getExitPrice() - trade.getEntryPrice())
                            .sum();
                    portfolioValueText.setText("Portfolio Value: " + portfolioValue);
                    Log.d(TAG, "Portfolio loaded: " + portfolioValue);
                } else {
                    Log.e(TAG, "Failed to load portfolio");
                }
            }

            @Override
            public void onFailure(Call<List<Trade>> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }

    private void startPriceUpdates() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchBinancePrice("BTCUSDT");
                handler.postDelayed(this, REFRESH_INTERVAL);
            }
        }, REFRESH_INTERVAL);
    }

    private void fetchBinancePrice(String symbol) {
        BinanceApiService apiService = NetworkUtilities.getRetrofitInstance().create(BinanceApiService.class);
        Call<PriceResponse> call = apiService.getPrice(symbol);
        call.enqueue(new Callback<PriceResponse>() {
            @Override
            public void onResponse(Call<PriceResponse> call, Response<PriceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Price: " + response.body().getPrice());
                } else {
                    Log.e(TAG, "Failed to fetch price");
                }
            }

            @Override
            public void onFailure(Call<PriceResponse> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }

    private void loadTrades() {
        tradeApiService.getTrades().enqueue(new Callback<List<Trade>>() {
            @Override
            public void onResponse(Call<List<Trade>> call, Response<List<Trade>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tradeList.clear();
                    tradeList.addAll(response.body());
                    tradeAdapter.notifyDataSetChanged();
                    Log.d(TAG, "Trades loaded");
                } else {
                    Log.e(TAG, "Failed to load trades. Response code: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Trade>> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }
}
