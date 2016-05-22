package bignerdrunch.brestblacklistgen.list_fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bignerdrunch.brestblacklistgen.R;
import bignerdrunch.brestblacklistgen.adapter.BeautyAndHealthAdapter;

public class BeautyAndHealthFragment extends CrimeFragment {

    private static final int LAYOUT = R.layout.fragment_health_and_beauty;

    public BeautyAndHealthFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(LAYOUT, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvBeauty);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BeautyAndHealthAdapter(this);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

}
