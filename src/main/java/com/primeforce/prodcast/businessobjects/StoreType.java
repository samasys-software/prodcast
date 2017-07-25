package com.primeforce.prodcast.businessobjects;




/**
 * Created by user on 12/29/2016.
 */


public class StoreType
{
    private String storeTypeName;
    private Long storeTypeId;
    private boolean active;

    public Long getStoreTypeId()
    {
        return storeTypeId;
    }
    public void setStoreTypeId(Long storeTypeId) {
        this.storeTypeId = storeTypeId;
    }

    public String getStoreTypeName()
    {
        return storeTypeName;
    }
    public void setStoreTypeName(String storeTypeName) {
        this.storeTypeName = storeTypeName;
    }

    public boolean isActive()
    {
        return active;
    }
    public void setActive(boolean active)
    {
        this.active = active;
    }


}
