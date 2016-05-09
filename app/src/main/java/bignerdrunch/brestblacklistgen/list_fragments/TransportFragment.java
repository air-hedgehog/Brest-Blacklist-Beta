package bignerdrunch.brestblacklistgen.list_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bignerdrunch.brestblacklistgen.R;

public class TransportFragment extends CrimeFragment {

    private static final int LAYOUT = R.layout.fragment_transport;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(LAYOUT, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvTransport);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        return inflater.inflate(LAYOUT, container, false);
    }

}