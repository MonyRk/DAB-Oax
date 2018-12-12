package mx.dabbot.pruebadab;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    ListView listview;
    List list = new ArrayList();
    ArrayAdapter adapter;
    //cantidad es el total de twits que se mostraran
    int cantidad=15;
    //infoTwit es el twit completo
    String infoTwit="Aqui va el twit seleccionado";
    JSONArray tweets;
    MyFirebaseInstanceIdService a = new MyFirebaseInstanceIdService();











    @Override
    protected void onCreate(Bundle savedInstanceState) {


         a.onTokenRefresh();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.listTwits);

        //cliente---------------------------
        //String tweets = "";
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
                   //Log.d("qqqq",infoTwit);


                }
            }
        });
        //---------------------------------------













        //en vez de agregar i se deben agregar los twits---------------
        for (int i = 1; i <= cantidad; i++) {

            list.add(i);

        }
        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position >= 0){


                    //json------
                    try {
                        JSONObject objeto = new JSONObject(infoTwit); //Creamos un objeto JSON a partir de la cadena
                        JSONArray tweets_temporal = objeto.optJSONArray("tweets"); //cogemos cada uno de los elementos dentro de la etiqueta "tweets"
                        tweets = tweets_temporal;
                        //Log.d("qqqq",tweets.getString(0));


                        Intent myIntent = new Intent(view.getContext(), BackActivity.class);
                        Log.d("qqqq",tweets.getJSONObject(position).getString("texto"));


                        myIntent.putExtra("twit", tweets.getJSONObject(position).getString("texto"));
                        //myIntent.putExtra("usuario",tweets.getJSONObject(position).getString("usuario"));
                        startActivity(myIntent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //---------------------

                }


            }
        }
        );
        //------------------------------------------------------------------



    }


}
