package com.example.smokare;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Input extends AppCompatActivity {
    private ArrayList<String>[][] data = new ArrayList[13][32];
    private int[] monthly = new int[12];
    private final Calendar today = Calendar.getInstance();
    private int[] lastDateOfMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private int[] lastWeekOfMonth = {0, 5, 5, 6, 5, 5, 6, 5, 5, 5, 5, 5, 5};
    private String lastSmoke;
    private File dir = null ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dir = getExternalFilesDir(null);
    }

    public void readFile() {
        for(int m = 1; m <= 12; m++)
            for(int d = 1; d <= lastDateOfMonth[m]; d++)
                data[m][d] = new ArrayList<>();

        //AssetManager am = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String line = "";

        try {
            //am = context.getResources().getAssets();
            //is = am.open("sample_data.txt");
            is = new FileInputStream(dir+"/testfolder/output.txt");
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            while ((line = br.readLine()) != null) {
                String[] line2 = line.split("\\.");
                int year = Integer.parseInt(line2[0]);
                int month = Integer.parseInt(line2[1]);
                int date = Integer.parseInt(line2[2]);
                int hour = Integer.parseInt(line2[3]);
                int minute = Integer.parseInt(line2[4]);
                int second = Integer.parseInt(line2[5]);

                data[month][date].add(line);

                monthly[month-1]++;

                lastSmoke = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if (isr != null){
                    isr.close();
                }
                if(is != null){
                    is.close();
                }
                if(br != null){
                    br.close();
                }
            } catch(Exception e2){
                e2.getMessage();
            }
        }
    }


    public ArrayList<String>[][] getData() {
        return data;
    }

    public int getMonth() {
        return today.get(Calendar.MONTH) + 1; // 0(January) ~ 11
    }

    public int getDate() {
        return today.get(Calendar.DATE); // 1 ~ 31
    }

    public int getDay() {
        return today.get(Calendar.DAY_OF_WEEK); // 1(Sunday) ~ 7
    }

    public int getLastDateOfMonth(int m) {
        return lastDateOfMonth[m];
    }

    public String getLast() {
        return lastSmoke;
    }

    public int countToday() {
        int month = today.get(Calendar.MONTH) + 1;
        int date = today.get(Calendar.DATE);
        List<String> list = data[month][date];

        return list.size();
    }

    public int countThisWeek(){

        int month = today.get(Calendar.MONTH) + 1;
        int date = today.get(Calendar.DATE);
        int day = today.get(Calendar.DAY_OF_WEEK);
        List<String> list = data[month][date];

        int sum = 0;
        for(int i = day; i >= 1 ; i--) {
            sum += list.size();
            if(date == 1) {
                month--;
                date = today.getActualMaximum(Calendar.DATE);
            }
            else date--;

            list = data[month][date];
        }
        return sum;
    }

    public int countThisMonth(){

        int month = today.get(Calendar.MONTH) + 1;
        int date = today.get(Calendar.DATE);
        int day = today.get(Calendar.DAY_OF_WEEK);
        List<String> list = data[month][date];

        int sum = 0;
        for(int i = day; i >= 1 ; i--) {
            sum += list.size();
            if(date == 1) {
                month--;
                date = today.getActualMaximum(Calendar.DATE);
            }
            else date--;

            list = data[month][date];
        }
        return sum;
    }



    public List<Integer> countMonthly() {

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list.add(i, monthly[i]);
        }
        return list;
    }
}