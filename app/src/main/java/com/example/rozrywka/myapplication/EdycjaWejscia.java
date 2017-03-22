package com.example.rozrywka.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EdycjaWejscia extends AppCompatActivity {
    EditText wplataEdit;
    EditText wyplataEdit;
    EditText dataEdit;
    Button zapisz;
    private boolean isTurniej;
    static long indeksWejscia;
    DataBaseHelper db;
    private Sesja sesja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edycja_wejscia);
        indeksWejscia=getIntent().getLongExtra("idWejscia",100);
        isTurniej=getIntent().getBooleanExtra("isTurniej",true);
        db = new DataBaseHelper(getApplicationContext());
        wplataEdit=(EditText) findViewById(R.id.wplataEdit);
        wyplataEdit=(EditText) findViewById(R.id.wyplataEdit);
        dataEdit = (EditText) findViewById(R.id.dataEdit);
        zapisz = (Button) findViewById(R.id.zapiszZmianyButton);
        config();
        zapisz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Double AktWplata= Double.parseDouble(wplataEdit.getText().toString());
                 Double AktWyplata =Double.parseDouble(wyplataEdit.getText().toString());
                 String data = dataEdit.getText().toString();
                 if(isTurniej)
                 {
                     Turniej turniej = new Turniej();
                     turniej.setIdT(indeksWejscia);
                     turniej.setWplata(AktWplata);
                     turniej.setWyplata(AktWyplata);
                     turniej.setData(data);
                     db.updateTurniej(turniej);
                 }else{
                     Stolik stolik = new Stolik();
                     stolik.setIdSt(indeksWejscia);
                     stolik.setWplata(AktWplata);
                     stolik.setData(data);
                     stolik.setWyplata(AktWyplata);
                     db.updateStolik(stolik);
                 }
                Toast.makeText(getApplicationContext(),"Zapisano",Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });


    }
    public void config()
    {

        if(isTurniej) {
            Turniej turniej = db.getTurniej(indeksWejscia);
            wplataEdit.setText(Double.toString(turniej.getWplata()));
            wyplataEdit.setText(Double.toString(turniej.getWyplata()));
            dataEdit.setText(turniej.getData().toString());

        }else{
            Stolik stolik = db.getStolik(indeksWejscia);
            wplataEdit.setText(Double.toString(stolik.getWplata()));
            wyplataEdit.setText(Double.toString(stolik.getWyplata()));
            dataEdit.setText(stolik.getData().toString());

        }

    }
}
