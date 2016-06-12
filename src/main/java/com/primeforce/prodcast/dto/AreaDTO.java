package com.primeforce.prodcast.dto;

import com.primeforce.prodcast.businessobjects.Area;

import java.util.List;

/**
 * Created by sarathan732 on 4/23/2016.
 */
public class AreaDTO extends ProdcastDTO{

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    private List<Area> areas;
 }
