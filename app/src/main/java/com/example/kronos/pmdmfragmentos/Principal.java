package com.example.kronos.pmdmfragmentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class Principal extends Activity {

    private GestionarInmueble gi;
    private Adaptador ad;
    private static final int CREAR=0;
    private static final int MODIFICAR=1;
    private final int DETALLE = 2;
    private final int FOTO = 3;
    ListView lv;
    Inmueble inmuebleFoto;
    ArrayList <File> fotos;
    int posActual;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==Activity.RESULT_OK){
            String localidad;
            String direccion;
            String tipo;
            String precio;
            long index=0;
            Inmueble i;
            gi.open();
            switch (requestCode){
                case CREAR:
                    //Hago cosas
                    localidad = data.getStringExtra("localidad");
                    direccion = data.getStringExtra("direccion");
                    tipo = data.getStringExtra("tipo");
                    precio = data.getStringExtra("precio");
                    i = new Inmueble(localidad,direccion,tipo,precio);
                    gi.insert(i);
                    actualizarLista();
                    break;
                case MODIFICAR:
                    //Hago cosas
                    index = data.getLongExtra("index",-1);
                    localidad = data.getStringExtra("localidad");
                    direccion = data.getStringExtra("direccion");
                    tipo = data.getStringExtra("tipo");
                    precio = data.getStringExtra("precio");
                    Double prec = Double.parseDouble(precio);
                    i = new Inmueble(index,localidad,direccion,tipo,prec);
                    gi.update(i);
                    actualizarLista();
                    break;
                case DETALLE:
                    i =(Inmueble) data.getSerializableExtra("inmueble");
                    ArrayList <File>fotos = new ArrayList<File>();
                    FragmentoDos fd = (FragmentoDos)getFragmentManager().findFragmentById(R.id.fragment2);
                    fotos = (ArrayList)data.getExtras().get("fotos");
                    if(fotos.size()>0){
                        fd.iv.setImageURI(Uri.fromFile(fotos.get(0)));

                    }
                    fd.setText(i.getDireccion() + ", " + i.getLocalidad());
                    break;
                case FOTO:
                    Bitmap foto = (Bitmap)data.getExtras().get("data");
                    FileOutputStream salida;
                    i = inmuebleFoto;
                    Calendar cal = new GregorianCalendar();
                    Date date = cal.getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
                    String formatteDate = df.format(date);
                    try {
                        salida = new FileOutputStream(getExternalFilesDir(Environment.DIRECTORY_DCIM)
                                +"/inmueble_"+i.getId()+"_"+formatteDate+".jpg");
                        foto.compress(Bitmap.CompressFormat.JPEG, 90, salida);
                    } catch (FileNotFoundException e) {
                    }


                    break;
            }
        }else {
            switch (requestCode){
                case CREAR:

                    break;
                case MODIFICAR:

                    break;


        }
    }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        gi = new GestionarInmueble(this);
        lv=(ListView)findViewById(R.id.lvLista);
        registerForContextMenu(lv);
        final ListView lv = (ListView)findViewById(R.id.lvLista);
        final FragmentoDos fd = (FragmentoDos)getFragmentManager().findFragmentById(R.id.fragment2);
        posActual = 0;


        final boolean horizontal = fd!=null && fd.isInLayout();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View v,int pos, long id) {
                List<Inmueble> al = gi.select(null,null,null);
                fotos = new ArrayList<File>();
                String ruta = getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath();
                File dir = new  File(ruta);
                File[] fotos1 = dir.listFiles();
                for (int i = 0; i < fotos1.length; i++) {
                    String idIn="";
                    idIn=fotos1[i].getName().split("_")[1];
                    Log.v("ID:", "ID:" + idIn);
                    if(idIn.equals(al.get(pos).getId()+"")){
                        fotos.add(fotos1[i]);
                    }
                }
                Inmueble inm = al.get(pos);
                if(horizontal){
                    Button btSiguiente = (Button)findViewById(R.id.btSiguiente);
                    Button btAnterior = (Button)findViewById(R.id.btAnterior);
                    btSiguiente.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int posMax;
                            posMax=fotos.size()-1;
                            if(posActual+1<=posMax){
                                fd.iv.setImageURI(Uri.fromFile(fotos.get(posActual+1)));
                                posActual++;
                            }else{
                                fd.iv.setImageURI(Uri.fromFile(fotos.get(0)));
                                posActual=0;
                            }
                        }
                    });
                    btAnterior.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int posMax;
                            posMax=fotos.size()-1;
                            if(posActual-1>=0){
                                fd.iv.setImageURI(Uri.fromFile(fotos.get(posActual-1)));
                                posActual--;
                            }else{
                                fd.iv.setImageURI(Uri.fromFile(fotos.get(posMax)));
                                posActual=posMax;
                            }
                        }
                    });
                    fd.setText(inm.getDireccion()+", "+inm.getLocalidad());
                    if(fotos.size()>0){
                        fd.iv.setImageURI(Uri.fromFile(fotos.get(0)));
                    }

                }else{
                    Intent i = new Intent(Principal.this,GFotos.class);
                    i.putExtra("inmueble",inm);
                    i.putExtra("fotos", fotos);
                    startActivityForResult(i, DETALLE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        gi.open();
        Cursor c = gi.getCursor(null, null, null);
        ad = new Adaptador(this, c);
        lv.setAdapter(ad);
        //datosPrueba();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gi.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_new:
                Intent i = new Intent(this,GAnadir.class);
                startActivityForResult(i, CREAR);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        int id=item.getItemId();
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int index = info.position;
        List<Inmueble> al = gi.select(null,null,null);
        Inmueble i = al.get(index);
        if(id==R.id.action_borrar){
            gi.delete(i);
            Cursor c = gi.getCursor(null,null,null);
            ad.changeCursor(c);
        }else if(id==R.id.action_modificar){
            Intent intent = new Intent(this,GAnadir.class);
            Bundle b = new Bundle();
            b.putSerializable("inmueble", i);
            b.putInt("index", index);
            intent.putExtras(b);
            startActivityForResult(intent, MODIFICAR);
        }else if (id==R.id.action_foto){
            inmuebleFoto = i;
            Intent intent = new Intent ("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(intent, FOTO);
        }
        return super.onContextItemSelected(item);
    }
    private void tostada(String s){
        String a= s;
    }
    public void actualizarLista(){
        Cursor c = gi.getCursor(null,null,null);
        ad.changeCursor(c);
    }
}
