package com.example.merts.kelimeezberleme;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    database dbhelper;
    Context context = this;
    EditText trkelime, enkelime;
    Button ceviributon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            dbhelper.createDB();
            dbhelper.openDB();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbhelper = new database(context);
        trkelime = (EditText)findViewById(R.id.trkelime);
        enkelime = (EditText)findViewById(R.id.enkelime);
        ceviributon = (Button)findViewById(R.id.ceviributon);
        ceviributon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String englishword = enkelime.getText().toString();
                Cursor crs = dbhelper.getDB().query("kelimeler", new String[]{"kelimeTr"}, "kelimeEn = ?", new String[]{englishword},null,null,null);
                if (crs != null){
                    crs.moveToFirst();
                    String turkishword = crs.getString(crs.getColumnIndex("kelimeTR"));
                    trkelime.setText(turkishword);
                }
                else{
                    Toast.makeText(context,"KELİMENİN KARŞILIĞI BULUNAMADI!", Toast.LENGTH_SHORT).show();
                    enkelime.getText().clear();
                }


            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbhelper.close();
    }
}
