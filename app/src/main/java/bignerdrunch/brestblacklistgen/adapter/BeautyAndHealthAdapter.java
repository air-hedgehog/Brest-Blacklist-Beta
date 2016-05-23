package bignerdrunch.brestblacklistgen.adapter;

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
import bignerdrunch.brestblacklistgen.model.ModelCard;

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
        TextView hashtag = (TextView) v.findViewById(R.id.test_hashtag_assignment);

        return new CrimeViewHolder(v, title, date, cardView, hashtag);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Item item = items.get(position);

        viewHolder.itemView.setEnabled(true);
        ModelCard crime = (ModelCard) item;
        CrimeViewHolder crimeViewHolder = (CrimeViewHolder) viewHolder;

        crimeViewHolder.title.setText(crime.getTitle());

        crimeViewHolder.hashtag.setText(crime.getHashtag());

        if (crime.getDate() != 0) {
            crimeViewHolder.date.setText(Utils.getFullDate(crime.getDate()));
        } else {
            crimeViewHolder.date.setText(null);
        }

    }
}
