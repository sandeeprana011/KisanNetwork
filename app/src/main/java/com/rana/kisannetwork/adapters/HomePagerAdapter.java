package com.rana.kisannetwork.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rana.kisannetwork.fragments.FirstTab;
import com.rana.kisannetwork.fragments.SecondTab;

/**
 * Created by sandeeprana on 04/10/16.
 * License is only applicable to individuals and non-profits
 * and that any for-profit company must
 * purchase a different license, and create
 * a second commercial license of your
 * choosing for companies
 */

public class HomePagerAdapter extends FragmentPagerAdapter {
    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new FirstTab();
//            Bundle args = new Bundle();

        switch (i) {
            case 0:
                fragment = new FirstTab();
                return fragment;
            case 1:
                fragment = new SecondTab();
                return fragment;
            default:
                return fragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return FirstTab.TITLE;
            case 1:
                return SecondTab.TITLE;
            default:
                return "Unknown";
        }
    }
}
