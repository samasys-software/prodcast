package com.primeforce.prodcast.businessobjects;

/**
 * Created by sarathan732 on 5/17/2016.
 */
public class Brand {
    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    private long brandId;
    private String brandName;
}
