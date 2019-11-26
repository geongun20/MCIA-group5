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
    ArrayList<String>[][] data2 = new ArrayList[13][32];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 코드 계속 ...
        super.onCreate(savedInstanceState);
    }

    public void input(String filename, Context context) {


        //System.out.println("Successsss");

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
                //System.out.println(line);

                String[] line2 = line.split("\\.");
                int year = Integer.parseInt(line2[0]);
                int month = Integer.parseInt(line2[1]);
                int day = Integer.parseInt(line2[2]);
                int hour = Integer.parseInt(line2[3]);
                int minute = Integer.parseInt(line2[4]);
                int second = Integer.parseInt(line2[5]);

                if (data2[month][day] == null)
                    data2[month][day] = new ArrayList<>();
                data2[month][day].add(line);
                //System.out.println("H");
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
        Calendar today = Calendar.getInstance();
        return today.get(Calendar.MONTH) + 1;
    }

    public int getDay() {
        Calendar today = Calendar.getInstance();
        return today.get(Calendar.DATE);
    }

    public int countToday(){
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH) + 1;
        int day = today.get(Calendar.DATE) ;
        List<String> s = data2[month][day];
        //System.out.println(data2[month][day].size());
        return s.size();
    }


    public int countThisWeek(){
        Calendar today = Calendar.getInstance();
        int month = today.get(Calendar.MONTH) + 1;
        int day = today.get(Calendar.DATE);
        //System.out.println(day);
        int dayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        List<String> s = data2[month][day];


        int sum = 0;
        System.out.println(dayOfWeek);
        for(int i = dayOfWeek; i >= 2 ; i--){
            sum += s.size();
            if(day == 1) {
                month--;
                day = today.getActualMaximum(Calendar.DATE);
            }
            else {
                day--;
            }
            s = data2[month][day];
            System.out.println(sum);
            System.out.println(day);
        }
        return sum;
    }
}