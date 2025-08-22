package com.ultimate.englishgrammarquiz.model;

/**
 * Created by Legandan on 27/02/2021.
 */

public class ResultModel {
    private int id;
    private String question;

    private String yourAnswer;

    private String correctAnswer;
    private boolean isCorect;


    public ResultModel(int id, String question, String correctAnswer, boolean isCorect) {
        this.id = id;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.isCorect = isCorect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCorect() {
        return isCorect;
    }

    public void setCorect(boolean corect) {
        isCorect = corect;
    }

    public ResultModel(int id, String question) {
        this.id =id;
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getYourAnswer() {
        return yourAnswer;
    }

    public void setYourAnswer(String yourAnswer) {
        this.yourAnswer = yourAnswer;
    }


    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    public boolean getKQ() {
        if (yourAnswer == null) return false;
        return yourAnswer.equals(correctAnswer);
    }
    @Override
    public String toString() {
        return "ResultModel{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", yourAnswer='" + yourAnswer + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", isCorect=" + isCorect +
                '}';
    }
}
