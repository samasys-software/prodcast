package com.primeforce.prodcast.dao;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.primeforce.prodcast.businessobjects.ServiceTicket;

@Component
public class ServiceSupportDatabaseManager {

private static final String SAVE_SERVICE_REQUEST_SQL = "insert into service_support (phone_number,con_id,issue,status,assigned_to,issue_startdate,issue_enddate,comments)  value (?,?,?,?,?,current_date,?,?)";
private static final String GET_ALL_REQUEST = "SELECT * from service_support Where status = ?";	
private static final String ASSIGN_TICKET = "UPDATE service_support SET assigned_to =? , status = 1 WHERE issue_id =? AND status = 0";
private static final String FIND_MY_TICKET = "SELECT * from service_support Where assigned_to=? AND status != 2";
private static final String CLOSE_TICKET = "UPDATE service_support SET comments =concat(comments,'\n',?), status =?, issue_enddate = current_date WHERE issue_id = ? AND assigned_to = ?";
private static final String REPORT_FOR_EMPLOYEE = "SELECT * from service_support WHERE issue_startdate >= ? AND issue_enddate <= ? AND assigned_to = ?";
private static final String ALL_REPORTS = "SELECT * from service_support WHERE issue_startdate >= ? AND issue_enddate <= ?";
private static final String GET_ISSUE = "SELECT ctry.isd_code,ser.* from service_support ser, country ctry WHERE ser.issue_id = ? AND ser.con_id = ctry.country_id ";
private static final String GET_ISD_CODE = "SELECT isd_code from country WHERE country_id =?";
private static final String REASSIGN_TICKET = "UPDATE service_support SET assigned_to = ? WHERE status = 1 AND issue_id = ?";
private static final String GET_ISSUE_DETAILS = "SELECT * from service_support WHERE issue_id = ?";
public final JdbcTemplate template;
@Autowired
public ServiceSupportDatabaseManager(JdbcTemplate template) {
	this.template=template;
}

public int saveServiceRequest(String phoneNumber,String countryId, String issue, String comments){
	
	Object[] obj = new Object[] {phoneNumber, countryId, issue,0,null,null,comments};
    return template.update(SAVE_SERVICE_REQUEST_SQL,obj);
}

public List<ServiceTicket> getRequestForStatus(int status){
   Object[] obj = new Object[] {status};
   return template.query(GET_ALL_REQUEST,obj, new ServiceSupportMapper());
}

public int updateIssue(String employeeId, int issueId) {
   Object[] obj = new Object[] {employeeId,issueId};
   return template.update(ASSIGN_TICKET, obj);
}

public List<ServiceTicket> findTickerForEmployee(String employeeId) {
   Object[] obj = new Object[] {employeeId};
   return template.query(FIND_MY_TICKET,obj,new ServiceSupportMapper());
}

public int updateTicket(String employeeId, int issueId, int status, String comments) {
	
   Object[] obj = new Object[] {new Date()+":"+comments, status, issueId, employeeId};
   return template.update(CLOSE_TICKET,obj);
}

public int viewSupport(String employeeId, int issueId) {
	Object[] obj = new Object[] {employeeId, issueId};
	return template.update(REASSIGN_TICKET,obj);
}

public List<ServiceTicket> viewReport(String employeeId, java.sql.Date startDate, java.sql.Date endDate){
	Object[] obj = new Object[] {startDate, endDate, employeeId};
	return template.query(REPORT_FOR_EMPLOYEE, obj, new ServiceSupportMapper());
}


public List<ServiceTicket> viewReportForAllEmployee( java.sql.Date startDate, java.sql.Date endDate){
	Object[] obj = new Object[] {startDate, endDate};
	return template.query(ALL_REPORTS, obj, new ServiceSupportMapper());
}
public List<ServiceTicket> getIssueDetails(int issueId){
	Object[] obj = new Object[] {issueId};
	return template.query(GET_ISSUE_DETAILS, obj, new ServiceSupportMapper());
}

public ServiceTicket getIssue(int issueId) {
	Object[] obj = new Object[] {issueId};
	return template.queryForObject(GET_ISSUE, obj,new ServiceSupportMapper(true));
}


public String getIsdCode(String countryId) {
	Object[] obj = new Object[] {countryId};
	return template.queryForObject(GET_ISD_CODE,obj,String.class);
}

}
