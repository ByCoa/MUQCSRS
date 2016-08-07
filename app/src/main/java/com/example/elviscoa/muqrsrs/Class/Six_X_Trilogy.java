package com.example.elviscoa.muqrsrs.Class;

import android.content.Context;

import com.example.elviscoa.muqrsrs.Library.DownloadFile;

/**
 * Created by soluciones on 7/3/2016.
 */
public class Six_X_Trilogy {
    //General Data
    private static final Integer ENERGIA=6;
    private static final Integer D0=1;
    private Double total_dose;
    private Integer number_of_fraction;
    private Double dose_fraction;
    private Double treatment_percentage;
    private Double weight_dose_maximum;
    //Arc Data
    private String arc_name;
    private Integer cone;
    private Double aver_depth_cm;
    private Double weight_factor;
    private Double mu_tps;

    public Six_X_Trilogy(Double total_dose,Integer number_of_fraction,Double dose_fraction,Double treatment_percentage, Double weight_dose_maximum,
                         Integer cone, Double aver_depth_cm, Double weight_factor, Double mu_tps){
        this.total_dose = total_dose;
        this.number_of_fraction = number_of_fraction;
        this.dose_fraction = dose_fraction;
        this.treatment_percentage = treatment_percentage;
        this.weight_dose_maximum = weight_dose_maximum;
        this.cone = cone;
        this.aver_depth_cm = aver_depth_cm/10;
        this.weight_factor = weight_factor;
        this.mu_tps = mu_tps;
    }

    public Six_X_Trilogy(Double total_dose,Integer number_of_fraction,Double dose_fraction,Double treatment_percentage, Double weight_dose_maximum){
        this.total_dose = total_dose;
        this.number_of_fraction = number_of_fraction;
        this.dose_fraction = dose_fraction;
        this.treatment_percentage = treatment_percentage;
        this.weight_dose_maximum = weight_dose_maximum;
    }

    public Six_X_Trilogy (){    }

    public String getEnergia (){
        return String.valueOf(ENERGIA);
    }

    public String getD_ZERO (){
        return String.valueOf(D0);
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

    public Double getWeight_dose_maximum() {
        return weight_dose_maximum;
    }

    public void setWeight_dose_maximum(Double weight_dose_maximum) {
        this.weight_dose_maximum = weight_dose_maximum;
    }

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

    public Double getMu_tps() {
        return mu_tps;
    }

    public void setMu_tps(Double mu_tps) {
        this.mu_tps = mu_tps;
    }

    public Double getRepeatFactor (){
        return Double.valueOf(Math.round((100*dose_fraction)/(treatment_percentage*weight_dose_maximum)*100))/100;
    }

    public Double getOutputFactor (){
        OutputFactor outputFactor = new OutputFactor();
        return outputFactor.getOutputFactor(cone);
    }

    public Double getTMR (){
        TMR tmr = new TMR();
        return tmr.getTMR(new OutputFactor().getIndexConoFromString(cone),aver_depth_cm);
    }

    public Double getMUQCSRS (){
        return (getRepeatFactor()*weight_factor)/(getTMR()*getOutputFactor());
    }

    public String getArc_name() {
        return arc_name;
    }

    public void setArc_name(String arc_name) {
        this.arc_name = arc_name;
    }

    public Double getError (){
        return (getMu_tps()-getMUQCSRS())/getMUQCSRS();
    }
}
