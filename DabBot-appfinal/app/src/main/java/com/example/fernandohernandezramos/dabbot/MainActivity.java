package com.example.fernandohernandezramos.dabbot;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String infoTwit="Aqui va el twit seleccionado";

    //notificaciones locales-------------------------------------------------------------------------------------------------

    private void addNotification() {
        // Builds your notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Dab Bot")
                .setContentText("¿Hay alguna emergencia o evento cercano que desees compartir a la comunidad?");

        // Creates the intent needed to show the notification
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab =  findViewById(R.id.fab);
        int tuiter = Color.parseColor("#00ABF1");
        fab.setBackgroundTintList(ColorStateList.valueOf(tuiter));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = getPackageManager().getLaunchIntentForPackage("com.twitter.android");
                Toast toast1 = Toast.makeText(getApplicationContext(),"Abriendo aplicacion de Twitter", Toast.LENGTH_SHORT);
                toast1.show();
                if (intento != null) {
                    // Iniciar la aplicacion de twitter
                    intento.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intento);
                } else {
                    // Enviar al usuario a la playstore si no la tiene instalada
                    intento = new Intent(Intent.ACTION_VIEW);
                    intento.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intento.setData(Uri.parse("market://details?id=" + "com.twitter.android"));
                    startActivity(intento);
                }
            }
        });

        //cliente api---------------------------
        OkHttpClient client = new OkHttpClient();

        String url = "https://infinite-crag-52022.herokuapp.com/noticias_recientes";

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    //Log.d("qqqq",response.body().string());
                    infoTwit = response.body().string();
                    infoTwit = "{\"tweets\":"+infoTwit+"}";
                    Log.d("qqqq",infoTwit);


                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.bloqueos) {
            Intent intento = new Intent(this,Lista.class);
            Log.d("Valor infotuit",infoTwit);
            if(!infoTwit.equals("{\"tweets\":[]}")) {
            JSONObject bloqueos = null; //Creamos un objeto JSON a partir de la cadena
                try {
                    bloqueos = new JSONObject(infoTwit);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray tweets_temporal = bloqueos.optJSONArray("tweets"); //cogemos cada uno de los elementos dentro de la etiqueta "tweets"

                intento.putExtra("respuesta",infoTwit);
                intento.putExtra("categoria","bloqueo");

                //intento.putExtra("respuesta", infoTwit);
                //intento.putExtra("categoria", "bloqueo");
                //String[] valores = {"bloqueo1","bloqueo2"};
                //intento.putExtra("valores_lista",valores);
               startActivity(intento);
            }else{
                String[] valores = {"No se han encontrado tweets, intentar mas tarde"};
                intento.putExtra("valores_lista",valores);
                startActivity(intento);
            }
        } else if (id == R.id.asaltos) {
            Intent intento = new Intent(this,Lista.class);
            Log.d("Valor infotuit",infoTwit);
            if(!infoTwit.equals("{\"tweets\":[]}")) {
                JSONObject bloqueos = null; //Creamos un objeto JSON a partir de la cadena
                try {
                    bloqueos = new JSONObject(infoTwit);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray tweets_temporal = bloqueos.optJSONArray("tweets"); //cogemos cada uno de los elementos dentro de la etiqueta "tweets"

                intento.putExtra("respuesta",infoTwit);
                intento.putExtra("categoria","asalto");
                startActivity(intento);
            }else{
                String[] valores = {"No se han encontrado tweets, intentar mas tarde"};
                intento.putExtra("valores_lista",valores);
                startActivity(intento);
            }
        } else if (id == R.id.emergencias) {
            Intent intento = new Intent(this,Lista.class);
            Log.d("Valor infotuit",infoTwit);
            if(!infoTwit.equals("{\"tweets\":[]}")) {
                JSONObject bloqueos = null; //Creamos un objeto JSON a partir de la cadena
                try {
                    bloqueos = new JSONObject(infoTwit);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray tweets_temporal = bloqueos.optJSONArray("tweets"); //cogemos cada uno de los elementos dentro de la etiqueta "tweets"


                intento.putExtra("respuesta",infoTwit);
                intento.putExtra("categoria","accidente");
                startActivity(intento);
            }else{
                String[] valores = {"No se han encontrado tweets, intentar mas tarde"};
                intento.putExtra("valores_lista",valores);
                startActivity(intento);
            }
        } else if (id == R.id.reportes) {
            Intent intento = new Intent(this,Lista.class);
            Log.d("Valor infotuit",infoTwit);
            if(!infoTwit.equals("{\"tweets\":[]}")) {
                JSONObject bloqueos = null; //Creamos un objeto JSON a partir de la cadena
                try {
                    bloqueos = new JSONObject(infoTwit);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray tweets_temporal = bloqueos.optJSONArray("tweets"); //cogemos cada uno de los elementos dentro de la etiqueta "tweets"


                intento.putExtra("respuesta",infoTwit);
                intento.putExtra("categoria","reportes");
                startActivity(intento);
            }else{
                String[] valores = {"No se han encontrado tweets, intentar mas tarde"};
                intento.putExtra("valores_lista",valores);
                startActivity(intento);
            }
        } else if(id == R.id.facebook){
            Uri uri = Uri.parse("https://www.facebook.com/DAB-Oax-326523064608925/");
            Intent intento = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intento);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //clase de time
    public class time extends AsyncTask<Void,Integer,Boolean> {

        //hilo que se ejecutara en segundo plano----------------------------------------------------------
        public void hilo(){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void ejecutar(){
            time timer = new time();
            timer.execute();
        }

        protected Boolean doInBackground(Void... voids) {
            for(int i=1;i<=20;i++){
                hilo();
            }
            return true;
        }

        protected void onPostExecute(Boolean aBoolean) {
            ejecutar();
            addNotification();
        }
    }
}
