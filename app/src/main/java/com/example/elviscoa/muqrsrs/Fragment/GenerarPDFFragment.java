package com.example.elviscoa.muqrsrs.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private Button generarpdf;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generarpdf, container, false);
        generarpdf = (Button) view.findViewById(R.id.generarpdf);

        return view;
    }

    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        generarpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ArcoActivity arcoActivity = (ArcoActivity) getActivity();
                Database database = new Database(getActivity());
                GenerarPDF.GenerarPDF(arcoActivity,database,arcoActivity.getDATE());

                Toast.makeText(getActivity(), "PDF Generado exitosamente", Toast.LENGTH_LONG).show();
            }
        });
    }

}

