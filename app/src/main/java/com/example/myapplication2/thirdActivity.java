package com.example.myapplication2;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class thirdActivity extends AppCompatActivity{
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        next = findViewById(R.id.button);
        next.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thirdActivity.this, ReportActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
