package com.example.segnorkopilkin.ui.chart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.segnorkopilkin.DBTransaction;
import com.example.segnorkopilkin.R;

import java.util.ArrayList;

public class ChartFragment extends Fragment {
    DBTransaction database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chart, container, false);
        initPieChartFrag();
        initDataBase(v);
        setRecycleView(v);

        return v;
    }

    public void initPieChartFrag() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        Fragment f = new PieChartFragment();
        transaction.add(R.id.pie_chart_in_chart_frag, f).commit();
    }

    public void initDataBase(View v) {
        database = new DBTransaction(v.getContext());
    }

    public void setRecycleView(View v) {
        ArrayList<Transaction> transactions = database.selectAll();
        TransactionsAdapter adapter = new TransactionsAdapter(v.getContext(), transactions);
        RecyclerView recyclerView = v.findViewById(R.id.recycle_view_in_chart_frag);
        recyclerView.setAdapter(adapter);
    }
}