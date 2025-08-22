package com.ultimate.englishgrammarquiz.model;

import java.util.ArrayList;

/**
 * Created by Legandan on 27/02/2021.
 */

public class QuestionModel {
    private int id;
    private String content;
    private ArrayList<OptionModel> optionsInQuestion;


    public QuestionModel() {
    }

    public QuestionModel(int id, String content){
        this.id = id;
        this.content = content;

    }

    public ArrayList<OptionModel> getOptionsInQuestion() {
        return optionsInQuestion;
    }

    public void setOptionsInQuestion(ArrayList<OptionModel> optionsInQuestion) {
        this.optionsInQuestion = optionsInQuestion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "QuestionModel{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", optionsInQuestion=" + optionsInQuestion +
                '}';
    }
}
