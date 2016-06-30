package com.pain.fleetin.brestblacklist.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.pain.fleetin.brestblacklist.list_fragments.BeautyAndHealthFragment;

public class TabAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public static final int BEAUTY_AND_HEALTH_FRAGMENT_POSITION = 0;
    public static final int BUY_FRAGMENT_POSITION = 1;
    public static final int FUN_FRAGMENT_POSITION = 2;
    public static final int PUB_FRAGMENT_POSITION = 3;
    public static final int TRANSPORT_FRAGMENT_POSITION = 4;

    private BeautyAndHealthFragment beautyAndHealthFragment;

    public TabAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;

        beautyAndHealthFragment = new BeautyAndHealthFragment();

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case BEAUTY_AND_HEALTH_FRAGMENT_POSITION:
                return beautyAndHealthFragment;
            case BUY_FRAGMENT_POSITION:
                return null;
            case FUN_FRAGMENT_POSITION:
                return null;
            case PUB_FRAGMENT_POSITION:
                return null;
            case TRANSPORT_FRAGMENT_POSITION:
                return null;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
