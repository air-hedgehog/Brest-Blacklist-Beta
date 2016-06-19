package com.pain.fleetin.brestblacklist.list_fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pain.fleetin.brestblacklist.R;
import com.pain.fleetin.brestblacklist.adapter.PubAdapter;
import com.pain.fleetin.brestblacklist.database.DBHelper;
import com.pain.fleetin.brestblacklist.model.ModelCard;

import java.util.ArrayList;
import java.util.List;

public class PubFragment extends CrimeFragment {

    private PubAdapter pubAdapter;

    public PubFragment(){

    }

    private static final int LAYOUT = R.layout.fragment_pub;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(LAYOUT, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvPub);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        pubAdapter = new PubAdapter(this);
        recyclerView.setAdapter(pubAdapter);

        return rootView;
    }

    public void addCrime(ModelCard modelCard, boolean saveToDB) {

        int position = -1;
        checkAdapter();
        for (int i = 0; i < pubAdapter.getItemCount(); i++) {

            ModelCard crime = (ModelCard) pubAdapter.getItem(i);
            if (modelCard.getDate() > crime.getDate()) {
                position = i;
                break;
            }
        }

        if (position != -1) {
            pubAdapter.addItem(position, modelCard);
        } else {
            pubAdapter.addItem(modelCard);
        }

        if (saveToDB){
            activity.dbHelper.saveCrime(modelCard);
        }
    }

    @Override
    public void checkAdapter() {
        if (adapter == null){
            adapter = new PubAdapter(this);
        }
    }

    @Override
    public void addCrimeFromDB() {
        checkAdapter();
        List<ModelCard> crimes = new ArrayList<>();
        crimes.addAll(activity
                .dbHelper
                .query()
                .getCrimes(DBHelper.CRIME_DATE_COLUMN));

        for (int i = 0; i < crimes.size(); i++){
            addCrime(crimes.get(i), false);
        }
    }

    @Override
    public void findCrimes(String title) {

    }
}