package com.example.rozrywka.myapplication;

/**
 * Created by Rozrywka on 2016-07-21.
 */
public class Turniej extends Wejscie {
    private long idT;
    private long nrSesji;

    public long getNrSesji() {
        return nrSesji;
    }

    public void setNrSesji(long nrSesji) {
        this.nrSesji = nrSesji;
    }

    public long getIdT() {
        return idT;
    }

    public void setIdT(long idT) {
        this.idT = idT;
    }

    public Turniej(){
    }
    public Turniej(double wpl, double wypl){
        super(wpl,wypl);
    }



}
