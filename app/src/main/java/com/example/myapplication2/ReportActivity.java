package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ReportActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
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
        input.readFile("sample_data.txt", getApplicationContext());
        List<String> list = input.getData()[input.getMonth()][input.getDay()];

        TextView msg = findViewById(R.id.textYoucould);
        msg.setText(String.format("You could have lived \n %f more minutes",((double)input.countThisWeek())*(13.8)));

    }
}
