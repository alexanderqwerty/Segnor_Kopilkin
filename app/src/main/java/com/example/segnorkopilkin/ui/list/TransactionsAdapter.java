package com.example.segnorkopilkin.ui.list;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.segnorkopilkin.R;
import com.example.segnorkopilkin.ui.chart.Transaction;

import java.util.Date;
import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {
    List<Transaction> transactions;
    LayoutInflater inflater;


    public TransactionsAdapter(Context context, OnDeleteTransactionListener listener,List<Transaction> transactions) {
        this.transactions = transactions;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.unit_recycleview, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        if (transaction.getSum() > 0) {
            holder.sum.setTextColor(Color.GREEN);
            holder.sum.setText(Math.abs(transaction.getSum()) + "");
            holder.name.setText(transaction.getSender());
        } else {
            holder.sum.setTextColor(Color.RED);
            holder.sum.setText(Math.abs(transaction.getSum()) + "");
            holder.name.setText(transaction.getRecipient());
        }
        holder.date.setText(dateToString(transaction.getDate()));
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public interface OnDeleteTransactionListener {
        void onDeleteTransaction(int id);
    }
    private String dateToString(Long date) {
        Date d = new Date(date);
        StringBuilder builder = new StringBuilder();
        builder.append("Y:"+d.getYear()+" ");
        builder.append("M"+d.getMonth()+" ");
        builder.append("D"+d.getDay());
        return builder.toString();
    }
    OnDeleteTransactionListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, PopupMenu.OnMenuItemClickListener {
        TextView date, name, sum;

        public ViewHolder(@NonNull View v) {
            super(v);
            date = v.findViewById(R.id.year_mouth_date);
            name = v.findViewById(R.id.text_view_name);
            sum = v.findViewById(R.id.text_view_sum);

            v.setOnLongClickListener(this::onLongClick);
        }

        @Override
        public boolean onLongClick(View v) {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.inflate(R.menu.menu_popup);
            popupMenu.setOnMenuItemClickListener(this::onMenuItemClick);
            popupMenu.show();
            return true;
        }


        @Override
        public boolean onMenuItemClick(MenuItem item) {
            listener.onDeleteTransaction(getAdapterPosition());
            return true;
        }
    }
}
