package bignerdrunch.brestblacklistgen.list_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bignerdrunch.brestblacklistgen.R;

public class FunFragment extends AbstractTabFragment {

    private static final int LAYOUT = R.layout.fragment_health_and_beauty;

    public static BeautyAndHealthFragment getInstance(Context context) {
        Bundle args = new Bundle();
        BeautyAndHealthFragment fragment = new BeautyAndHealthFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_fun));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}