package com.example.elviscoa.muqrsrs.Fragment;

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
    private Button generarpdf;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generarpdf, container, false);
        generarpdf = (Button) view.findViewById(R.id.generarpdf);
        patient_id = (EditText) view.findViewById(R.id.input_patient_id);
        plan_id = (EditText) view.findViewById(R.id.input_plan_id);
        return view;
    }

    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        generarpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ArcoActivity arcoActivity = (ArcoActivity) getActivity();
                Database database = new Database(getActivity());
                ArrayList<Six_X_Trilogy> arrayList = arcoActivity.getArcs();
                ArrayList<Boolean> full = arcoActivity.getFull();
                Boolean fullAll = true;
                generarpdf.setEnabled(true);
                for (int ij = 0; ij < full.size(); ij++) {
                    Log.i("Full", String.valueOf(full.get(ij)));
                    if (!full.get(ij)) {
                        fullAll = false;

                        int aux=ij+1;
                        Toast.makeText(getActivity(), "Empty fields in ARC: "+aux, Toast.LENGTH_LONG).show();
                        generarpdf.setEnabled(false);
                        break;
                    }
                }
                Log.i("Full All", String.valueOf(fullAll));
                if (fullAll==true){
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (!arrayList.get(i).getCono().equals("") && !arrayList.get(i).getProfundidad().equals("") && !arrayList.get(i).getPeso_del_arco().equals("") && !arrayList.get(i).getMU_TPS().equals("")) {
                            Log.i("Arco" + i, "Dosis prescrita: " + arrayList.get(i).getDosisprescrita() + " Normalizacion: " + arrayList.get(i).getNormalizacion() + " Peso Maximo Dosis: " + arrayList.get(i).getPeso_maximo_dosis()
                                    + " Cono: " + arrayList.get(i).getCono() + " Output Factor: " + arrayList.get(i).getOutputfactor() + " Profundidad: " + arrayList.get(i).getProfundidad() + " TMR: " + arrayList.get(i).getTMR());
                        database.write();
                            database.createArc("ARCO" + (i + 1), "" + arrayList.get(i).getCono(), "" + arrayList.get(i).getOutputfactor(),""+ arrayList.get(i).getProfundidad(), ""+arrayList.get(i).getTMR(),""+ arrayList.get(i).getPeso_del_arco(), ""+arrayList.get(i).getDosisXFraccion(), ""+arrayList.get(i).getMU_TPS(),
                                    ""+arrayList.get(i).getMU(arrayList.get(i).getOutputfactor(),arrayList.get(i).getTMR()), arcoActivity.getDATE(),arrayList.get(i).getEnergia(),arrayList.get(i).getD_ZERO(),String.valueOf(arrayList.get(i).getDosisprescrita()),
                                    String.valueOf(arrayList.get(i).getNormalizacion()), String.valueOf(arrayList.get(i).getPeso_maximo_dosis()));
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

