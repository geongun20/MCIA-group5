package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    ImageView welcomeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler timer = new Handler();

        // populate the layout defined in xml file
        setContentView(R.layout.activity_welcome);

        // get the text view instance
        welcomeImage = (ImageView)findViewById(R.id.welcomeImage);

        timer.postDelayed(new Runnable() {
            @Override
            public void run() {
                // make a intent
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);


    }

//
//    @Override
//    protected void onStop() {
//        super.onStop();
//    }
}
