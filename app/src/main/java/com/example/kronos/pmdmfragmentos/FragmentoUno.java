package com.example.kronos.pmdmfragmentos;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;


public class FragmentoUno extends Fragment {

    public FragmentoUno() {
        // Required empty public constructor
    }


    ImageView iv;
    View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragmento_uno, container, false);
        return v;
    }
}