package com.primeforce.prodcast.dto;

/**
 * Created by sarathan732 on 4/26/2016.
 */
public class OrderEntryDTO {
    private String productId;
    private String quantity;
    private long optionId;
    private long flavorId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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
}
