package com.pain.fleetin.brestblacklist.new_crime_dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class DialogGridAdapter extends BaseAdapter{

    private Context context;
    private Integer[] pics;

    public DialogGridAdapter(Context c) {
        context = c;
    }

    public int getCount() {
        return pics.length;
    }

    public Object getItem(int position) {return null;}

    public long getItemId(int position) {return 0;}

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            //You can set some params here
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(pics[position]);
        return imageView;
    }

    public void SetImages(Integer[] id){
        pics = id.clone();
    }

}
