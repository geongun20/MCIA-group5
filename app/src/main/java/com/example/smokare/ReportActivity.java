package com.example.smokare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ReportActivity extends AppCompatActivity {
    private static Handler mHandler;
    Button alert;
    int value;
    EditText valueEditText;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
      
        ProgressBar progress = (ProgressBar) findViewById(R.id.progress);
        TextView report_main = findViewById(R.id.report_main_text);
        Global_Variable global = (Global_Variable) getApplication();
        value = global.getReport_value();

        if(value == 0){
            report_main.setText("Please enter your goal\nto quit smoking!");
            progress.setMax(100);
            progress.setProgress(0);
        }
        else {
            report_main.setText(String.format("\nMY GOAL\nto quit smoking\n%d DAYS", value));
            String content = report_main.getText().toString();
            SpannableString spannableString = new SpannableString(content);
            int start = 25;
            int end = content.length();

            spannableString.setSpan(new RelativeSizeSpan(2f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#26689A")), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

            report_main.setText(spannableString);
            progress.setMax(86400 * value);
        }

        Button applyButton = (Button) findViewById(R.id.apply);
        applyButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueEditText = (EditText) findViewById(R.id.edit);
                ProgressBar progress = (ProgressBar) findViewById(R.id.progress);

                try {
                    // 문자열을 숫자로 변환.
                    value = Integer.parseInt(valueEditText.getText().toString());
                    TextView report_main = findViewById(R.id.report_main_text);
                    report_main.setText(String.format("\nMY GOAL\nto quit smoking\n%d DAYS", value));

                    String content = report_main.getText().toString();
                    SpannableString spannableString = new SpannableString(content);
                    int start = 25;
                    int end = content.length();

                    spannableString.setSpan(new RelativeSizeSpan(2f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#26689A")), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

                    report_main.setText(spannableString);

                    progress.setMax(86400 * value);
                } catch (Exception e) {
                    // 토스트(Toast) 메시지 표시.
                    Toast toast = Toast.makeText(ReportActivity.this, "Invalid number format",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_menu1: {
                        Intent intent = new Intent(ReportActivity.this, HomeActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.navigation_menu2: {
                        Intent intent = new Intent(ReportActivity.this, TimelineActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.navigation_menu3: {
                        Intent intent = new Intent(ReportActivity.this, ReportActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.navigation_menu4: {
                        Intent intent = new Intent(ReportActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        return true;
                    }
                }
                return false;
            }
        });

        Input input = new Input();
        input.readFile(getExternalFilesDir(null));
        final List<String> list = input.getData()[input.getMonthOfToday()][input.getDateOfToday()];
        final float average = input.Calculate_average();
        final int price = Integer.parseInt(global.getPrice());
        alert = (Button) findViewById(R.id.button);
        alert.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                if (view == alert) {
                    Context mContext = getApplicationContext();
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                    View layout = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.popup));
                    AlertDialog.Builder aDialog = new AlertDialog.Builder(ReportActivity.this, R.style.MyDialogTheme);

                    aDialog.setTitle("How to quit smoking?");
                    aDialog.setView(layout);

                    aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    AlertDialog ad = aDialog.create();
                    ad.show();
                }
            }
        });

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                try {
                    Calendar cal = Calendar.getInstance();
                    Date now = cal.getTime();


                    if(list.size()==0) {
                        TextView last_smoke = findViewById(R.id.report_text_1_2);
                        last_smoke.setText("No data!");
                    }else{
                        final String last_time = list.get(0);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
                        Date lsTime = sdf.parse(last_time);
                        //System.out.println(now);
                        //System.out.println(lsTime);

                        //86400초 = 하루


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

                        TextView last_smoke = findViewById(R.id.report_text_1_2);
                        last_smoke.setText("From LAST Smoking\n" + strTime);
                        ProgressBar progress = (ProgressBar) findViewById(R.id.progress);

                        // 변환된 값을 프로그레스바에 적용.
                        progress.setProgress((int) seconds);

                        TextView save_price = findViewById(R.id.report_text_3_2);
                        save_price.setText(String.format("%.2f won", seconds * average * price));
                        TextView life_extension = findViewById(R.id.report_text_2_2);
                        DecimalFormat form = new DecimalFormat("#.#");
                        life_extension.setText(String.format("You could live %.2f more minutes", (double) seconds * average * (13.8)));


                    }
                } catch (ParseException p) {
                    p.printStackTrace();
                }
            }
        };

        class NewRunnable implements Runnable {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mHandler.sendEmptyMessage(0);
                }
            }
        }

        NewRunnable nr = new NewRunnable();
        Thread t = new Thread(nr);
        t.start();
    }
  
    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();

        // Remove the activity when its off the screen
        finish();
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        System.out.println(value);
        Global_Variable global = (Global_Variable) getApplication();
        global.setReport(value);
        savedInstanceState.putInt("value", value);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
