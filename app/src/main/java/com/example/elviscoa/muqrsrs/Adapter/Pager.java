package com.example.elviscoa.muqrsrs.Adapter;

/**
 * Created by elvis on 09/07/16.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.elviscoa.muqrsrs.Class.Six_X_Trilogy;
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
}