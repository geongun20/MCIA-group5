package com.example.smokare;

import android.app.Application;
import android.content.SharedPreferences;


public class Global_Variable extends Application {

    public boolean getInfo() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getBoolean("info", false);
    }
    public String getName(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString("name", "");
    }
    public String getAge(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString("age", "");
    }
    public String getYear(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString("year", "");
    }
    public String getPrice(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString("price", "");
    }
    public String getTar(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString("tar", "");
    }
    public Integer getReport_value() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getInt("report_value", 0);
    }
    public void setInfo(boolean info) {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("info", info);
        editor.commit();
    }
    public void setName(String name){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name", name);
        editor.commit();
    }
    public void setAge(String age){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("age", age);
        editor.commit();
    }
    public void setYear(String year){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("year", year);
        editor.commit();
    }
    public void setPrice(String price){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("price", price);
        editor.commit();
    }
    public void setTar(String tar){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("tar", tar);
        editor.commit();
    }

    public void setReport(Integer value) {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("report_value", value);
        editor.commit();

    }
}
