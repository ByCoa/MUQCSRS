package com.example.elviscoa.muqrsrs.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.elviscoa.muqrsrs.Class.OutputFactor;
import com.example.elviscoa.muqrsrs.Class.Six_X_Trilogy;
import com.example.elviscoa.muqrsrs.Class.TMR;
import com.example.elviscoa.muqrsrs.Class.Util;
import com.example.elviscoa.muqrsrs.R;

/**
 * Created by soluciones on 7/5/2016.
 */
public class SecondActivity extends AppCompatActivity {
    //Constants
    private static final String STRINGDOSIS_PRESCRITA="DOSIS_PRESCRITA";
    private static final String STRINGNORMALIZACION="NORMALIZACION";
    private static final String STRINGPESO_MAXIMO_DOSIS="PESO_MAXIMO_DOSIS";
    //Extras
    private Integer DOSIS_PRESCRITA=2000;
    private Double NORMALIZACION=1.0;
    private Double PESO_MAXIMO_DOSIS=5.052;
    //UI
    private Spinner  cono;
    private EditText output_factor;
    private EditText profundidad;
    private EditText tmr;
    private EditText peso_arco;
    private EditText dosis_fraccion;
    private EditText mu_tps;
    private EditText per_dif;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    //Variables
    private String cono_value=null;
    private String output_factor_value=null;
    private String profundidad_value;
    private String tmr_value=null;
    private String peso_arco_value=null;
    private String dosis_fraccion_value=null;
    private String mu_tps_value=null;
    private String per_dif_value=null;
    private Integer cono_index=null;
    private Integer profundidad_index=null;
    //Class
    private Six_X_Trilogy six_x_trilogy_class;
    private OutputFactor outputFactor_class;
    private TMR tmr_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arco_data_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initInstancesTabs();
        cono=(Spinner) findViewById(R.id.cono);
        Bundle extras = getIntent().getExtras();
        /*
        DOSIS_PRESCRITA=extras.getInt(STRINGDOSIS_PRESCRITA);
        NORMALIZACION=extras.getDouble(STRINGNORMALIZACION);
        PESO_MAXIMO_DOSIS=extras.getDouble(STRINGPESO_MAXIMO_DOSIS);
        */
        output_factor=(EditText) findViewById(R.id.input_output_factor);
        profundidad=(EditText) findViewById(R.id.input_profundidad);
        tmr=(EditText) findViewById(R.id.input_tmr);
        peso_arco=(EditText) findViewById(R.id.input_peso_arco);
        dosis_fraccion=(EditText) findViewById(R.id.input_dosis_fraccion);
        mu_tps=(EditText) findViewById(R.id.input_mu_tps);
        per_dif=(EditText) findViewById(R.id.input_percentage_dif);
        six_x_trilogy_class= new Six_X_Trilogy(SecondActivity.this,DOSIS_PRESCRITA,NORMALIZACION,PESO_MAXIMO_DOSIS);

        cono.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                cono_value=cono.getSelectedItem().toString();
                setOutput_factor_text_value(SecondActivity.this,cono_value);           }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

        profundidad.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    profundidad_value = profundidad.getText().toString();
                    setTMR_text_value(SecondActivity.this, cono_value, profundidad_value);
                }
                return false;
            }
        });


        peso_arco.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    peso_arco_value = peso_arco.getText().toString();
                    setDosis_fraccion_text_value(peso_arco_value);
                }
                return false;
            }
        });

        mu_tps.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mu_tps_value=mu_tps.getText().toString();
                    setPer_dif_text_value(mu_tps_value);
                }
                return false;
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void setPer_dif_text_value (String mu_tps_value){
        if (!cono.equals("") && !profundidad.equals("") && Double.parseDouble(profundidad_value)>=2.0 &&  Double.parseDouble(profundidad_value)<=25.0 && peso_arco_value!=null){
            six_x_trilogy_class.setMU_TPS(Double.parseDouble(mu_tps_value));
            Log.i ("Dosis por fraccion",dosis_fraccion_value);
            Log.i ("Output Factor", String.valueOf(outputFactor_class.getOutputFactor(Integer.parseInt(new Util().splitCono(cono_value)))));
            Log.i ("TMR",String.valueOf(tmr_class.getTMR(outputFactor_class.getCono_index(),Double.parseDouble(profundidad_value))));
            per_dif.setText(String.valueOf(six_x_trilogy_class.getMU(outputFactor_class.getOutputFactor(Integer.parseInt(new Util().splitCono(cono_value))),tmr_class.getTMR(outputFactor_class.getCono_index(),Double.parseDouble(profundidad_value)))));
        }

    }

    public void setDosis_fraccion_text_value(String peso_arco_value){
        if (!cono.equals("") && !profundidad.equals("") && Double.parseDouble(profundidad_value)>=2.0 &&  Double.parseDouble(profundidad_value)<=25.0 && peso_arco_value!=null) {
            six_x_trilogy_class.setPeso_del_arco(Double.parseDouble(peso_arco_value));
            dosis_fraccion.setText(String.valueOf(six_x_trilogy_class.getDosisXFraccion()));
            dosis_fraccion_value = dosis_fraccion.getText().toString();
        }

    }
    public void setTMR_text_value (Context context,String cono,String profundidad){
        if (!cono.equals("") && !profundidad.equals("") && Double.parseDouble(profundidad)>=2.0 &&  Double.parseDouble(profundidad)<=25.0){
            tmr_class=new TMR();
            six_x_trilogy_class.setProfundidad((double) tmr_class.getProfundidadIndex(Double.parseDouble(profundidad)));
            tmr.setText(String.valueOf(tmr_class.getTMR(outputFactor_class.getCono_index(),Double.parseDouble(profundidad))));
            six_x_trilogy_class.setTmr(tmr_class);
        }
    }

    public void setOutput_factor_text_value (Context context,String cono){
        outputFactor_class= new OutputFactor();
        six_x_trilogy_class.setCono(outputFactor_class.getCono_index());
        output_factor.setText(String.valueOf(outputFactor_class.getOutputFactor(Integer.parseInt(new Util().splitCono(cono)))));
        six_x_trilogy_class.setOutputfactor(outputFactor_class);
    }


    private void initInstancesTabs() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (String tabs: this.getResources().getStringArray(R.array.tabs)){
            tabLayout.addTab(tabLayout.newTab().setText(tabs));
        }

    }
}
