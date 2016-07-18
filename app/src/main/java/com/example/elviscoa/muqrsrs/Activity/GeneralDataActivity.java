package com.example.elviscoa.muqrsrs.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.elviscoa.muqrsrs.R;

public class GeneralDataActivity extends AppCompatActivity {
    //putExtra
    private static final String DOSIS_PRESCRITA="DOSIS_PRESCRITA";
    private static final String NORMALIZACION="NORMALIZACION";
    private static final String PESO_MAXIMO_DOSIS="PESO_MAXIMO_DOSIS";
    //UI
    private EditText dosis_prescrita;
    private EditText normalizacion;
    private EditText peso_maximo_dosis;
    private Toolbar toolbar;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_data_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dosis_prescrita = (EditText) findViewById(R.id.input_dosis_prescrita);
        normalizacion   = (EditText) findViewById(R.id.input_nomalizacion);
        peso_maximo_dosis=(EditText) findViewById(R.id.input_peso_maximo_dosis);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!dosis_prescrita.getText().toString().equals("") && !normalizacion.getText().toString().equals("") && !peso_maximo_dosis.getText().toString().equals("")){
                    Intent i = new Intent(GeneralDataActivity.this, ArcoActivity.class);
                    i.putExtra(DOSIS_PRESCRITA, dosis_prescrita.getText().toString());
                    i.putExtra(NORMALIZACION, normalizacion.getText().toString());
                    i.putExtra(PESO_MAXIMO_DOSIS, peso_maximo_dosis.getText().toString());
                    GeneralDataActivity.this.startActivity(i);
                }
            }
        });
    }


}
