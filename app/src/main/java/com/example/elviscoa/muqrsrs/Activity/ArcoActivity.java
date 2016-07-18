package com.example.elviscoa.muqrsrs.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.elviscoa.muqrsrs.Adapter.Pager;
import com.example.elviscoa.muqrsrs.Database.Database;
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
    //Extras
    private Integer DOSIS_PRESCRITA=2000;
    private Double NORMALIZACION=1.0;
    private Double PESO_MAXIMO_DOSIS=5.052;
    private String DATE="";
    //UI
    private Toolbar toolbar;
    private TabLayout tabLayout;
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
        }
        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Tablayout
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (String tabs: this.getResources().getStringArray(R.array.tabs)){
            tabLayout.addTab(tabLayout.newTab().setText(tabs));
        }

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Log.d("Page", String.valueOf(tab.getPosition()));
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
}