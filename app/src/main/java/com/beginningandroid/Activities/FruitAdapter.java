package com.beginningandroid.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.beginningandroid.R;

/**
 * Created by LingChen on 15/2/25.
 */
public class FruitAdapter extends ArrayAdapter<Fruit> {
    private int resID;

    public FruitAdapter (Context context, int resID, List<Fruit> data){
        super(context, resID, data);
        this.resID = resID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit fruit = getItem(position);
        View view;
        SubView subView;
        if (null != convertView) {
            view = convertView;
            subView = (SubView) view.getTag();
        }
        else {
            view = LayoutInflater.from(getContext()).inflate(resID, null);
            subView = new SubView();
            subView.tvName = (TextView)view.findViewById(R.id.tvFruit);
            subView.imgPic = (ImageView)view.findViewById(R.id.imgFruit);
            view.setTag(subView);
        }

        subView.tvName.setText(fruit.getName());
        subView.imgPic.setImageResource(fruit.getPic());

        return view;
    }

    class SubView
    {
        ImageView imgPic;
        TextView tvName;
    }
}
