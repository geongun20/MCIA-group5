package com.example.smokare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {
    EditText nicknameInput, ageInput, priceInput, tarInput;
    NumberPicker yearInput;
    Button submitButton;
    private SharedPreferences info;
    String nickname;
    String age, year, price;
    String tar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        final Global_Variable global = (Global_Variable) getApplication();

        NumberPicker picker = (NumberPicker)findViewById(R.id.picker2);
        picker.setMaxValue(2019);
        picker.setMinValue(1919);
        //picker.setWrapSelectorWheel(false);

        nicknameInput = (EditText)findViewById(R.id.nicknameInput);
        ageInput = (EditText)findViewById(R.id.ageInput);
        yearInput = picker;
        priceInput = (EditText)findViewById(R.id.priceInput);
        tarInput = (EditText)findViewById(R.id.tarInput);

        submitButton = (Button)findViewById(R.id.submitButton);


        submitButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(nicknameInput.getText().equals("")
                        || ageInput.getText().toString().equals("")
                        || yearInput.toString().equals("")
                        || priceInput.getText().toString().equals("")
                        || tarInput.getText().toString().equals("")) {
                    Toast.makeText(InfoActivity.this, "please enter all info", Toast.LENGTH_LONG).show();
                }
                else {
                    // all info are submitted
                    try {
                        info = getSharedPreferences("user_info", MODE_PRIVATE);

                        System.out.println("Success");
                        nickname = nicknameInput.getText().toString();
                        age = ageInput.getText().toString();
                        year = String.valueOf(yearInput.getValue());
                        price = priceInput.getText().toString();
                        tar = tarInput.getText().toString();
                        global.setName(nickname);
                        global.setAge(age);
                        global.setYear(year);
                        global.setPrice(price);
                        global.setTar(tar);

                        //save();

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

//    private void save(){
//        SharedPreferences.Editor editor = info.edit();
//        editor.putString("name", nickname);
//        editor.putInt("age", age);
//        editor.putInt("year", year);
//        editor.putInt("price", price);
//        editor.putFloat("tar", tar);
//        editor.commit();
//    }

}
