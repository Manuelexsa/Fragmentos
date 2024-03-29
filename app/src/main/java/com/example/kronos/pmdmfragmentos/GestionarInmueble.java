package com.example.kronos.pmdmfragmentos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kronos on 05/12/2014.
 */
public class GestionarInmueble {

    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestionarInmueble(Context c) {
        abd = new Ayudante(c);
    }

    public void open() {
        bd = abd.getWritableDatabase();
    }

    public void openRead() {
        bd = abd.getReadableDatabase();
    }

    public void close() {
        abd.close();
    }

    public long insert(Inmueble objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Pinche.TablaInmueble.LOCALIDAD,objeto.getLocalidad());
        valores.put(Pinche.TablaInmueble.DIRECCION,objeto.getDireccion());
        valores.put(Pinche.TablaInmueble.TIPO,objeto.getTipo());
        valores.put(Pinche.TablaInmueble.PRECIO,objeto.getPrecio());
        long id = bd.insert(Pinche.TablaInmueble.TABLA,null, valores);
        return id;//es el codio autonumerico
    }

    public int delete(Inmueble objeto) {
        String condicion = Pinche.TablaInmueble._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        int cuenta = bd.delete(Pinche.TablaInmueble.TABLA, condicion,argumentos);
        return cuenta;
    }

    public int update(Inmueble objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Pinche.TablaInmueble.LOCALIDAD, objeto.getLocalidad());
        valores.put(Pinche.TablaInmueble.DIRECCION,objeto.getDireccion());
        valores.put(Pinche.TablaInmueble.TIPO,objeto.getTipo());
        valores.put(Pinche.TablaInmueble.PRECIO,objeto.getPrecio());
        String condicion = Pinche.TablaInmueble._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        int cuenta = bd.update(Pinche.TablaInmueble.TABLA, valores,condicion, argumentos);
        return cuenta;
    }

    public List<Inmueble> select(String condicion,String[] parametros,String orden) {
        List<Inmueble> ali = new ArrayList<Inmueble>();
        Cursor cursor = bd.query(Pinche.TablaInmueble.TABLA, null,condicion, null, null, null, null);
        cursor.moveToFirst();
        Inmueble objeto;
        while (!cursor.isAfterLast()) {
            objeto = getRow(cursor);
            ali.add(objeto);
            cursor.moveToNext();
        }
        cursor.close();
        return ali;
    }

    public List<Inmueble>  select(){
        return select(null,null,null);
    }

    public Inmueble getRow(Cursor c) {
        Inmueble objeto = new Inmueble();
        objeto.setId(c.getLong(0));
        objeto.setLocalidad(c.getString(1));
        objeto.setDireccion(c.getString(2));
        objeto.setTipo(c.getString(3));
        objeto.setPrecio(c.getDouble(4));
        return objeto;
    }

    public Cursor getCursor(String condicion, String[] parametros, String orden) {
        Cursor cursor = bd.query(Pinche.TablaInmueble.TABLA, null, condicion, parametros, null, null,orden);
        return cursor;
    }

}