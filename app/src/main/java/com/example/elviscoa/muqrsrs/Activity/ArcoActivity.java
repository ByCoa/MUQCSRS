package com.example.elviscoa.muqrsrs.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.elviscoa.muqrsrs.Adapter.Pager;
import com.example.elviscoa.muqrsrs.Class.Six_X_Trilogy;
import com.example.elviscoa.muqrsrs.Class.Util;
import com.example.elviscoa.muqrsrs.Database.Database;
import com.example.elviscoa.muqrsrs.Fragment.Arco1;
import com.example.elviscoa.muqrsrs.Fragment.Arco10;
import com.example.elviscoa.muqrsrs.Fragment.Arco11;
import com.example.elviscoa.muqrsrs.Fragment.Arco12;
import com.example.elviscoa.muqrsrs.Fragment.Arco13;
import com.example.elviscoa.muqrsrs.Fragment.Arco14;
import com.example.elviscoa.muqrsrs.Fragment.Arco15;
import com.example.elviscoa.muqrsrs.Fragment.Arco16;
import com.example.elviscoa.muqrsrs.Fragment.Arco17;
import com.example.elviscoa.muqrsrs.Fragment.Arco18;
import com.example.elviscoa.muqrsrs.Fragment.Arco19;
import com.example.elviscoa.muqrsrs.Fragment.Arco2;
import com.example.elviscoa.muqrsrs.Fragment.Arco3;
import com.example.elviscoa.muqrsrs.Fragment.Arco4;
import com.example.elviscoa.muqrsrs.Fragment.Arco5;
import com.example.elviscoa.muqrsrs.Fragment.Arco6;
import com.example.elviscoa.muqrsrs.Fragment.Arco7;
import com.example.elviscoa.muqrsrs.Fragment.Arco8;
import com.example.elviscoa.muqrsrs.Fragment.Arco9;
import com.example.elviscoa.muqrsrs.Fragment.GenerarPDFFragment;
import com.example.elviscoa.muqrsrs.R;

import java.util.ArrayList;

/**
 * Created by elvis on 09/07/16.
 */
public class ArcoActivity extends AppCompatActivity {
    //Constants
    private static final String STRINGDOSIS_PRESCRITA="DOSIS_PRESCRITA";
    private static final String STRINGNORMALIZACION="NORMALIZACION";
    private static final String STRINGPESO_MAXIMO_DOSIS="PESO_MAXIMO_DOSIS";
    private static final String STRINGDATE="DATE";
    private static final String STRINGD_ZERO="D_ZERO";
    private static final String STRINGARCOS="ARCOS";

