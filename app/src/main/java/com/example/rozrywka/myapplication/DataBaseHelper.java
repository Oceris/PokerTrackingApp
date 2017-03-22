package com.example.rozrywka.myapplication;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rozrywka on 2016-07-26.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String LOG ="DatabaseHelper";

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="SesjePokerowe";
    private static final String TABLE_SESJE="sesje";
    private static final String TABLE_TURNIEJE="turnieje";
    private static final String TABLE_STOLIKI="stoly";
    //Kolumny tabeli sesje
    private static final String KEY_IDS="ids";
    //Kolumny tabeli turnieje
    private static final String KEY_IDT="idt";
    //Kolumny tabeli stoliki
    private static final String KEY_IDSTOLIKI="idStoliki";
    //kolumny powtarzalne
    private static final String KEY_WPLATA="wplata";
    private static final String KEY_WYPLATA="wyplata";
    private static final String KEY_DATADOD="dataDodania";


    private static final String CREATAE_TABLE_SESJE ="CREATE TABLE "+TABLE_SESJE+"("+KEY_IDS+" INTEGER PRIMARY KEY, "
            + KEY_WPLATA+" REAL, "+KEY_WYPLATA+" REAL, "+KEY_DATADOD+" TEXT"+")";
    private static final String CREATAE_TABLE_TURNIEJE ="CREATE TABLE "+TABLE_TURNIEJE+"("+KEY_IDT+" INTEGER PRIMARY KEY, "+ KEY_IDS+" INTEGER, "
            + KEY_WPLATA+" REAL, "+KEY_WYPLATA+" REAL, "+KEY_DATADOD+" TEXT"+")";
    private static final String CREATAE_TABLE_STOLIKI ="CREATE TABLE "+TABLE_STOLIKI+"("+KEY_IDSTOLIKI+" INTEGER PRIMARY KEY, "+ KEY_IDS+" INTEGER, "
            + KEY_WPLATA+" REAL, "+KEY_WYPLATA+" REAL, "+KEY_DATADOD+" TEXT"+")";


    public DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATAE_TABLE_SESJE);
        db.execSQL(CREATAE_TABLE_TURNIEJE);
        db.execSQL(CREATAE_TABLE_STOLIKI);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_SESJE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TURNIEJE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_STOLIKI);
        onCreate(db);
    }

    //Metody sesji
    public long createSesja(Sesja sesja)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WPLATA,sesja.wplata);
        values.put(KEY_WYPLATA,sesja.wyplata);
        values.put(KEY_DATADOD,sesja.data);
        long idS = db.insert(TABLE_SESJE,null,values);
        sesja.setIdS(idS);//Potrzebne?
        return idS;
    }
    public Sesja getSesja(long idS)
    {
        SQLiteDatabase db =this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+TABLE_SESJE+" WHERE "+KEY_IDS+" = "+idS;
        Log.e(LOG,selectQuery);
        Cursor c =db.rawQuery(selectQuery,null);
        if(c!=null)
        {
            c.moveToFirst();
        }
        Sesja sesja = new Sesja();
        sesja.setWplata(c.getDouble(c.getColumnIndex(KEY_WPLATA)));
        sesja.setWyplata(c.getDouble(c.getColumnIndex(KEY_WYPLATA)));
        sesja.setData(c.getString(c.getColumnIndex(KEY_DATADOD)));
        sesja.setIdS(c.getLong(c.getColumnIndex(KEY_IDS)));
        return sesja;
    }
    public List<Sesja> getAllSesja()
    {
        List<Sesja> sesjeTemp = new ArrayList<Sesja>();
        String selectQuery = "SELECT * FROM "+TABLE_SESJE;
        Log.e(LOG,selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);
        if (c.moveToFirst()) {
            do {
                Sesja sesja = new Sesja();
                sesja.setWplata(c.getDouble(c.getColumnIndex(KEY_WPLATA)));
                sesja.setWyplata(c.getDouble(c.getColumnIndex(KEY_WYPLATA)));
                sesja.setData(c.getString(c.getColumnIndex(KEY_DATADOD)));
                sesja.setIdS(c.getLong(c.getColumnIndex(KEY_IDS)));
                sesjeTemp.add(sesja);
            } while (c.moveToNext());
        }
        return sesjeTemp;

    }
    public int updateSesja(Sesja sesja){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WPLATA,sesja.wplata);
        values.put(KEY_WYPLATA,sesja.wyplata);
        return db.update(TABLE_SESJE,values,KEY_IDS+" =?",new String[]{ String.valueOf(sesja.getId())});
    }
    public void deleteSesja(long idS)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = KEY_IDS+"="+idS;
        deleteStolikBySesja(idS);
        deleteTurniejBySesja(idS);
        db.delete(TABLE_SESJE,where,null);
       // db.delete(TABLE_SESJE,KEY_IDS+" =?",new String[]{String.valueOf(idS)});
    }
    //Metody Turnieju
    public long createTurniej(Turniej turniej)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WPLATA,turniej.getWplata());
        values.put(KEY_WYPLATA,turniej.getWyplata());
        values.put(KEY_IDS,turniej.getNrSesji());
        values.put(KEY_DATADOD,turniej.getData());
        long idT = db.insert(TABLE_TURNIEJE,null,values);
        turniej.setIdT(idT);
        return idT;

    }
    public Turniej getTurniej(long idT)
    {
        SQLiteDatabase db =this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+TABLE_TURNIEJE+" WHERE "+KEY_IDT+" = "+idT;
        Log.e(LOG,selectQuery);
        Cursor c =db.rawQuery(selectQuery,null);
        if(c!=null)
        {
            c.moveToFirst();
        }
        Turniej turniej = new Turniej();
        turniej.setWplata(c.getDouble(c.getColumnIndex(KEY_WPLATA)));
        turniej.setWyplata(c.getDouble(c.getColumnIndex(KEY_WYPLATA)));
        turniej.setData(c.getString(c.getColumnIndex(KEY_DATADOD)));
        turniej.setIdT(c.getLong(c.getColumnIndex(KEY_IDT)));
        return turniej;

    }
    public List<Wejscie> getAllTurnieje(long idS)
    {
        List<Wejscie> turnieje = new ArrayList<Wejscie>();
        String selectQuery = "SELECT  * FROM " + TABLE_TURNIEJE + " tt "
                 + "  WHERE tt."
                + KEY_IDS + " = " + idS;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Turniej turniej = new Turniej();
                turniej.setIdT(c.getLong(c.getColumnIndex(KEY_IDT)));
                turniej.setWplata(c.getDouble(c.getColumnIndex(KEY_WPLATA)));
                turniej.setWyplata(c.getDouble(c.getColumnIndex(KEY_WYPLATA)));
                turniej.setData(c.getString(c.getColumnIndex(KEY_DATADOD)));
                turnieje.add(turniej);
            } while (c.moveToNext());
        }
        return turnieje;

    }

    public int updateTurniej(Turniej turniej){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WPLATA,turniej.getWplata());
        values.put(KEY_WYPLATA,turniej.getWyplata());
        values.put(KEY_DATADOD,turniej.getData());
        return db.update(TABLE_TURNIEJE,values,KEY_IDT+" =?",new String[]{ String.valueOf(turniej.getIdT())});
    }
    public void deleteTurniej(long idT)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = KEY_IDT+"="+idT;
        db.delete(TABLE_TURNIEJE,where,null);
        // db.delete(TABLE_SESJE,KEY_IDS+" =?",new String[]{String.valueOf(idS)});
    }
    public void deleteTurniejBySesja(long idS)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Wejscie> lista =(ArrayList<Wejscie>) getAllTurnieje(idS);
        for(Wejscie w: lista)
        {
            Turniej t = (Turniej) w;
            deleteTurniej(t.getIdT());
        }

    }


    //Metody Stolika
    public long createStolik(Stolik stolik) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WPLATA, stolik.getWplata());
        values.put(KEY_WYPLATA, stolik.getWyplata());
        values.put(KEY_IDS, stolik.getNrSesji());
        values.put(KEY_DATADOD, stolik.getData());
        long idSTOLIKA = db.insert(TABLE_TURNIEJE, null, values);
        stolik.setIdSt(idSTOLIKA);
        return idSTOLIKA;
    }

    public Stolik getStolik(long idSt)
    {
        SQLiteDatabase db =this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+TABLE_STOLIKI+" WHERE "+KEY_IDSTOLIKI+" = "+idSt;
        Log.e(LOG,selectQuery);
        Cursor c =db.rawQuery(selectQuery,null);
        if(c!=null)
        {
            c.moveToFirst();
        }
        Stolik stolik = new Stolik();
        stolik.setWplata(c.getDouble(c.getColumnIndex(KEY_WPLATA)));
        stolik.setWyplata(c.getDouble(c.getColumnIndex(KEY_WYPLATA)));
        stolik.setData(c.getString(c.getColumnIndex(KEY_DATADOD)));
        stolik.setIdSt(c.getLong(c.getColumnIndex(KEY_IDS)));
        return stolik;
    }
    public List<Wejscie> getAllStoliki(long idSTOLIK)
    {
        List<Wejscie> stoliki = new ArrayList<Wejscie>();
        String selectQuery = "SELECT  * FROM " + TABLE_STOLIKI + " tt "
                +  "  WHERE tt."
                + KEY_IDS + " = " + idSTOLIK;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Stolik stolik = new Stolik();
                stolik.setIdSt(c.getLong(c.getColumnIndex(KEY_IDSTOLIKI)));
                stolik.setWplata(c.getDouble(c.getColumnIndex(KEY_WPLATA)));
                stolik.setWyplata(c.getDouble(c.getColumnIndex(KEY_WYPLATA)));
                stolik.setData(c.getString(c.getColumnIndex(KEY_DATADOD)));
                stoliki.add(stolik);
            } while (c.moveToNext());
        }
        return stoliki;

    }
    public int updateStolik(Stolik stolik){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WPLATA,stolik.getWplata());
        values.put(KEY_WYPLATA,stolik.getWyplata());
        values.put(KEY_DATADOD,stolik.getData());
        return db.update(TABLE_STOLIKI,values,KEY_IDSTOLIKI+" =?",new String[]{ String.valueOf(stolik.getIdSt())});
    }
    public void deleteStolik(long idSt)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = KEY_IDSTOLIKI+"="+idSt;
        db.delete(TABLE_STOLIKI,where,null);
        // db.delete(TABLE_SESJE,KEY_IDS+" =?",new String[]{String.valueOf(idS)});
    }
    public void deleteStolikBySesja(long idS)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Wejscie> lista =(ArrayList<Wejscie>) getAllStoliki(idS);
        for(Wejscie w: lista)
        {
            Stolik s = (Stolik) w;
            deleteStolik(s.getIdSt());
        }

    }






    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
    public int getSesjaCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SESJE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
    public int getTurniejCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TURNIEJE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
    public int getStolikCount() {
        String countQuery = "SELECT  * FROM " + TABLE_STOLIKI;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

}
