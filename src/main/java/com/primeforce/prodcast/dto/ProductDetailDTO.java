package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.ProductFlavors;
import com.primeforce.prodcast.businessobjects.ProductOptions;

import javax.ws.rs.FormParam;
import java.util.List;

public class ProductDetailDTO extends ProdcastDTO{


    private String employeeId;
    private String productId;
    private String productName;
    private String productDesc;
    private String productSku;
    private String unitPrice;
    private String priceType;
    private String categoryId;
    private String subCategoryId;
    private String brandId;
    private String active ;
    private String salesTax;
    private String otherTax;
    private String retailPrice;
    private String uom ;
    private boolean hasOptions;
    private String optionName;
    private List<ProductOptions> productOptions;
    private String flavorName;
    private boolean hasFlavors;
    private List<ProductFlavors> productFlavors;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }



    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }


    public boolean isHasOptions() {
        return hasOptions;
    }

    public void setHasOptions(boolean hasOptions) {
        this.hasOptions = hasOptions;
    }

    public String getOptionName() {
        return optionName;
    }



    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public List<ProductOptions> getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(List<ProductOptions> productOptions) {
        this.productOptions = productOptions;
    }

    public String getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(String salesTax) {
        this.salesTax = salesTax;
    }

    public String getOtherTax() {
        return otherTax;
    }

    public void setOtherTax(String otherTax) {
        this.otherTax = otherTax;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getFlavorName() {
        return flavorName;
    }

    public void setFlavorName(String flavorName) {
        this.flavorName = flavorName;
    }

    public boolean isHasFlavors() {
        return hasFlavors;
    }

    public void setHasFlavors(boolean hasFlavors) {
        this.hasFlavors = hasFlavors;
    }

    public List<ProductFlavors> getProductFlavors() {
        return productFlavors;
    }

    public void setProductFlavors(List<ProductFlavors> productFlavors) {
        this.productFlavors = productFlavors;
    }
}
