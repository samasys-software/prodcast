package com.primeforce.prodcast.dao;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sarathan732 on 8/26/2016.
 */
public class DBRowColumnMapper implements RowMapper {

    public Map mapRow(ResultSet rs , int rowNo) throws SQLException{
        ResultSetMetaData metaData = rs.getMetaData();

        int colCount = metaData.getColumnCount();
        Map row = new HashMap();

        for(int i=1;i<=colCount;i++){
            String colName = metaData.getColumnName(i);
            String colValue = rs.getString(colName);
            row.put(colName, colValue );
        }

        return row;

    }

}
