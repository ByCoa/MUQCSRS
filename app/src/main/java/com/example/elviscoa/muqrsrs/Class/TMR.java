package com.example.elviscoa.muqrsrs.Class;

import android.content.Context;

import com.example.elviscoa.muqrsrs.R;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by soluciones on 7/2/2016.
 */
public class TMR {

    private InputStream TMRSTREAM;
    private ArrayList<ArrayList<String>> tmrMatrix;

    public TMR (Context context){
        this.TMRSTREAM = context.getResources().openRawResource(R.raw.tmr);
        this.tmrMatrix=new CSV().readTMRCVS(context,TMRSTREAM);
    }

    public Double getTMR (int cono,Double profundidad){
        return Double.parseDouble(tmrMatrix.get(cono).get(getProfundidadIndex(profundidad)));
    }

    public int getProfundidadIndex (Double profundidad){
        int i=0;
        Double j=2.0;
        while (profundidad>j){
            j=+0.1;
            i++;
        }
        return i;
    }


}
