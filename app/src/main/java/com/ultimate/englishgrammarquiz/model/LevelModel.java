package com.ultimate.englishgrammarquiz.model;

/**
 * Created by Legandan on 27/02/2021.
 */

public class LevelModel {
    private String titleTopic;
    private int id;
    private int id_categories;
    private int  score;
    private int sl;
    private int levelscore_id;

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public LevelModel(String titleTopic, int id, int id_categories) {
        this.titleTopic = titleTopic;
        this.id = id;
        this.id_categories = id_categories;
        score = 0;
    }

    public LevelModel() {
        score = 0;
    }

    public int getLevelscore_id() {
        return levelscore_id;
    }

    public void setLevelscore_id(int levelscore_id) {
        this.levelscore_id = levelscore_id;
    }

    public int getScore() {

        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_categories() {
        return id_categories;
    }

    public void setId_categories(int id_categories) {
        this.id_categories = id_categories;
    }

    public String getTitleTopic() {
        return titleTopic;
    }

    public void setTitleTopic(String titleTopic) {
        this.titleTopic = titleTopic;
    }

    @Override
    public String toString() {
        return titleTopic;
    }
}
