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
    private static final int DATABASE_VERSION = 2;
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
    private static final String D_ZERO="D_ZERO";
    private static final String DOSIS_PRESCRITA="DOSIS_PRESCRITA";
    private static final String NORMALIZACION="NORMALIZACION";
    private static final String PESO_MAXIMO_DOSIS="PESO_MAXIMO_DOSIS";

    // Arc Table Columns names
    private static final String IDA="IDA";
    private static final String ARC="ARC";
    private static final String CONO="CONO";
    private static final String OUTPUT_FACTOR="OUTPUT_FACTOR";
    private static final String PROFUNDIDAD="PROFUNDIDAD";
    private static final String TMR="TMR";
    private static final String PESO_ARCO="PESO_ARCO";
    private static final String DOSIS_FRACCION="DOSIS_FRACCION";
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
                + PATIENT_ID     + " TEXT," + PLAN_ID          + " TEXT,"
                + DATE           + " TEXT," + ENERGY           + " TEXT,"
                + D_ZERO         + " TEXT," + DOSIS_PRESCRITA  + " TEXT,"
                + NORMALIZACION  + " TEXT," + PESO_MAXIMO_DOSIS+ " TEXT,"
                + IDG            + " INTEGER PRIMARY KEY AUTOINCREMENT)";

        db.execSQL(CREATE_GENERALS_TABLE);

        String CREATE_ARCS_TABLE = "CREATE TABLE " + TABLE_ARCS+ "("
                + CONO           + " TEXT," + OUTPUT_FACTOR + " TEXT,"
                + PROFUNDIDAD    + " TEXT," + TMR           + " TEXT,"
                + PESO_ARCO      + " TEXT,"
                + DOSIS_FRACCION + " TEXT," + MU_QC_SRS     + " TEXT,"
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
                                   String DATE, String ENERGY, String D_ZERO, String DOSIS_PRESCRITA,
                                   String NORMALIZACION, String PESO_MAXIMO_DOSIS) {
        if (!(PATIENT_ID.equals(""))) {
            ContentValues newGeneral = new ContentValues();
            newGeneral.put(this.PATIENT_ID, PATIENT_ID);
            newGeneral.put(this.PLAN_ID, PLAN_ID);
            newGeneral.put(this.DATE, DATE);
            newGeneral.put(this.ENERGY, ENERGY);
            newGeneral.put(this.D_ZERO, D_ZERO);
            newGeneral.put(this.DOSIS_PRESCRITA, DOSIS_PRESCRITA);
            newGeneral.put(this.NORMALIZACION, NORMALIZACION);
            newGeneral.put(this.PESO_MAXIMO_DOSIS, PESO_MAXIMO_DOSIS);
            DATABASE.insert(TABLE_GENERALS, null, newGeneral);
        }
    }

    public void createArc(String ARC,String CON, String OUT,
                               String PRO, String TMR, String PESO_ARCO, String DOS,
                               String MU_TPS, String MU_QC_SRS, String DAT) {
        if (!(ARC.equals(""))) {
            ContentValues newArc= new ContentValues();
            newArc.put(this.CONO, new Util().splitCono(CON));
            newArc.put(this.OUTPUT_FACTOR, new Util().roundThreeDecimals(Double.parseDouble(OUT)));
            newArc.put(this.PROFUNDIDAD, String.valueOf(Double.valueOf(PRO)));
            newArc.put(this.TMR, new Util().roundThreeDecimals(Double.parseDouble(TMR)));
            newArc.put(this.PESO_ARCO, String.valueOf(Double.valueOf(PESO_ARCO)));
            newArc.put(this.DOSIS_FRACCION, new Util().roundThreeDecimals(Double.parseDouble(DOS)));
            newArc.put(this.MU_QC_SRS, new Util().roundThreeDecimals(Double.parseDouble(MU_QC_SRS)));
            newArc.put(this.MU_TPS, new Util().roundThreeDecimals(Double.parseDouble(MU_TPS)));
            newArc.put(this.DATEF, DAT);
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
}