package com.example.elviscoa.muqrsrs.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.elviscoa.muqrsrs.Class.Util;

/**
 * Created by Elvis on 7/16/2016.
 */
public class Database extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;
    private SQLiteDatabase DATABASE;
    // Database Name
    private static final String DATABASE_NAME="MUQCSRS";

    // Generals Data
    private static final String TABLE_GENERALS="GENERALS";
    // Arcs
    private static final String TABLE_ARCS="ARCS";


    // Generals Table Columns names
    private static final String IDG="IDG";
    private static final String PATIENT_ID="PATIENT_ID";
    private static final String PLAN_ID="PLAN_ID";
    private static final String DATE="DATE";
    private static final String ENERGY="ENERGY";
    private static final String D_ZERO ="D_ZERO";
    private static final String TOTAL_DOSE ="TOTAL_DOSE";
    private static final String NUMBER_FRACTION ="NUMBER_FRACTION";
    private static final String DOSE_FRACTION ="DOSE_FRACTION";
    private static final String TREATMENT_PER ="TREATMENT_PER";
    private static final String WEIGHT_DOSE_MAXIMUM ="WEIGHT_DOSE_MAXIMUM";
    private static final String REPEAT_FACTOR ="REPEAT_FACTOR";

    // Arc Table Columns names
    private static final String IDA="IDA";
    private static final String ARC="ARC";
    private static final String CONO="CONO";
    private static final String OUTPUT_FACTOR="OUTPUT_FACTOR";
    private static final String PROFUNDIDAD="PROFUNDIDAD";
    private static final String TMR="TMR";
    private static final String PESO_ARCO="PESO_ARCO";
    private static final String MU_QC_SRS="MU_QC_SRS";
    private static final String MU_TPS="MU_TPS";
    private static final String DATEF="DATE";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GENERALS_TABLE = "CREATE TABLE " + TABLE_GENERALS + "("
                + PATIENT_ID       + " TEXT," + PLAN_ID             + " TEXT,"
                + DATE             + " TEXT," + ENERGY              + " TEXT,"
                + D_ZERO           + " TEXT," + TOTAL_DOSE          + " TEXT,"
                + NUMBER_FRACTION  + " TEXT," + DOSE_FRACTION       + " TEXT,"
                + TREATMENT_PER    + " TEXT," + WEIGHT_DOSE_MAXIMUM + " TEXT,"
                + REPEAT_FACTOR    + " TEXT," + IDG                 + " INTEGER PRIMARY KEY AUTOINCREMENT)";

        db.execSQL(CREATE_GENERALS_TABLE);

        String CREATE_ARCS_TABLE = "CREATE TABLE " + TABLE_ARCS+ "("
                + CONO           + " TEXT," + OUTPUT_FACTOR + " TEXT,"
                + PROFUNDIDAD    + " TEXT," + TMR           + " TEXT,"
                + PESO_ARCO      + " TEXT," + MU_QC_SRS     + " TEXT,"
                + MU_TPS         + " TEXT," + DATEF         + " TEXT,"
                + ARC            + " TEXT," + IDA           + " INTEGER PRIMARY KEY AUTOINCREMENT)";

        db.execSQL(CREATE_ARCS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GENERALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARCS);
        // Create tables again
        onCreate(db);
    }

    public void write() {
        DATABASE = this.getWritableDatabase();
    }

    public void close() {
        DATABASE.close();
    }

    public void createGeneralData(String PATIENT_ID, String PLAN_ID,
                                   String DATE, String ENERGY, String D_ZERO,String TOTAL_DOSE,
                                  String NUMBER_FRACTION, String DOSE_FRACTION, String TREATMENT_PER,
                                  String WEIGHT_DOSE_MAXIMUM, String REPEAT_FACTOR) {
        if (!(PATIENT_ID.equals(""))) {
            ContentValues newGeneral = new ContentValues();
            newGeneral.put(this.PATIENT_ID, PATIENT_ID);
            newGeneral.put(this.PLAN_ID, PLAN_ID);
            newGeneral.put(this.DATE, DATE);
            newGeneral.put(this.ENERGY, ENERGY);
            newGeneral.put(this.D_ZERO, D_ZERO);
            newGeneral.put(this.TOTAL_DOSE, TOTAL_DOSE);
            newGeneral.put(this.NUMBER_FRACTION, NUMBER_FRACTION);
            newGeneral.put(this.DOSE_FRACTION, DOSE_FRACTION);
            newGeneral.put(this.TREATMENT_PER, TREATMENT_PER);
            newGeneral.put(this.WEIGHT_DOSE_MAXIMUM, WEIGHT_DOSE_MAXIMUM);
            newGeneral.put(this.REPEAT_FACTOR, REPEAT_FACTOR);
            DATABASE.insert(TABLE_GENERALS, null, newGeneral);

        }
    }

    public void createArc(String ARC,String CON, String OUT,
                               String PRO, String TMR, String PESO_ARCO,
                               String MU_TPS, String MU_QC_SRS, String DAT, String ENERGY, String D_ZERO,String TOTAL_DOSE,
                               String NUMBER_FRACTION, String DOSE_FRACTION, String TREATMENT_PER,
                               String WEIGHT_DOSE_MAXIMUM, String REPEAT_FACTOR) {
        if (!(ARC.equals(""))) {
            ContentValues newArc= new ContentValues();
            newArc.put(this.CONO, CON);
            newArc.put(this.OUTPUT_FACTOR, new Util().roundThreeDecimals(Double.parseDouble(OUT)));
            newArc.put(this.PROFUNDIDAD, String.valueOf(Double.valueOf(PRO)));
            newArc.put(this.TMR, new Util().roundThreeDecimals(Double.parseDouble(TMR)));
            newArc.put(this.PESO_ARCO, String.valueOf(Double.valueOf(PESO_ARCO)));
            newArc.put(this.MU_QC_SRS, new Util().roundThreeDecimals(Double.parseDouble(MU_QC_SRS)));
            newArc.put(this.MU_TPS, new Util().roundThreeDecimals(Double.parseDouble(MU_TPS)));
            newArc.put(this.DATEF, DAT);
            /*createGeneralData("            ", "             ", DAT, ENERGY, D_ZERO ,TOTAL_DOSE, NUMBER_FRACTION,
                    DOSE_FRACTION, TREATMENT_PER, WEIGHT_DOSE_MAXIMUM, REPEAT_FACTOR);*/
            newArc.put(this.ARC, ARC);
            DATABASE.insert(TABLE_ARCS, null, newArc);
        }
    }


    private Cursor query (String qr){
        Cursor c= DATABASE.rawQuery(qr, null);
        return c;
    }

    public Cursor getGeneralData(String Date){
        this.write();
        return  this.query("SELECT * FROM GENERALS WHERE DATE='"+Date+"'");

    }

    public Cursor getArc(String Date){
        this.write();
        return  this.query("SELECT * FROM ARCS WHERE DATE='"+Date+"'");

    }

    public void updateGeneralData (String PATIENT_ID,String PLAN_ID, String DATE){
        ContentValues newGeneral= new ContentValues();
        newGeneral.put(this.PATIENT_ID, PATIENT_ID);
        newGeneral.put(this.PLAN_ID, PLAN_ID);
        DATABASE.update(TABLE_GENERALS,newGeneral,"DATE="+DATE,null);

    }
}