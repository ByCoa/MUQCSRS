package com.example.elviscoa.muqrsrs.Class;

import android.content.Context;
import android.util.Log;

import com.example.elviscoa.muqrsrs.R;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by soluciones on 7/2/2016.
 */
public class OutputFactor {
    private InputStream outputFactorSTREAM;
    private Integer cono_index;
    private ArrayList<String> tmrArray;

    public OutputFactor (Context context){
        this.outputFactorSTREAM = context.getResources().openRawResource(R.raw.outputfactor);
        this.tmrArray=new CSV().readOutputFactorCVS(context,outputFactorSTREAM);
    }

    public Double getOutputFactor (int cono){
        cono_index=getIndexConoFromString(cono);
        return Double.parseDouble(tmrArray.get(cono_index));
    }

    public Integer getCono_index (){
        return cono_index;
    }

    public Double roundThreeDecimals(Double d)
    {
        String x=String.valueOf(d);
        x=x.replace(",",".");
        Log.i("format", x);
        DecimalFormat threeDForm = new DecimalFormat("#.###");
        return Double.valueOf(threeDForm.format(Double.parseDouble(x)));
    }

    public int getIndexConoFromString (int cono){
        int index;
        switch (cono) {
            case 5:   index = 0;
                break;
            case 10:  index = 1;
                break;
            case 12:  index = 2;
                break;
            case 14:  index = 3;
                break;
            case 16:  index = 4;
                break;
            case 18:  index = 5;
                break;
            case 20:  index = 6;
                break;
            case 22:  index = 7;
                break;
            case 24:  index = 8;
                break;
            case 26:  index = 9;
                break;
            case 28:  index = 10;
                break;
            case 30:  index = 11;
                break;
            default: index = 0;
                break;
        }
        return index;
    }

}


