package bignerdrunch.brestblacklistgen.list_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bignerdrunch.brestblacklistgen.R;

public class TransportFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_transport;

    private RecyclerView rvTransport;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(LAYOUT, container, false);

        rvTransport = (RecyclerView) rootView.findViewById(R.id.rvTransport);
        layoutManager = new LinearLayoutManager(getActivity());

        rvTransport.setLayoutManager(layoutManager);

        return inflater.inflate(LAYOUT, container, false);
    }

}