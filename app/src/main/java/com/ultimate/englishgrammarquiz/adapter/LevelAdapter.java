package com.ultimate.englishgrammarquiz.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ultimate.englishgrammarquiz.Database.Databases;
import com.ultimate.englishgrammarquiz.model.LevelModel;
import com.ultimate.englishgrammarquiz.R;

import java.util.ArrayList;

/**
 * Created by Legandan on 27/02/2021.
 */

public class LevelAdapter extends ArrayAdapter <LevelModel>{
    private Context context;
    private int resource;
    private ArrayList<LevelModel> levelModels;
    Databases databases;
    private RatingBar ratingBar;
    int bestScore;
    public LevelAdapter(@NonNull Context context, int resource, ArrayList<LevelModel> levelModels1) {
        super(context, resource,levelModels1);
        this.context = context;
        this.resource = resource;
        this.levelModels = levelModels1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(resource,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tv_topic = convertView.findViewById(R.id.tv_topic);
            viewHolder.tvSTT = convertView.findViewById(R.id.tvSTT);
            viewHolder.ratingBar = convertView.findViewById(R.id.rating);
            convertView.setTag(viewHolder);
        }else {
            viewHolder =(ViewHolder) convertView.getTag();
        }
        LevelModel levelModel = levelModels.get(position);
        viewHolder.tv_topic.setText(levelModel.getTitleTopic());
        viewHolder.tvSTT.setText(String.valueOf(position+1));
        viewHolder.ratingBar.setStepSize((float)(5/levelModel.getSl()));
        viewHolder.ratingBar.setRating(((float) levelModel.getScore()/levelModel.getSl())*5);

        return convertView;
    }
    public class ViewHolder{
        TextView tv_topic;
        TextView tvSTT;
        RatingBar ratingBar;
    }
}
