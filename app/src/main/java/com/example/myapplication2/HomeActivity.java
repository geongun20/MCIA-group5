package com.example.myapplication2;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HomeActivity extends AppCompatActivity  {

    private static Handler mHandler;

    private boolean isFragmentA = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AssetManager am = getResources().getAssets();


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, new FragmentA());
        fragmentTransaction.commit();

        Button buttonTemp = findViewById(R.id.button0) ;
        buttonTemp.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_menu1: {
                        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.navigation_menu2: {
                        Intent intent = new Intent(HomeActivity.this, TimelineActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.navigation_menu3: {
                        Intent intent = new Intent(HomeActivity.this, ReportActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.navigation_menu4: {
                        Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        return true;
                    }
                }

                return false;
            }
        });

        Input input = new Input();
        input.readFile("sample_data.txt", getApplicationContext());
        List<String> list = input.getData()[input.getMonth()][input.getDay()];
        final String last_time = list.get(list.size() - 1);
        System.out.println(last_time);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                try{
                    Calendar cal = Calendar.getInstance();
                    Date now = cal.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
                    Date lsTime = sdf.parse(last_time);
                    //System.out.println(now);
                    //System.out.println(lsTime);
                    long diff = now.getTime() - lsTime.getTime();
                    long seconds = diff/1000;
                    long minutes = seconds/60;
                    long hours = minutes / 60;
                    long days = hours/24;

                    String strTime = String.format("%d days %d hours \n%d minutes and %d seconds", days, hours % 24, minutes % 60, seconds % 60);

                    TextView last_smoke = findViewById(R.id.after_last_smoke);
                    last_smoke.setText("From the last smoking...\n" + strTime);
                } catch (ParseException p){
                    p.printStackTrace();
                }
            }
        };

        class NewRunnable implements Runnable{
            @Override
            public void run(){
                while(true){
                    try{
                        Thread.sleep(1000);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    mHandler.sendEmptyMessage(0);
                }
            }
        }

        NewRunnable nr = new NewRunnable();
        Thread t = new Thread(nr);
        t.start();


        TextView today = findViewById(R.id.today);
        today.setText("Today\n" + String.valueOf(input.countToday()));

        TextView week = findViewById(R.id.this_week);
        week.setText("This WEEK\n" + String.valueOf(input.countThisWeek()));

    }


    public void switchFragment() {
        Fragment fr;
        fr = isFragmentA ? new FragmentB() : new FragmentA();
        isFragmentA = !isFragmentA;

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fr);
        fragmentTransaction.commit();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

}
