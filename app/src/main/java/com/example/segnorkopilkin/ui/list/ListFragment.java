package com.example.segnorkopilkin.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.segnorkopilkin.DBTransaction;
import com.example.segnorkopilkin.R;
import com.example.segnorkopilkin.ui.chart.Transaction;

import java.util.ArrayList;

public class ListFragment extends Fragment implements TransactionsAdapter.OnDeleteTransactionListener {
    DBTransaction database;
    ArrayList<Transaction> transactions;
    RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        database = new DBTransaction(v.getContext());
        setRecycleView(v);
        return v;
    }

    private void setRecycleView(View v) {
        transactions = database.selectAll();
        TransactionsAdapter adapter = new TransactionsAdapter(v.getContext(),this::onDeleteTransaction,transactions);
        recyclerView = v.findViewById(R.id.recycle_view_in_list_frag);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDeleteTransaction(int pos) {
        database.delete(transactions.get(pos).getId());
        transactions.remove(pos);
        TransactionsAdapter adapter = new TransactionsAdapter(getView().getContext(),this::onDeleteTransaction,transactions);
        recyclerView.setAdapter(adapter);
    }
}