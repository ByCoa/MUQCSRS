package com.example.elviscoa.muqrsrs.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
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

import com.example.elviscoa.muqrsrs.Class.Six_X_Trilogy;
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
    private static final String TOTAL_DOSE ="TOTAL_DOSE";
    private static final String NUMBER_FRACTION ="NUMBER_FRACTION";
    private static final String DOSE_FRACTION ="DOSE_FRACTION";
    private static final String TREATMENT_PER ="TREATMENT_PER";
    private static final String WEIGHT_DOSE_MAXIMUM ="WEIGHT_DOSE_MAXIMUM";
    private static final String ARCS ="ARCS";
    private static final String PDFARCOS="PDFARCOS";
    private String drawerTitle;
    private Long tsLong;
    //Array
    private ArrayList<String> extrasString = new ArrayList<String>();
    //UI
    private EditText d_zero;
    private EditText total_dose;
    private EditText number_fraction;
    private EditText dose_fraction;
    private EditText treatment_per;
    private EditText weight_dose_maximum;
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
        dose_fraction = (EditText) findViewById(R.id.input_dose_fraction);
        total_dose = (EditText) findViewById(R.id.input_total_dose);
        number_fraction =(EditText) findViewById(R.id.input_number_fraction);
        treatment_per = (EditText) findViewById(R.id.input_treatment_per);
        weight_dose_maximum = (EditText) findViewById(R.id.input_weight_dose_maximum);
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
                if (!d_zero.getText().toString().equals("") && !cant_arcos.getSelectedItem().toString().equals("") && !dose_fraction.getText().toString().equals("")
                        && !total_dose.getText().toString().equals("") && !number_fraction.getText().toString().equals("") && !treatment_per.getText().toString().equals("")
                        && !weight_dose_maximum.getText().toString().equals("")) {
                    Intent i = new Intent(GeneralDataActivity.this, ArcoActivity.class);
                    i.putExtra(D_ZERO, d_zero.getText().toString());
                    i.putExtra(TOTAL_DOSE, Double.parseDouble(total_dose.getText().toString()));
                    i.putExtra(NUMBER_FRACTION, Integer.parseInt(number_fraction.getText().toString()));
                    i.putExtra(DOSE_FRACTION, Double.parseDouble(dose_fraction.getText().toString()));
                    i.putExtra(TREATMENT_PER, Double.parseDouble(treatment_per.getText().toString()));
                    i.putExtra(WEIGHT_DOSE_MAXIMUM, Double.parseDouble(weight_dose_maximum.getText().toString()));
                    i.putExtra(ARCS, cant_arcos.getSelectedItem().toString());
                    i.putExtra(PDFARCOS,extrasString.size());
                    generalDate();
                    i.putExtra("DATE", String.valueOf(tsLong));
                    for (int j=0; j<extrasString.size();j++){
                        i.putExtra(String.valueOf(j), extrasString.get(j));
                    }
                    Six_X_Trilogy six_x_trilogyAux= new Six_X_Trilogy(Double.parseDouble(total_dose.getText().toString()), Integer.parseInt(number_fraction.getText().toString())
                            ,Double.parseDouble(dose_fraction.getText().toString()),Double.parseDouble(treatment_per.getText().toString()),
                            Double.parseDouble(weight_dose_maximum.getText().toString()));
                    setGeneralData("           ", "           ",
                            "6X", d_zero.getText().toString(), total_dose.getText().toString(), number_fraction.getText().toString(), dose_fraction.getText().toString(),
                            treatment_per.getText().toString(), weight_dose_maximum.getText().toString(), String.valueOf(six_x_trilogyAux.getRepeatFactor()));
                    Log.i("Repeat factor", "" + six_x_trilogyAux.getRepeatFactor());
                    fillGereralData(dbHandler,String.valueOf(tsLong));
                    GeneralDataActivity.this.startActivity(i);
                }
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();
            //Toast.makeText(GeneralDataActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
            if (menuItem.getTitle().toString().equals("Contact us")){
                Intent i = new Intent(GeneralDataActivity.this, Contact.class);
                GeneralDataActivity.this.startActivity(i);
            }
            if (menuItem.getTitle().toString().equals("Tolerance")){
                Intent i = new Intent(GeneralDataActivity.this, Tolerance.class);
                GeneralDataActivity.this.startActivity(i);
            }
            if (menuItem.getTitle().toString().equals("Who we are?")){
                Intent i = new Intent(GeneralDataActivity.this, WhoWeAre.class);
                GeneralDataActivity.this.startActivity(i);
            }
            if (menuItem.getTitle().toString().equals("Calculation Formalism")){
                Intent i = new Intent(GeneralDataActivity.this, CalculationFormalism.class);
                GeneralDataActivity.this.startActivity(i);
            }
            if (menuItem.getTitle().toString().equals("My own data")){
                Intent i = new Intent(GeneralDataActivity.this, MyOwnData.class);
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

    private static void fillGereralData(Database dbHandler, String date){
        Cursor c = dbHandler.getGeneralData(date);
        if (c.moveToFirst()){
            do{
                Log.d("Database Gen", c.getString(0) + " " + c.getString(1)
                        + " " + c.getString(2) + " " + c.getString(3)
                        + " " + c.getString(4) + " " + c.getString(5)
                        + " " + c.getString(6) + " " + c.getString(7)
                        + " " + c.getString(8) + " " + c.getString(9)
                        + " " + c.getString(10));

            }while (c.moveToNext());
        }
        dbHandler.close();
    }
    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_18dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    }

    private void bitmap (){

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.papel1e);
        bm = compressImage.getResizedBitmap(bm,500);
        Log.i("Image encode", compressImage.encodeToBase(bm, Bitmap.CompressFormat.JPEG, 100));

    }


    public void setGeneralData (String PATIENT_ID, String PLAN_ID,
                                String ENERGY, String D_ZERO, String TOTAL_DOSE, String NUMBER_FRACTION, String DOSE_FRACTION,
                                String TREATMENT_PER, String WEIGHT_DOSE_MAXIMUM, String REPEAT_FACTOR){
        dbHandler.write();
        dbHandler.createGeneralData(PATIENT_ID, PLAN_ID,
                String.valueOf(tsLong), ENERGY, D_ZERO, TOTAL_DOSE, NUMBER_FRACTION, DOSE_FRACTION,
                TREATMENT_PER, WEIGHT_DOSE_MAXIMUM, REPEAT_FACTOR);
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
                        + " " + c.getString(6) + " " + c.getString(7)
                        + " " + c.getString(8) + " " + c.getString(9)
                        + " " + c.getString(10));
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
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("requestCode", "" + requestCode);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                //Toast.makeText(GeneralDataActivity.this, "Loading PDF data", Toast.LENGTH_SHORT).show();
                final ProgressDialog dialog = ProgressDialog.show(GeneralDataActivity.this, "",
                        "Loading PDF data. Please wait...", true);
                dialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        // do the thing that takes a long time
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Log.i("Response", GenerarPDF.read(String.valueOf(data.getData().getPath())));
                                getPDFData(GenerarPDF.read(String.valueOf(data.getData().getPath())));
                                dialog.dismiss();
                            }
                        });
                    }
                }).start();
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
        Six_X_Trilogy six_x_trilogy= new Six_X_Trilogy();
        Integer arcscount=0;
        for (int i=0;i<splinter.length;i++){
            if (splinter[i].startsWith("Total Dose:")){
                String Aux[]=splinter[i].split(" ");
                //String SI[]= Aux[2].split(".");
                six_x_trilogy.setTotal_dose(Double.valueOf(Aux[2]));
                Log.i("Total Dose", String.valueOf(Aux[2]));
                total_dose.setText(Aux[2]);

            }
            if (splinter[i].startsWith("Dose / Fraction:")){
                String Aux[]=splinter[i].split(" ");
                Log.i("Dose/Fraction", String.valueOf(Aux[3]));
                //String SI[]= Aux[3].split(".");
                six_x_trilogy.setDose_fraction(Double.valueOf(Aux[3]));
                dose_fraction.setText(Aux[3]);
            }
            if (splinter[i].startsWith("Number of Fractions:")){
                String Aux[]=splinter[i].split(" ");
                Log.i("Number of Fractions", String.valueOf(Aux[3]));
                six_x_trilogy.setNumber_of_fraction(Integer.valueOf(Aux[3]));
                number_fraction.setText(Aux[3]);
            }

            if (splinter[i].startsWith("Treatment Percentage:")){
                String Aux[]=splinter[i].split(" ");
                Log.i("Treatment Percetage", String.valueOf(Aux[2]));
                String SI[]= Aux[2].split("%");
                treatment_per.setText(SI[0]);
                six_x_trilogy.setTreatment_percentage(Double.valueOf(SI[0]));
            }

            if (splinter[i].startsWith("Campo")){
                String Aux[]=splinter[i].split(" ");
                String MU[];
                arcscount=arcscount+1;
                if (Aux.length==17){
                    MU=Aux[14].split("M");
                    Log.i(Aux[0] + " " + Aux[1], "Cone: "+Aux[6] +" Weight Factor: "+ Aux[10] +" MU: "+ MU[0]+" Aver. D: "+ Aux[16]  );

                    extrasString.add("ARC " + Aux[1]+","+Aux[6] +","+ Aux[10] +","+ MU[0].substring(0,MU[0].length()-1)+","+ Aux[16]  );
                }
                else if (Aux.length==14){
                    MU=Aux[11].split("M");
                    Log.i("ARC " + Aux[1], "Cone: " + Aux[3] + " Weight Factor: " + Aux[7] + " MU: " + MU[0]+" Aver. D: "+ Aux[13] );

                    extrasString.add("ARC " + Aux[1]+","+Aux[3] +","+ Aux[7] +","+ MU[0].substring(0,MU[0].length()-1)+","+ Aux[13]  );
                }

            }
        }
        cant_arcos.setSelection(arcscount - 1);
    }
}
