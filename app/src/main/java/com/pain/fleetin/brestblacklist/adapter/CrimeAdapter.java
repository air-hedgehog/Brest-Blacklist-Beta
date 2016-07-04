package com.pain.fleetin.brestblacklist.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public abstract class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Item> items;

    CrimeFragment crimeFragment;

    public ModelCard modelCard;
    private Context context;

    public CrimeAdapter(CrimeFragment crimeFragment, Context context) {
        this.context = context;
        this.crimeFragment = crimeFragment;
        items = new ArrayList<>();
    }

    public Item getItem(int position) {
        return items.get(position);
    }

    public void addItem(Item item) {
        items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addItem(int location, Item item) {
        items.add(location, item);
        notifyItemInserted(location);
    }

    public void removeItem(int location) {
        if (location >= 0 && location <= getItemCount() - 1) {
            items.remove(location);
            notifyItemRemoved(location);
        }
    }

    public void removeAllItems() {
        for (int i =0 ; i < getItemCount() - 1; i++){
            removeItem(i);
        }
        if (getItemCount() != 0) {
            items = new ArrayList<>();
            notifyDataSetChanged();
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class CrimeViewHolder extends RecyclerView.ViewHolder {

        protected TextView title;
        protected TextView date;
        protected CardView cardView;
        protected ImageView photoCard;

        public CrimeViewHolder(View itemView, TextView title, TextView date, CardView cardView,
                               ImageView photoCard) {
            super(itemView);
            this.title = title;
            this.date = date;
            this.cardView = cardView;
            this.photoCard = photoCard;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_card, parent, false);
        TextView title = (TextView) v.findViewById(R.id.tvCrimeTitle);
        TextView date = (TextView) v.findViewById(R.id.tvCrimeDate);
        CardView cardView = (CardView) v.findViewById(R.id.cardView);
        ImageView photoCard = (ImageView) v.findViewById(R.id.card_image_view);

        return new CrimeViewHolder(v, title, date, cardView, photoCard);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = items.get(position);

        CrimeViewHolder crimeViewHolder = (CrimeViewHolder) holder;
        holder.itemView.setEnabled(true);
        modelCard = (ModelCard) item;

        crimeViewHolder.title.setText(modelCard.getTitle());
        crimeViewHolder.date.setText(Utils.getFullDate(modelCard.getDate()));

        if (modelCard.getPictureURL() == null) {
            crimeViewHolder.photoCard.setVisibility(View.GONE);
        } else {
            crimeViewHolder.photoCard.setVisibility(View.VISIBLE);
            Context context = ((CrimeViewHolder) holder).photoCard.getContext();
            Picasso.with(context)
                    .load(modelCard.getPictureURL())
                    .placeholder(R.drawable.ic_download_grey600_48dp)
                    .error(R.drawable.ic_error_outline_black_48dp)
                    .into(crimeViewHolder.photoCard);
        }
        final String postId;
        if (modelCard.getPostId() != 0){
            postId = String.valueOf(modelCard.getPostId());
        } else {
            postId = null;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://vk.com/wall-%d_%s",
                        Utils.BREST_BLACKLIST, postId)));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });


    }



    public CrimeFragment getCrimeFragment() {
        return crimeFragment;
    }
}
