package com.pain.fleetin.brestblacklist.list_fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pain.fleetin.brestblacklist.R;
import com.pain.fleetin.brestblacklist.adapter.FunAdapter;
import com.pain.fleetin.brestblacklist.model.ModelCard;

public class FunFragment extends CrimeFragment {

    private FunAdapter funAdapter;

    public FunFragment(){

    }

    private static final int LAYOUT = R.layout.fragment_fun;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(LAYOUT, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvFun);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        funAdapter = new FunAdapter(this);
        recyclerView.setAdapter(funAdapter);

        return rootView;
    }

    public void addCrime(ModelCard modelCard) {

        int position = -1;

        for (int i = 0; i < funAdapter.getItemCount(); i++) {

            ModelCard crime = (ModelCard) funAdapter.getItem(i);
            if (modelCard.getDate() > crime.getDate()) {
                position = i;
                break;
            }
        }

        if (position != -1) {
            funAdapter.addItem(position, modelCard);
        } else {
            funAdapter.addItem(modelCard);
        }
    }

    @Override
    public void checkAdapter() {
        if (adapter == null){
            adapter = new FunAdapter(this);
        }
    }
}