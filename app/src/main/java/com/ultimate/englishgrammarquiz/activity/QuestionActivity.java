package com.ultimate.englishgrammarquiz.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.content.res.ColorStateList;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ultimate.englishgrammarquiz.Constant;
import com.ultimate.englishgrammarquiz.Database.Databases;
import com.ultimate.englishgrammarquiz.dialog.EnglishDialog;
import com.ultimate.englishgrammarquiz.model.OptionModel;
import com.ultimate.englishgrammarquiz.model.QuestionModel;
import com.ultimate.englishgrammarquiz.model.ResultModel;
import com.ultimate.englishgrammarquiz.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionActivity extends AppCompatActivity implements EnglishDialog.EnglishDialogListener {
    final public static String KEY_TITLE = "TitleQuestionActivity";
    Button btnNext;
    Button btnPre;
    TextView tvPage;
    TextView tvQuestion;
    RadioGroup radioGroups;
    ArrayList<OptionModel> optionModels;
    ArrayList<ResultModel> correctA;
    private TextView ToolbarTitle;
    private Toolbar toolbar;
    private int counter = 0;
    int id;
    int level_score_id;
    Databases databases;
    HashMap<Integer, String> map = new HashMap<>();

    int index = 0;
    int score = 0;
    EnglishDialog englishDialog;
    List<QuestionModel> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_main);
        databases = new Databases(this);
        databases.openDataBase();

        setupToolbar();

        correctA = new ArrayList<>();
        btnNext = findViewById(R.id.btnNext);
        btnPre = findViewById(R.id.btnPrevious);
        tvPage = findViewById(R.id.txtPage);
        tvQuestion = findViewById(R.id.txtQuestion);
        btnPre.setEnabled(false);
        radioGroups = findViewById(R.id.groupChoice);

        radioGroups.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rd = findViewById(radioGroup.getCheckedRadioButtonId());

                if (rd != null) {
                    map.put(index, rd.getText().toString());
                }
            }
        });

        id = getIntent().getIntExtra("ID", 0);
        level_score_id = getIntent().getIntExtra("level_score_id", 0);

        prepareData(id);
        addEvent();
        correctA = databases.getResult(id);

        englishDialog = new EnglishDialog();
        englishDialog.setCancelable(false);
        englishDialog.setEnglishDialogListener(this);
    }

    private void setupToolbar() {
        if (Build.VERSION.SDK_INT <= 22) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        toolbar = findViewById(R.id.toolbar);
        ToolbarTitle = findViewById(R.id.toolbar_title);
        ToolbarTitle.setTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        ToolbarTitle.setText(getIntent().getStringExtra(KEY_TITLE));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    private void addEvent() {

        btnPre.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                Typeface typeface = Typeface.createFromAsset(getAssets(), "font1.otf");
                radioGroups.removeAllViews();
                index--;
                tvPage.setText((index + 1) + "/" + listData.size());
                QuestionModel q = listData.get(index);
                tvQuestion.setText(q.getContent());
                int qid = q.getId();
                ArrayList<OptionModel> optionModels = databases.getOption(qid);
                for (int i = 0; i < optionModels.size(); i++) {
                    OptionModel o = optionModels.get(i);
                    RadioButton rd = new RadioButton(QuestionActivity.this);
                    rd.setId(o.getId());
                    rd.setButtonTintList(ColorStateList.valueOf(Color.WHITE));
                    rd.setText(o.getContent());
                    rd.setTextSize(24);
                    rd.setTextColor(Color.WHITE);
                    rd.setTypeface(typeface);
                    if (o.getContent().equals(map.get(index))) rd.setChecked(true);
                    radioGroups.addView(rd);
                }
                if (index == 0) {
                    btnPre.setEnabled(false);
                }

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface typeface = Typeface.createFromAsset(getAssets(), "font1.otf");
                if (index == listData.size() - 1) {
                    englishDialog.show(getFragmentManager(), "TEST");
                } else {
                    radioGroups.removeAllViews();
                    index++;
                    tvPage.setText((index + 1) + "/" + listData.size());
                    QuestionModel q = listData.get(index);
                    tvQuestion.setText(q.getContent());
                    int qid = q.getId();
                    ArrayList<OptionModel> optionModels = databases.getOption(qid);
                    for (int i = 0; i < optionModels.size(); i++) {
                        OptionModel o = optionModels.get(i);
                        RadioButton rd = new RadioButton(QuestionActivity.this);
                        rd.setId(o.getId());
                        rd.setButtonTintList(ColorStateList.valueOf(Color.WHITE));
                        rd.setText(o.getContent());
                        rd.setTextSize(24);
                        rd.setTextColor(Color.WHITE);
                        rd.setTypeface(typeface);
                        if (o.getContent().equals(map.get(index))) rd.setChecked(true);
                        radioGroups.addView(rd);
                    }

                }
                if (index != 0) {
                    btnPre.setEnabled(true);
                }
            }
        });
    }

    private void prepareData(int id) {

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font1.otf");
        listData = databases.getQuestion(id);
        QuestionModel q = listData.get(index);
        tvPage.setText((index + 1) + "/" + listData.size());
        int qid = q.getId();
        tvQuestion.setText(q.getContent());
        optionModels = databases.getOption(qid);

        for (int i = 0; i < optionModels.size(); i++) {
            OptionModel o = optionModels.get(i);
            RadioButton rd = new RadioButton(QuestionActivity.this);
            rd.setId(o.getId());
            rd.setButtonTintList(ColorStateList.valueOf(Color.WHITE));
            rd.setText(o.getContent());
            rd.setTextSize(24);
            rd.setTextColor(Color.WHITE);
            rd.setTypeface(typeface);
            radioGroups.addView(rd);
        }
        tvPage.setText((index + 1) + "/" + listData.size());
    }

    @Override
    public void onPosClickListener() {

        Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
        intent.putExtra("ID", id);
        intent.putExtra("KQ", map);
        intent.putExtra("lsd", level_score_id);
        intent.putExtra("score", getIntent().getIntExtra("score", 0));
        Intent kqIntent = new Intent();
        for (int i = 0; i < correctA.size(); i++) {
            if (correctA.get(i).getCorrectAnswer().equals(map.get(i))) score++;
        }
        kqIntent.putExtra("score", score);
        setResult(RESULT_OK, kqIntent);
        finish();
        startActivity(intent);
    }

    @Override
    public void onNeClickListener() {
        englishDialog.dismiss();
    }
}
