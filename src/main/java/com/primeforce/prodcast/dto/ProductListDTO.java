package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.Customer;
import com.primeforce.prodcast.businessobjects.Product;
import com.primeforce.prodcast.businessobjects.ProductFlavors;
import com.primeforce.prodcast.businessobjects.ProductOptions;

import java.util.List;

/**
 * Created by sarathan732 on 4/23/2016.
 */
public class ProductListDTO extends ProdcastDTO {

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    private List<Product> productList;
    private  List<ProductOptions> productOptionsList;
    private  List<ProductFlavors> productFlavorsList;

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
