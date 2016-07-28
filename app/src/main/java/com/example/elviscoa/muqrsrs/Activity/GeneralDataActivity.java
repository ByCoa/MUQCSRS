package com.example.elviscoa.muqrsrs.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.elviscoa.muqrsrs.Database.Database;
import com.example.elviscoa.muqrsrs.R;

public class GeneralDataActivity extends AppCompatActivity {
    //putExtra
    private static final String D_ZERO ="D_ZERO";
    private static final String ARCOS ="ARCOS";
    private static final String DOSIS_PRESCRITA="DOSIS_PRESCRITA";
    private static final String NORMALIZACION="NORMALIZACION";
    private static final String PESO_MAXIMO_DOSIS="PESO_MAXIMO_DOSIS";
    private String drawerTitle;
    private Long tsLong;
    //UI
    private EditText dosis_prescrita;
    private EditText normalizacion;
    private EditText peso_maximo_dosis;
    private EditText d_zero;
    private Spinner  cant_arcos;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton fab;
    //Database
    private Database dbHandler = new Database(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_data_layout);
        setToolbar();
        d_zero          = (EditText) findViewById(R.id.input_d_zero);
        cant_arcos      = (Spinner) findViewById(R.id.cant_arco);
        dosis_prescrita = (EditText) findViewById(R.id.input_dosis_prescrita);
        normalizacion   = (EditText) findViewById(R.id.input_nomalizacion);
        peso_maximo_dosis=(EditText) findViewById(R.id.input_peso_maximo_dosis);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_design_support_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //read(this);
        //Log.i("Response", String.valueOf(GenerarPDF.read("srs")));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!d_zero.getText().toString().equals("") && !cant_arcos.getSelectedItem().toString().equals("") && !dosis_prescrita.getText().toString().equals("")
                        && !normalizacion.getText().toString().equals("") && !peso_maximo_dosis.getText().toString().equals("")) {
                    Intent i = new Intent(GeneralDataActivity.this, ArcoActivity.class);
                    i.putExtra(D_ZERO, d_zero.getText().toString());
                    i.putExtra(ARCOS, cant_arcos.getSelectedItem().toString());
                    i.putExtra(DOSIS_PRESCRITA, dosis_prescrita.getText().toString());
                    i.putExtra(NORMALIZACION, normalizacion.getText().toString());
                    i.putExtra(PESO_MAXIMO_DOSIS, peso_maximo_dosis.getText().toString());
                    setGeneralData("         ", "           ",
                            "6X", d_zero.getText().toString(), dosis_prescrita.getText().toString(),
                            String.valueOf(Double.parseDouble(normalizacion.getText().toString())),
                            peso_maximo_dosis.getText().toString());
                    //GenerarPDF.GenerarPDF(GeneralDataActivity.this, dbHandler, String.valueOf(tsLong));
                    //fillRecentProductList(dbHandler, String.valueOf(tsLong));
                    i.putExtra("DATE", String.valueOf(tsLong));
                    Log.d("DATE", String.valueOf(tsLong));
                    GeneralDataActivity.this.startActivity(i);
                }
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();
            Toast.makeText(GeneralDataActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
            if (menuItem.getTitle().toString().equals("Contact us")){
                Intent i = new Intent(GeneralDataActivity.this, ContentFragment.class);
                GeneralDataActivity.this.startActivity(i);
            }

            return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_18dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    public void setGeneralData (String PATIENT_ID, String PLAN_ID,
                                String ENERGY, String D_ZERO, String DOSIS_PRESCRITA,
                                String NORMALIZACION, String PESO_MAXIMO_DOSIS){
        dbHandler.write();
        dbHandler.createGeneralData(PATIENT_ID, PLAN_ID,
                generalDate(), ENERGY, D_ZERO, DOSIS_PRESCRITA,
                NORMALIZACION, PESO_MAXIMO_DOSIS);
        dbHandler.close();
        //Toast.makeText(GeneralDataActivity.this, "General", Toast.LENGTH_SHORT).show();
    }

    public String generalDate(){
        tsLong = System.currentTimeMillis()/1000;
        return tsLong.toString();
    }
    private void fillRecentProductList (Database dbHandler,String date){
        Cursor c = dbHandler.getGeneralData(date);
        if (c.moveToFirst()){
            do{
                Log.d("Database", c.getString(0) + " " + c.getString(1)
                        + " " + c.getString(2) + " " + c.getString(3)
                        + " " + c.getString(4) + " " + c.getString(5)
                        + " " + c.getString(6) + " " + c.getString(7));
            }while (c.moveToNext());
        }
        dbHandler.close();
    }
}
