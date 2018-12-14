package mx.dabbot.pruebadab;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {


    ListView listview;
    List<Integer> list = new ArrayList<Integer>();
    ArrayAdapter<Integer> adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    //cantidad es el total de twits que se mostraran
    int cantidad=10;
    //infoTwit es el twit completo
    String infoTwit="hola";

    JSONArray tweets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Log.d("qwqw",getIntent().getExtras().getString("respuesta"));
        infoTwit = getIntent().getExtras().getString("respuesta");
        listview = (ListView) findViewById(R.id.listTwits);
        //en vez de agregar i se deben agregar los twits
        //int cantidad = 0;


        JSONObject objeto = null; //Creamos un objeto JSON a partir de la cadena
        try {
            objeto = new JSONObject(infoTwit);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray tweets_temporal = objeto.optJSONArray("tweets"); //cogemos cada uno de los elementos dentro de la etiqueta "tweets"
        JSONArray seleccion = new JSONArray();

        for(int i=0;i<tweets_temporal.length();i++){

            try {
                if(tweets_temporal.getJSONObject(i).getString("categoria").equals(getIntent().getExtras().getString("categoria"))){
                    seleccion.put(tweets_temporal.getJSONObject(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        cantidad = seleccion.length();




        for (int i = 1; i <= cantidad; i++) list.add(i);



        adapter = new ArrayAdapter<>(ListActivity.this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                                if (position >= 0) {
                                                    //response a json y mostrar en activity------
                                                    try {
                                                        JSONObject objeto = new JSONObject(infoTwit); //Creamos un objeto JSON a partir de la cadena
                                                        JSONArray tweets_temporal = objeto.optJSONArray("tweets"); //cogemos cada uno de los elementos dentro de la etiqueta "tweets"
                                                        tweets = tweets_temporal;
                                                        //Log.d("qqqq",tweets.getString(0));


                                                        Intent myIntent = new Intent(view.getContext(), BackActivity.class);
                                                        Log.d("qqqq", tweets.getJSONObject(position).getString("texto"));


                                                        myIntent.putExtra("twit", tweets.getJSONObject(position).getString("texto")+
                                                                "\n\n Usuario: "+tweets.getJSONObject(position).getString("usuario")+
                                                                "\n\n Localidad: "+tweets.getJSONObject(position).getString("locacion")+
                                                                "\n\n Fecha: "+tweets.getJSONObject(position).getString("fecha")+
                                                                "\n\n Seguimiento: "+tweets.getJSONObject(position).getString("estatus")
                                                        );
                                                        //myIntent.putExtra("usuario",tweets.getJSONObject(position).getString("usuario"));
                                                        startActivity(myIntent);


                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                                //---------------------





                                            }

        }
        );



        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.tweets);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //peticion de tweets
                cantidad++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },4000);
            }
        });


    setupActionBar();
    }//finish onCreate


    private void setupActionBar() {
        ActionBar atras = getSupportActionBar();
        if (atras != null) {
            atras.setDisplayHomeAsUpEnabled(true);
            atras.setTitle("Por Categoria");

        }

    }
}



