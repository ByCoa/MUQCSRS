package com.example.elviscoa.muqrsrs.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elviscoa.muqrsrs.Class.Results;
import com.example.elviscoa.muqrsrs.Class.Six_X_Trilogy;
import com.example.elviscoa.muqrsrs.R;

import java.util.ArrayList;

/**
 * Created by soluciones on 8/3/2016.
 */
public class ResultAdapter extends ArrayAdapter<Results> {


    private Activity context;
    private ArrayList<Results> mResults;

    public ResultAdapter(Activity context, int resourse, ArrayList<Results> six_x_trilogies) {
        super(context, resourse, six_x_trilogies);

        this.context = context;
        this.mResults = six_x_trilogies;
    }

    public ArrayList<Results> getProductList(){
        return this.mResults;
    }

    public int getCount(){
        return mResults.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.result_list, null);
        Results result = mResults.get(position);

        TextView muqc = (TextView)item.findViewById(R.id.muQc);
        TextView muqcsrs = (TextView)item.findViewById(R.id.muqcsrs);
        TextView arc = (TextView)item.findViewById(R.id.field);
        TextView error =(TextView)item.findViewById(R.id.error);
        ImageView errorImage = (ImageView)item.findViewById(R.id.errorImage);
        arc.setText("A"+(position+1));
        muqcsrs.setText("MU QC SRS = " + result.getMuqcsrs());
        muqc.setText("MU QC = " + result.getMutps());
        double errord = Math.abs(Double.parseDouble(result.getError()));
        error.setText(errord+"%");


        if(errord < 3.0){
            errorImage.setImageResource(R.drawable.ic_check_black_24dp);
            errorImage.setBackgroundResource(R.drawable.oval_green);
            error.setTextColor(context.getResources().getColor(R.color.green_color));
        }else if(errord > 3.0 && errord <= 5.0){
            errorImage.setImageResource(R.drawable.ic_warning_black_24dp);
            errorImage.setBackgroundResource(R.drawable.oval_yellow);
            error.setTextColor(context.getResources().getColor(R.color.yellow_color));
        }else if(errord > 5.0){
            errorImage.setImageResource(R.drawable.ic_close_black_24dp);
            errorImage.setBackgroundResource(R.drawable.oval_red);
            error.setTextColor(context.getResources().getColor(R.color.red_color));
        }

        return item;
    }
}
