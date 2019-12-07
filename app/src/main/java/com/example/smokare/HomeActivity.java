package com.example.smokare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HomeActivity extends AppCompatActivity  {

    private static Handler mHandler;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    Button manualButton;
    Input input;
    FileOutputStream fos;
    File file;

    // private boolean isFragmentA = true;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        input = new Input();
        input.readFile(getExternalFilesDir(null));
//      input.readFile2("sample_data.txt", getApplicationContext());

        final List<String> list = input.getData()[input.getMonthOfToday()][input.getDateOfToday()];

        ListView lv = findViewById(R.id.listView1);
        String[] arr = list.toArray(new String[0]);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arr);
        lv.setAdapter(adapter);

        // no use fragment
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.add(R.id.frameLayout, new FragmentA());
//        fragmentTransaction.commit();

        // Button buttonTemp = findViewById(R.id.button0) ;
//        buttonTemp.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchFragment();
//            }
//        });

        manualButton = findViewById(R.id.button1);
        manualButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                file = new File(getExternalFilesDir(null)+"/testfolder/output.txt");

                String outstr = input.readAllBytesJava7(getExternalFilesDir(null) + "/testfolder/output.txt");
                outstr = (sdf.format(timestamp) + "\n") + outstr;
                Log.d("outstring", outstr);

                writeFile(file, outstr.getBytes());

                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
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


            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                        try {
                            Calendar cal = Calendar.getInstance();
                            Date now = cal.getTime();


                            if(list.size()==0) {
                                TextView fromLast = findViewById(R.id.after_last_smoke);
                                fromLast.setText("No Data!");
                            }else {
                                final String last_time = list.get(list.size() - 1);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
                                Date lsTime = sdf.parse(last_time);

                                long diff = now.getTime() - lsTime.getTime();
                                long seconds = diff / 1000;
                                long minutes = seconds / 60;
                                long hours = minutes / 60;
                                long days = hours / 24;
                                String strTime = "";
                                if (days == 0)
                                    strTime = String.format("%dh %dm %ds", hours % 24, minutes % 60, seconds % 60);
                                else if (days == 1)
                                    strTime = String.format("%d day\n%dh %dm %ds", days, hours % 24, minutes % 60, seconds % 60);
                                else
                                    strTime = String.format("%d days\n%dh %dm %ds", days, hours % 24, minutes % 60, seconds % 60);
                                TextView fromLast = findViewById(R.id.after_last_smoke);
                                fromLast.setText("From LAST Smoking\n" + strTime);

                            }
                        } catch (ParseException p) {
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
        today.setText("TODAY\n" + String.valueOf(input.countToday()));

        TextView week = findViewById(R.id.this_week);
        week.setText("THIS WEEK\n" + String.valueOf(input.countThisWeek()));
    }

    private boolean writeFile(File file , byte[] file_content){
        boolean result;
        if(file!=null&&file.exists()&&file_content!=null){
            try {
                fos = new FileOutputStream(file);
                try {
                    fos.write(file_content);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            result = true;
        }else{
            result = false;
        }
        return result;
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();

        // Remove the activity when its off the screen
        finish();
    }
}
