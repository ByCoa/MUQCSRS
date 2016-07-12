package com.example.elviscoa.muqrsrs.Adapter;

/**
 * Created by elvis on 09/07/16.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.elviscoa.muqrsrs.Fragment.Arco1;
import com.example.elviscoa.muqrsrs.Fragment.Arco2;
import com.example.elviscoa.muqrsrs.Fragment.Arco3;
import com.example.elviscoa.muqrsrs.Fragment.Arco4;
import com.example.elviscoa.muqrsrs.Fragment.Arco5;
import com.example.elviscoa.muqrsrs.Fragment.Arco6;
import com.example.elviscoa.muqrsrs.Fragment.Arco7;
import com.example.elviscoa.muqrsrs.Fragment.Arco8;
import com.example.elviscoa.muqrsrs.Fragment.Arco9;

import java.util.ArrayList;
import java.util.List;

public class Pager extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    int mNumOfTabs;

    public Pager(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Arco1 arco1= new Arco1();
                return arco1;
            case 1:
                Arco2 arco2= new Arco2();
                return arco2;
            case 2:
                Arco3 arco3= new Arco3();
                return arco3;
            case 3:
                Arco4 arco4= new Arco4();
                return arco4;
            case 4:
                Arco5 arco5= new Arco5();
                return arco5;
            case 5:
                Arco6 arco6= new Arco6();
                return arco6;
            case 6:
                Arco7 arco7= new Arco7();
                return arco7;
            case 7:
                Arco8 arco8= new Arco8();
                return arco8;
            case 8:
                Arco9 arco9= new Arco9();
                return arco9;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}