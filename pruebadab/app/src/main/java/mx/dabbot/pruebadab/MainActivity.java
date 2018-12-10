package mx.dabbot.pruebadab;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listview;
    List list = new ArrayList();
    ArrayAdapter adapter;
    //cantidad es el total de twits que se mostraran
    int cantidad=15;
    //infoTwit es el twit completo
    String infoTwit="Aqui va el twit seleccionado";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.listTwits);
        //en vez de agregar i se deben agregar los twits
        for (int i = 1; i <= cantidad; i++) {
            list.add(i);
        }
        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position >= 0){
                    Intent myIntent = new Intent(view.getContext(), BackActivity.class);
                    myIntent.putExtra("twit", infoTwit);
                    startActivity(myIntent);
                }


            }
        }
        );

    }


}
