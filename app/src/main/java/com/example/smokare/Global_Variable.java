package com.example.smokare;

import android.app.Application;

public class Global_Variable extends Application {
    private String name;
    private String age, year, price;
    private String tar;
    private Integer report_value = 0;
    private Integer report_progress = 0;

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

    public Integer getReport_value(){return report_value;}
    public Integer getReport_progress(){return report_progress;}

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

    public void setReport(Integer value) {this.report_value = value;}
    public void setReport_progress(Integer progress) {this.report_progress = progress;}
}
