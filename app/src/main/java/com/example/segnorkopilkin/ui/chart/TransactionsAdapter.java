package com.example.segnorkopilkin.ui.chart;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.segnorkopilkin.R;

import java.util.Date;
import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<Transaction> transactions;

    public TransactionsAdapter(Context context, List<Transaction> transactions) {
        this.transactions = transactions;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.unit_recycleview_for_chartfrag, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.date.setText(dateToString(transaction.getDate()));
        if (transaction.getSum() > 0) {
            holder.sum.setTextColor(Color.GREEN);
            holder.sum.setText(String.valueOf(transaction.getSum()));
        } else {
            holder.sum.setTextColor(Color.RED);
            holder.sum.setText(String.valueOf(-transaction.getSum()));
        }
    }

    private String dateToString(Long date) {
        Date d = new Date(date);
        StringBuilder builder = new StringBuilder();
        builder.append("Y:"+d.getYear()+" ");
        builder.append("M"+d.getMonth()+" ");
        builder.append("D"+d.getDay());
        return builder.toString();
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, sum;

        public ViewHolder(@NonNull View v) {
            super(v);
            date = v.findViewById(R.id.tv_date);
            sum = v.findViewById(R.id.tv_sum);
        }
    }
}
