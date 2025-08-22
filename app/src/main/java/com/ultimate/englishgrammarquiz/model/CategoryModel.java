package com.ultimate.englishgrammarquiz.model;

/**
 * Created by Legandan on 27/02/2021.
 */
public class CategoryModel {
    private String titleCatelogy;
    private String id;
    public CategoryModel() {
    }

    public CategoryModel(String titleCatelogy, String id) {
        this.titleCatelogy = titleCatelogy;
        this.id = id;
    }

    public String getTitleCatelogy() {
        return titleCatelogy;
    }

    public void setTitleCatelogy(String titleCatelogy) {
        this.titleCatelogy = titleCatelogy;
    }

    @Override
    public String toString() {
        return titleCatelogy ;
    }
}
