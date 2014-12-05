package com.example.kronos.pmdmfragmentos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kronos on 05/12/2014.
 */
public class Ayudante extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "inmobiliaria.sqlite";
    public static final int DATABASE_VERSION = 2;
    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }
    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion) {
        //transformar esquemas
        //de la version old a la version new
        //sin que se produzcan pérdidas de datos
        //1º crear tablas de respaldo (identicas)
        //2º copio los datos a esas tablas
        //3º borro las tablas originales
        //4º creo las tablas nuevas(llamo a oncreate)
        //5º copio los datos de las tablas de respaldo en las nuevas tablas
        //6º borro las tablas de respaldo
        String sql="drop table if exists "+ Pinche.TablaInmueble.TABLA;
        db.execSQL(sql);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql = "create table "+Pinche.TablaInmueble.TABLA+
                " ("+Pinche.TablaInmueble._ID+
                " integer primary key autoincrement, "+
                Pinche.TablaInmueble.LOCALIDAD+" text, "+
                Pinche.TablaInmueble.DIRECCION+" text,"+
                Pinche.TablaInmueble.TIPO+" text,"+
                Pinche.TablaInmueble.PRECIO+" real)";
        Log.v("sql", sql);
        db.execSQL(sql);
    }
}
