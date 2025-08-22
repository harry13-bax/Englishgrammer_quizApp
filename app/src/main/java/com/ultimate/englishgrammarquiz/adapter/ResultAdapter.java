package com.ultimate.englishgrammarquiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ultimate.englishgrammarquiz.model.ResultModel;
import com.ultimate.englishgrammarquiz.R;

import java.util.ArrayList;

/**
 * Created by Legandan on 27/02/2021.
 */

public class ResultAdapter extends ArrayAdapter<ResultModel>{
    private Context context;
    private int resource;
    private ArrayList<ResultModel> resultModels;
    public ResultAdapter(@NonNull Context context, int resource, ArrayList<ResultModel> resultModels1) {
        super(context, resource,resultModels1);
        this.context = context;
        this.resource = resource;
        this.resultModels = resultModels1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(resource,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvResult1 = convertView.findViewById(R.id.tv_result1);
            viewHolder.tvResult2 = convertView.findViewById(R.id.tv_result2);
            viewHolder.tvResult3 = convertView.findViewById(R.id.tv_result3);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ResultModel resultModel = resultModels.get(position);
        //System.out.println(resultModel);
        viewHolder.tvResult1.setText(resultModel.getQuestion());
        viewHolder.tvResult2.setText("Your answer: "+ (resultModel.getYourAnswer() == null ? "" : resultModel.getYourAnswer()));
        viewHolder.tvResult3.setText("Correct answer: "+resultModel.getCorrectAnswer());
        return convertView;
    }
    public class ViewHolder{
        TextView tvResult1,tvResult2,tvResult3;
    }
}
