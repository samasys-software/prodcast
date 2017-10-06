package com.primeforce.prodcast.businessobjects;

public class Discount {
    private int discountType;
    private float discountValue;
    private float totalAmount;
    private float oldTotalAmount;

    public int getDiscountType() {
        return discountType;
    }

    public void setDiscountType(int discountType) {
        this.discountType = discountType;
    }

    public float getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(float discountValue) {
        this.discountValue = discountValue;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getOldTotalAmount() {
        return oldTotalAmount;
    }

    public void setOldTotalAmount(float oldTotalAmount) {
        this.oldTotalAmount = oldTotalAmount;
    }
}