    //Extras
    private Integer DOSIS_PRESCRITA=1500;
    private Double NORMALIZACION=1.0;
    private Double PESO_MAXIMO_DOSIS=5.052;
    private String DATE="123";
    private String D_ZERO="";
    private String ARCOS="2 ARCS";
    //UI
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private Pager adapter;
    private FloatingActionButton fab;
    //Database
    private Database dbHandler = new Database(this);
    //ArrayList
    Six_X_Trilogy six_x_trilogy_class;
    private ArrayList<Six_X_Trilogy> ArcosArray;
    private ArrayList<Boolean> full;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arc_data_layout);

        Bundle extras = getIntent().getExtras();
        if (extras==null) {

        } else {
            DOSIS_PRESCRITA = Integer.valueOf(extras.getString(STRINGDOSIS_PRESCRITA));
            NORMALIZACION = Double.valueOf(extras.getString(STRINGNORMALIZACION));
            PESO_MAXIMO_DOSIS = Double.valueOf(extras.getString(STRINGPESO_MAXIMO_DOSIS));
            DATE = extras.getString(STRINGDATE);
            D_ZERO = extras.getString(STRINGD_ZERO);
            ARCOS = extras.getString(STRINGARCOS);
        }

        six_x_trilogy_class= new Six_X_Trilogy(this,DOSIS_PRESCRITA,NORMALIZACION,PESO_MAXIMO_DOSIS);
        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Tablayout
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        String Arc[] = ARCOS.split(" ");
        int idx= Integer.parseInt(Arc[0]);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager (viewPager,idx);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Log.d("Page", String.valueOf(adapter.getPageTitle(tab.getPosition())));
                getFragmentPager(tab.getPosition(), String.valueOf(adapter.getPageTitle(tab.getPosition())));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public Integer getDOSIS_PRESCRITA() {
        return DOSIS_PRESCRITA;
    }

    public Double getNORMALIZACION() {
        return NORMALIZACION;
    }

    public Double getPESO_MAXIMO_DOSIS() {
        return PESO_MAXIMO_DOSIS;
    }

    public String getDATE() {
        return DATE;
    }

    public Database getDbHandler() {
        return dbHandler;
    }

    public ArrayList<Six_X_Trilogy> getArcs (){
        return this.ArcosArray;
    }

    public ArrayList<Boolean> getFull (){
        return this.full;
    }

    public void getFragmentPager (int Arco,String title){
        int i=0;
        if (title.equals(getString(R.string.report))){

       // }else {
            for (int j = 0; j < full.size(); j++) {
                Six_X_Trilogy six_arc;
                switch (j) {
                    case 0:
                        Arco1 arco1 = (Arco1) adapter.getItem(j);
                        if (!arco1.getCono().equals("") && !arco1.getMonitorUnits().equals("") && !arco1.getPesoArco().equals("") && !arco1.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco1.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco1.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco1.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco1.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco1", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(0, six_arc);
                            full.set(0,true);
                        }else{
                            full.set(0,false);
                        }
                        break;
                    case 1:
                        Arco2 arco2 = (Arco2) adapter.getItem(j);
                        if (!arco2.getCono().equals("") && !arco2.getMonitorUnits().equals("") && !arco2.getPesoArco().equals("") && !arco2.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco2.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco2.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco2.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco2.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco2", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(1, six_arc);
                            full.set(1,true);
                        }else{
                            full.set(1,false);
                        }
                        break;
                    case 2:
                        Arco3 arco3 = (Arco3) adapter.getItem(Arco);
                        if (!arco3.getCono().equals("") && !arco3.getMonitorUnits().equals("") && !arco3.getPesoArco().equals("") && !arco3.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco3.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco3.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco3.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco3.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco3", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(2, six_arc);
                            full.set(2,true);
                        }else{
                            full.set(2,false);
                        }
                        break;
                    case 3:
                        Arco4 arco4 = (Arco4) adapter.getItem(Arco);
                        if (!arco4.getCono().equals("") && !arco4.getMonitorUnits().equals("") && !arco4.getPesoArco().equals("") && !arco4.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco4.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco4.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco4.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco4.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco4", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(3, six_arc);
                            full.set(3,true);
                        }else{
                            full.set(3,false);
                        }
                        break;
                    case 4:
                        Arco5 arco5 = (Arco5) adapter.getItem(Arco);
                        if (!arco5.getCono().equals("") && !arco5.getMonitorUnits().equals("") && !arco5.getPesoArco().equals("") && !arco5.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco5.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco5.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco5.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco5.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco5", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(4, six_arc);
                            full.set(4,true);
                        }else{
                            full.set(4,false);
                        }
                        break;
                    case 5:
                        Arco6 arco6 = (Arco6) adapter.getItem(Arco);
                        if (!arco6.getCono().equals("") && !arco6.getMonitorUnits().equals("") && !arco6.getPesoArco().equals("") && !arco6.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco6.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco6.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco6.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco6.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco6", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(5, six_arc);
                            full.set(5,true);
                        }else{
                            full.set(5,false);
                        }
                        break;
                    case 6:
                        Arco7 arco7 = (Arco7) adapter.getItem(Arco);
                        if (!arco7.getCono().equals("") && !arco7.getMonitorUnits().equals("") && !arco7.getPesoArco().equals("") && !arco7.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco7.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco7.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco7.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco7.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco7", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(6, six_arc);
                            full.set(6,true);
                        }else{
                            full.set(6,false);
                        }
                        break;
                    case 7:
                        Arco8 arco8 = (Arco8) adapter.getItem(Arco);
                        if (!arco8.getCono().equals("") && !arco8.getMonitorUnits().equals("") && !arco8.getPesoArco().equals("") && !arco8.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco8.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco8.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco8.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco8.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco8", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(7, six_arc);
                            full.set(7,true);
                        }else{
                            full.set(7,false);
                        }
                        break;
                    case 8:
                        Arco9 arco9 = (Arco9) adapter.getItem(Arco);
                        if (!arco9.getCono().equals("") && !arco9.getMonitorUnits().equals("") && !arco9.getPesoArco().equals("") && !arco9.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco9.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco9.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco9.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco9.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco9", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(8, six_arc);
                            full.set(8,true);
                        }else{
                            full.set(8,false);
                        }
                        break;
                    case 9:
                        Arco10 arco10 = (Arco10) adapter.getItem(Arco);
                        if (!arco10.getCono().equals("") && !arco10.getMonitorUnits().equals("") && !arco10.getPesoArco().equals("") && !arco10.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco10.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco10.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco10.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco10.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco10", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(9, six_arc);
                            full.set(9,true);
                        }else{
                            full.set(9,false);
                        }
                        break;
                    case 10:
                        Arco11 arco11 = (Arco11) adapter.getItem(Arco);
                        if (!arco11.getCono().equals("") && !arco11.getMonitorUnits().equals("") && !arco11.getPesoArco().equals("") && !arco11.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco11.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco11.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco11.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco11.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco11", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(10, six_arc);
                            full.set(10,true);
                        }else{
                            full.set(10,false);
                        }
                        break;
                    case 11:
                        Arco12 arco12 = (Arco12) adapter.getItem(Arco);
                        if (!arco12.getCono().equals("") && !arco12.getMonitorUnits().equals("") && !arco12.getPesoArco().equals("") && !arco12.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco12.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco12.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco12.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco12.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco12", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(11, six_arc);
                            full.set(11,true);
                        }else{
                            full.set(11,false);
                        }
                        break;
                    case 12:
                        Arco13 arco13 = (Arco13) adapter.getItem(Arco);
                        if (!arco13.getCono().equals("") && !arco13.getMonitorUnits().equals("") && !arco13.getPesoArco().equals("") && !arco13.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco13.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco13.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco13.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco13.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco13", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(12, six_arc);
                            full.set(12,true);
                        }else{
                            full.set(12,false);
                        }
                        break;
                    case 13:
                        Arco14 arco14 = (Arco14) adapter.getItem(Arco);
                        if (!arco14.getCono().equals("") && !arco14.getMonitorUnits().equals("") && !arco14.getPesoArco().equals("") && !arco14.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco14.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco14.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco14.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco14.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco14", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(13, six_arc);
                            full.set(13,true);
                        }else{
                            full.set(13,false);
                        }
                        break;
                    case 14:
                        Arco15 arco15 = (Arco15) adapter.getItem(Arco);
                        if (!arco15.getCono().equals("") && !arco15.getMonitorUnits().equals("") && !arco15.getPesoArco().equals("") && !arco15.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco15.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco15.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco15.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco15.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco15", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(14, six_arc);
                            full.set(14,true);
                        }else{
                            full.set(14,false);
                        }
                        break;
                    case 15:
                        Arco16 arco16 = (Arco16) adapter.getItem(Arco);
                        if (!arco16.getCono().equals("") && !arco16.getMonitorUnits().equals("") && !arco16.getPesoArco().equals("") && !arco16.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco16.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco16.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco16.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco16.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco16", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(15, six_arc);
                            full.set(15,true);
                        }else{
                            full.set(15,false);
                        }
                        break;
                    case 16:
                        Arco17 arco17 = (Arco17) adapter.getItem(Arco);
                        if (!arco17.getCono().equals("") && !arco17.getMonitorUnits().equals("") && !arco17.getPesoArco().equals("") && !arco17.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco17.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco17.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco17.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco17.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco17", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());

                            ArcosArray.set(16, six_arc);
                            full.set(16,true);
                        }else{
                            full.set(16,false);
                        }
                        break;
                    case 17:
                        Arco18 arco18 = (Arco18) adapter.getItem(Arco);
                        if (!arco18.getCono().equals("") && !arco18.getMonitorUnits().equals("") && !arco18.getPesoArco().equals("") && !arco18.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco18.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco18.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco18.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco18.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco18", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(17, six_arc);
                            full.set(17,true);
                        }else{
                            full.set(17,false);
                        }
                        break;
                    case 18:
                        Arco19 arco19 = (Arco19) adapter.getItem(Arco);
                        if (!arco19.getCono().equals("") && !arco19.getMonitorUnits().equals("") && !arco19.getPesoArco().equals("") && !arco19.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(Integer.parseInt(new Util().splitCono(arco19.getCono())), new Util().roundThreeDecimals(Double.parseDouble(arco19.getPrdofundidad())), new Util().roundThreeDecimals(Double.parseDouble(arco19.getPesoArco()))
                                    , new Util().roundThreeDecimals(Double.parseDouble(arco19.getMonitorUnits())), getDOSIS_PRESCRITA(), getNORMALIZACION(), getPESO_MAXIMO_DOSIS());
                            Log.i("Arco19", "Dosis prescrita: " + six_arc.getDosisprescrita() + " Normalizacion: " + six_arc.getNormalizacion() + " Peso Maximo Dosis: " + six_arc.getWeight_dose_maximum()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputfactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(18, six_arc);
                            full.set(18,true);
                        }else{
                            full.set(18,false);
                        }
                    case 19:
                        GenerarPDFFragment generarPDFFragment = new GenerarPDFFragment();
                        adapter.addFragment(generarPDFFragment, "REPORTE");
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void setupViewPager(ViewPager viewPager,int Arcos) {
        adapter = new Pager(getSupportFragmentManager(),Arcos);
        int i=0;
        for (String tabs: this.getResources().getStringArray(R.array.tabs)){
            if (i==Arcos)
                i=19;
            switch (i) {
                case 0:
                    Arco1 arco1= new Arco1();
                    adapter.addFragment(arco1, tabs);
                    break;
                case 1:
                    Arco2 arco2= new Arco2();
                    adapter.addFragment(arco2, tabs);
                    break;
                case 2:
                    Arco3 arco3= new Arco3();
                    adapter.addFragment(arco3, tabs);
                    break;
                case 3:
                    Arco4 arco4= new Arco4();
                    adapter.addFragment(arco4, tabs);
                    break;
                case 4:
                    Arco5 arco5= new Arco5();
                    adapter.addFragment(arco5, tabs);
                    break;
                case 5:
                    Arco6 arco6= new Arco6();
                    adapter.addFragment(arco6, tabs);
                    break;
                case 6:
                    Arco7 arco7= new Arco7();
                    adapter.addFragment(arco7, tabs);
                    break;
                case 7:
                    Arco8 arco8= new Arco8();
                    adapter.addFragment(arco8, tabs);
                    break;
                case 8:
                    Arco9 arco9= new Arco9();
                    adapter.addFragment(arco9, tabs);
                    break;
                case 9:
                    Arco10 arco10= new Arco10();
                    adapter.addFragment(arco10, tabs);
                    break;
                case 10:
                    Arco11 arco11= new Arco11();
                    adapter.addFragment(arco11, tabs);
                    break;
                case 11:
                    Arco12 arco12= new Arco12();
                    adapter.addFragment(arco12, tabs);
                    break;
                case 12:
                    Arco13 arco13= new Arco13();
                    adapter.addFragment(arco13, tabs);
                    break;
                case 13:
                    Arco14 arco14= new Arco14();
                    adapter.addFragment(arco14, tabs);
                    break;
                case 14:
                    Arco15 arco15= new Arco15();
                    adapter.addFragment(arco15, tabs);
                    break;
                case 15:
                    Arco16 arco16= new Arco16();
                    adapter.addFragment(arco16, tabs);
                    break;
                case 16:
                    Arco17 arco17= new Arco17();
                    adapter.addFragment(arco17, tabs);
                    break;
                case 17:
                    Arco18 arco18= new Arco18();
                    adapter.addFragment(arco18, tabs);
                    break;
                case 18:
                    Arco19 arco19= new Arco19();
                    adapter.addFragment(arco19, tabs);
                    break;
                case 19:
                    GenerarPDFFragment generarPDFFragment= new GenerarPDFFragment();
                    adapter.addFragment(generarPDFFragment, getString(R.string.report));
                    break;
                default:
                    break;
            }
            if (i==19)
                break;
            i++;
        }
        viewPager.setAdapter(adapter);
        Log.i("aaa", "" + adapter.getCount());
        ArcosArray = new ArrayList<Six_X_Trilogy>();
        full = new ArrayList<Boolean>();
        for (int j=0; j<adapter.getCount()-1;j++) {
            ArcosArray.add(j, six_x_trilogy_class);
            full.add(j, false);
        }
    }
}