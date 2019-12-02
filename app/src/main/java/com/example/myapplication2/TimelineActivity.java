package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

/**
 * Timeline 안에
 * 1. 년월 selector: 고르면 변수에 값 할당
 * 2. 이번 달 쓴 돈: 1의 변수로부터 몇 월인지 받아옴.
 * 3. 이번 달 그래프: 1의 변수로부터 몇 월인지 받아옴. viewpager fragment 3개(하나 당 열흘). 각 fragment 안에 막대그래프(막대 하나가 하루).
 */

public class TimelineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addItem(new Fragment1(), ".01 ~ .10");
        adapter.addItem(new Fragment2(), ".11 ~ .20");
        adapter.addItem(new Fragment3(), ".21 ~");

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

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
