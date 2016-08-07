package com.example.elviscoa.muqrsrs.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.elviscoa.muqrsrs.Adapter.Pager;
import com.example.elviscoa.muqrsrs.Class.OutputFactor;
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
    private static final String STRINGD_ZERO ="D_ZERO";
    private static final String STRINGTOTAL_DOSE ="TOTAL_DOSE";
    private static final String STRINGNUMBER_FRACTION ="NUMBER_FRACTION";
    private static final String STRINGDOSE_FRACTION ="DOSE_FRACTION";
    private static final String STRINGTREATMENT_PER ="TREATMENT_PER";
    private static final String STRINGWEIGHT_DOSE_MAXIMUM ="WEIGHT_DOSE_MAXIMUM";
    private static final String STRINGARCS ="ARCS";
    private static final String STRINGPDFARCOS="PDFARCOS";
    private static final String STRINGDATE="DATE";
    //Extras
    private Double total_dose;
    private Integer number_of_fraction;
    private Double dose_fraction;
    private Double treatment_percentage;
    private Double weight_dose_maximum;
    private String ARCS;
    private String DATE="";
    private Integer PDF;
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
    private ArrayList<Six_X_Trilogy> ArcosArrayExtras;
    private ArrayList<String> extrasString= new ArrayList<String>();
    private ArrayList<Boolean> fullExtras;
    private ArrayList<Boolean> full;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arc_data_layout);

            final Bundle extras = getIntent().getExtras();
        if (extras==null) {

        } else {
            total_dose = extras.getDouble(STRINGTOTAL_DOSE);
            number_of_fraction = extras.getInt(STRINGNUMBER_FRACTION);
            dose_fraction = extras.getDouble(STRINGDOSE_FRACTION);
            treatment_percentage = extras.getDouble(STRINGTREATMENT_PER);
            weight_dose_maximum = extras.getDouble(STRINGWEIGHT_DOSE_MAXIMUM);
            ARCS = extras.getString(STRINGARCS);
            DATE = extras.getString(STRINGDATE);
            PDF= extras.getInt(STRINGPDFARCOS);
        }
        //Toolbar
        setToolbar();
        //Tablayout
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        String Arc[] = ARCS.split(" ");
        int idx= Integer.parseInt(Arc[0]);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        setupViewPager(viewPager, idx);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        setUPExtras(extras);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition()==0)

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

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    public void setUPExtras (Bundle extras){
        if (PDF!=null && PDF>0){
            ArcosArrayExtras = new ArrayList<Six_X_Trilogy>();
            fullExtras = new ArrayList<Boolean>();
            for (int i=0;i<PDF;i++) {
                if(extras.getString(String.valueOf(i))!=null){
                    String extraString [] = extras.getString(String.valueOf(i)).split(",");
                    String splice [] = extraString[0].split(" ");
                    fullExtras.add(false);
                    Six_X_Trilogy six_x_trilogy = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction,
                            treatment_percentage, weight_dose_maximum,Integer.parseInt(extraString[1]),Double.parseDouble(extraString[4]),Double.parseDouble(extraString[2]),Double.parseDouble(extraString[3]));
                    six_x_trilogy.setArc_name(splice[1]);
                    Log.i(String.valueOf(i), six_x_trilogy.getArc_name() + " " + total_dose + " " + number_of_fraction + " " + dose_fraction + " " + treatment_percentage + " " + weight_dose_maximum + " " + Integer.parseInt(extraString[1]) + " " + Double.parseDouble(extraString[2]) + " " + Double.parseDouble(extraString[3]) + " " + Double.parseDouble(extraString[4]));
                    ArcosArrayExtras.add(six_x_trilogy);
                }

            }
        }
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
                        if (!arco1.getCono().equals("") && !arco1.getMonitorUnits().equals("") && !arco1.getPesoArco().equals("") && !arco1.getProfundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco1.getCono().substring(5)), Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco1.getProfundidad())*10)/10)),
                                    Double.parseDouble(arco1.getPesoArco()),Double.parseDouble(arco1.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(0, six_arc);
                            full.set(0,true);
                        }else{
                            full.set(0,false);
                        }
                        break;
                    case 1:
                        Arco2 arco2 = (Arco2) adapter.getItem(j);
                        if (!arco2.getCono().equals("") && !arco2.getMonitorUnits().equals("") && !arco2.getPesoArco().equals("") && !arco2.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco2.getCono().substring(5)),Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco2.getPrdofundidad())*10)/10)),
                                    Double.parseDouble(arco2.getPesoArco()),Double.parseDouble(arco2.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(1, six_arc);
                            full.set(1,true);
                        }else{
                            full.set(1,false);
                        }
                        break;
                    case 2:
                        Arco3 arco3 = (Arco3) adapter.getItem(j);
                        if (!arco3.getCono().equals("") && !arco3.getMonitorUnits().equals("") && !arco3.getPesoArco().equals("") && !arco3.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco3.getCono().substring(5)),Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco3.getPrdofundidad()) * 10) / 10)),
                                    Double.parseDouble(arco3.getPesoArco()),Double.parseDouble(arco3.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(2, six_arc);
                            full.set(2,true);
                        }else{
                            full.set(2,false);
                        }
                        break;
                    case 3:
                        Arco4 arco4 = (Arco4) adapter.getItem(j);
                        if (!arco4.getCono().equals("") && !arco4.getMonitorUnits().equals("") && !arco4.getPesoArco().equals("") && !arco4.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco4.getCono().substring(5)),Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco4.getPrdofundidad()) * 10) / 10)),
                                    Double.parseDouble(arco4.getPesoArco()),Double.parseDouble(arco4.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(3, six_arc);
                            full.set(3,true);
                        }else{
                            full.set(3,false);
                        }
                        break;
                    case 4:
                        Arco5 arco5 = (Arco5) adapter.getItem(j);
                        if (!arco5.getCono().equals("") && !arco5.getMonitorUnits().equals("") && !arco5.getPesoArco().equals("") && !arco5.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco5.getCono().substring(5)),Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco5.getPrdofundidad()) * 10) / 10)),
                                    Double.parseDouble(arco5.getPesoArco()),Double.parseDouble(arco5.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(4, six_arc);
                            full.set(4,true);
                        }else{
                            full.set(4,false);
                        }
                        break;
                    case 5:
                        Arco6 arco6 = (Arco6) adapter.getItem(j);
                        if (!arco6.getCono().equals("") && !arco6.getMonitorUnits().equals("") && !arco6.getPesoArco().equals("") && !arco6.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco6.getCono().substring(5)),Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco6.getPrdofundidad()) * 10) / 10)),
                                    Double.parseDouble(arco6.getPesoArco()),Double.parseDouble(arco6.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(5, six_arc);
                            full.set(5,true);
                        }else{
                            full.set(5,false);
                        }
                        break;
                    case 6:
                        Arco7 arco7 = (Arco7) adapter.getItem(j);
                        if (!arco7.getCono().equals("") && !arco7.getMonitorUnits().equals("") && !arco7.getPesoArco().equals("") && !arco7.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco7.getCono().substring(5)),Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco7.getPrdofundidad()) * 10) / 10)),
                                    Double.parseDouble(arco7.getPesoArco()),Double.parseDouble(arco7.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(6, six_arc);
                            full.set(6,true);
                        }else{
                            full.set(6,false);
                        }
                        break;
                    case 7:
                        Arco8 arco8 = (Arco8) adapter.getItem(j);
                        if (!arco8.getCono().equals("") && !arco8.getMonitorUnits().equals("") && !arco8.getPesoArco().equals("") && !arco8.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco8.getCono().substring(5)),Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco8.getPrdofundidad()) * 10) / 10)),
                                    Double.parseDouble(arco8.getPesoArco()),Double.parseDouble(arco8.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(7, six_arc);
                            full.set(7,true);
                        }else{
                            full.set(7,false);
                        }
                        break;
                    case 8:
                        Arco9 arco9 = (Arco9) adapter.getItem(j);
                        if (!arco9.getCono().equals("") && !arco9.getMonitorUnits().equals("") && !arco9.getPesoArco().equals("") && !arco9.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco9.getCono().substring(5)),Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco9.getPrdofundidad()) * 10) / 10)),
                                    Double.parseDouble(arco9.getPesoArco()),Double.parseDouble(arco9.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(8, six_arc);
                            full.set(8,true);
                        }else{
                            full.set(8,false);
                        }
                        break;
                    case 9:
                        Arco10 arco10 = (Arco10) adapter.getItem(j);
                        if (!arco10.getCono().equals("") && !arco10.getMonitorUnits().equals("") && !arco10.getPesoArco().equals("") && !arco10.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco10.getCono().substring(5)),Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco10.getPrdofundidad()) * 10) / 10)),
                                    Double.parseDouble(arco10.getPesoArco()),Double.parseDouble(arco10.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(9, six_arc);
                            full.set(9,true);
                        }else{
                            full.set(9,false);
                        }
                        break;
                    case 10:
                        Arco11 arco11 = (Arco11) adapter.getItem(j);
                        if (!arco11.getCono().equals("") && !arco11.getMonitorUnits().equals("") && !arco11.getPesoArco().equals("") && !arco11.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco11.getCono().substring(5)),Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco11.getPrdofundidad()) * 10) / 10)),
                                    Double.parseDouble(arco11.getPesoArco()),Double.parseDouble(arco11.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(10, six_arc);
                            full.set(10,true);
                        }else{
                            full.set(10,false);
                        }
                        break;
                    case 11:
                        Arco12 arco12 = (Arco12) adapter.getItem(j);
                        if (!arco12.getCono().equals("") && !arco12.getMonitorUnits().equals("") && !arco12.getPesoArco().equals("") && !arco12.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco12.getCono().substring(5)),Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco12.getPrdofundidad()) * 10) / 10)),
                                    Double.parseDouble(arco12.getPesoArco()),Double.parseDouble(arco12.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(11, six_arc);
                            full.set(11,true);
                        }else{
                            full.set(11,false);
                        }
                        break;
                    case 12:
                        Arco13 arco13 = (Arco13) adapter.getItem(j);
                        if (!arco13.getCono().equals("") && !arco13.getMonitorUnits().equals("") && !arco13.getPesoArco().equals("") && !arco13.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco13.getCono().substring(5)),Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco13.getPrdofundidad()) * 10) / 10)),
                                    Double.parseDouble(arco13.getPesoArco()),Double.parseDouble(arco13.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(12, six_arc);
                            full.set(12,true);
                        }else{
                            full.set(12,false);
                        }
                        break;
                    case 13:
                        Arco14 arco14 = (Arco14) adapter.getItem(j);
                        if (!arco14.getCono().equals("") && !arco14.getMonitorUnits().equals("") && !arco14.getPesoArco().equals("") && !arco14.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco14.getCono().substring(5)),Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco14.getPrdofundidad()) * 10) / 10)),
                                    Double.parseDouble(arco14.getPesoArco()),Double.parseDouble(arco14.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(13, six_arc);
                            full.set(13,true);
                        }else{
                            full.set(13,false);
                        }
                        break;
                    case 14:
                        Arco15 arco15 = (Arco15) adapter.getItem(j);
                        if (!arco15.getCono().equals("") && !arco15.getMonitorUnits().equals("") && !arco15.getPesoArco().equals("") && !arco15.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco15.getCono().substring(5)),Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco15.getPrdofundidad()) * 10) / 10)),
                                    Double.parseDouble(arco15.getPesoArco()),Double.parseDouble(arco15.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(14, six_arc);
                            full.set(14,true);
                        }else{
                            full.set(14,false);
                        }
                        break;
                    case 15:
                        Arco16 arco16 = (Arco16) adapter.getItem(j);
                        if (!arco16.getCono().equals("") && !arco16.getMonitorUnits().equals("") && !arco16.getPesoArco().equals("") && !arco16.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco16.getCono().substring(5)),Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco16.getPrdofundidad()) * 10) / 10)),
                                    Double.parseDouble(arco16.getPesoArco()),Double.parseDouble(arco16.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(15, six_arc);
                            full.set(15,true);
                        }else{
                            full.set(15,false);
                        }
                        break;
                    case 16:
                        Arco17 arco17 = (Arco17) adapter.getItem(j);
                        if (!arco17.getCono().equals("") && !arco17.getMonitorUnits().equals("") && !arco17.getPesoArco().equals("") && !arco17.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco17.getCono().substring(5)),Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco17.getPrdofundidad()) * 10) / 10)),
                                    Double.parseDouble(arco17.getPesoArco()),Double.parseDouble(arco17.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(16, six_arc);
                            full.set(16,true);
                        }else{
                            full.set(16,false);
                        }
                        break;
                    case 17:
                        Arco18 arco18 = (Arco18) adapter.getItem(j);
                        if (!arco18.getCono().equals("") && !arco18.getMonitorUnits().equals("") && !arco18.getPesoArco().equals("") && !arco18.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco18.getCono().substring(5)),Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco18.getPrdofundidad()) * 10) / 10)),
                                    Double.parseDouble(arco18.getPesoArco()),Double.parseDouble(arco18.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(17, six_arc);
                            full.set(17,true);
                        }else{
                            full.set(17,false);
                        }
                        break;
                    case 18:
                        Arco19 arco19 = (Arco19) adapter.getItem(j);
                        if (!arco19.getCono().equals("") && !arco19.getMonitorUnits().equals("") && !arco19.getPesoArco().equals("") && !arco19.getPrdofundidad().equals("")) {
                            six_arc = new Six_X_Trilogy(total_dose, number_of_fraction, dose_fraction, treatment_percentage, weight_dose_maximum,
                                    Integer.parseInt(arco19.getCono().substring(5)),Double.parseDouble(String.valueOf(Math.round(Double.parseDouble(arco19.getPrdofundidad()) * 10) / 10)),
                                    Double.parseDouble(arco19.getPesoArco()),Double.parseDouble(arco19.getMonitorUnits()));
                            Log.i("Arco1", "Total dose: " + six_arc.getTotal_dose() + " Number fraction: " + six_arc.getNumber_of_fraction() + " Dose fraction: " + six_arc.getDose_fraction()
                                    + " Cono: " + six_arc.getCone() + " Output Factor: " + six_arc.getOutputFactor() + " Profundidad: " + six_arc.getAver_depth_cm() + " TMR: " + six_arc.getTMR());
                            ArcosArray.set(18, six_arc);
                            full.set(18,true);
                        }else{
                            full.set(18,false);
                        }
                        break;
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

    public void setUPPDF (Integer arcoIDX){
        if (PDF!=null && PDF>0) {
            Integer arcoIDXX = arcoIDX;
            switch (arcoIDX) {
                case 0:
                    Arco1 arco = (Arco1) adapter.getItem(arcoIDXX);
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("1"))) {
                            arcoIDX = i;
                            break;
                        }
                    }

                    arco.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                case 1:
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("2"))) {
                            arcoIDX = i;
                            break;
                        }
                    }
                    Arco2 arco2 = (Arco2) adapter.getItem(arcoIDXX);
                    arco2.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco2.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco2.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco2.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                case 2:
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("3"))) {
                            arcoIDX = i;
                            break;
                        }
                    }
                    Arco3 arco3 = (Arco3) adapter.getItem(arcoIDXX);
                    arco3.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco3.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco3.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco3.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                case 3:
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("4"))) {
                            arcoIDX = i;
                            break;
                        }
                    }
                    Arco4 arco4 = (Arco4) adapter.getItem(arcoIDXX);
                    arco4.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco4.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco4.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco4.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                case 4:
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("5"))) {
                            arcoIDX = i;
                            break;
                        }
                    }
                    Arco5 arco5 = (Arco5) adapter.getItem(arcoIDXX);
                    arco5.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco5.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco5.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco5.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                case 5:
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("6"))) {
                            arcoIDX = i;
                            break;
                        }
                    }
                    Arco6 arco6 = (Arco6) adapter.getItem(arcoIDXX);
                    arco6.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco6.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco6.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco6.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                case 6:
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("7"))) {
                            arcoIDX = i;
                            break;
                        }
                    }
                    Arco7 arco7 = (Arco7) adapter.getItem(arcoIDXX);
                    arco7.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco7.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco7.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco7.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                case 7:
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("8"))) {
                            arcoIDX = i;
                            break;
                        }
                    }
                    Arco8 arco8 = (Arco8) adapter.getItem(arcoIDXX);
                    arco8.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco8.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco8.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco8.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                case 8:
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("9"))) {
                            arcoIDX = i;
                            break;
                        }
                    }
                    Arco9 arco9 = (Arco9) adapter.getItem(arcoIDXX);
                    arco9.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco9.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco9.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco9.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                case 9:
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("10"))) {
                            arcoIDX = i;
                            break;
                        }
                    }
                    Arco10 arco10 = (Arco10) adapter.getItem(arcoIDXX);
                    arco10.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco10.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco10.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco10.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                case 10:
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("11"))) {
                            arcoIDX = i;
                            break;
                        }
                    }
                    Arco11 arco11 = (Arco11) adapter.getItem(arcoIDXX);
                    arco11.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco11.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco11.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco11.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                case 11:
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("12"))) {
                            arcoIDX = i;
                            break;
                        }
                    }
                    Arco12 arco12 = (Arco12) adapter.getItem(arcoIDXX);
                    arco12.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco12.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco12.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco12.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                case 12:
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("13"))) {
                            arcoIDX = i;
                            break;
                        }
                    }
                    Arco13 arco13 = (Arco13) adapter.getItem(arcoIDXX);
                    arco13.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco13.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco13.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco13.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                case 13:
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("14"))) {
                            arcoIDX = i;
                            break;
                        }
                    }
                    Arco14 arco14 = (Arco14) adapter.getItem(arcoIDXX);
                    arco14.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco14.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco14.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco14.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                case 14:
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("15"))) {
                            arcoIDX = i;
                            break;
                        }
                    }
                    Arco15 arco15 = (Arco15) adapter.getItem(arcoIDXX);
                    arco15.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco15.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco15.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco15.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                case 15:
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("16"))) {
                            arcoIDX = i;
                            break;
                        }
                    }
                    Arco16 arco16 = (Arco16) adapter.getItem(arcoIDXX);
                    arco16.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco16.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco16.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco16.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                case 16:
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("17"))) {
                            arcoIDX = i;
                            break;
                        }
                    }
                    Arco17 arco17 = (Arco17) adapter.getItem(arcoIDXX);
                    arco17.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco17.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco17.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco17.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                case 17:
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("18"))) {
                            arcoIDX = i;
                            break;
                        }
                    }
                    Arco18 arco18 = (Arco18) adapter.getItem(arcoIDXX);
                    arco18.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco18.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco18.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco18.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                case 18:
                    for (int i = 0; i < ArcosArrayExtras.size(); i++) {
                        if (ArcosArrayExtras.get(i).getArc_name().equals(("19"))) {
                            arcoIDX = i;
                            break;
                        }
                    }
                    Arco19 arco19 = (Arco19) adapter.getItem(arcoIDXX);
                    arco19.setCono(new OutputFactor().getIndexConoFromString(ArcosArrayExtras.get(arcoIDX).getCone()));
                    arco19.setMonitorUnits(String.valueOf(ArcosArrayExtras.get(arcoIDX).getMu_tps()));
                    arco19.setProfundidad(String.valueOf(ArcosArrayExtras.get(arcoIDX).getAver_depth_cm() * 10));
                    arco19.setPesoArco(String.valueOf(ArcosArrayExtras.get(arcoIDX).getWeight_factor()));
                    break;
                default:
                    break;
            }
            Log.i ("IDX",arcoIDX+" IDXX "+arcoIDXX);
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