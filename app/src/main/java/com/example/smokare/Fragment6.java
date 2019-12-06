package com.example.smokare;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class Fragment6 extends Fragment {

    BarChart barChart;
    List<BarEntry> entries;
    BarDataSet dataSet;
    BarData data;
    Input input;
    List<Integer> nums = new ArrayList<>();
    List<String> labels = new ArrayList<>();


    public Fragment6() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        input = new Input();
        input.readFile();
//        input.readFile2("sample_data.txt", getContext());

        int m = TimelineActivity.pickedMonth;
        int firstDay = input.getFirstDayOfMonth(m);
        int lastDate = input.getLastDateOfMonth(m);
        if (firstDay == 6 && lastDate == 31) {
            for (int d = 37 - firstDay; d <= lastDate; d++) {
                nums.add(input.getData()[m][d].size());
                labels.add(d+"");
            }
            for (int i = 0; i < 6; i++) {
                nums.add(0);
                labels.add(" ");
            }
        }
        else if(firstDay == 7) {
            for(int d = 37-firstDay; d <= lastDate; d++) {
                nums.add(input.getData()[m][d].size());
                labels.add(d+"");
            }
            for(int i = 0; i < 36-lastDate; i++) {
                nums.add(0);
                labels.add(" ");
            }
        }

        int total = 0;
        for(int i = 0; i < 7; i++)
            total += nums.get(i);
        nums.add(total);
        labels.add("Total");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_5,null);

        nums.clear();
        labels.clear();

        int m = TimelineActivity.pickedMonth;
        int firstDay = input.getFirstDayOfMonth(m);
        int lastDate = input.getLastDateOfMonth(m);
        if (firstDay == 6 && lastDate == 31) {
            for (int d = 37 - firstDay; d <= lastDate; d++) {
                nums.add(input.getData()[m][d].size());
                labels.add(d+"");
            }
            for (int i = 0; i < 6; i++) {
                nums.add(0);
                labels.add(" ");
            }
        }
        else if(firstDay == 7) {
            for(int d = 37-firstDay; d <= lastDate; d++) {
                nums.add(input.getData()[m][d].size());
                labels.add(d+"");
            }
            for(int i = 0; i < 36-lastDate; i++) {
                nums.add(0);
                labels.add(" ");
            }
        }

        int total = 0;
        for(int i = 0; i < 7; i++)
            total += nums.get(i);
        nums.add(total);
        labels.add("Total");

        chartInit(v);
        return v;
    }

    private void chartInit(View view) {

        barChart = view.findViewById(R.id.barChart);
        barChart.setAutoScaleMinMaxEnabled(true);

        entries = new ArrayList<BarEntry>();
        for(int i = 0; i < nums.size(); i++)
            entries.add(new BarEntry(i, nums.get(i)));

        dataSet = new BarDataSet(entries, "Number of cigarettes (SUN - SAT)");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setDrawValues(true);

        data = new BarData(dataSet);
        data.setBarWidth(0.9f);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelCount(8, true);
        String[] labels2 = labels.toArray(new String[labels.size()]);
        xAxis.setValueFormatter(new MyXAxisValueFormatter(labels2));


        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setTextColor(Color.BLACK);

        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);

//        Legend legend = barChart.getLegend();
//        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//        legend.setTextColor(ContextCompat.getColor(getContext(), R.color.textColor));

        barChart.setVisibleXRangeMinimum(8);
        barChart.setVisibleXRangeMaximum(8);
        barChart.setDescription(null);
        barChart.setFitBars(true);
        barChart.setData(data);
        barChart.invalidate();
    }

    public void chartUpdate() {
        return;
    }



}
