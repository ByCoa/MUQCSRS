package com.example.elviscoa.muqrsrs.Fragment;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.elviscoa.muqrsrs.Activity.ArcoActivity;
import com.example.elviscoa.muqrsrs.Activity.GeneralDataActivity;
import com.example.elviscoa.muqrsrs.Activity.Result;
import com.example.elviscoa.muqrsrs.Class.Six_X_Trilogy;
import com.example.elviscoa.muqrsrs.Class.Util;
import com.example.elviscoa.muqrsrs.Database.Database;
import com.example.elviscoa.muqrsrs.Library.GenerarPDF;
import com.example.elviscoa.muqrsrs.R;

import java.util.ArrayList;

/**
 * Created by soluciones on 7/17/2016.
 */
public class GenerarPDFFragment extends Fragment {
    //UI
    private EditText plan_id;
    private EditText patient_id;
    private FloatingActionButton generarpdf;
    private Button seerror;
    private FloatingActionButton clear;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generarpdf, container, false);
        generarpdf = (FloatingActionButton) view.findViewById(R.id.fabcal);
        patient_id = (EditText) view.findViewById(R.id.input_patient_id);
        plan_id = (EditText) view.findViewById(R.id.input_plan_id);
        seerror = (Button) view.findViewById(R.id.buttongenPDF);
        clear = (FloatingActionButton) view.findViewById(R.id.faberror);
        return view;
    }

    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArcoActivity arcoActivity = (ArcoActivity) getActivity();
                Intent intent = new Intent(arcoActivity, GeneralDataActivity.class);
                arcoActivity.startActivity(intent);
            }});
        generarpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArcoActivity arcoActivity = (ArcoActivity) getActivity();
                ArrayList<Six_X_Trilogy> arrayList = arcoActivity.getArcs();
                ArrayList<Boolean> full = arcoActivity.getFull();
                Boolean fullAll = true;
                generarpdf.setEnabled(true);
                for (int ij = 0; ij < full.size(); ij++) {
                    Log.i("Full", String.valueOf(full.get(ij)));
                    if (!full.get(ij)) {
                        fullAll = false;

                        int aux = ij + 1;
                        Toast.makeText(getActivity(), "Empty fields in ARC: " + aux, Toast.LENGTH_LONG).show();
                        generarpdf.setEnabled(false);
                        break;
                    }

                }
                if (fullAll == true) {
                    Intent intent = new Intent(arcoActivity, Result.class);
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i) != null && !arrayList.get(i).getCone().equals("") && !arrayList.get(i).getAver_depth_cm().equals("") && !arrayList.get(i).getWeight_factor().equals("") && !arrayList.get(i).getMu_tps().equals("")) {
                            Double a, b, error;
                            a = arrayList.get(i).getMUQCSRS();
                            b = arrayList.get(i).getMu_tps();
                            error = new Util().roundThreeDecimals(((b - a) / a) * 100);
                            String extraData = arrayList.get(i).getMu_tps() + "," + (new Util().roundThreeDecimals(arrayList.get(i).getMUQCSRS())) + "," + error;
                            Log.i("Error", arrayList.get(i).getMu_tps() + "," + (new Util().roundThreeDecimals(arrayList.get(i).getMUQCSRS())) + "," + error);
                            intent.putExtra("" + i, extraData);
                        }
                    }
                    intent.putExtra("Size", arrayList.size());
                    arcoActivity.startActivity(intent);
                }
            }
        });

        seerror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ArcoActivity arcoActivity = (ArcoActivity) getActivity();
                Database database = new Database(getActivity());
                ArrayList<Six_X_Trilogy> arrayList = arcoActivity.getArcs();
                ArrayList<Boolean> full = arcoActivity.getFull();
                Boolean fullAll = true;
                seerror.setEnabled(true);
                for (int ij = 0; ij < full.size(); ij++) {
                    Log.i("Full", String.valueOf(full.get(ij)));
                    if (!full.get(ij)) {
                        fullAll = false;

                        int aux=ij+1;
                        Toast.makeText(getActivity(), "Empty fields in ARC: "+aux, Toast.LENGTH_LONG).show();
                        seerror.setEnabled(false);
                        break;
                    }
                }
                Log.i("Full All", String.valueOf(fullAll));
                if (fullAll==true){
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i)!=null && !arrayList.get(i).getCone().equals("") && !arrayList.get(i).getAver_depth_cm().equals("") && !arrayList.get(i).getWeight_factor().equals("") && !arrayList.get(i).getMu_tps().equals("")) {
                        database.write();
                            database.createArc("ARC" + (i + 1), "" + arrayList.get(i).getCone(), "" + arrayList.get(i).getOutputFactor(),""+ arrayList.get(i).getAver_depth_cm(), ""+arrayList.get(i).getTMR(),
                                    ""+ arrayList.get(i).getWeight_factor(), ""+arrayList.get(i).getMu_tps(),
                                    ""+arrayList.get(i).getMUQCSRS(), arcoActivity.getDATE(),arrayList.get(i).getEnergia(),arrayList.get(i).getD_ZERO(),
                                    ""+arrayList.get(i).getTotal_dose(),""+arrayList.get(i).getNumber_of_fraction(), ""+arrayList.get(i).getDose_fraction(),
                                    ""+arrayList.get(i).getTreatment_percentage(), ""+arrayList.get(i).getWeight_dose_maximum(),""+arrayList.get(i).getRepeatFactor());
                        database.close();
                        }
                    }

                    if (!plan_id.getText().toString().equals("") && !patient_id.getText().toString().equals("")) {
                        database.write();
                        database.updateGeneralData(patient_id.getText().toString(),plan_id.getText().toString(),arcoActivity.getDATE());
                        database.close();
                    }
                    GenerarPDF.GenerarPDF(arcoActivity,database,arcoActivity.getDATE());

                    Toast.makeText(getActivity(), R.string.succes_pdf, Toast.LENGTH_LONG).show();
                }
            }

        });
    }

}

