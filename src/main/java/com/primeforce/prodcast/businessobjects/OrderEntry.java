package com.primeforce.prodcast.businessobjects;

/**
 * Created by sarathan732 on 4/26/2016.
 */
public class OrderEntry {

    private long orderEntryId;

    private float salesTax;
    private float otherTax;

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getOtherTax() {
        return otherTax;
    }

    public void setOtherTax(float otherTax) {
        this.otherTax = otherTax;
    }

    public float getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(float salesTax) {
        this.salesTax = salesTax;
    }

    private float subtotal;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    private String productName;

    public long getOrderEntryId() {
        return orderEntryId;
    }

    public void setOrderEntryId(long orderEntryId) {
        this.orderEntryId = orderEntryId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public long getOptionId() {
        return optionId;
    }

    public void setOptionId(long optionId) {
        this.optionId = optionId;
    }

    public long getFlavorId() {
        return flavorId;
    }

    public void setFlavorId(long flavorId) {
        this.flavorId = flavorId;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public String getFlavorValue() {
        return flavorValue;
    }

    public void setFlavorValue(String flavorValue) {
        this.flavorValue = flavorValue;
    }

    private long productId;
    private int quantity;
    private float unitPrice;
    private float amount;
    private long optionId;
    private long flavorId;
    private String optionValue;
    private String flavorValue;
}
