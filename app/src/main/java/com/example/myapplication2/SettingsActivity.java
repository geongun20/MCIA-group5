package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class SettingsActivity extends AppCompatActivity{
    ImageButton b1, b2, b3, b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ImageButton buttonH = findViewById(R.id.imageButton1) ;
        buttonH.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buttonT = findViewById(R.id.imageButton2) ;
        buttonT.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, TimelineActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buttonR = findViewById(R.id.imageButton3) ;
        buttonR.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buttonS = findViewById(R.id.imageButton4) ;
        buttonS.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
