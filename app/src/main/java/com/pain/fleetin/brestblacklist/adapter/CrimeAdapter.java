package com.pain.fleetin.brestblacklist.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pain.fleetin.brestblacklist.R;
import com.pain.fleetin.brestblacklist.Utils;
import com.pain.fleetin.brestblacklist.list_fragments.CrimeFragment;
import com.pain.fleetin.brestblacklist.model.Item;
import com.pain.fleetin.brestblacklist.model.ModelCard;

import java.util.ArrayList;
import java.util.List;

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

    public void removeAllItems(){
        if (getItemCount() != 0){
            items = new ArrayList<>();
            notifyDataSetChanged();
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
        protected ImageView photoCard;

        public CrimeViewHolder(View itemView, TextView title, TextView date, CardView cardView, TextView hashtag, ImageView photoCard) {
            super(itemView);
            this.title = title;
            this.date = date;
            this.cardVew = cardView;
            this.hashtag = hashtag;
            this.photoCard = photoCard;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_card, parent, false);
        TextView title = (TextView) v.findViewById(R.id.tvCrimeTitle);
        TextView date = (TextView) v.findViewById(R.id.tvCrimeDate);
        CardView cardView = (CardView) v.findViewById(R.id.cardView);
        TextView hashtag = (TextView) v.findViewById(R.id.test_hashtag_assignment);
        ImageView photoCard = (ImageView) v.findViewById(R.id.card_image_view);

        return new CrimeViewHolder(v, title, date, cardView, hashtag, photoCard);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = items.get(position);

        holder.itemView.setEnabled(true);
        ModelCard modelCard = (ModelCard) item;
        CrimeViewHolder crimeViewHolder = (CrimeViewHolder) holder;

        crimeViewHolder.title.setText(modelCard.getTitle());

        crimeViewHolder.hashtag.setText(modelCard.getHashtag());

        if (modelCard.getDate() != 0) {
            crimeViewHolder.date.setText(Utils.getFullDate(modelCard.getDate()));
        } else {
            crimeViewHolder.date.setText(null);
        }

        if (modelCard.getImageBitmap() != null){
            crimeViewHolder.photoCard.setImageBitmap(modelCard.getImageBitmap());
        } else {
            crimeViewHolder.photoCard.setVisibility(View.GONE);
        }
    }

    public CrimeFragment getCrimeFragment(){
        return crimeFragment;
    }
}
