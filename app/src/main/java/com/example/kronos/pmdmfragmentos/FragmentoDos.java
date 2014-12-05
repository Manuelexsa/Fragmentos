package com.example.kronos.pmdmfragmentos;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class FragmentoDos extends Fragment {

    public FragmentoDos() {
        // Required empty public constructor
    }
    TextView tvTituloDetalle;
    ImageView iv;
    View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragmento_dos, container, false);
        iv =(ImageView) v.findViewById(R.id.iv);
        return v;
    }

    public void setText(String s){
        this.tvTituloDetalle = (TextView)v.findViewById(R.id.tvTituloDetalle);
        this.tvTituloDetalle.setText(s);
    }
}
