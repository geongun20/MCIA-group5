package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class InfoActivity extends AppCompatActivity {
    EditText nicknameInput, ageInput, yearInput, priceInput, tarInput;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        nicknameInput = (EditText)findViewById(R.id.nicknameInput);
        ageInput = (EditText)findViewById(R.id.ageInput);
        yearInput = (EditText)findViewById(R.id.yearInput);
        priceInput = (EditText)findViewById(R.id.priceInput);
        tarInput = (EditText)findViewById(R.id.tarInput);

        submitButton = (Button)findViewById(R.id.submitButton);


        submitButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickname;
                int age, year, price;
                double tar;

                if(nicknameInput.getText().equals("")
                        || ageInput.getText().toString().equals("")
                        || yearInput.getText().toString().equals("")
                        || priceInput.getText().toString().equals("")
                        || tarInput.getText().toString().equals("")) {
                    Toast.makeText(InfoActivity.this,
                            "please enter all info",
                            Toast.LENGTH_LONG).show();
                } else {
                    // all info are submitted
                    nickname = nicknameInput.getText().toString();
                    try {
                        age = Integer.parseInt(ageInput.getText().toString());
                        year = Integer.parseInt(yearInput.getText().toString());
                        price = Integer.parseInt(priceInput.getText().toString());
                        tar = Double.parseDouble(tarInput.getText().toString());
                    } catch(NumberFormatException nfe) {
                        System.out.println("Could not parse " + nfe);
                    }

                    // TODO save user info in application
                }

                // TODO go next activity
                Intent intent = new Intent(InfoActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
