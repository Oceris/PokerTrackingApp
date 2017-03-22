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
 * Created by Rozrywka on 2016-07-22.
 */
public class WejscieAdapter extends ArrayAdapter<Wejscie> {
    private static class ViewHolder {
        TextView typ;
        TextView data;
        TextView koszt;
        TextView wygrana;
        ImageView photo;
    }
    public WejscieAdapter(Context context, ArrayList<Wejscie> wejscia) {
        super(context,R.layout.element_sesja,wejscia);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        Wejscie wejscie = getItem(position);
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder= new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.element_wejscie,parent,false);
            viewHolder.typ=(TextView) convertView.findViewById(R.id.dataEdit);
            viewHolder.data=(TextView) convertView.findViewById(R.id.data);
            viewHolder.koszt=(TextView) convertView.findViewById(R.id.koszt);
            viewHolder.wygrana=(TextView) convertView.findViewById(R.id.wygrana);
            viewHolder.photo = (ImageView) convertView.findViewById(R.id.ikona);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.photo.setImageResource(R.drawable.karta);
        viewHolder.koszt.setText(String.valueOf(wejscie.wplata));
        viewHolder.wygrana.setText(String.valueOf(wejscie.wyplata));
        viewHolder.data.setText(wejscie.data);
        if(wejscie instanceof Turniej)
        {
            viewHolder.typ.setText("Turniej");
        }else if(wejscie instanceof Stolik)
        {
            viewHolder.typ.setText("Stolik");
        }

        return convertView;
    }
}
