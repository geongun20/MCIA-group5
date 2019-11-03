package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeActivity extends AppCompatActivity {

    private boolean isFragmentA = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        ImageButton buttonH = findViewById(R.id.imageButton1) ;
        buttonH.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buttonT = findViewById(R.id.imageButton2) ;
        buttonT.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TimelineActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buttonR = findViewById(R.id.imageButton3) ;
        buttonR.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buttonS = findViewById(R.id.imageButton4) ;
        buttonS.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
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


}
