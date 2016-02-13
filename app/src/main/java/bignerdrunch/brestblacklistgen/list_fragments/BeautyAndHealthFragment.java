package bignerdrunch.brestblacklistgen.list_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bignerdrunch.brestblacklistgen.R;

public class BeautyAndHealthFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_health_and_beauty;

    private View view;

    public static BeautyAndHealthFragment getInstance() {
        Bundle args = new Bundle();
        BeautyAndHealthFragment fragment = new BeautyAndHealthFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }
}
