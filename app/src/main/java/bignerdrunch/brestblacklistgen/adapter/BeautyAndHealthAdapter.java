package bignerdrunch.brestblacklistgen.adapter;

import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bignerdrunch.brestblacklistgen.R;
import bignerdrunch.brestblacklistgen.Utils;
import bignerdrunch.brestblacklistgen.list_fragments.BeautyAndHealthFragment;
import bignerdrunch.brestblacklistgen.model.Item;
import bignerdrunch.brestblacklistgen.model.ModelCrime;

public class BeautyAndHealthAdapter extends CrimeAdapter {

    public BeautyAndHealthAdapter(BeautyAndHealthFragment crimeFragment) {
        super(crimeFragment);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_crime, viewGroup, false);
        TextView title = (TextView) v.findViewById(R.id.tvCrimeTitle);
        TextView date = (TextView) v.findViewById(R.id.tvCrimeDate);
        CardView cardView = (CardView) v.findViewById(R.id.cardView);

        return new CrimeViewHolder(v, title, date, cardView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Item item = items.get(position);

        viewHolder.itemView.setEnabled(true);
        ModelCrime crime = (ModelCrime) item;
        CrimeViewHolder crimeViewHolder = (CrimeViewHolder) viewHolder;

        crimeViewHolder.title.setText(crime.getTitle());

        if (crime.getDate() != 0) {
            crimeViewHolder.date.setText(Utils.getFullDate(crime.getDate()));
        } else {
            crimeViewHolder.date.setText(null);
        }

    }
}
