package com.example.rozrywka.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ListView lista;
    SesjaAdapter adapter;
    static ArrayList<Sesja> sesje;
    Intent intent;
    DataBaseHelper db;
    FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBaseHelper(getApplicationContext());
        lista = (ListView) findViewById(R.id.lista);
        sesje= new ArrayList<Sesja>();
        intent = new Intent(this,SesjaAkt.class);
        wczytajSesje();

        adapter = new SesjaAdapter(this,sesje);
        fb= (FloatingActionButton) findViewById(R.id.buttonDodaj);

        lista.setAdapter(adapter);


        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sesja sesja = new Sesja();
                sesja.setIdS(db.createSesja(sesja));
                sesje.add(sesja);
                adapter.notifyDataSetChanged();

            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Sesja temp = (Sesja) lista.getItemAtPosition(position);
                usun(temp);
                return false;
            }
        });
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Sesja temp2 = (Sesja) lista.getItemAtPosition(position);
                long indeks = temp2.getIdS();
                intent.putExtra("indeksS",indeks);
                startActivity(intent);


            }
        });

        db.closeDB();
    }




    public void wczytajSesje(){

            sesje= (ArrayList<Sesja>) db.getAllSesja();
    }
    public void usun(Sesja temp)
    {
        //dodane usuwanie wszystkich turnieji i stolików powiązanych z sesją
        Log.d("Tag Count", "Tag Count Before Deleting: " + db.getSesjaCount());
        db.deleteSesja(temp.getIdS());
        Log.d("Tag Count", "Tag Count After Deleting: " + db.getSesjaCount());
        adapter.remove(temp);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onResume()//Po powrocie z innej aktywności w której mogliśmy zmienić pewne dane (np. przez backButton) powiadomienie adapetera że w danych mogły nastąpić zmiany
    {
        super.onResume();
        adapter.notifyDataSetChanged();

    }








}
