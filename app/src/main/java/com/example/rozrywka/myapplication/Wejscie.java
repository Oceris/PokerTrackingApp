package com.example.rozrywka.myapplication;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rozrywka on 2016-07-22.
 */
public class Wejscie {
    double wplata=0.0;
    double wyplata=0.0;
    String data;

    public double getWplata() {
        return wplata;
    }

    public void setWplata(double wplata) {
        this.wplata = wplata;
    }

    public double getWyplata() {
        return wyplata;
    }

    public void setWyplata(double wyplata) {
        this.wyplata = wyplata;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    public Wejscie()
    {
    }

    public Wejscie(double wpl, double wypl){
        wplata=wpl;
        wyplata=wypl;
        data=new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    }
}
