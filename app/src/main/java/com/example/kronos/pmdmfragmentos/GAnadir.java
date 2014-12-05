package com.example.kronos.pmdmfragmentos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class GAnadir extends Activity {

    EditText etLocalidad, etDireccion, etTipo, etPrecio;
    long index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ganadir);
        etLocalidad = (EditText)findViewById(R.id.etLocalidad);
        etDireccion = (EditText)findViewById(R.id.etDireccion);
        etTipo = (EditText)findViewById(R.id.etTipo);
        etPrecio = (EditText)findViewById(R.id.etPrecio);
        Bundle b = getIntent().getExtras();
        index=0;
        if(b !=null ){
            Inmueble in = (Inmueble)b.getSerializable("inmueble");
            index = in.getId();
            etLocalidad.setText(in.getLocalidad());
            etDireccion.setText(in.getDireccion());
            etTipo.setText(in.getTipo());
            etPrecio.setText(in.getPrecio()+"");
            }


    }

    public void aceptar(View v){
        String localidad,direccion,tipo,precio;
        localidad=etLocalidad.getText().toString();
        direccion= etDireccion.getText().toString();
        tipo = etTipo.getText().toString();
        precio = etPrecio.getText().toString();

        Intent i = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("localidad",localidad);
        bundle.putString("direccion",direccion);
        bundle.putString("tipo",tipo);
        bundle.putString("precio", precio);
        bundle.putLong("index",index);
        i.putExtras(bundle);
        setResult(Activity.RESULT_OK, i);
        finish();
    }
    public void cancelar(View v){
        Intent i = new Intent();
        setResult(Activity.RESULT_CANCELED, i);
        finish();
    }
}