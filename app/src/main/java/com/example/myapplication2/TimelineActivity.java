package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;


public class TimelineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);


        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addItem(new Fragment1(), "Monthly");
        adapter.addItem(new Fragment2(), "Weekly");
        adapter.addItem(new Fragment3(), "Daily");

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
