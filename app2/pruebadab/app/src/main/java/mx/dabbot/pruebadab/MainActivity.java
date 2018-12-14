package mx.dabbot.pruebadab;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button botonbloqueos, botonaccidentes, botonreportes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    botonbloqueos = (Button) findViewById(R.id.botonbloqueos);
    botonbloqueos.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(myIntent);
        }
    });


        botonaccidentes = (Button) findViewById(R.id.accidentes);
        botonaccidentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(myIntent);
            }
        });


        botonreportes = (Button) findViewById(R.id.botonreportes);
        botonreportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(myIntent);
            }
        });

    }


}
