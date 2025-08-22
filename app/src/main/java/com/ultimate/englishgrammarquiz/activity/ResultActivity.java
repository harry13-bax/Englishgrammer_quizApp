package com.ultimate.englishgrammarquiz.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ayoubfletcher.consentsdk.ConsentSDK;
import com.ultimate.englishgrammarquiz.Database.Databases;
import com.ultimate.englishgrammarquiz.adapter.ResultAdapter;
import com.ultimate.englishgrammarquiz.model.ResultModel;
import com.ultimate.englishgrammarquiz.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Legandan on 27/02/2021.
 */

public class ResultActivity extends AppCompatActivity {
    ListView lvResult;
    int id;
    TextView tvImage;
    ImageButton imResult;
    ArrayList<ResultModel> resultModels;
    Databases databases;
    HashMap<Integer, String> map;
    int score=0;
    int lsd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_main);
        databases = new Databases(this);
        databases.openDataBase();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Your Test Result ");

        tvImage = findViewById(R.id.tv_result);

        id = getIntent().getIntExtra("ID", 0);
        resultModels = databases.getResult(id);
        map = (HashMap<Integer, String>) getIntent().getSerializableExtra("KQ");
        lsd = getIntent().getIntExtra("lsd", 0);
        lvResult = findViewById(R.id.listResult);

        for (int i = 0; i < resultModels.size(); i++) {
            resultModels.get(i).setYourAnswer(map.get(i));
            if (resultModels.get(i).getKQ()) score++;
        }

        ResultAdapter resultAdapter = new ResultAdapter(this, R.layout.item_result, resultModels);
        if (lsd == 0) {
            databases.insertToDB(id, score);
        } else {
            int diem = getIntent().getIntExtra("score", 0);
            if (score > diem) databases.updateLevelScore(lsd, id, score);
        }
        tvImage.setText("Correct: " + score + "/" + resultModels.size());
        lvResult.setAdapter(resultAdapter);
        imResult = findViewById(R.id.ig_result);
        imResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
