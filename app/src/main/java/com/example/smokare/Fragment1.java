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
    List<Integer> nums = new ArrayList<>();


    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Input input = new Input();
        input.readFile();

        int m = 11; // Timeline에서 받아와야함
        for(int d = 1; d <= 7; d++)
            if(input.getData()[m][d] != null) nums.add(input.getData()[m][d].size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_1,null);

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
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //x축 표시에 대한 위치 설정
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularityEnabled(true);
        //        xAxis.setGranularity(1f);
        xAxis.setLabelCount(10, true); // x축 레이블을 최대 몇 개 보여줄 지. force가 true이면 설정개수만큼 반드시 보여줌
        String[] values = {"1", "2", "3", "4", "5", "6", "7"};
        xAxis.setValueFormatter(new MyXAxisValueFormatter(values));

        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setTextColor(Color.BLACK);

        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);

//        Legend legend = barChart.getLegend(); //레전드 설정 (차트 밑에 색과 라벨을 나타내는 설정)
//        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);//하단 왼쪽에 설정
//        legend.setTextColor(ContextCompat.getColor(getContext(), R.color.textColor)); // 레전드 컬러 설정

        barChart.setVisibleXRangeMinimum(7); // 최소로 보여질 x축의 데이터 설정
        barChart.setVisibleXRangeMaximum(7); // 최대로 보여질 x축의 데이터 설정
        barChart.setDescription(null);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.setData(data);
        barChart.invalidate(); // refresh
    }

    public void chartUpdate() {
        return;
    }



}
