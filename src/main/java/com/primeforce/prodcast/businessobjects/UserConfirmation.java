package com.primeforce.prodcast.businessobjects;

/**
 * Created by Thiru on 11/1/2017.
 */
public class UserConfirmation
{
    private String mobilephone,status,countryId,isdCode;
    private long pin;


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

    public String getMobilePhone() {
        return mobilephone;
    }

    public void setMobilePhone(String mobilephone) {
        this.mobilephone = mobilephone;
    }


    public long getPin() {
        return pin;
    }

    public void setPin(long pin) {
        this.pin = pin;
    }


    public String getIsdCode() {
        return isdCode;
    }

    public void setIsdCode(String isdCode) {
        this.isdCode = isdCode;
    }
}


