package bignerdrunch.brestblacklistgen.list_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bignerdrunch.brestblacklistgen.R;

public class PubFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_pub;

    private RecyclerView rvPub;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(LAYOUT, container, false);

        rvPub = (RecyclerView) rootView.findViewById(R.id.rvPub);
        layoutManager = new LinearLayoutManager(getActivity());

        rvPub.setLayoutManager(layoutManager);

        return rootView;
    }

}