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
import bignerdrunch.brestblacklistgen.adapter.BeautyAndHealthAdapter;
import bignerdrunch.brestblacklistgen.model.ModelCrime;

public class BeautyAndHealthFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_health_and_beauty;

    private BeautyAndHealthAdapter adapter;

    private RecyclerView rvBeauty;
    private RecyclerView.LayoutManager layoutManager;

    public BeautyAndHealthFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(LAYOUT, container, false);
        rvBeauty = (RecyclerView) rootView.findViewById(R.id.rvBeauty);

        layoutManager = new LinearLayoutManager(getActivity());
        rvBeauty.setLayoutManager(layoutManager);

        adapter = new BeautyAndHealthAdapter();
        rvBeauty.setAdapter(adapter);

        return rootView;
    }

    public void addCrime (ModelCrime newCrime){
        int position = -1;

        for (int i = 0; i < adapter.getItemCount(); i++){
            if (adapter.getItem(i).isCrime()){
                ModelCrime crime = (ModelCrime) adapter.getItem(i);
                if (newCrime.getDate() < crime.getDate()){
                    position = i;
                    break;
                }
            }
        }

        if (position != -1){
            adapter.addItem(position, newCrime);
        } else {
            adapter.addItem(newCrime);
        }
    }

}
