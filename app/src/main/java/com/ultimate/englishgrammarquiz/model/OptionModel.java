package com.ultimate.englishgrammarquiz.model;

/**
 * Created by Legandan on 27/02/2021.
 */

public class OptionModel {
    private int id;
    private int idQuestion;
    private String content;
    private boolean correct;

    public OptionModel(int id, int idQuestion, String content, boolean correct) {

        this.id = id;
        this.idQuestion = idQuestion;
        this.content = content;
        this.correct = correct;
    }

    public OptionModel() {
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

}
