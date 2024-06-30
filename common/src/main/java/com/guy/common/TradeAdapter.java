package com.guy.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class TradeAdapter extends RecyclerView.Adapter<TradeAdapter.TradeViewHolder> {

    private List<Trade> tradeList;

    public TradeAdapter(List<Trade> tradeList) {
        this.tradeList = tradeList;
    }

    @NonNull
    @Override
    public TradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trade_item, parent, false);
        return new TradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TradeViewHolder holder, int position) {
        Trade trade = tradeList.get(position);
        holder.symbolText.setText("Currency: " + trade.getSymbol());
        holder.entryPriceText.setText("Bought at: " + trade.getEntryPrice());
        holder.exitPriceText.setText("Sold at: " + trade.getExitPrice());
        holder.profitText.setText("Profit: " + trade.getProfit());
    }

    @Override
    public int getItemCount() {
        return tradeList.size();
    }

    static class TradeViewHolder extends RecyclerView.ViewHolder {
        TextView symbolText;
        TextView entryPriceText;
        TextView exitPriceText;
        TextView profitText;

        TradeViewHolder(@NonNull View itemView) {
            super(itemView);
            symbolText = itemView.findViewById(R.id.symbolText);
            entryPriceText = itemView.findViewById(R.id.entryPriceText);
            exitPriceText = itemView.findViewById(R.id.exitPriceText);
            profitText = itemView.findViewById(R.id.profitText);
        }
    }
}
