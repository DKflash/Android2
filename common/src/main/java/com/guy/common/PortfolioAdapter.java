package com.guy.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Map;

public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.ViewHolder> {

    private final List<Map.Entry<String, Double>> portfolioEntries;

    public PortfolioAdapter(List<Map.Entry<String, Double>> portfolioEntries) {
        this.portfolioEntries = portfolioEntries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_portfolio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map.Entry<String, Double> entry = portfolioEntries.get(position);
        holder.symbolTextView.setText(entry.getKey());
        holder.amountTextView.setText(String.valueOf(entry.getValue()));
    }

    @Override
    public int getItemCount() {
        return portfolioEntries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView symbolTextView;
        TextView amountTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            symbolTextView = itemView.findViewById(R.id.symbolTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
        }
    }
}
