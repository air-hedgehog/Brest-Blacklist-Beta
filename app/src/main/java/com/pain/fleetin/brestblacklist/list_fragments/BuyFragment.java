package com.pain.fleetin.brestblacklist.list_fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pain.fleetin.brestblacklist.R;
import com.pain.fleetin.brestblacklist.adapter.BuyAdapter;
import com.pain.fleetin.brestblacklist.model.ModelCard;

public class BuyFragment extends CrimeFragment {

    private BuyAdapter buyAdapter;

    public BuyFragment(){

    }

    private static final int LAYOUT = R.layout.fragment_buy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(LAYOUT, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvBuy);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        buyAdapter = new BuyAdapter(this);
        recyclerView.setAdapter(buyAdapter);

        return rootView;
    }

    public void addCrime(ModelCard modelCard) {

        int position = -1;

        for (int i = 0; i < buyAdapter.getItemCount(); i++) {

            ModelCard crime = (ModelCard) buyAdapter.getItem(i);
            if (modelCard.getDate() > crime.getDate()) {
                position = i;
                break;
            }
        }

        if (position != -1) {
            buyAdapter.addItem(position, modelCard);
        } else {
            buyAdapter.addItem(modelCard);
        }
    }

    @Override
    public void checkAdapter() {
        if (adapter == null){
            adapter = new BuyAdapter(this);
        }
    }
}