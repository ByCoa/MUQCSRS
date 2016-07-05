package com.example.elviscoa.muqrsrs.Class;

import android.content.Context;

import com.example.elviscoa.muqrsrs.R;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by soluciones on 7/2/2016.
 */
public class OutputFactor {
    private InputStream outputFactorSTREAM;
    private ArrayList<String> tmrArray;

    public OutputFactor (Context context){
        this.outputFactorSTREAM = context.getResources().openRawResource(R.raw.outputfactor);
        this.tmrArray=new CSV().readOutputFactorCVS(context,outputFactorSTREAM);
    }

    public Double getOutputFactor (int cono){
        return Double.parseDouble(tmrArray.get(cono));
    }

}


