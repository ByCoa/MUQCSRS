package com.example.elviscoa.muqrsrs.Class;

import android.content.Context;

/**
 * Created by soluciones on 7/3/2016.
 */
public class Six_X_Trilogy {
    private static final Integer ENERGIA=6;
    private static final Integer D0=1;
    private Integer cono;
    private Double profundidad;
    private Double peso_del_arco;
    private Double mu_tps;
    private OutputFactor outputfactor;
    private TMR tmr;
    private Integer dosis_prescrita;
    private Double normalizacion;
    private Double peso_maximo_dosis;



    public Six_X_Trilogy(Integer cono, Double profundidad, Double peso_del_arco, Double mu_tps, Integer dosis_prescrita, Double normalizacion, Double peso_maximo_dosis){
        this.cono = cono;
        this.profundidad = profundidad;
        this.peso_del_arco = peso_del_arco;
        this.mu_tps = mu_tps;
        this.dosis_prescrita = dosis_prescrita;
        this.normalizacion = normalizacion;
        this.peso_maximo_dosis = peso_maximo_dosis;
        this.outputfactor = new OutputFactor();
        this.tmr = new TMR();
    }

    public Six_X_Trilogy(Context context, Integer dosis_prescrita, Double normalizacion, Double peso_maximo_dosis){
        this.dosis_prescrita = dosis_prescrita;
        this.normalizacion = normalizacion;
        this.peso_maximo_dosis = peso_maximo_dosis;
        this.outputfactor = new OutputFactor();
        this.tmr = new TMR();
    }

    public String getEnergia (){
        return String.valueOf(ENERGIA);
    }

    public String getD_ZERO (){
        return String.valueOf(D0);
    }
    public Double getPeso_maximo_dosis () { return peso_maximo_dosis;}

    public Integer getCono() {
        return cono;
    }

    public void setCono(Integer cono) {
        this.cono = cono;
    }

    public Double getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(Double profundidad) {
        this.profundidad = profundidad;
    }

    public Double getPeso_del_arco() {
        return peso_del_arco;
    }

    public void setPeso_del_arco(Double peso_del_arco) {
        this.peso_del_arco = peso_del_arco;
    }

    public Double getMU_TPS() {
        return mu_tps;
    }

    public void setMU_TPS(Double MU_TPS) {
        this.mu_tps = MU_TPS;
    }

    public void setOutputfactor (OutputFactor outputfactor){
        this.outputfactor=outputfactor;
    }

    public void setTmr (TMR tmr){
        this.tmr=tmr;
    }

    public Double getOutputfactor (){
        return outputfactor.getOutputFactor(cono);
    }

    public Double getTMR (){
        return tmr.getTMR(outputfactor.getIndexConoFromString(cono), profundidad);
    }

    public Integer getDosisprescrita() {
        return dosis_prescrita;
    }

    public void setDosisprescrita(Integer dosisprescrita) {
        this.dosis_prescrita = dosisprescrita;
    }

    public Double getDosisXFraccion (){
        return (dosis_prescrita*peso_del_arco)/peso_maximo_dosis;
    }

    public Double getMU (Double outputfactor,Double tmr)
    {
        return getDosisXFraccion()/(outputfactor*tmr*D0*normalizacion);
    }


    public Double getNormalizacion() {
        return normalizacion;
    }

    public void setNormalizacion(Double normalizacion) {
        this.normalizacion = normalizacion;
    }
}
