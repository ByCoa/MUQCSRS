package com.example.elviscoa.muqrsrs.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.elviscoa.muqrsrs.Class.OCRService;
import com.example.elviscoa.muqrsrs.Class.Six_X_Trilogy;
import com.example.elviscoa.muqrsrs.Class.Util;
import com.example.elviscoa.muqrsrs.Database.Database;
import com.example.elviscoa.muqrsrs.Library.GenerarPDF;
import com.example.elviscoa.muqrsrs.Library.CompressImage;
import com.example.elviscoa.muqrsrs.R;
import com.itextpdf.text.Image;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;

public class GeneralDataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //putExtra
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1, SELECT_PDF = 2;
    private static final String D_ZERO ="D_ZERO";
    private static final String TOTAL_DOSE ="TOTAL_DOSE";
    private static final String NUMBER_FRACTION ="NUMBER_FRACTION";
    private static final String DOSE_FRACTION ="DOSE_FRACTION";
    private static final String TREATMENT_PER ="TREATMENT_PER";
    private static final String WEIGHT_DOSE_MAXIMUM ="WEIGHT_DOSE_MAXIMUM";
    private static final String ARCS ="ARCS";
    private static final String PDFARCOS="PDFARCOS";
    private boolean[] chosen= new boolean[4];
    private String drawerTitle;
    private Long tsLong;
    //Array
    private ArrayList<String> extrasString = new ArrayList<String>();
    //UI
    private EditText d_zero;
    private EditText total_dose;
    private EditText number_fraction;
    private EditText treatment_per;
    private EditText weight_dose_maximum;
    private Spinner  cant_arcos;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton fab;
    private FloatingActionButton fabpdf;
    private FloatingActionButton fabcamera;
    private ImageView imageView,imageView2;
    private String userChoosenTask;
    private MaterialSpinner dzero;
    //Database
    private Database dbHandler = new Database(this);
    //
    private Integer OCR=0;
    private String OCRWF="";
    private String OCRCONE="";
    private String OCRMUTPS="";
    private String OCRAD="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_data_layout);
        setToolbar();

        cant_arcos      = (Spinner) findViewById(R.id.cant_arco);
        total_dose = (EditText) findViewById(R.id.input_total_dose);
        number_fraction =(EditText) findViewById(R.id.input_number_fraction);
        treatment_per = (EditText) findViewById(R.id.input_treatment_per);
        weight_dose_maximum = (EditText) findViewById(R.id.input_weight_dose_maximum);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fabpdf = (FloatingActionButton) findViewById(R.id.fabpdf);
        dzero = (MaterialSpinner) findViewById (R.id.d_zero);
        String mEnergyArray[] = getResources().getStringArray(R.array.mu_arrays);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mEnergyArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dzero.setAdapter(arrayAdapter);
        dzero.setOnItemSelectedListener(this);
        dzero.setSelection(1);
        fabcamera = (FloatingActionButton) findViewById(R.id.fabcamera);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_design_support_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        imageView = (ImageView) findViewById(R.id.info_do);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new android.support.v7.app.AlertDialog.Builder(GeneralDataActivity.this)
                        .setTitle("Weight dose at maximum information")
                        .setMessage("Is the relative weighting at the location of the hotspot. You can find it in the Dosimetry Report or in your Eclipse Cone Planning  System. For more detail, please check your operation Manual")
                        .setPositiveButton("OK", new DialogInterface
                                .OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();

            }
        });

        imageView2 = (ImageView) findViewById(R.id.info_doz);
        imageView2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new android.support.v7.app.AlertDialog.Builder(GeneralDataActivity.this)
                        .setTitle("D'o Information")
                        .setMessage("For any doubt about your calibration condition, please contact The Medical Physicist of your institution.")
                        .setPositiveButton("OK", new DialogInterface
                                .OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();

            }
        });
        for (int i=0;i<4;i++)
            chosen[i]=false;
        //bitmap();
        //read(this);
        //Log.i("Response", String.valueOf(GenerarPDF.read("srs")));
        fabpdf.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
               new android.support.v7.app.AlertDialog.Builder(GeneralDataActivity.this)
                       .setTitle("Information")
                       .setMessage("This option just work with the \"Plan Parameter Report\" from your Eclipse Cone Planning.")
                       .setPositiveButton("OK", new DialogInterface
                               .OnClickListener() {
                           public void onClick(DialogInterface dialog, int which) {
                               // continue with delete
                               verifyStoragePermissions(GeneralDataActivity.this);
                               pdfIntent();
                           }
                       })
                       .setIcon(android.R.drawable.ic_dialog_info)
                       .show();

                                   }
        });

        fabcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.support.v7.app.AlertDialog.Builder(GeneralDataActivity.this)
                        .setTitle("Information")
                        .setMessage("This option just work with the \"Plan Parameter Report\" from your Eclipse Cone Planning.")
                        .setPositiveButton("OK", new DialogInterface
                                .OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                Toast.makeText(GeneralDataActivity.this, "Recuerde seleccionar la cantidad de arcos", Toast.LENGTH_SHORT).show();
                                selectImage();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cant_arcos.getSelectedItem().toString().equals("")
                        && !total_dose.getText().toString().equals("") && !number_fraction.getText().toString().equals("") && !treatment_per.getText().toString().equals("")
                        && !weight_dose_maximum.getText().toString().equals("")) {
                    Intent i = new Intent(GeneralDataActivity.this, ArcoActivity.class);
                    String dzeroo;
                    if (dzero.getSelectedItem().toString().equals("SSD")){
                        i.putExtra(D_ZERO, "1.03");
                        dzeroo="1.03";
                    }
                    else{
                        i.putExtra(D_ZERO, "1.00");
                        dzeroo="1.00";
                    }

                    i.putExtra(TOTAL_DOSE, Double.parseDouble(total_dose.getText().toString()));
                    i.putExtra(NUMBER_FRACTION, Integer.parseInt(number_fraction.getText().toString()));
                    Double dose_fraction = Double.parseDouble(total_dose.getText().toString())/Double.parseDouble(number_fraction.getText().toString());
                    i.putExtra(DOSE_FRACTION, dose_fraction);
                    i.putExtra(TREATMENT_PER, Double.parseDouble(treatment_per.getText().toString()));
                    i.putExtra(WEIGHT_DOSE_MAXIMUM, Double.parseDouble(weight_dose_maximum.getText().toString()));
                    i.putExtra(ARCS, cant_arcos.getSelectedItem().toString());
                    if (OCR==4)
                        putextraOCRDATA();
                    i.putExtra(PDFARCOS,extrasString.size());

                    generalDate();
                    i.putExtra("DATE", String.valueOf(tsLong));
                    for (int j=0; j<extrasString.size();j++){
                        Log.i("PDF",extrasString.get(j));
                        i.putExtra(String.valueOf(j), extrasString.get(j));
                    }
                    Six_X_Trilogy six_x_trilogyAux= new Six_X_Trilogy(Double.parseDouble(total_dose.getText().toString()), Integer.parseInt(number_fraction.getText().toString())
                            ,Double.parseDouble(treatment_per.getText().toString()),
                            Double.parseDouble(weight_dose_maximum.getText().toString()));
                    setGeneralData("           ", "           ",
                            "6X", dzeroo, total_dose.getText().toString(), number_fraction.getText().toString(), String.valueOf(dose_fraction),
                            treatment_per.getText().toString(), weight_dose_maximum.getText().toString(), String.valueOf(six_x_trilogyAux.getRepeatFactor()));
                    Log.i("Repeat factor", "" + six_x_trilogyAux.getRepeatFactor());
                    fillGereralData(dbHandler,String.valueOf(tsLong));
                    GeneralDataActivity.this.startActivity(i);
                }
                if (weight_dose_maximum.getText().toString().equals(""))
                    Toast.makeText(GeneralDataActivity.this,"Weight at Dose maximum is empty", Toast.LENGTH_LONG).show();
                else if (treatment_per.getText().toString().equals(""))
                    Toast.makeText(GeneralDataActivity.this, "Percentage treatment is empty", Toast.LENGTH_LONG).show();
                else if (number_fraction.getText().toString().equals(""))
                    Toast.makeText(GeneralDataActivity.this, "Number of fraction is empty", Toast.LENGTH_LONG).show();
                else if (total_dose.getText().toString().equals(""))
                    Toast.makeText(GeneralDataActivity.this, "Total dose is empty", Toast.LENGTH_LONG).show();
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            //menuItem.setChecked(true);
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
        bm = CompressImage.getResizedBitmap(bm, 500);
        Log.i("Image encode", CompressImage.encodeToBase(bm, Bitmap.CompressFormat.JPEG, 100));

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



    private void pdfIntent(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_PDF);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("requestCode", "" + requestCode);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PDF) {

                /*final ProgressDialog dialog = ProgressDialog.show(GeneralDataActivity.this, "",
                        "Loading PDF data. Please wait...", true);*/
                //dialog.show();

                String response=GenerarPDF.read(String.valueOf(data.getData().getPath()));
                Log.d("Response", response);
                getPDFData2(response);
                /*new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        // do the thing that takes a long time
                        String response="";
                        try {

                            Log.i("Response", response);
                                    Thread.sleep(7000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        final String finalResponse = response;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //

                                //Toast.makeText(GeneralDataActivity.this, "PDF DATA LOAD", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        });
                    }
                }).start();*/
            }
            else if (requestCode == SELECT_FILE){
                onSelectFromGalleryResult(data);
            }
            else if (requestCode == REQUEST_CAMERA){
                Log.d("requestCode", "EntréCamaera");
                onCaptureImageResult(data);
            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Log.d("Activity result", "" + result);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    Log.d("resultUri", "" + resultUri);
                    onSelectFromGalleryResult(resultUri);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    Log.d("resultUri", "" + error);

                }
            }
        }
    }

    private void getPDFData2 (String data){
        String[] splinter= data.split("\n");
        Integer arcscount=1;
        for (int i=0;i<splinter.length;i++){
            if (splinter[i].contains("Campo "+arcscount)){
                String X[]=splinter[i+4].split(" ");
                Log.i("PDF 2", X[0] + " " + X[3]);
                if (splinter[i+14].contains("MU")){
                    String W=splinter[i+13];
                    String MU[]=splinter[i+14].split(" ");
                    Log.i("PDF 2", "MU: "+MU[0] + " W:" + W);
                } else if(splinter[i+15].contains("MU")){
                    String W=splinter[i+14];
                    String MU[]=splinter[i+15].split(" ");
                    Log.i("PDF 2", "MU: "+MU[0] + " W:" + W);
                } else if(splinter[i+16].contains("MU")){
                    String W=splinter[i+15];
                    String MU[]=splinter[i+16].split(" ");
                    Log.i("PDF 2", "MU: "+MU[0] + " W:" + W);
                }

                arcscount++;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Util.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from gallery"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    public void putextraOCRDATA (){
        String []a,b,c,d;
        a=OCRCONE.split(",");
        b=OCRAD.split(",");
        c=OCRWF.split(",");
        d=OCRMUTPS.split(",");
        for (int i=0;i<b.length-1;i++) {
            extrasString.add("ARC " + (i + 1) + "," + a[i] + "," + c[i] + "," + d[i] + "," + b[i]);
            Log.i("Extra","ARC " + (i + 1) + "," + a[i] + "," + c[i] + "," + d[i] + "," + b[i]);
        }


    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Gallery",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(GeneralDataActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Util.checkPermission(GeneralDataActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Gallery")) {
                    userChoosenTask = "Choose from Gallery";
                    if (result)
                        selectPart();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void selectPart() {
        final CharSequence[] items = { "CONE", "AVG. DEPTH(MM)","WEIGHT FACTOR","MU TPS",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(GeneralDataActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Util.checkPermission(GeneralDataActivity.this);

                if (items[item].equals("CONE")) {
                    userChoosenTask = "Choose from Gallery";
                    if (result) {
                        chosen[0] = true;
                        galleryIntent();
                    }

                } else if (items[item].equals("AVG. DEPTH(MM)")) {
                    userChoosenTask = "Choose from Gallery";
                    if (result) {
                        chosen[1] = true;
                        galleryIntent();
                    }
                }
                if (items[item].equals("WEIGHT FACTOR")) {
                    userChoosenTask = "Choose from Gallery";
                    if (result) {
                        chosen[2] = true;
                        galleryIntent();
                    }
                } else if (items[item].equals("MU TPS")) {
                    userChoosenTask = "Choose from Gallery";
                    if (result) {
                        chosen[3] = true;
                        galleryIntent();
                    }
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OCRService ocrService= new OCRService();
        ocrService.callOCRAPI(GeneralDataActivity.this, destination);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                Log.d("data", "" + data.getData());
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onSelectFromGalleryResult(Uri data) {
        Log.d("data2", "" + data);
        final OCRService ocrService = new OCRService();
        String Arc[] = cant_arcos.getSelectedItem().toString().split(" ");
        ocrService.setArc(Integer.valueOf(Arc[0]));
        ocrService.setChosen(chosen);
        ocrService.callOCRAPI(GeneralDataActivity.this, new File(data.getPath()));
        final ProgressDialog dialog = ProgressDialog.show(GeneralDataActivity.this, "",
                "Loading OCR data. Please wait...", true);
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                // do the thing that takes a long time
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        OCR = OCR+1;

                        if (chosen[0]){
                            Log.i("OCR",ocrService.getCone());
                            OCRCONE=ocrService.getCone();
                            chosen[0]=false;
                        }else if (chosen[1]){
                            Log.i("OCR",ocrService.getAvgDepth());
                            OCRAD=ocrService.getAvgDepth();
                            chosen[1]=false;
                        }else if (chosen[2]){
                            Log.i("OCR",ocrService.getWeightFactor());
                            OCRWF=ocrService.getWeightFactor();
                            chosen[2]=false;
                        }else if (chosen[3]){
                            Log.i("OCR",ocrService.getMuTps());
                            OCRMUTPS=ocrService.getMuTps();
                            chosen[3]=false;
                        }

                    }
                });
            }
        }).start();

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.d_zero) {
            String d= (String) parent.getItemAtPosition(position);
            Log.i("dzero", d);
        }
        else if (parent.getId() == R.id.info_do){

        }
        else if (parent.getId() == R.id.info_doz){

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
