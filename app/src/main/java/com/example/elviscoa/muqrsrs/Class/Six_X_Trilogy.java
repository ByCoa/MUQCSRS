package com.example.elviscoa.muqrsrs.Class;

import android.content.Context;

/**
 * Created by soluciones on 7/3/2016.
 */
public class Six_X_Trilogy {
    //General Data
    private static final Integer ENERGIA=6;
    private Double total_dose;
    private Integer number_of_fraction;
    private Double dose_fraction;
    private Double treatment_percentage;
    private Double weight_dose_maximum;
    private Integer D0;
    private Double repeat_factor;
    //Arc Data
    private Integer cone;
    private OutputFactor outputfactor;
    private Double aver_depth_cm;
    private TMR tmr;
    private Double weight_factor;
    private Double mu_tps;
    private Double mu_qc_srs;

    private Integer dosis_prescrita;
    private Integer dosis_fraction;
    private Double normalizacion;



    public Six_X_Trilogy(Integer cone, Double aver_depth_cm, Double weight_factor, Double mu_tps, Integer dosis_prescrita, Double normalizacion, Double weight_dose_maximum){
        this.cone = cone;
        this.aver_depth_cm = aver_depth_cm;
        this.weight_factor = weight_factor;
        this.mu_tps = mu_tps;
        this.dosis_prescrita = dosis_prescrita;
        this.normalizacion = normalizacion;
        this.weight_dose_maximum = weight_dose_maximum;
        this.outputfactor = new OutputFactor();
        this.tmr = new TMR();
    }

    public Six_X_Trilogy(Context context, Integer dosis_prescrita, Double normalizacion, Double weight_dose_maximum){
        this.dosis_prescrita = dosis_prescrita;
        this.normalizacion = normalizacion;
        this.weight_dose_maximum = weight_dose_maximum;
        this.outputfactor = new OutputFactor();
        this.tmr = new TMR();
    }
    public Six_X_Trilogy (){
        this.outputfactor = new OutputFactor();
        this.tmr = new TMR();
    }

    public String getEnergia (){
        return String.valueOf(ENERGIA);
    }

    public String getD_ZERO (){
        return String.valueOf(D0);
    }
    public Double getWeight_dose_maximum() { return weight_dose_maximum;}

    public Integer getCone() {
        return cone;
    }

    public void setCone(Integer cone) {
        this.cone = cone;
    }

    public Double getAver_depth_cm() {
        return aver_depth_cm;
    }

    public void setAver_depth_cm(Double aver_depth_cm) {
        this.aver_depth_cm = aver_depth_cm;
    }

    public Double getWeight_factor() {
        return weight_factor;
    }

    public void setWeight_factor(Double weight_factor) {
        this.weight_factor = weight_factor;
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
        return outputfactor.getOutputFactor(cone);
    }

    public Double getTMR (){
        return tmr.getTMR(outputfactor.getIndexConoFromString(cone), aver_depth_cm);
    }

    public Integer getDosisprescrita() {
        return dosis_prescrita;
    }

    public void setDosisprescrita(Integer dosisprescrita) {
        this.dosis_prescrita = dosisprescrita;
    }

    public Double getDosisXFraccion (){
        return (dosis_prescrita* weight_factor)/ weight_dose_maximum;
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

    public Integer getDosis_fraction() {
        return dosis_fraction;
    }

    public void setDosis_fraction(Integer dosis_fraction) {
        this.dosis_fraction = dosis_fraction;
    }

    public void setD0(Integer d0) {
        D0 = d0;
    }

    public Double getTotal_dose() {
        return total_dose;
    }

    public void setTotal_dose(Double total_dose) {
        this.total_dose = total_dose;
    }

    public Integer getNumber_of_fraction() {
        return number_of_fraction;
    }

    public void setNumber_of_fraction(Integer number_of_fraction) {
        this.number_of_fraction = number_of_fraction;
    }

    public Double getDose_fraction() {
        return dose_fraction;
    }

    public void setDose_fraction(Double dose_fraction) {
        this.dose_fraction = dose_fraction;
    }

    public Double getTreatment_percentage() {
        return treatment_percentage;
    }

    public void setTreatment_percentage(Double treatment_percentage) {
        this.treatment_percentage = treatment_percentage;
    }

    public Double getRepeat_factor() {
        return repeat_factor;
    }

    public void setRepeat_factor(Double repeat_factor) {
        this.repeat_factor = repeat_factor;
    }
}
