package com.primeforce.prodcast.businessobjects;

/**
 * Created by sarathan732 on 6/14/2016.
 */
public class ExpenseCategory {

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    private long categoryId;
    private String categoryDesc;

}
