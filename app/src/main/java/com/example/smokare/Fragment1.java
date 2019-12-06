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


public class Fragment1 extends Fragment {

    BarChart barChart;
    List<BarEntry> entries;
    BarDataSet dataSet;
    BarData data;

    Input input;
    List<Integer> nums = new ArrayList<>();
    List<String> labels = new ArrayList<>();


    public Fragment1() {
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
        if(firstDay >= 1) {
            for (int i = 0; i < firstDay - 1; i++) {
                nums.add(0);
                labels.add(" ");
            }
            for (int d = 1; d <= 8 - firstDay; d++) {
                nums.add(input.getData()[m][d].size());
                labels.add(d+"");
            }
        }
        else {
            for (int d = 1; d <= 8 - firstDay; d++) {
                nums.add(input.getData()[m][d].size());
                labels.add(d+"");
            }
        }

        int total = 0;
        for(int i = 0; i < 7; i++)
            total += nums.get(i);
        nums.add(total);
        labels.add("Total");

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
         * d = [1](6개 +)1~1 ... [5]23~29(+ 0개), [6]~말일(+ 36-말일개)
         */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_1,null);

        nums.clear();
        labels.clear();

        int m = TimelineActivity.pickedMonth;
        int firstDay = input.getFirstDayOfMonth(m);
        if(firstDay >= 1) {
            for (int i = 0; i < firstDay - 1; i++) {
                nums.add(0);
                labels.add(" ");
            }
            for (int d = 1; d <= 8 - firstDay; d++) {
                nums.add(input.getData()[m][d].size());
                labels.add(d+"");
            }
        }
        else {
            for (int d = 1; d <= 8 - firstDay; d++) {
                nums.add(input.getData()[m][d].size());
                labels.add(d+"");
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
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //x축 표시에 대한 위치 설정
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularityEnabled(true);
//        xAxis.setGranularity(1f);
        xAxis.setLabelCount(8, true); // x축 레이블을 최대 몇 개 보여줄 지. force가 true이면 설정개수만큼 반드시 보여줌
        String[] labels2 = labels.toArray(new String[labels.size()]);
        xAxis.setValueFormatter(new MyXAxisValueFormatter(labels2));

        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setTextColor(Color.BLACK);

        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);

//        Legend legend = barChart.getLegend(); //레전드 설정 (차트 밑에 색과 라벨을 나타내는 설정)
//        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);//하단 왼쪽에 설정
//        legend.setTextColor(ContextCompat.getColor(getContext(), R.color.textColor)); // 레전드 컬러 설정

        barChart.setVisibleXRangeMinimum(8); // 최소로 보여질 x축의 데이터 설정
        barChart.setVisibleXRangeMaximum(8); // 최대로 보여질 x축의 데이터 설정
        barChart.setDescription(null);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.setData(data);
        barChart.invalidate(); // refresh
    }

    public void chartUpdate() {
        return;
    }
}
