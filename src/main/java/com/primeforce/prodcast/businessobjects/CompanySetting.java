package com.primeforce.prodcast.businessobjects;

/**
 * Created by sarathan732 on 5/21/2016.
 */
public class CompanySetting {

    private long distributorId;
    private float salesTaxRate;
    private String companyName;
    private String address;
    private String city;
    private String stateorprovince;
    private String postalcode;
    private String timezone;
    private float minimumDeliveryAmount;


    public String getFulfillmentType() {
        return fulfillmentType;
    }

    public void setFulfillmentType(String fulfillmentType) {
        this.fulfillmentType = fulfillmentType;
    }

    private String fulfillmentType;

    public long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(long distributorId) {
        this.distributorId = distributorId;
    }

    public float getSalesTaxRate() {
        return salesTaxRate;
    }

    public void setSalesTaxRate(float salesTaxRate) {
        this.salesTaxRate = salesTaxRate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateorprovince() {
        return stateorprovince;
    }

    public void setStateorprovince(String stateorprovince) {
        this.stateorprovince = stateorprovince;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    private String country;
    private String phoneNumber;
    private String faxNumber;

    public float getMinimumDeliveryAmount() {
        return minimumDeliveryAmount;
    }

    public void setMinimumDeliveryAmount(float minimumDeliveryAmount) {
        this.minimumDeliveryAmount = minimumDeliveryAmount;
    }
}
