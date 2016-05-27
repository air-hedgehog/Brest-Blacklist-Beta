package bignerdrunch.brestblacklistgen.list_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import bignerdrunch.brestblacklistgen.MainActivity;
import bignerdrunch.brestblacklistgen.adapter.CrimeAdapter;

public abstract class CrimeFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;

    protected CrimeAdapter adapter;
    public MainActivity activity;

    public abstract void checkAdapter();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null){
            activity = (MainActivity) getActivity();
        }
    }
}
