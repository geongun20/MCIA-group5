package com.example.smokare;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

/**
 * Timeline 안에
 * 1. 년월 selector: 고르면 변수에 값 할당.
 * 2. 이번 달 총 개수 & 쓴 돈
 * 3. 이번 달 그래프: viewpager fragment 5~6개(주별로). 각 fragment 안에 막대그래프(막대 하나가 하루).
 * 4. 이번 달 요일별 개수
 */

public class TimelineActivity extends AppCompatActivity {
    NumberPicker monthPicker;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    ViewPager viewPager;
    TabLayout tabLayout;

    Input input;
    Global_Variable global;

    static int pickedMonth = 0;
    final String months[] = {"DEC 2019", "NOV 2019", "OCT 2019", "SEP 2019", "AUG 2019", "JUL 2019", "JUN 2019", "MAY 2019", "APR 2019", "MAR 2019", "FEB 2019", "JAN 2019"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        input = new Input();
        input.readFile(getExternalFilesDir(null));
//        input.readFile2("sample_data.txt", getApplicationContext());

        global = (Global_Variable) getApplication();

        monthPicker = findViewById(R.id.monthPicker1);
        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(months.length -1);
        monthPicker.setDisplayedValues(months);
        monthPicker.setWrapSelectorWheel(false);
        monthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        pickedMonth = input.getMonthOfToday();

        tv1 = findViewById(R.id.textView9);
        tv1.setText("" + months[12 - pickedMonth]);
        tv2 = findViewById(R.id.textView10);
        tv2.setText("Total cigarettes: " + input.countMonth(pickedMonth));
        tv3 = findViewById(R.id.textView11);
        tv3.setText("Total money spent: " + Integer.parseInt(global.getPrice()) / 20 * input.countMonth(pickedMonth) + " KRW");

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        if(pickedMonth == 3 || pickedMonth == 6) {
            adapter.addItem(new Fragment1(), "WEEK 1");
            adapter.addItem(new Fragment2(), "WEEK 2");
            adapter.addItem(new Fragment3(), "WEEK 3");
            adapter.addItem(new Fragment4(), "WEEK 4");
            adapter.addItem(new Fragment5(), "WEEK 5");
            adapter.addItem(new Fragment6(), "WEEK 6");
        }
        else {
            adapter.addItem(new Fragment1(), "WEEK 1");
            adapter.addItem(new Fragment2(), "WEEK 2");
            adapter.addItem(new Fragment3(), "WEEK 3");
            adapter.addItem(new Fragment4(), "WEEK 4");
            adapter.addItem(new Fragment5(), "WEEK 5");
        }

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


        Button confirmButton = findViewById(R.id.button1);
        confirmButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickedMonth = 12 - monthPicker.getValue();

                tv1.setText("" + months[monthPicker.getValue()]);
                tv2.setText("Total cigarettes: " + input.countMonth(pickedMonth));
                tv3.setText("Total money spent: " + Integer.parseInt(global.getPrice()) / 20 * input.countMonth(pickedMonth) + " KRW");

                MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
                if(pickedMonth == 3 || pickedMonth == 6) {
                    adapter.addItem(new Fragment1(), "WEEK 1");
                    adapter.addItem(new Fragment2(), "WEEK 2");
                    adapter.addItem(new Fragment3(), "WEEK 3");
                    adapter.addItem(new Fragment4(), "WEEK 4");
                    adapter.addItem(new Fragment5(), "WEEK 5");
                    adapter.addItem(new Fragment6(), "WEEK 6");
                }
                else {
                    adapter.addItem(new Fragment1(), "WEEK 1");
                    adapter.addItem(new Fragment2(), "WEEK 2");
                    adapter.addItem(new Fragment3(), "WEEK 3");
                    adapter.addItem(new Fragment4(), "WEEK 4");
                    adapter.addItem(new Fragment5(), "WEEK 5");
                }

                viewPager.setAdapter(adapter);

                tabLayout.setupWithViewPager(viewPager);
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_menu1: {
                        Intent intent = new Intent(TimelineActivity.this, HomeActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.navigation_menu2: {
                        Intent intent = new Intent(TimelineActivity.this, TimelineActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.navigation_menu3: {
                        Intent intent = new Intent(TimelineActivity.this, ReportActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.navigation_menu4: {
                        Intent intent = new Intent(TimelineActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        return true;
                    }
                }

                return false;
            }
        });
    }
}
