package com.pyconindia.pycon.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;

public class TabPagerAdapter extends PagerAdapter {
    public TabPagerAdapter() {
        super();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return false;
    }


    }