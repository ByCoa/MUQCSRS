package com.example.elviscoa.muqrsrs.Class;

import com.example.elviscoa.muqrsrs.Activity.Result;

/**
 * Created by soluciones on 8/6/2016.
 */
public class Results {
    private String mutps;
    private String muqcsrs;
    private String error;

    public Results (){

    }
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMuqcsrs() {
        return muqcsrs;
    }

    public void setMuqcsrs(String muqcsrs) {
        this.muqcsrs = muqcsrs;
    }

    public String getMutps() {
        return mutps;
    }

    public void setMutps(String mutps) {
        this.mutps = mutps;
    }
}
