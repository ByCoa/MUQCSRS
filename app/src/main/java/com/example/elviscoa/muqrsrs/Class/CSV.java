package com.example.elviscoa.muqrsrs.Class;

/**
 * Created by soluciones on 7/2/2016.
 */
import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class CSV {

    private static final String CVS_SPLIT_BY="\",\"";
    public ArrayList<ArrayList<String>>  readTMRCVS(Context context,InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ArrayList<ArrayList<String>> csvContentMatrix = new ArrayList<ArrayList<String>>();
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                // use comma as separator
                String[] csvContent = line.split(CVS_SPLIT_BY);
                ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(csvContent));
                csvContentMatrix.add(stringList);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return csvContentMatrix;
    }

    public ArrayList<String> readOutputFactorCVS(Context context,InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ArrayList<String> csvContentArray= new ArrayList<String>();
        String line = "";

        try {
            while ((line = reader.readLine()) != null) {
                // use comma as separator
                String[] csvContent = line.split(CVS_SPLIT_BY);
                csvContentArray.add(csvContent[0]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return csvContentArray;
    }


}
