package com.example.myapplications2;

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


public class Fragment5 extends Fragment {

    BarChart barChart;
    List<BarEntry> entries;
    BarDataSet dataSet;
    BarData data;
    Input input = new Input();
    List<Integer> nums = new ArrayList<>();
    private static int lastDay;


    public Fragment5() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        input.readFile();
        lastDay = input.getLastDayOfMonth(m);

        int m = TimelineActivity.pickedMonth;
        for(int d = 29; d <= input.getLastDateOfMonth(m); d++)
            if(input.getData()[m][d] != null) nums.add(input.getData()[m][d].size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_5,null);

        int m = TimelineActivity.pickedMonth;
        nums.clear();
        for(int d = 29; d <= input.getLastDateOfMonth(m); d++)
            if(input.getData()[m][d] != null) nums.add(input.getData()[m][d].size());

        chartInit(v, nums);
        return v;
    }

    private void chartInit(View view, List<Integer> valList) {

        barChart = view.findViewById(R.id.barChart);
        barChart.setAutoScaleMinMaxEnabled(true);

        entries = new ArrayList<BarEntry>();
        for(int i = 0; i < valList.size(); i++)
            entries.add(new BarEntry(i, valList.get(i)));

        dataSet = new BarDataSet(entries, "number of cigarettes");
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
        xAxis.setLabelCount(10, true);
        String[] values30 = {"29", "30"};
        String[] values31 = {"29", "30", "31"};

        if(lastDay == 30)
            xAxis.setValueFormatter(new MyXAxisValueFormatter(values30));
        else if(lastDay == 31)
            xAxis.setValueFormatter(new MyXAxisValueFormatter(values31));


        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setTextColor(Color.BLACK);

        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);

//        Legend legend = barChart.getLegend();
//        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//        legend.setTextColor(ContextCompat.getColor(getContext(), R.color.textColor));

        barChart.setVisibleXRangeMinimum(7);
        barChart.setVisibleXRangeMaximum(7);
        barChart.setDescription(null);
        barChart.setFitBars(true);
        barChart.setData(data);
        barChart.invalidate();
    }

    public void chartUpdate() {
        return;
    }



}
