package com.example.rozrywka.myapplication;

/**
 * Created by Rozrywka on 2016-07-21.
 */
public class Stolik  extends Wejscie{
    private long idSt;
    private long nrSesji;

    public long getNrSesji() {
        return nrSesji;
    }

    public void setNrSesji(long nrSesji) {
        this.nrSesji = nrSesji;
    }

    public long getIdSt() {
        return idSt;
    }

    public void setIdSt(long idSt) {
        this.idSt = idSt;
    }
    public Stolik()
    {

    }

    public Stolik(double wpl, double wypl)
    {
        super(wpl, wypl);
    }
}
