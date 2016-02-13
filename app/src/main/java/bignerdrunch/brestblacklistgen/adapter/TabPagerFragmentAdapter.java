package bignerdrunch.brestblacklistgen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import bignerdrunch.brestblacklistgen.list_fragments.BeautyAndHealthFragment;

public class TabPagerFragmentAdapter extends FragmentPagerAdapter {

    private String[] tabs;

    public TabPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
        tabs = new String[] {
                "Красота и здоровье",
                "Покупки",
                "Транспорт",
                "Кафе и бары",
                "Развлечения"
        };
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return BeautyAndHealthFragment.getInstance();
            case 1:
                return BeautyAndHealthFragment.getInstance();
            case 2:
                return BeautyAndHealthFragment.getInstance();
            case 3:
                return BeautyAndHealthFragment.getInstance();
            case 4:
                return BeautyAndHealthFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
