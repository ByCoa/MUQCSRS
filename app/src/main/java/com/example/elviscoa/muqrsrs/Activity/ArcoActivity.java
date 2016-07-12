package com.example.elviscoa.muqrsrs.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.elviscoa.muqrsrs.Adapter.Pager;
import com.example.elviscoa.muqrsrs.R;

/**
 * Created by elvis on 09/07/16.
 */
public class ArcoActivity extends AppCompatActivity {
    //Constants
    private static final String STRINGDOSIS_PRESCRITA="DOSIS_PRESCRITA";
    private static final String STRINGNORMALIZACION="NORMALIZACION";
    private static final String STRINGPESO_MAXIMO_DOSIS="PESO_MAXIMO_DOSIS";
    //Extras
    private Integer DOSIS_PRESCRITA=2000;
    private Double NORMALIZACION=1.0;
    private Double PESO_MAXIMO_DOSIS=5.052;
    //UI
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);
        /*
        Bundle extras = getIntent().getExtras();
        DOSIS_PRESCRITA=extras.getInt(STRINGDOSIS_PRESCRITA);
        NORMALIZACION=extras.getDouble(STRINGNORMALIZACION);
        PESO_MAXIMO_DOSIS=extras.getDouble(STRINGPESO_MAXIMO_DOSIS);
        */
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
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}