package com.primeforce.prodcast.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import com.primeforce.prodcast.businessobjects.ServiceTicket;

public class ServiceSupportMapper implements RowMapper<ServiceTicket> {
	
	private boolean useIsdCode;
	private boolean useFirstName;
	private boolean useLastName;
	public ServiceSupportMapper() {
		useIsdCode = false;
		useFirstName = false;
		useLastName = false;
	}
	public ServiceSupportMapper(boolean useIsdCode, boolean useFirstName, boolean useLastName) {
		this.useIsdCode = useIsdCode;
		this.useFirstName = useFirstName;
		this.useLastName = useLastName;
	}

	@Override
	public ServiceTicket mapRow(ResultSet rs, int rowNumber) throws SQLException {
		ServiceTicket support = new ServiceTicket();
		support.setIssueId(rs.getInt("issue_id"));
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
		if(useFirstName) {
			support.setFirstName(rs.getString("firstname"));
		}
		if(useLastName) {
			support.setLastName(rs.getString("lastname"));
		}
		
		
		return support;
	}
	

}
