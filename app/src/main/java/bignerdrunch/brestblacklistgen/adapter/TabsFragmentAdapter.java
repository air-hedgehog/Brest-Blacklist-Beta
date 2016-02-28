package bignerdrunch.brestblacklistgen.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.Map;

import bignerdrunch.brestblacklistgen.list_fragments.AbstractTabFragment;
import bignerdrunch.brestblacklistgen.list_fragments.BeautyAndHealthFragment;
import bignerdrunch.brestblacklistgen.list_fragments.BuyFragment;
import bignerdrunch.brestblacklistgen.list_fragments.FunFragment;
import bignerdrunch.brestblacklistgen.list_fragments.PubFragment;
import bignerdrunch.brestblacklistgen.list_fragments.TransportFragment;

public class TabsFragmentAdapter extends FragmentPagerAdapter {


    private Context context;
    private Map<Integer, AbstractTabFragment> tabs;

    public TabsFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        initTabMap(context);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //Получаем value по id табы. В качестве value выступает String
        return tabs.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabMap(Context context) {
        tabs = new HashMap<>();
        tabs.put(0, BeautyAndHealthFragment.getInstance(context));
        tabs.put(1, BuyFragment.getInstance(context));
        tabs.put(2, TransportFragment.getInstance(context));
        tabs.put(3, PubFragment.getInstance(context));
        tabs.put(4, FunFragment.getInstance(context));
    }
}
