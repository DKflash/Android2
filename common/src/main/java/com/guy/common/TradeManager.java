package com.guy.common;

import java.util.ArrayList;
import java.util.List;

public class TradeManager {

    private static TradeManager instance;
    private List<Double> trades;
    private double portfolioValue;

    private TradeManager() {
        trades = new ArrayList<>();
        portfolioValue = 50000; // Initial portfolio value
    }

    public static synchronized TradeManager getInstance() {
        if (instance == null) {
            instance = new TradeManager();
        }
        return instance;
    }

    public synchronized void saveTrade(double amount) {
        trades.add(amount);
        portfolioValue += amount;
    }

    public synchronized double getPortfolioValue() {
        return portfolioValue;
    }

    public synchronized List<Double> getTrades() {
        return new ArrayList<>(trades);
    }
}
