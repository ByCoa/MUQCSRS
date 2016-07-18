package com.example.elviscoa.muqrsrs.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elviscoa.muqrsrs.Activity.ArcoActivity;
import com.example.elviscoa.muqrsrs.Class.OutputFactor;
import com.example.elviscoa.muqrsrs.Class.Six_X_Trilogy;
import com.example.elviscoa.muqrsrs.Class.TMR;
import com.example.elviscoa.muqrsrs.Class.Util;
import com.example.elviscoa.muqrsrs.Database.Database;
import com.example.elviscoa.muqrsrs.R;

/**
 * Created by elvis on 09/07/16.
 */
public class Arco4 extends Fragment {
    //UI
    private Spinner cono;
    private EditText output_factor;
    private EditText profundidad;
    private EditText tmr;
    private EditText peso_arco;
    private EditText dosis_fraccion;
    private EditText mu_tps;
    private EditText per_dif;
    private FloatingActionButton fab;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.arco_data_content, container, false);
        cono=(Spinner) view.findViewById(R.id.cono);
        output_factor=(EditText) view.findViewById(R.id.input_output_factor);
        profundidad=(EditText) view.findViewById(R.id.input_profundidad);
        tmr=(EditText) view.findViewById(R.id.input_tmr);
        peso_arco=(EditText) view.findViewById(R.id.input_peso_arco);
        dosis_fraccion=(EditText) view.findViewById(R.id.input_dosis_fraccion);
        mu_tps=(EditText) view.findViewById(R.id.input_mu_tps);
        per_dif=(EditText) view.findViewById(R.id.input_percentage_dif);
        fab= (FloatingActionButton) view.findViewById(R.id.fabarc);
        final ArcoActivity arcoActivity= (ArcoActivity) getActivity();
        six_x_trilogy_class= new Six_X_Trilogy(getActivity(), arcoActivity.getDOSIS_PRESCRITA(),arcoActivity.getNORMALIZACION(),arcoActivity.getPESO_MAXIMO_DOSIS());
        cono.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                cono_value = cono.getSelectedItem().toString();
                setOutput_factor_text_value(getActivity(), cono_value);
            }

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
                    setTMR_text_value(getActivity(), cono_value, profundidad_value);
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

        return view;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cono.equals("") && !profundidad.equals("") && Double.parseDouble(profundidad_value) >= 2.0 && Double.parseDouble(profundidad_value) <= 25.0 && peso_arco_value != null) {
                    final ArcoActivity arcoActivity = (ArcoActivity) getActivity();
                    Database database = new Database(getActivity());
                    database.write();
                    database.createArc("ARCO4", cono_value, output_factor_value, profundidad_value, tmr_value, peso_arco_value, dosis_fraccion_value, mu_tps_value, per_dif_value, arcoActivity.getDATE());
                    database.close();
                }
                Toast.makeText(getActivity(), "ARCO 4 Guardado exitosamente", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setPer_dif_text_value (String mu_tps_value){
        if (!cono.equals("") && !profundidad.equals("") && Double.parseDouble(profundidad_value)>=2.0 &&  Double.parseDouble(profundidad_value)<=25.0 && peso_arco_value!=null){
            six_x_trilogy_class.setMU_TPS(Double.parseDouble(mu_tps_value));
            Log.i("Dosis por fraccion", dosis_fraccion_value);
            Log.i ("Output Factor", String.valueOf(outputFactor_class.getOutputFactor(Integer.parseInt(new Util().splitCono(cono_value)))));
            Log.i ("TMR",String.valueOf(tmr_class.getTMR(outputFactor_class.getCono_index(), Double.parseDouble(profundidad_value))));
            per_dif.setText(String.valueOf(six_x_trilogy_class.getMU(outputFactor_class.getOutputFactor(Integer.parseInt(new Util().splitCono(cono_value))), tmr_class.getTMR(outputFactor_class.getCono_index(), Double.parseDouble(profundidad_value)))));
            per_dif_value=per_dif.getText().toString();
        }

    }

    public void setDosis_fraccion_text_value(String peso_arco_value){
        if (!cono.equals("") && !profundidad.equals("") && Double.parseDouble(profundidad_value)>=2.0 &&  Double.parseDouble(profundidad_value)<=25.0 && peso_arco_value!=null) {
            six_x_trilogy_class.setPeso_del_arco(Double.parseDouble(peso_arco_value));
            dosis_fraccion.setText(String.valueOf(six_x_trilogy_class.getDosisXFraccion()));
            dosis_fraccion_value = dosis_fraccion.getText().toString();
        }

    }
    public void setTMR_text_value (Context context, String cono, String profundidad){
        if (!cono.equals("") && !profundidad.equals("") && Double.parseDouble(profundidad)>=2.0 &&  Double.parseDouble(profundidad)<=25.0){
            tmr_class=new TMR();
            six_x_trilogy_class.setProfundidad((double) tmr_class.getProfundidadIndex(Double.parseDouble(profundidad)));
            tmr.setText(String.valueOf(tmr_class.getTMR(outputFactor_class.getCono_index(), Double.parseDouble(profundidad))));
            tmr_value=tmr.getText().toString();
            six_x_trilogy_class.setTmr(tmr_class);
        }
    }

    public void setOutput_factor_text_value (Context context,String cono){
        outputFactor_class= new OutputFactor(context);
        six_x_trilogy_class.setCono(outputFactor_class.getCono_index());
        output_factor.setText(String.valueOf(outputFactor_class.getOutputFactor(Integer.parseInt(new Util().splitCono(cono)))));
        output_factor_value=output_factor.getText().toString();
        six_x_trilogy_class.setOutputfactor(outputFactor_class);
    }
}