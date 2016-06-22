package com.pain.fleetin.brestblacklist.list_fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pain.fleetin.brestblacklist.R;
import com.pain.fleetin.brestblacklist.adapter.BeautyAndHealthAdapter;
import com.pain.fleetin.brestblacklist.database.DBHelper;
import com.pain.fleetin.brestblacklist.model.ModelCard;

import java.util.ArrayList;
import java.util.List;

public class BeautyAndHealthFragment extends CrimeFragment {
    private BeautyAndHealthAdapter beautyAndHealthAdapter;

    private static final int LAYOUT = R.layout.fragment_health_and_beauty;

    public BeautyAndHealthFragment(){

    }


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

    public void addCrime(ModelCard modelCard, boolean saveToDB) {

        int position = -1;
        checkAdapter();
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

        if (saveToDB){
            activity.dbHelper.saveCrime(modelCard);
        }
    }

    @Override
    public void checkAdapter() {
        if (beautyAndHealthAdapter == null){
            beautyAndHealthAdapter = new BeautyAndHealthAdapter(this);
        }
    }

    @Override
    public void addCrimeFromDB() {
        beautyAndHealthAdapter.removeAllItems();
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
        beautyAndHealthAdapter.removeAllItems();
        List<ModelCard> crimes = new ArrayList<>();

        crimes.addAll(activity.dbHelper.query().getCrimes(DBHelper.SELECTION_LIKE_TITLE,
                new String[] {"%" + title + "%"}, DBHelper.CRIME_DATE_COLUMN));

        for (int i = 0; i < crimes.size(); i++){
            addCrime(crimes.get(i), false);
        }
    }
}
