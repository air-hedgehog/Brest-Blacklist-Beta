package com.pain.fleetin.brestblacklist.list_fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pain.fleetin.brestblacklist.R;
import com.pain.fleetin.brestblacklist.adapter.BeautyAndHealthAdapter;
import com.pain.fleetin.brestblacklist.model.ModelCard;

public class BeautyAndHealthFragment extends CrimeFragment {

    private BeautyAndHealthAdapter beautyAndHealthAdapter;

    private static final int LAYOUT = R.layout.fragment_health_and_beauty;

    public BeautyAndHealthFragment(){

    }

    private ModelCard modelCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(LAYOUT, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvBeauty);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        beautyAndHealthAdapter = new BeautyAndHealthAdapter(this);
        recyclerView.setAdapter(beautyAndHealthAdapter);

        return rootView;
    }

    public void addCrime(ModelCard modelCard) {

        int position = -1;

        for (int i = 0; i < beautyAndHealthAdapter.getItemCount(); i++) {

            ModelCard crime = (ModelCard) beautyAndHealthAdapter.getItem(i);
            if (modelCard.getDate() > crime.getDate()) {
                position = i;
                break;
            }
        }

        if (position != -1) {
            beautyAndHealthAdapter.addItem(position, modelCard);
        } else {
            beautyAndHealthAdapter.addItem(modelCard);
        }
    }

    @Override
    public void checkAdapter() {
        if (adapter == null){
            adapter = new BeautyAndHealthAdapter(this);
        }
    }
}
