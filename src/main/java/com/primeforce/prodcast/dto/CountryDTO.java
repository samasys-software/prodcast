package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.Country;
import com.primeforce.prodcast.businessobjects.Timezone;

import java.util.List;

/**
 * Created by Nandhini Govindasamy on 10/14/2016.
 */
public class CountryDTO extends ProdcastDTO {
    private List<Country> result;
    private List<Timezone> timezones;

    public List<Country> getResult() {
        return result;
    }

    public void setResult(List<Country> result) {
        this.result = result;
    }

    public List<Timezone> getTimezones() {
        return timezones;
    }

    public void setTimezones(List<Timezone> timezones) {
        this.timezones = timezones;
    }
}
