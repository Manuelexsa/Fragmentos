package com.example.kronos.pmdmfragmentos;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kronos on 03/12/2014.
 */
public class Adaptador extends CursorAdapter {

    private Cursor c;

    public Adaptador(Context context, Cursor c){
        super(context,c,true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.detalle_lista,parent,false);
        return v;
    }
    public static class ViewHolder{
        public TextView tvDireccion, tvLocalidad, tvPrecio, tvTipo;

        public int posicion;
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder vh;
        Inmueble in=null;

        if(view!=null){
            GestionarInmueble gi = new GestionarInmueble(context);
            in = gi.getRow(cursor);
            vh = new ViewHolder();
            vh.tvDireccion = (TextView)view.findViewById(R.id.tvCalle);
            vh.tvLocalidad = (TextView)view.findViewById(R.id.tvLocalidad);
            vh.tvTipo = (TextView)view.findViewById(R.id.tvTipo);
            vh.tvPrecio = (TextView)view.findViewById(R.id.tvPrecio);

            view.setTag(vh);
        }else{
            vh = (ViewHolder)view.getTag();
        }


        vh.tvDireccion.setText(in.getDireccion());
        vh.tvLocalidad.setText(in.getLocalidad());
        vh.tvTipo.setText(in.getTipo());
        vh.tvPrecio.setText(in.getPrecio()+"");
    }
}
