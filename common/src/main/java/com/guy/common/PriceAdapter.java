package com.guy.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.PriceViewHolder> {

    private List<PriceResponse> priceList;

    public PriceAdapter(List<PriceResponse> priceList) {
        this.priceList = priceList;
    }

    @NonNull
    @Override
    public PriceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_price, parent, false);
        return new PriceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriceViewHolder holder, int position) {
        PriceResponse priceResponse = priceList.get(position);
        holder.symbolTextView.setText(priceResponse.getSymbol());
        holder.priceTextView.setText(priceResponse.getPrice());
    }

    @Override
    public int getItemCount() {
        return priceList.size();
    }

    public static class PriceViewHolder extends RecyclerView.ViewHolder {
        TextView symbolTextView;
        TextView priceTextView;

        public PriceViewHolder(@NonNull View itemView) {
            super(itemView);
            symbolTextView = itemView.findViewById(R.id.symbolTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
        }
    }
}
