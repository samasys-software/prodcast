package com.primeforce.prodcast.businessobjects;

/**
 * Created by sarathan732 on 5/17/2016.
 */
public class Category {

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    private long categoryId;
    private String categoryName;

}
