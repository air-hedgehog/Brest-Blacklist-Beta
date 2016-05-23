package bignerdrunch.brestblacklistgen.list_fragments;

import android.app.Fragment;
import android.support.v7.widget.RecyclerView;

import bignerdrunch.brestblacklistgen.adapter.BeautyAndHealthAdapter;
import bignerdrunch.brestblacklistgen.model.ModelCard;

public abstract class CrimeFragment extends Fragment {

    protected BeautyAndHealthAdapter adapter;

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;

    public void addCrime(ModelCard newCrime) {
        int position = -1;

        for (int i = 0; i < adapter.getItemCount(); i++) {

            ModelCard crime = (ModelCard) adapter.getItem(i);
            if (newCrime.getDate() > crime.getDate()) {
                position = i;
                break;
            }
        }

        if (position != -1) {
            adapter.addItem(position, newCrime);
        } else {
            adapter.addItem(newCrime);
        }
    }
}
