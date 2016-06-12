package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.Customer;
import com.primeforce.prodcast.businessobjects.Product;

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
}
