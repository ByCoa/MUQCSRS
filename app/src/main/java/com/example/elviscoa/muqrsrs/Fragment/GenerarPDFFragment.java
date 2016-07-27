package com.example.elviscoa.muqrsrs.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.elviscoa.muqrsrs.Activity.ArcoActivity;
import com.example.elviscoa.muqrsrs.Database.Database;
import com.example.elviscoa.muqrsrs.Library.GenerarPDF;
import com.example.elviscoa.muqrsrs.R;

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
                if (!plan_id.getText().toString().equals("") && !patient_id.getText().toString().equals("")){
                    database.write();
                    database.updateGeneralData(patient_id.getText().toString(),plan_id.getText().toString(),arcoActivity.getDATE());
                    database.close();
                }
                GenerarPDF.GenerarPDF(arcoActivity,database,arcoActivity.getDATE());

                Toast.makeText(getActivity(), R.string.succes_pdf, Toast.LENGTH_LONG).show();
            }
        });
    }

}

