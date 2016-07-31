package com.example.elviscoa.muqrsrs.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.example.elviscoa.muqrsrs.Library.GenerarPDF;
import com.example.elviscoa.muqrsrs.Library.compressImage;
import com.example.elviscoa.muqrsrs.R;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;

public class GeneralDataActivity extends AppCompatActivity {
    //putExtra
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
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
    private FloatingActionButton fabpdf;
    private FloatingActionButton fabcamera;
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
        fabpdf = (FloatingActionButton) findViewById(R.id.fabpdf);
        fabcamera = (FloatingActionButton) findViewById(R.id.fabcamera);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_design_support_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //bitmap();
        //read(this);
        //Log.i("Response", String.valueOf(GenerarPDF.read("srs")));
        fabpdf.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                    galleryIntent();
                                   }
        });

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

    private void bitmap (){

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.papel1e);
        bm = compressImage.getResizedBitmap(bm,500);
        Log.i("Image encode", compressImage.encodeToBase(bm, Bitmap.CompressFormat.JPEG, 100));

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

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Log.d("requestCode", "" + requestCode);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                Log.d("data", String.valueOf(data.getData()));//onSelectFromGalleryResult(data);
                Log.i("Response", GenerarPDF.read(String.valueOf(data.getData().getPath())));
                getPDFData(GenerarPDF.read(String.valueOf(data.getData().getPath())));
            }
            else if (requestCode == REQUEST_CAMERA)
                Log.d("requestCode", "EntréCamaera");//onCaptureImageResult(data);
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Log.d("Activity result", "" + result);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    Log.d("resultUri", "" + resultUri);
                    //onSelectFromGalleryResult(resultUri);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    Log.d("resultUri", "" + error);

                }
            }
        }
    }

    private void getPDFData (String data){
        String[] splinter= data.split("\n");
        Log.i("Splinter", String.valueOf(splinter.length));

        for (int i=0;i<splinter.length;i++){
            if (splinter[i].startsWith("Total Dose:")){
                String Aux[]=splinter[i].split(" ");
                Log.i("Total Dose", String.valueOf(Aux[2]));
            }
            if (splinter[i].startsWith("Dose / Fraction:")){
                String Aux[]=splinter[i].split(" ");
                Log.i("Dose/Fraction", String.valueOf(Aux[3]));
            }
            if (splinter[i].startsWith("Repeat Factor:")){
                String Aux[]=splinter[i].split(" ");
                Log.i("Repeat Factor", String.valueOf(Aux[2]));
            }
            if (splinter[i].startsWith("Treatment Percentage:")){
                String Aux[]=splinter[i].split(" ");
                Log.i("Treatment Percetage", String.valueOf(Aux[2]));
            }
            if (splinter[i].startsWith("Campo")){
                String Aux[]=splinter[i].split(" ");
                Log.i("Campo size", String.valueOf(Aux.length));
                if (Aux.length==17)
                    Log.i(Aux[0] + " " + Aux[1], "Cone: "+Aux[6] +" Weight Factor: "+ Aux[10] +" MU: "+ Aux[14]+" Aver. D: "+ Aux[16]  );
                else if (Aux.length==14)
                    Log.i(Aux[0] + " " + Aux[1], "Cone: "+Aux[3] +" Weight Factor: "+ Aux[7] +" MU: "+ Aux[11]+" Aver. D: "+ Aux[13] );
            }
        }
    }
}
