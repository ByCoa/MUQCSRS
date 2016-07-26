package com.example.elviscoa.muqrsrs.Adapter;

/**
 * Created by elvis on 09/07/16.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.elviscoa.muqrsrs.Fragment.Arco1;
import com.example.elviscoa.muqrsrs.Fragment.Arco10;
import com.example.elviscoa.muqrsrs.Fragment.Arco11;
import com.example.elviscoa.muqrsrs.Fragment.Arco12;
import com.example.elviscoa.muqrsrs.Fragment.Arco13;
import com.example.elviscoa.muqrsrs.Fragment.Arco14;
import com.example.elviscoa.muqrsrs.Fragment.Arco15;
import com.example.elviscoa.muqrsrs.Fragment.Arco16;
import com.example.elviscoa.muqrsrs.Fragment.Arco17;
import com.example.elviscoa.muqrsrs.Fragment.Arco18;
import com.example.elviscoa.muqrsrs.Fragment.Arco19;
import com.example.elviscoa.muqrsrs.Fragment.Arco2;
import com.example.elviscoa.muqrsrs.Fragment.Arco3;
import com.example.elviscoa.muqrsrs.Fragment.Arco4;
import com.example.elviscoa.muqrsrs.Fragment.Arco5;
import com.example.elviscoa.muqrsrs.Fragment.Arco6;
import com.example.elviscoa.muqrsrs.Fragment.Arco7;
import com.example.elviscoa.muqrsrs.Fragment.Arco8;
import com.example.elviscoa.muqrsrs.Fragment.Arco9;
import com.example.elviscoa.muqrsrs.Fragment.GenerarPDFFragment;
import com.example.elviscoa.muqrsrs.Library.GenerarPDF;

import java.util.ArrayList;
import java.util.List;

public class Pager extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    int mNumOfTabs;

    public Pager(FragmentManager fm,int arc) {
        super(fm);
        this.mNumOfTabs=arc;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mFragmentTitleList.get(position);
    }

    /*
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
            case 9:
                Arco10 arco10= new Arco10();
                return arco10;
            case 10:
                Arco11 arco11= new Arco11();
                return arco11;
            case 11:
                Arco12 arco12= new Arco12();
                return arco12;
            case 12:
                Arco13 arco13= new Arco13();
                return arco13;
            case 13:
                Arco14 arco14= new Arco14();
                return arco14;
            case 14:
                Arco15 arco15= new Arco15();
                return arco15;
            case 15:
                Arco16 arco16= new Arco16();
                return arco16;
            case 16:
                Arco17 arco17= new Arco17();
                return arco17;
            case 17:
                Arco18 arco18= new Arco18();
                return arco18;
            case 18:
                Arco19 arco19= new Arco19();
                return arco19;
            case 19:
                GenerarPDFFragment generarPDFFragment= new GenerarPDFFragment();
                return generarPDFFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }*/
}