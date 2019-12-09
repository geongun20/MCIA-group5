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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ReportActivity extends AppCompatActivity {

    TextView textViewMain;
    ProgressBar progress;
    EditText editTextValue;
    Button buttonApply;
    TextView textViewLast;
    TextView textViewLife;
    TextView textViewMoney;
    Button buttonAlert;
    View layout;
    BottomNavigationView bottomNavigationView;

    private static Handler mHandler;

    Input input;
    Global_Variable global;
    int value;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        input = new Input();
        input.readFile(getExternalFilesDir(null));

        textViewMain = findViewById(R.id.report_main_text);
        progress = findViewById(R.id.progress);
        global = (Global_Variable) getApplication();
        value = global.getReport_value();
        if(value == 0) {
            textViewMain.setText("Please enter\nthe goal number of days\nyou will not smoke!");
            progress.setMax(100);
            progress.setProgress(0);
        }
        else {
            if(value == 1) textViewMain.setText(String.format("\nMY GOAL:\nNo Smoking For\n%d DAY", value));
            else textViewMain.setText(String.format("\nMY GOAL:\nNo Smoking For\n%d DAYS", value));
            String content = textViewMain.getText().toString();
            SpannableString spannableString = new SpannableString(content);
            int start = 25;
            int end = content.length();

            spannableString.setSpan(new RelativeSizeSpan(2f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#26689A")), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

            textViewMain.setText(spannableString);
            progress.setMax(86400 * value);
        }

        buttonApply = findViewById(R.id.apply);
        buttonApply.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextValue = findViewById(R.id.edit);
                progress = findViewById(R.id.progress);

                try {
                    // 문자열을 숫자로 변환.
                    value = Integer.parseInt(editTextValue.getText().toString());
                    global.setReport(value);
                    textViewMain = findViewById(R.id.report_main_text);
                    if(value == 1) textViewMain.setText(String.format("\nMY GOAL:\nNo Smoking For\n%d DAY", value));
                    else textViewMain.setText(String.format("\nMY GOAL:\nNo Smoking For\n%d DAYS", value));

                    String content = textViewMain.getText().toString();
                    SpannableString spannableString = new SpannableString(content);
                    int start = 25;
                    int end = content.length();

                    spannableString.setSpan(new RelativeSizeSpan(2f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#26689A")), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

                    textViewMain.setText(spannableString);

                    progress.setMax(86400 * value);
                } catch (Exception e) {
                    // 토스트(Toast) 메시지 표시.
                    Toast toast = Toast.makeText(ReportActivity.this, "Invalid number format",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        final List<String> list = input.getData()[input.getMonthOfToday()][input.getDateOfToday()];
        final float average = input.calculateAverage();
        final int price = Integer.parseInt(global.getPrice());
        buttonAlert = findViewById(R.id.button);
        buttonAlert.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View view) {
                if (view == buttonAlert) {
                    Context mContext = getApplicationContext();
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                    layout = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.popup));
                    AlertDialog.Builder aDialog = new AlertDialog.Builder(ReportActivity.this, R.style.MyDialogTheme);

                    aDialog.setTitle("How to quit smoking?");
                    aDialog.setView(layout);

                    aDialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
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
                    textViewLast = findViewById(R.id.report_text_1_2);
                    textViewLife = findViewById(R.id.report_text_2_2);
                    textViewMoney = findViewById(R.id.report_text_3_2);

                    Calendar cal = Calendar.getInstance();
                    Date now = cal.getTime();

                    if(list.size()==0) {
                        textViewLast.setText("No Data");
                    }
                    else {
                        final String last_time = list.get(0);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
                        Date lsTime = sdf.parse(last_time);

                        // 86400초 = 하루
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

                        textViewLast.setText(strTime + "\nhave passed\nsince the last smoking.");

                        progress = findViewById(R.id.progress);
                        progress.setProgress((int) seconds); // 변환된 값을 프로그레스바에 적용

                        textViewLife.setText(String.format("You have been getting\n%.2f more minutes of life\nsince the last smoking.", (double) seconds * average * (13.8)));

                        textViewMoney.setText(String.format("You have been saving\n%.2f KRW\nsince the last smoking.", seconds * average * price));

//                        DecimalFormat form = new DecimalFormat("#.#");
                    }
                } catch (ParseException p) {
                    p.printStackTrace();
                }
            }
        };

        bottomNavigationView = findViewById(R.id.navigationView);
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

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
