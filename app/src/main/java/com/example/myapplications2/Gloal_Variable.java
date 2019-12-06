package com.example.myapplications2;

import android.app.Application;

public class Gloal_Variable extends Application {
    private String name;
    private String age, year, price;
    private String tar;

    public String getName(){
        return name;
    }
    public String getAge(){
        return age;
    }
    public String getYear(){
        return year;
    }
    public String getPrice(){
        return price;
    }
    public String getTar(){
        return tar;
    }


    public void setName(String name){
        this.name = name;
    }
    public void setAge(String age){
        this.age = age;
    }
    public void setYear(String year){
        this.year = year;
    }
    public void setPrice(String price){
        this.price = price;
    }
    public void setTar(String tar){
        this.tar = tar;
    }


}
