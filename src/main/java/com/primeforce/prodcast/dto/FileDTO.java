package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.*;

import java.util.List;

/**
 * Created by God on 4/11/2017.
 */
public class FileDTO extends ProdcastDTO {
    private List<Product> productList;
    private List<Brand> brandList;
    private List<Category> categoryList;
    private List<SubCategory> subCategoryList;
    private List<ProductOptions> productOptionsList;
    private List<ProductFlavors> productFlavorsList;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<SubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<SubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public List<ProductOptions> getProductOptionsList() {
        return productOptionsList;
    }

    public void setProductOptionsList(List<ProductOptions> productOptionsList) {
        this.productOptionsList = productOptionsList;
    }

    public List<ProductFlavors> getProductFlavorsList() {
        return productFlavorsList;
    }

    public void setProductFlavorsList(List<ProductFlavors> productFlavorsList) {
        this.productFlavorsList = productFlavorsList;
    }
}
