package bignerdrunch.brestblacklistgen.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bignerdrunch.brestblacklistgen.list_fragments.CrimeFragment;
import bignerdrunch.brestblacklistgen.model.Item;
import de.hdodenhof.circleimageview.CircleImageView;

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

        public CrimeViewHolder(View itemView, TextView title, TextView date, CardView cardView) {
            super(itemView);
            this.title = title;
            this.date = date;
            this.cardVew = cardView;
        }
    }

    public CrimeFragment getCrimeFragment(){
        return crimeFragment;
    }
}
