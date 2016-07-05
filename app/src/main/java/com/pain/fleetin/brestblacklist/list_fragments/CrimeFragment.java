package com.pain.fleetin.brestblacklist.list_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.pain.fleetin.brestblacklist.MainActivity;
import com.pain.fleetin.brestblacklist.adapter.CrimeAdapter;
import com.pain.fleetin.brestblacklist.adapter.MainFragmentAdapter;
import com.pain.fleetin.brestblacklist.model.ModelCard;

public abstract class CrimeFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;

    protected CrimeAdapter adapter;
    public MainActivity activity;
    protected ModelCard modelCard;
    private MainFragmentAdapter mainFragmentAdapter;
    private MainFragment mainFragment;

    public abstract void checkAdapter();

    public abstract void addCrimeFromDB();

    public abstract void findCrimes(String title);



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainFragment = new MainFragment();
        mainFragmentAdapter = new MainFragmentAdapter(mainFragment, getActivity().getApplicationContext());

        if (getActivity() != null){
            activity = (MainActivity) getActivity();
        }


    }
}
