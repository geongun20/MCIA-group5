package com.example.myapplication2;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Input extends AppCompatActivity {
    ArrayList<String>[][] data = new ArrayList[13][32];
    private final Calendar today = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void readFile(String filename, Context context) {
        AssetManager am = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String line = "";

        try {
            am = context.getResources().getAssets();
            is = am.open("sample_data.txt", Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            while ((line = br.readLine()) != null) {
                String[] line2 = line.split("\\.");
                int year = Integer.parseInt(line2[0]);
                int month = Integer.parseInt(line2[1]);
                int day = Integer.parseInt(line2[2]);
                int hour = Integer.parseInt(line2[3]);
                int minute = Integer.parseInt(line2[4]);
                int second = Integer.parseInt(line2[5]);

                if (data[month][day] == null)
                    data[month][day] = new ArrayList<>();
                data[month][day].add(line);
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

    public int getMonth() {
        return today.get(Calendar.MONTH) + 1; // 0(January) ~ 11
    }

    public int getDay() {
        return today.get(Calendar.DATE); // 1 ~ 31
    }

    public int getDayOfWeek() {
        return today.get(Calendar.DAY_OF_WEEK); // 1(Sunday) ~ 7
    }

    public int countToday(){
        int month = today.get(Calendar.MONTH) + 1;
        int day = today.get(Calendar.DATE);
        List<String> list = data[month][day];

        return list.size();
    }


    public int countThisWeek(){
        int month = today.get(Calendar.MONTH) + 1;
        int day = today.get(Calendar.DATE);
        int dayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        List<String> list = data[month][day];

        int sum = 0;
        for(int i = dayOfWeek; i >= 2 ; i--) {
            sum += list.size();
            if(day == 1) {
                month--;
                day = today.getActualMaximum(Calendar.DATE);
            }
            else {
                day--;
            }
            list = data[month][day];
        }

        return sum;
    }
}