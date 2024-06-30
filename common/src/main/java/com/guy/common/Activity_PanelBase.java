package com.guy.common;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import java.util.List;

public abstract class Activity_PanelBase extends AppCompatActivity {

    protected TradeManager tradeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tradeManager = TradeManager.getInstance();
    }

    protected void saveTrade(double amount) {
        tradeManager.saveTrade(amount);
    }

    protected List<Double> getTrades() {
        return tradeManager.getTrades();
    }
}
