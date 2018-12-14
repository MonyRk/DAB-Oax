package mx.dabbot.pruebadab;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

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

    Button botonbloqueos, botonaccidentes, botonreportes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //------ notificaciones programadas

        time timer = new time();
        timer.execute();


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
//---------------------------------------



    botonbloqueos = (Button) findViewById(R.id.botonbloqueos);
    botonbloqueos.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(MainActivity.this, ListActivity.class);

            JSONObject bloqueos = null; //Creamos un objeto JSON a partir de la cadena
            try {
                bloqueos = new JSONObject(infoTwit);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray tweets_temporal = bloqueos.optJSONArray("tweets"); //cogemos cada uno de los elementos dentro de la etiqueta "tweets"


            myIntent.putExtra("respuesta",infoTwit);
            myIntent.putExtra("categoria","bloqueo");
            startActivity(myIntent);
        }
    });


        botonaccidentes = (Button) findViewById(R.id.accidentes);
        botonaccidentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ListActivity.class);

                JSONObject bloqueos = null; //Creamos un objeto JSON a partir de la cadena
                try {
                    bloqueos = new JSONObject(infoTwit);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray tweets_temporal = bloqueos.optJSONArray("tweets"); //cogemos cada uno de los elementos dentro de la etiqueta "tweets"


                myIntent.putExtra("respuesta",infoTwit);
                myIntent.putExtra("categoria","accidente");
                startActivity(myIntent);
            }
        });


        botonreportes = (Button) findViewById(R.id.botonreportes);
        botonreportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ListActivity.class);


                JSONObject bloqueos = null; //Creamos un objeto JSON a partir de la cadena
                try {
                    bloqueos = new JSONObject(infoTwit);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray tweets_temporal = bloqueos.optJSONArray("tweets"); //cogemos cada uno de los elementos dentro de la etiqueta "tweets"


                myIntent.putExtra("respuesta",infoTwit);
                myIntent.putExtra("categoria","emergencia");
                startActivity(myIntent);
            }
        });

    }





    //clase de time
    public class time extends AsyncTask<Void,Integer,Boolean> {

        //hilo que se ejecutara en segundo plano----------------------------------------------------------
        public void hilo(){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void ejecutar(){
            time timer = new time();
            timer.execute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            for(int i=1;i<=20;i++){
                hilo();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            ejecutar();
            addNotification();
        }
    }



}





