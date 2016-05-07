package bignerdrunch.brestblacklistgen.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import bignerdrunch.brestblacklistgen.list_fragments.BeautyAndHealthFragment;
import bignerdrunch.brestblacklistgen.list_fragments.BuyFragment;
import bignerdrunch.brestblacklistgen.list_fragments.FunFragment;
import bignerdrunch.brestblacklistgen.list_fragments.PubFragment;
import bignerdrunch.brestblacklistgen.list_fragments.TransportFragment;

public class TabsFragmentAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public TabsFragmentAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BeautyAndHealthFragment();
            case 1:
                return new BuyFragment();
            case 2:
                return new FunFragment();
            case 3:
                return new PubFragment();
            case 4:
                return  new TransportFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
