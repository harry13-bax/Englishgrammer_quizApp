package com.ultimate.englishgrammarquiz.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.ultimate.englishgrammarquiz.Constant;
import com.ultimate.englishgrammarquiz.Database.Databases;
import com.ultimate.englishgrammarquiz.adapter.LevelAdapter;
import com.ultimate.englishgrammarquiz.model.CategoryModel;
import com.ultimate.englishgrammarquiz.model.LevelModel;
import com.ultimate.englishgrammarquiz.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvCategory;
    ListView lvTopic;
    ArrayList<CategoryModel> catelogyModels;
    ArrayList<LevelModel> levelModels = new ArrayList<>();
    Databases databases;
    int selectedLevel = 1;
    LevelAdapter topicAdapter;
    int selectedCate = 1;
    private TextView ToolbarTitle;
    private Toolbar toolbar;
    private long exitTime = 0;

    private int counter = 0;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);
        databases = new Databases(this);
        databases.openDataBase();

        setupToolbar();

        levelModels = databases.getTopics(selectedCate);
        lvTopic = findViewById(R.id.list_viewTopic);
        topicAdapter = new LevelAdapter(this, R.layout.item_topic, levelModels);
        lvTopic.setAdapter(topicAdapter);

        lvTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                intent.putExtra(QuestionActivity.KEY_TITLE, String.valueOf(levelModels.get(i)));
                intent.putExtra("ID", levelModels.get(i).getId());
                intent.putExtra("level_score_id", levelModels.get(i).getLevelscore_id());
                intent.putExtra("score", levelModels.get(i).getScore());
                selectedLevel = i;
                startActivityForResult(intent, 0);
            }
        });
    }

    private void setupToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For Android 11 and above, use the new InsetsController API
            WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars());
            }
        } else {
            // For lower versions, continue using the FLAG_FULLSCREEN flag
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        toolbar = findViewById(R.id.toolbar);
        ToolbarTitle = findViewById(R.id.toolbar_title);
        ToolbarTitle.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
        setSupportActionBar(toolbar);

        ToolbarTitle.setText(R.string.app_name);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setElevation(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Any necessary code for resuming activity (e.g., refresh data)
    }
}
