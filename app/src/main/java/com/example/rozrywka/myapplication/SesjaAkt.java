package com.example.rozrywka.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.view.MenuItem;
import android.view.Menu;


import java.util.ArrayList;

public class SesjaAkt extends AppCompatActivity implements  DodajWejscie.NoticeDialogListener{
    private boolean czyPierwszeUruchomienie;
    private boolean isTurniej=true;
    Intent wejscieInent;
    WejscieAdapter adapter;
    ArrayList<Wejscie> wejscia;
    DataBaseHelper db;
    FloatingActionButton fb;
    ListView lista;
    static long indeksSesji;
    public static double wplata=0.0; //CZY MOŻNA TO TAK ZOSTAWIć JAKO STATIC
    public static double wyplata=0.0;
    public static boolean czyTurniej =false;
   DodajWejscie dialog;
    @Override
    protected  void onStart()
    {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesja);
        indeksSesji=getIntent().getLongExtra("indeksS",100);
        fb= (FloatingActionButton) findViewById(R.id.buttonDodaj);
        db= new DataBaseHelper(getApplicationContext());
        wejscia= new ArrayList<Wejscie>();
        wejscia=(ArrayList<Wejscie>)db.getAllTurnieje(indeksSesji);
        wejscia.addAll((ArrayList<Wejscie>)db.getAllStoliki(indeksSesji));
        wejscieInent = new Intent(this,EdycjaWejscia.class);
        czyPierwszeUruchomienie=true;



        dialog= new DodajWejscie();

       adapter = new WejscieAdapter(this,wejscia);
        lista = (ListView) findViewById(R.id.SessionsList);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm =getSupportFragmentManager();
                pokazDialog();

            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                    Wejscie wejscie =(Wejscie) lista.getItemAtPosition(position);
                    usunWejscie(wejscie);


                return false;
            }
        });
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Wejscie wejscie = (Wejscie) lista.getItemAtPosition(i);

                if(wejscie instanceof Turniej)
                {
                    isTurniej=true;
                    wejscieInent.putExtra("idWejscia",((Turniej) wejscie).getIdT());

                }else if(wejscie instanceof Stolik)
                {
                    isTurniej=false;
                    wejscieInent.putExtra("idWejscia",((Stolik) wejscie).getIdSt());
                }
                wejscieInent.putExtra("isTurniej",isTurniej);
                startActivity(wejscieInent);
            }

        });


        lista.setAdapter(adapter);




    }



    public void pokazDialog(){
        DialogFragment dialogFragment = new DodajWejscie();
        dialogFragment.show(getSupportFragmentManager(),"TEST DIALOGU");
    }
    @Override
    public void onDialogPositiveClick(DialogFragment dialog,boolean isCorrect)
    {


        if(isCorrect) {

            if (czyTurniej) {
                Turniej turniej = new Turniej(wplata, wyplata);
                turniej.setNrSesji(indeksSesji);
                db.createTurniej(turniej);
                wejscia.add(turniej);
                adapter.notifyDataSetChanged();


            } else {
                Stolik stolik = new Stolik(wplata, wyplata);
                stolik.setNrSesji(indeksSesji);
                db.createStolik(stolik);
                wejscia.add(stolik);
                adapter.notifyDataSetChanged();

            }
        }else{
            pokazDialog();
        }




    }
    @Override
    public void onDialogNegativeClick(DialogFragment dialog)
    {
        Toast.makeText(this, "Negatyw",
                Toast.LENGTH_SHORT).show();

    }
    public void usunWejscie(Wejscie wejscie){
        if(wejscie instanceof Turniej)
        {
            Log.d("Tag Count", "Turnieje Count Before Deleting: " + db.getTurniejCount());
            db.deleteTurniej(((Turniej) wejscie).getIdT());
            Log.d("Tag Count", "Turnieje Count After Deleting: " + db.getTurniejCount());
        }else if( wejscie instanceof Stolik)
        {
            Log.d("Tag Count", "Stoliki Count Before Deleting: " + db.getStolikCount());
            db.deleteStolik(((Stolik) wejscie).getIdSt());
            Log.d("Tag Count", "Stoliki Count After Deleting: " + db.getStolikCount());
        }

        adapter.remove(wejscie);
        adapter.notifyDataSetChanged();

    }
    @Override
    public void onPause()
    {
        super.onPause();

        czyPierwszeUruchomienie=false;

    }
    @Override
    public void onResume(){
        super.onResume();
        wejscia.clear();
        wejscia.addAll(db.getAllTurnieje(indeksSesji));
        wejscia.addAll(db.getAllStoliki(indeksSesji));
        adapter.notifyDataSetChanged();

    }

}
