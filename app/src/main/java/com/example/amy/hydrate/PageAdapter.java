package com.example.amy.hydrate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter (FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        switch (arg0) {
            case 0:
                return new frag_basement_floor();
            case 1:
                return new frag_first_floor();
            case 2:
                return new frag_second_floor();
            case 3:
                return new frag_third_floor();
            case 4:
                return new frag_fourth_floor();
            case 5:
                return new frag_fifth_floor();
            case 6:
                return new frag_sixth_floor();
            case 7:
                return new frag_seventh_floor();
            case 8:
                return new frag_eighth_floor();
            case 9:
                return new frag_ninth_floor();

            default:
                break;
        }
        return null;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Basement";
            case 1:
                return "First Floor";
            case 2:
                return "Second Floor";
            case 3:
                return "Third Floor";
            case 4:
                return "Fourth Floor";
            case 5:
                return "Fifth Floor";
            case 6:
                return "Sixth Floor";
            case 7:
                return "seventh Floor";
            case 8:
                return "Eighth Floor";
            case 9:
                return "Ninth Floor";

        }

        return null;
    }
        @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 10   ;
    }


}
