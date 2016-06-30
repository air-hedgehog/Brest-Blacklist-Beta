package com.pain.fleetin.brestblacklist.list_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.pain.fleetin.brestblacklist.MainActivity;
import com.pain.fleetin.brestblacklist.R;
import com.pain.fleetin.brestblacklist.adapter.BeautyAndHealthAdapter;
import com.pain.fleetin.brestblacklist.adapter.CrimeAdapter;
import com.pain.fleetin.brestblacklist.model.ModelCard;

public abstract class CrimeFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;

    protected CrimeAdapter adapter;
    public MainActivity activity;
    protected ModelCard modelCard;
    private BeautyAndHealthAdapter beautyAndHealthAdapter;
    private BeautyAndHealthFragment beautyAndHealthFragment;
    public abstract void checkAdapter();

    public abstract void addCrimeFromDB();

    public abstract void findCrimes(String title);

    public String hashtag_beauty;
    public String hashtag_buy;
    public String hashtag_fun;

    public String hashtag_pub;

    public String hashtag_transport;

    public String hashtag;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        hashtag_beauty = getResources().getString(R.string.hashtag_beauty);
        hashtag_buy = getResources().getString(R.string.hashtag_buy);
        hashtag_fun = getResources().getString(R.string.hashtag_fun);
        hashtag_pub = getResources().getString(R.string.hashtag_pub);
        hashtag_transport = getResources().getString(R.string.hashtag_transport);

        beautyAndHealthFragment = new BeautyAndHealthFragment();
        beautyAndHealthAdapter = new BeautyAndHealthAdapter(beautyAndHealthFragment, getActivity().getApplicationContext());

        if (getActivity() != null){
            activity = (MainActivity) getActivity();
        }


    }
}
