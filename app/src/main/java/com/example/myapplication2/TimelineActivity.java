package com.example.myapplication2;

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
 * 3. 이번 달 그래프: viewpager fragment 5개(1~7, 8~14, 15~21, 22~28, 29~말일). 각 fragment 안에 막대그래프(막대 하나가 하루).
 * 4. 이번 달 요일별 개수
 */

public class TimelineActivity extends AppCompatActivity {
    static int pickedMonth = 0;
    static String s = "init";
    NumberPicker monthPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        System.out.println("Timeline onCreate()");

        monthPicker = findViewById(R.id.monthPicker1);
        final String months[] = {"2019.12(DEC)", "2019.11(NOV)", "2019.10(OCT)", "2019.09(SEP)", "2019.08(AUG)", "2019.07(JUL)", "2019.06(JUN)"};

        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(months.length -1);
        monthPicker.setWrapSelectorWheel(false);
        monthPicker.setDisplayedValues(months);
        monthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
//        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                s = newVal+"";
//            }
//        });


        Button confirmButton = findViewById(R.id.button1);
        confirmButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                s = String.valueOf(monthPicker.getValue());
                pickedMonth = 12 - monthPicker.getValue();

                System.out.println("inside of onClick() " + pickedMonth);

                TextView temp = findViewById(R.id.textView10);
                temp.setText(pickedMonth + "");

                MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
                if(pickedMonth == 3 || pickedMonth == 6) {
                    adapter.addItem(new Fragment1(), "WEEK 1");
                    adapter.addItem(new Fragment2(), "WEEK 2");
                    adapter.addItem(new Fragment3(), "WEEK 3");
                    adapter.addItem(new Fragment4(), "WEEK 4");
                    adapter.addItem(new Fragment5(), "WEEK 5");
                }
                else {
                    adapter.addItem(new Fragment1(), "WEEK 1");
                    adapter.addItem(new Fragment2(), "WEEK 2");
                    adapter.addItem(new Fragment3(), "WEEK 3");
                    adapter.addItem(new Fragment4(), "WEEK 4");
                    adapter.addItem(new Fragment5(), "WEEK 5");
//                    adapter.addItem(new Fragment6(), "WEEK 6");
                }

                ViewPager viewPager = findViewById(R.id.viewPager);
                viewPager.setAdapter(adapter);

                TabLayout tabLayout = findViewById(R.id.tabLayout);
                tabLayout.setupWithViewPager(viewPager);
            }
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
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
