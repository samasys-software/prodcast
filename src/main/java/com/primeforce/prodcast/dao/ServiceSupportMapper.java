package com.primeforce.prodcast.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import com.primeforce.prodcast.businessobjects.ServiceTicket;

public class ServiceSupportMapper implements RowMapper<ServiceTicket> {
	
	private boolean useIsdCode;
	public ServiceSupportMapper() {
		useIsdCode = false;
	}
	public ServiceSupportMapper(boolean useIsdCode) {
		this.useIsdCode = useIsdCode;
	}

	@Override
	public ServiceTicket mapRow(ResultSet rs, int rowNumber) throws SQLException {
		ServiceTicket support = new ServiceTicket();
		support.setIssueId(rs.getInt("issue_id"));
		support.setName(rs.getString("name"));
		support.setPhoneNumber(rs.getString("phone_number"));
		support.setCountryId(rs.getString("con_id"));
		support.setIssue(rs.getString("issue"));
		support.setStatus(rs.getInt("status"));
		support.setAssignedTo(rs.getString("assigned_to"));
		support.setStartDate(rs.getDate("issue_startdate"));
		support.setEndDate(rs.getDate("issue_enddate"));
		support.setComments(rs.getString("comments"));
		
		if( useIsdCode ) {
			support.setIsdCode( rs.getString("isd_code"));
		}
		
		return support;
	}
	

}
