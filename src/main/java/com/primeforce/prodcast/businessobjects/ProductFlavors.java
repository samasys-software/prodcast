package com.primeforce.prodcast.businessobjects;

public class ProductFlavors {
    private String flavorId;
    private String flavorValue;

    private boolean active;
    private long productId;

    public String getFlavorId() {
        return flavorId;
    }

    public void setFlavorId(String flavorId) {
        this.flavorId = flavorId;
    }

    public String getFlavorValue() {
        return flavorValue;
    }

    public void setFlavorValue(String flavorValue) {
        this.flavorValue = flavorValue;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
