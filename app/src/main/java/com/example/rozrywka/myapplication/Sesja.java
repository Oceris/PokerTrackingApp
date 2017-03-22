package com.example.rozrywka.myapplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Rozrywka on 2016-07-21.
 */
public class Sesja {
    int id;
    long IDS;


    public long getIdS() {
        return IDS;
    }

    public void setIdS(long idS) {
        this.IDS = idS;
    }

    String data;
    ArrayList<Wejscie> wejscia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    Double wplata=0.0;
    Double wyplata=0.0;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    public Date getDateData() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(getData());
    }

    public ArrayList<Wejscie> getWejscia() {
        return wejscia;
    }

    public void setWejscia(ArrayList<Wejscie> wejscia) {
        this.wejscia = wejscia;
    }

    public Double getWplata() {
        return wplata;
    }

    public void setWplata(Double wplata) {
        this.wplata = wplata;
    }

    public Double getWyplata() {
        return wyplata;
    }

    public void setWyplata(Double wyplata) {
        this.wyplata = wyplata;
    }

    public Sesja()
    {

        data=  new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        wejscia= new ArrayList<Wejscie>();

    }
    public Sesja(String data)
    {
        this.data=data;
    }


}

