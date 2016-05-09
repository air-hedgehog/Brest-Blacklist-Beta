package bignerdrunch.brestblacklistgen.list_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bignerdrunch.brestblacklistgen.R;

public class PubFragment extends CrimeFragment {

    private static final int LAYOUT = R.layout.fragment_pub;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(LAYOUT, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvPub);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        return rootView;
    }

}