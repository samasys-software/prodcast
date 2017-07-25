package com.primeforce.prodcast.dao;
import com.primeforce.prodcast.businessobjects.StoreType;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by user on 12/29/2016.
 */
public class StoreTypeMapper implements RowMapper<StoreType> {
    public StoreType mapRow(ResultSet rs, int rowNum) throws SQLException{
        StoreType storeType=new StoreType();
        storeType.setStoreTypeId(rs.getLong("store_type_id"));
        storeType.setStoreTypeName(rs.getString("store_type_name"));
        storeType.setActive(rs.getBoolean("active_yn"));
        return storeType;

    }



}