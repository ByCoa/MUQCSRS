package com.example.elviscoa.muqrsrs.Class;

import java.text.DecimalFormat;

/**
 * Created by elvis on 05/07/16.
 */
public class Util {
    private static final String CONO_SEPARATOR=" ";

    public String splitCono (String cono){
        String[] result=cono.split(CONO_SEPARATOR);
        return result[1];
    }
    public Double roundThreeDecimals(Double d)
    {
        String x=String.valueOf(d);
        x.replace(",",".");
        DecimalFormat threeDForm = new DecimalFormat("#.###");
        return Double.valueOf(threeDForm.format(Double.parseDouble(x)));
    }
}
