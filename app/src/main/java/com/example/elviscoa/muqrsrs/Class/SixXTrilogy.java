package com.example.elviscoa.muqrsrs.Class;

/**
 * Created by soluciones on 7/16/2016.
 */
public class SixXTrilogy {
    private String cono;
    private String outputfactor;
    private String profundidad;
    private String tmr;
    private String peso_del_arco;
    private String mu_tps;
    private String mu_qc_srs;

    public SixXTrilogy(String cono,String outputfactor, String profundidad, String tmr, String peso_del_arco, String mu_qc_srs, String mu_tps){
        this.cono = cono;
        this.outputfactor = outputfactor;
        this.profundidad = profundidad;
        this.tmr = tmr;
        this.peso_del_arco = peso_del_arco;
        this.mu_tps = mu_tps;
        this.mu_qc_srs = mu_qc_srs;
    }
    public String getCono() {
        return cono;
    }

    public void setCono(String cono) {
        this.cono = cono;
    }

    public String getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(String profundidad) {
        this.profundidad = profundidad;
    }

    public String getOutputfactor() {
        return outputfactor;
    }

    public void setOutputfactor(String outputfactor) {
        this.outputfactor = outputfactor;
    }

    public String getPeso_del_arco() {
        return peso_del_arco;
    }

    public void setPeso_del_arco(String peso_del_arco) {
        this.peso_del_arco = peso_del_arco;
    }

    public String getMu_tps() {
        return mu_tps;
    }

    public void setMu_tps(String mu_tps) {
        this.mu_tps = mu_tps;
    }

    public String getTmr() {
        return tmr;
    }

    public void setTmr(String tmr) {
        this.tmr = tmr;
    }

    public String getMu_qc_srs() {
        return mu_qc_srs;
    }

    public void setMu_qc_srs(String mu_qc_srs) {
        this.mu_qc_srs = mu_qc_srs;
    }
}
