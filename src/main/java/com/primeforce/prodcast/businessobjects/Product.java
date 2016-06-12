package com.primeforce.prodcast.businessobjects;

/**
 * Created by sarathan732 on 4/23/2016.
 */
public class Product {

    //(product_name , product_desc, product_sku, distributor_id ,  manufacturer_id ,  unitprice, price_type , prod_catg_id ,
    // prod_sub_catg_id , product_brand_id , active,  user_id , updt_dt_tm , ip_address)
    private long id;
    private String productName;
    private String productDesc;
    private String productSku;

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private float unitPrice;
    private String priceType;
    private long categoryId;
    private long subCategoryId;
    private long brandId;
    private boolean active;

}
