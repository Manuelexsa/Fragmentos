package com.example.kronos.pmdmfragmentos;

import android.provider.BaseColumns;

/**
 * Created by kronos on 05/12/2014.
 */
public class Pinche {
    public Pinche() {
    }

    public static abstract class TablaInmueble implements BaseColumns {
        public static final String TABLA = "Inmueble";
        public static final String LOCALIDAD = "localidad";
        public static final String DIRECCION = "direccion";
        public static final String TIPO = "tipo";
        public static final String PRECIO = "precio";

    }
}
