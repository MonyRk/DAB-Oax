package mx.dabbot.pruebadab;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class BackActivity extends AppCompatActivity {
    TextView twitCompleto;
    @Override
    public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_datos);
            setupActionBar();
            recibirInfo();
    }

    private void setupActionBar(){
        ActionBar atras = getSupportActionBar();
        if (atras != null){
            atras.setDisplayHomeAsUpEnabled(true);
            atras.setTitle("Info");

        }
    }

    private void recibirInfo (){
            Bundle extras = getIntent().getExtras();
            String infot =  extras.getString("twit");
        twitCompleto = (TextView) findViewById(R.id.twitCompleto);
        twitCompleto.setText(infot);

    }


}
