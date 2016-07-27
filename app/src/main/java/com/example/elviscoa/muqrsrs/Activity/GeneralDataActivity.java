package com.example.elviscoa.muqrsrs.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.elviscoa.muqrsrs.Database.Database;
import com.example.elviscoa.muqrsrs.Library.GenerarPDF;
import com.example.elviscoa.muqrsrs.R;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class GeneralDataActivity extends AppCompatActivity {
    //putExtra
    private static final String D_ZERO ="D_ZERO";
    private static final String ARCOS ="ARCOS";
    private static final String DOSIS_PRESCRITA="DOSIS_PRESCRITA";
    private static final String NORMALIZACION="NORMALIZACION";
    private static final String PESO_MAXIMO_DOSIS="PESO_MAXIMO_DOSIS";
    private Long tsLong;
    //UI
    private EditText dosis_prescrita;
    private EditText normalizacion;
    private EditText peso_maximo_dosis;
    private EditText d_zero;
    private Spinner  cant_arcos;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    //Database
    private Database dbHandler = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_data_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        d_zero          = (EditText) findViewById(R.id.input_d_zero);
        cant_arcos      = (Spinner) findViewById(R.id.cant_arco);
        dosis_prescrita = (EditText) findViewById(R.id.input_dosis_prescrita);
        normalizacion   = (EditText) findViewById(R.id.input_nomalizacion);
        peso_maximo_dosis=(EditText) findViewById(R.id.input_peso_maximo_dosis);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        //read(this);
        Log.i("Response", String.valueOf(GenerarPDF.read("srs")));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!d_zero.getText().toString().equals("") && !cant_arcos.getSelectedItem().toString().equals("") && !dosis_prescrita.getText().toString().equals("")
                        && !normalizacion.getText().toString().equals("") && !peso_maximo_dosis.getText().toString().equals("")) {
                    Intent i = new Intent(GeneralDataActivity.this, ArcoActivity.class);
                    i.putExtra(D_ZERO, d_zero.getText().toString());
                    i.putExtra(ARCOS, cant_arcos.getSelectedItem().toString());
                    i.putExtra(DOSIS_PRESCRITA, dosis_prescrita.getText().toString());
                    i.putExtra(NORMALIZACION, normalizacion.getText().toString());
                    i.putExtra(PESO_MAXIMO_DOSIS, peso_maximo_dosis.getText().toString());
                    setGeneralData("         ", "           ",
                            "6X", d_zero.getText().toString(), dosis_prescrita.getText().toString(),
                            String.valueOf(Double.parseDouble(normalizacion.getText().toString())),
                            peso_maximo_dosis.getText().toString());
                    //GenerarPDF.GenerarPDF(GeneralDataActivity.this, dbHandler, String.valueOf(tsLong));
                    //fillRecentProductList(dbHandler, String.valueOf(tsLong));
                    i.putExtra("DATE", String.valueOf(tsLong));
                    Log.d("DATE", String.valueOf(tsLong));
                    GeneralDataActivity.this.startActivity(i);
                }
            }
        });
    }

    public void setGeneralData (String PATIENT_ID, String PLAN_ID,
                                String ENERGY, String D_ZERO, String DOSIS_PRESCRITA,
                                String NORMALIZACION, String PESO_MAXIMO_DOSIS){
        dbHandler.write();
        dbHandler.createGeneralData(PATIENT_ID, PLAN_ID,
                generalDate(), ENERGY, D_ZERO, DOSIS_PRESCRITA,
                NORMALIZACION, PESO_MAXIMO_DOSIS);
        dbHandler.close();
        //Toast.makeText(GeneralDataActivity.this, "General", Toast.LENGTH_SHORT).show();
    }

    public String generalDate(){
        tsLong = System.currentTimeMillis()/1000;
        return tsLong.toString();
    }
    private void fillRecentProductList (Database dbHandler,String date){
        Cursor c = dbHandler.getGeneralData(date);
        if (c.moveToFirst()){
            do{
                Log.d("Database", c.getString(0) + " " + c.getString(1)
                        + " " + c.getString(2) + " " + c.getString(3)
                        + " " + c.getString(4) + " " + c.getString(5)
                        + " " + c.getString(6) + " " + c.getString(7));
            }while (c.moveToNext());
        }
        dbHandler.close();
    }
}
