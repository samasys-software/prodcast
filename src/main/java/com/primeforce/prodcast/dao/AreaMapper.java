package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.Area;
import com.primeforce.prodcast.businessobjects.Brand;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sarathan732 on 4/26/2016.
 */
public class AreaMapper implements RowMapper<Area> {

    public AreaMapper(){

    }
    public Area mapRow(ResultSet rs, int rowNum ) throws SQLException {
        Area area = new Area();
        area.setId( rs.getLong("area_id"));
        area.setDescription( rs.getString( "area_desc"));
        return area;
    }

}
