package com.primeforce.prodcast.businessobjects;

/**
 * Created by Hai on 11/3/2016.
 */
public class CustomerRegistration {

        private String country,mobilePhone,pinNumber;
        private Long confirmationId;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country)
        {
            this.country = country;
        }

        public String getPinNumber()
        {
            return pinNumber;
        }

        public void setPinNumber(String pinNumber)
        {
            this.pinNumber = pinNumber;
        }

        public String getMobilePhone()
        {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone)
        {
            this.mobilePhone = mobilePhone;
        }

        public Long getConfirmationId()
        {
            return confirmationId;
        }

        public void setConfirmationId(Long confirmationId)
        {
            this.confirmationId = confirmationId;
        }

}
