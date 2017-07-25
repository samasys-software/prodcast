package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.Brand;
import com.primeforce.prodcast.businessobjects.Category;
import com.primeforce.prodcast.businessobjects.Product;
import com.primeforce.prodcast.businessobjects.SubCategory;

import java.util.List;

/**
 * Created by God on 4/11/2017.
 */
public class FileDTO extends ProdcastDTO {
    private List<Product> productList;
    private List<Brand> brandList;
    private List<Category> categoryList;
    private List<SubCategory> subCategoryList;

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
}
