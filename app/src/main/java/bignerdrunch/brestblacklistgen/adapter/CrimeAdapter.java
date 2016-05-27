package bignerdrunch.brestblacklistgen.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bignerdrunch.brestblacklistgen.R;
import bignerdrunch.brestblacklistgen.Utils;
import bignerdrunch.brestblacklistgen.list_fragments.CrimeFragment;
import bignerdrunch.brestblacklistgen.model.Item;
import bignerdrunch.brestblacklistgen.model.ModelCard;

public abstract class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Item> items;

    CrimeFragment crimeFragment;

    public CrimeAdapter(CrimeFragment crimeFragment){
        this.crimeFragment = crimeFragment;
        items = new ArrayList<>();
    }

    public Item getItem(int position){
        return items.get(position);
    }

    public void addItem(Item item){
        items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addItem (int location, Item item){
        items.add(location, item);
        notifyItemInserted(location);
    }

    public void removeItem(int location){
        if (location >= 0 && location <= getItemCount() - 1){
            items.remove(location);
            notifyItemRemoved(location);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class CrimeViewHolder extends RecyclerView.ViewHolder{

        protected TextView title;
        protected TextView date;
        protected CardView cardVew;
        protected TextView hashtag;

        public CrimeViewHolder(View itemView, TextView title, TextView date, CardView cardView, TextView hashtag) {
            super(itemView);
            this.title = title;
            this.date = date;
            this.cardVew = cardView;
            this.hashtag = hashtag;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_crime, parent, false);
        TextView title = (TextView) v.findViewById(R.id.tvCrimeTitle);
        TextView date = (TextView) v.findViewById(R.id.tvCrimeDate);
        CardView cardView = (CardView) v.findViewById(R.id.cardView);
        TextView hashtag = (TextView) v.findViewById(R.id.test_hashtag_assignment);

        return new CrimeViewHolder(v, title, date, cardView, hashtag);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = items.get(position);

        holder.itemView.setEnabled(true);
        ModelCard crime = (ModelCard) item;
        CrimeViewHolder crimeViewHolder = (CrimeViewHolder) holder;

        crimeViewHolder.title.setText(crime.getTitle());

        crimeViewHolder.hashtag.setText(crime.getHashtag());

        if (crime.getDate() != 0) {
            crimeViewHolder.date.setText(Utils.getFullDate(crime.getDate()));
        } else {
            crimeViewHolder.date.setText(null);
        }
    }

    public CrimeFragment getCrimeFragment(){
        return crimeFragment;
    }
}
