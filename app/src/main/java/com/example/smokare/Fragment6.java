package com.example.smokare;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smokare.R;
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
    Input input = new Input();
    List<Integer> nums = new ArrayList<>();
    List<String> labels = new ArrayList<>();


    public Fragment6() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        input.readFile("sample_data.txt", getContext());

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
        /**
         * day = 1
         * d = [1](0개 +)1~7 ... [5]29~말일(+ 35-말일개)
         *
         * day = 2
         * d = [1](1개 +)1~6 ... [5]28~말일(+ 36-말일개)
         *
         * day = 3
         * d = [1](2개 +)1~5 ... [5]27~말일(+ 37-말일개)
         *
         * day = 4
         * d = [1](3개 +)1~4 ... [5]26~말일(+ 38-말일개)
         *
         * day = 5
         * d = [1](4개 +)1~3 ... [5]25~말일(+ 39-말일개)
         *
         * day = 6 & 말일 = 30
         * d = [1](5개 +)1~2 ... [5]24~30(+ 0개)
         *
         * day = 6 & 말일 = 31
         * d = [1](5개 +)1~2 ... [5]24~30(+ 0개), [6]31(+ 6개)
         *
         * day = 7
         * d = [1](6개 +)1~1 ... [5]23~29(+ 0개), [6]30~말일(+ 36-말일개)
         */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        xAxis.setLabelCount(7, true);
        String[] labels2 = labels.toArray(new String[labels.size()]);
        xAxis.setValueFormatter(new MyXAxisValueFormatter(labels2));


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
