package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.Registration;

import java.util.List;

/**
 * Created by Nandhini on 10/25/2016.
 */
public class RegistrationDTO extends ProdcastDTO {

        private List<Registration> result;

    public List<Registration> getResult() {
        return result;
    }

    public void setResult(List<Registration> result) {
        this.result = result;
    }
}
