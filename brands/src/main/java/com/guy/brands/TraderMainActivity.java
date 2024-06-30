package com.guy.brands;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.guy.common.Activity_PanelBase;
import com.guy.common.BinanceApiService;
import com.guy.common.NetworkUtilities;
import com.guy.common.TradeAdapter;
import com.guy.common.PriceResponse;
import com.guy.common.Trade;
import com.guy.common.TradeApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TraderMainActivity extends Activity_PanelBase {

    private TextView currentPriceText;
    private Button tradeButton;
    private RecyclerView recyclerView;
    private TradeAdapter adapter;
    private List<Trade> tradeList = new ArrayList<>();
    private Handler handler = new Handler(Looper.getMainLooper());
    private static final String TAG = "TraderMainActivity";
    private static final int REFRESH_INTERVAL = 1000;
    private TradeApiService tradeApiService;
    private BinanceApiService binanceApiService;
    private boolean isTradeActive = false;
    private Trade currentTrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trader_main);

        currentPriceText = findViewById(R.id.currentPriceText);
        tradeButton = findViewById(R.id.tradeButton);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TradeAdapter(tradeList);
        recyclerView.setAdapter(adapter);

        tradeButton.setOnClickListener(v -> handleTradeButtonClick());

        tradeApiService = NetworkUtilities.getRetrofitInstanceTrades().create(TradeApiService.class);
        binanceApiService = NetworkUtilities.getRetrofitInstance().create(BinanceApiService.class);

        startPriceUpdates();
        loadTrades();
    }

    private void handleTradeButtonClick() {
        if (!isTradeActive) {
            // Buy
            fetchBinancePrice("BTCUSDT", price -> {
                currentTrade = new Trade();
                currentTrade.setSymbol("BTCUSDT");
                currentTrade.setEntryPrice(price);
                isTradeActive = true;
                tradeButton.setText("Sell");
                tradeButton.setBackgroundColor(getResources().getColor(R.color.button_sell));
            });
        } else {
            // Sell
            fetchBinancePrice("BTCUSDT", price -> {
                currentTrade.setExitPrice(price);
                currentTrade.setProfit(currentTrade.getExitPrice() - currentTrade.getEntryPrice());
                saveTrade(currentTrade);
                isTradeActive = false;
                tradeButton.setText("Buy");
                tradeButton.setBackgroundColor(getResources().getColor(R.color.button_buy));
            });
        }
    }

    private void saveTrade(Trade trade) {
        tradeApiService.saveTrade(trade).enqueue(new Callback<Trade>() {
            @Override
            public void onResponse(Call<Trade> call, Response<Trade> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Trade saved: " + trade.getSymbol() + " Entry Price: " + trade.getEntryPrice() + " Exit Price: " + trade.getExitPrice() + " Profit: " + trade.getProfit());
                    tradeList.add(trade);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Failed to save trade. Response code: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Trade> call, Throwable t) {
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
        binanceApiService.getPrice(symbol).enqueue(new Callback<PriceResponse>() {
            @Override
            public void onResponse(Call<PriceResponse> call, Response<PriceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        double price = Double.parseDouble(response.body().getPrice());
                        currentPriceText.setText("Current BTC Price: " + price);
                        Log.d(TAG, "Price: " + price);
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "Error parsing price: " + e.getMessage());
                    }
                } else {
                    Log.e(TAG, "Failed to fetch price. Response code: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PriceResponse> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }

    private void fetchBinancePrice(String symbol, final PriceCallback callback) {
        binanceApiService.getPrice(symbol).enqueue(new Callback<PriceResponse>() {
            @Override
            public void onResponse(Call<PriceResponse> call, Response<PriceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        double price = Double.parseDouble(response.body().getPrice());
                        callback.onPriceFetched(price);
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "Error parsing price: " + e.getMessage());
                    }
                } else {
                    Log.e(TAG, "Failed to fetch price. Response code: " + response.code() + " Message: " + response.message());
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
                    adapter.notifyDataSetChanged();
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

    interface PriceCallback {
        void onPriceFetched(double price);
    }
}
