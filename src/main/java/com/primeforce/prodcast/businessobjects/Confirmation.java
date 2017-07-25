package com.primeforce.prodcast.businessobjects;

/**
 * Created by Nandhini on 10/31/2016.
 */
public class Confirmation {
    private String username,status,countryId,isdCode;
    private long accessId;

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    private boolean registered;

    public long getOutletId() {
        return outletId;
    }

    public void setOutletId(long outletId) {
        this.outletId = outletId;
    }

    private long outletId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public long getAccessId() {
        return accessId;
    }

    public void setAccessId(long accessId) {
        this.accessId = accessId;
    }


    public String getIsdCode() {
        return isdCode;
    }

    public void setIsdCode(String isdCode) {
        this.isdCode = isdCode;
    }
}
