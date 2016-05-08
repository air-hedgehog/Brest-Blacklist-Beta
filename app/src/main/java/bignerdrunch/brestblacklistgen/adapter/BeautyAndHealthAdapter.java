package bignerdrunch.brestblacklistgen.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bignerdrunch.brestblacklistgen.R;
import bignerdrunch.brestblacklistgen.Utils;
import bignerdrunch.brestblacklistgen.model.Item;
import bignerdrunch.brestblacklistgen.model.ModelCrime;

public class BeautyAndHealthAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_CRIME = 0;
    private static final int TYPE_SEPARATOR = 1;

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

    List<Item> items = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case TYPE_CRIME:
                View v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.model_crime, viewGroup, false);
                TextView title = (TextView) v.findViewById(R.id.tvCrimeTitle);
                TextView date = (TextView) v.findViewById(R.id.tvCrimeDate);

                return new CrimeViewHolder(v, title, date);
            /*case TYPE_SEPARATOR:
                break;*/
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Item item = items.get(position);
        if (item.isCrime()){
            viewHolder.itemView.setEnabled(true);
            ModelCrime crime = (ModelCrime) item;
            CrimeViewHolder crimeViewHolder = (CrimeViewHolder) viewHolder;

            crimeViewHolder.title.setText(crime.getTitle());

            if (crime.getDate() != 0){
                crimeViewHolder.date.setText(Utils.getFullDate(crime.getDate()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).isCrime()) return TYPE_CRIME;
        else return TYPE_SEPARATOR;
    }

    private class CrimeViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView date;

        public CrimeViewHolder(View itemView, TextView title, TextView date) {
            super(itemView);
            this.title = title;
            this.date = date;
        }
    }
}
