package com.example.rozrywka.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by Rozrywka on 2016-07-22.
 */
public class DodajWejscie extends android.support.v4.app.DialogFragment{
    public static EditText nazwaText;


    public interface NoticeDialogListener {
        public void onDialogPositiveClick(android.support.v4.app.DialogFragment dialog,boolean isCorrect);
        public void onDialogNegativeClick(android.support.v4.app.DialogFragment dialog);
    }

    NoticeDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
       final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view =inflater.inflate(R.layout.wejscie_dialog,null);
        builder.setView(view);

        builder.setMessage("Nowa aktywność").setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText wplataText = (EditText) view.findViewById(R.id.wplataText);
                EditText wyplataText = (EditText) view.findViewById(R.id.wyplataEdit);
                RadioButton radioTurniej = (RadioButton) view.findViewById(R.id.turniejRadio);
                RadioButton stolikTurniej =(RadioButton) view.findViewById(R.id.stolikRadio);
                String wplata =wplataText.getText().toString();
                String wyplata =wyplataText.getText().toString();
                if(wplata.equals("")||wyplata.equals("")) {
                    Toast.makeText(getContext(), "Wprowadź obie dane", //zmienić to tak żeby przyjmowało jedno albo żadne wypełnione pole
                            Toast.LENGTH_SHORT).show();
                    mListener.onDialogPositiveClick(DodajWejscie.this,false);

                }else {
                    if(radioTurniej.isChecked()){
                        SesjaAkt.czyTurniej=true;
                    }else{
                        SesjaAkt.czyTurniej=false;
                    }
                    SesjaAkt.wplata = Double.parseDouble(wplata);
                    SesjaAkt.wyplata = Double.parseDouble(wyplata);
                    mListener.onDialogPositiveClick(DodajWejscie.this,true);
                }








            }
        }).setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogNegativeClick(DodajWejscie.this);

            }
        });
        return builder.create();

    }
}
