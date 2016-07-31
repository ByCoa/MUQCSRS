package com.example.elviscoa.muqrsrs.Class;

import android.content.Context;
import android.util.Log;

import com.example.elviscoa.muqrsrs.R;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by soluciones on 7/2/2016.
 */
public class OutputFactor {
    private Integer cono_index;
    private static final ArrayList<String> tmrArray= new ArrayList<String>(Arrays.asList(new String[]{"0.71507140932363", "0.84621880894638","0.87129830234438", "0.8878752357855", "0.89968094853139", "0.90846681922197",
            "0.91838291380625", "0.92219679633867", "0.92677345537757", "0.9279176201373", "0.93135011441648", "0.93516399694889"}));
    public OutputFactor (){

    }

    public Double getOutputFactor (int cono){
        cono_index=getIndexConoFromString(cono);
        return Double.parseDouble(tmrArray.get(cono_index));
    }

    public Integer getCono_index (){
        return cono_index;
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


