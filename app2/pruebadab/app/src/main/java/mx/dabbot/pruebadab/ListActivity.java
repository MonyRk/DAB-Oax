package mx.dabbot.pruebadab;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    String infoTwit="Aqui va el twit seleccionado";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listview = (ListView) findViewById(R.id.listTwits);
        //en vez de agregar i se deben agregar los twits
        for (int i = 1; i <= cantidad; i++) list.add(i);

        adapter = new ArrayAdapter<>(ListActivity.this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                if (position >= 0) {
                                                    Intent myIntent = new Intent(view.getContext(), BackActivity.class);
                                                    myIntent.putExtra("twit", infoTwit);
                                                    startActivity(myIntent);
                                                }


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



