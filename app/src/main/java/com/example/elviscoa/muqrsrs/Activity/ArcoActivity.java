package com.example.elviscoa.muqrsrs.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.elviscoa.muqrsrs.Adapter.Pager;
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
    private Integer DOSIS_PRESCRITA=2000;
    private Double NORMALIZACION=1.0;
    private Double PESO_MAXIMO_DOSIS=5.052;
    private String DATE="";
    private String D_ZERO="";
    private String ARCOS="";
    //UI
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private Pager adapter;
    private FloatingActionButton fab;
    //Database
    private Database dbHandler = new Database(this);

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

    public void getFragmentPager (int Arco,String title){
        int i=0;
        if (title.equals(getString(R.string.report))){

        }else {

            switch (Arco) {
                case 0:
                    Arco1 arco1 = (Arco1) adapter.getItem(Arco);
                    Log.i("Spinner", arco1.getCono());
                    break;
                case 1:
                    Arco2 arco2 = (Arco2) adapter.getItem(Arco);

                    break;
                case 2:
                    Arco3 arco3 = (Arco3) adapter.getItem(Arco);

                    break;
                case 3:
                    Arco4 arco4 = (Arco4) adapter.getItem(Arco);

                    break;
                case 4:
                    Arco5 arco5 = (Arco5) adapter.getItem(Arco);

                    break;
                case 5:
                    Arco6 arco6 = (Arco6) adapter.getItem(Arco);

                    break;
                case 6:
                    Arco7 arco7 = (Arco7) adapter.getItem(Arco);

                    break;
                case 7:
                    Arco8 arco8 = (Arco8) adapter.getItem(Arco);

                    break;
                case 8:
                    Arco9 arco9 = (Arco9) adapter.getItem(Arco);

                    break;
                case 9:
                    Arco10 arco10 = (Arco10) adapter.getItem(Arco);

                    break;
                case 10:
                    Arco11 arco11 = (Arco11) adapter.getItem(Arco);

                    break;
                case 11:
                    Arco12 arco12 = (Arco12) adapter.getItem(Arco);

                    break;
                case 12:
                    Arco13 arco13 = (Arco13) adapter.getItem(Arco);

                    break;
                case 13:
                    Arco14 arco14 = (Arco14) adapter.getItem(Arco);

                    break;
                case 14:
                    Arco15 arco15 = (Arco15) adapter.getItem(Arco);

                    break;
                case 15:
                    Arco16 arco16 = (Arco16) adapter.getItem(Arco);

                    break;
                case 16:
                    Arco17 arco17 = (Arco17) adapter.getItem(Arco);

                    break;
                case 17:
                    Arco18 arco18 = (Arco18) adapter.getItem(Arco);

                    break;
                case 18:
                    Arco19 arco19 = (Arco19) adapter.getItem(Arco);

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
    }
}