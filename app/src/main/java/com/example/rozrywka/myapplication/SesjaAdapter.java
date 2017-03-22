package com.example.rozrywka.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rozrywka on 2016-07-21.
 */
public class SesjaAdapter extends ArrayAdapter<Sesja>{
    private double buyIn=0.0;
    private double prize=0.0;
    private double profit=0.0;

    private static class ViewHolder {
        TextView data;
        TextView zysk;
        ImageView photo;
    }
    public SesjaAdapter(Context context, ArrayList<Sesja> sesje) {
        super(context,R.layout.element_sesja,sesje);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        buyIn=0.0;
        prize=0.0;
        profit=0.0;
        Sesja sesja = getItem(position);
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder= new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.element_sesja,parent,false);
            viewHolder.data=(TextView) convertView.findViewById(R.id.dataEdit);
            viewHolder.zysk=(TextView) convertView.findViewById(R.id.opis);
            viewHolder.photo = (ImageView) convertView.findViewById(R.id.ikona);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        countProfit(sesja);
        viewHolder.data.setText(sesja.data);
        viewHolder.photo.setImageResource(R.drawable.karta);
        if(profit==0.0)
        {
            viewHolder.zysk.setText("Brak danych");
        }else {
            viewHolder.zysk.setText(String.valueOf(profit));
        }
        return convertView;
    }

    public void countProfit(Sesja session){
        DataBaseHelper db = new DataBaseHelper(getContext());
        ArrayList<Wejscie> entries = new ArrayList<Wejscie>();
        long sessionsId =session.getIdS();
        entries.addAll(db.getAllStoliki(sessionsId));
        entries.addAll(db.getAllTurnieje(sessionsId));
        for(Wejscie w: entries)
        {
            buyIn+=w.getWplata();
            prize+=w.getWyplata();
        }
        profit=prize-buyIn;


    }

}
