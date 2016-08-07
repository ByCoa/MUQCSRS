package com.example.elviscoa.muqrsrs.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.elviscoa.muqrsrs.Adapter.ResultAdapter;
import com.example.elviscoa.muqrsrs.Class.Results;
import com.example.elviscoa.muqrsrs.R;

import java.util.ArrayList;

/**
 * Created by soluciones on 8/6/2016.
 */
public class Result  extends AppCompatActivity {
    //UInterface
    private ListView trackingList;

    //ArrayList
    private ArrayList<Results> trackingProducts= new ArrayList<Results>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.semaforo);
        setToolbar();// Añadir la Toolbar
        trackingList = (ListView)findViewById(R.id.listView);
        trackingList.setItemsCanFocus(false);
        fillTrackingProductList();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void fillTrackingProductList(){
        final Bundle extras = getIntent().getExtras();
        int size= extras.getInt("Size");
        for (int i=0;i<size;i++){

                Results P= new Results();
                String data[]= extras.getString(""+i).split(",");
                P.setMutps(data[0]);
                P.setMuqcsrs(data[1]);
                P.setError(data[2]);
                trackingProducts.add(P);
        }
            ResultAdapter adapter= new ResultAdapter(Result.this,R.layout.result_list, trackingProducts) ;
            trackingList.setAdapter(adapter);
    }

}
