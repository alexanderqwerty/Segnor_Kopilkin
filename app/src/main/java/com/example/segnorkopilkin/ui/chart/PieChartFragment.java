package com.example.segnorkopilkin.ui.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.segnorkopilkin.DBTransaction;
import com.example.segnorkopilkin.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;


public class PieChartFragment extends Fragment implements
        OnChartValueSelectedListener {
    DBTransaction database;
    private PieChart chart;
    public static final String get = "Получено";
    public static final String spent = "Потрачено";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pie_chart, container, false);
        database = new DBTransaction(v.getContext());

        chart = v.findViewById(R.id.pieChart);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setDrawHoleEnabled(false);
//        chart.setHoleColor(Color.TRANSPARENT);
//        chart.setHoleRadius(58f);
//        chart.setTransparentCircleRadius(61f);
//        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
//        chart.setOnChartValueSelectedListener(this);
        chart.animateY(1400, Easing.EaseInOutSine);
        chart.getLegend().setEnabled(false);
        chart.setEntryLabelColor(Color.WHITE);
        chart.setEntryLabelTextSize(12f);
        ArrayList<Transaction> transactions = database.selectAll();
        for (int i = 0; i < transactions.size(); i++) {
            setData(transactions.get(i).getSum());
        }

        return v;
    }


    ArrayList<PieEntry> entries = new ArrayList<>();

    ArrayList<Integer> colors = new ArrayList<>();

    public void setData(float value) {

        if (colors.isEmpty() && value > 0) {
            colors.add(Color.rgb(72, 191, 33));
            colors.add(Color.rgb(191, 33, 33));
        } else {
            colors.add(Color.rgb(191, 33, 33));
            colors.add(Color.rgb(72, 191, 33));
        }
        if (entries.isEmpty())
            if (value > 0)
                entries.add(new PieEntry(Math.abs(value), get, null));
            else
                entries.add(new PieEntry(Math.abs(value), spent, null));
        else if (entries.size() == 1)
            if (entries.get(0).getLabel().equals(get))
                if (value > 0)
                    entries.set(0, new PieEntry(entries.get(0).getValue() + Math.abs(value), get, null));
                else
                    entries.add(new PieEntry(Math.abs(value), spent, null));
            else {
                if (value > 0)
                    entries.add(new PieEntry(Math.abs(value), get, null));
                else
                    entries.set(0, new PieEntry(entries.get(0).getValue() + Math.abs(value), spent, null));
            }
        else {
            if (entries.get(0).getLabel().equals(get))
                if (value > 0)
                    entries.set(0, new PieEntry(entries.get(0).getValue() + Math.abs(value), get, null));
                else
                    entries.set(1, new PieEntry(entries.get(1).getValue() + Math.abs(value), spent, null));
            else {
                if (value > 0)
                    entries.set(1, new PieEntry(Math.abs(value), get, null));
                else
                    entries.set(0, new PieEntry(entries.get(0).getValue() + Math.abs(value), spent, null));
            }
        }
        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);


        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);


        chart.invalidate();
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}