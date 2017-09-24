package com.primeforce.prodcast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.inject.Named;
import javax.websocket.server.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import com.primeforce.prodcast.businessobjects.ServiceTicket;
import com.primeforce.prodcast.dao.ServiceSupportDatabaseManager;
import com.primeforce.prodcast.dto.ProdcastDTO;
import com.primeforce.prodcast.dto.ServiceSupportDTO;
import com.primeforce.prodcast.util.Amazon;
import com.primeforce.prodcast.util.Notifier;


@Named
@Path("/support/")
public class SupportRest {

	private final ServiceSupportDatabaseManager databaseManager; 

	@Autowired
	public SupportRest(ServiceSupportDatabaseManager manager) {
		this.databaseManager = manager;
		
	}
	@POST
	@Path("requestraised")
	@Produces(MediaType.APPLICATION_JSON)
	public ProdcastDTO raiseRequest(@FormParam("name") String name, @FormParam("phoneNumber") String phoneNumber,
			@FormParam("issue") String issue, @FormParam("countryId") String countryId) {
		
		ProdcastDTO dto = new ProdcastDTO();
		int allServiceReq = databaseManager.saveServiceRequest(name,phoneNumber,countryId,issue,"");
		if(allServiceReq != 1) {
			dto.setError(true);
			dto.setErrorMessage("Request not saved");
		}else {
			dto.setError(false);
			dto.setErrorMessage("Request has been received by support team");
			String isdCode = databaseManager.getIsdCode(countryId);
			String customerNumber = isdCode + phoneNumber;
			Amazon.sendSMS("Thankyou for sending the issue. We will work on it.", customerNumber);
		}
		return dto;
		}
	@GET
	@Path("newrequest")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ServiceTicket> getNewRequest(@QueryParam("status") int status){
		ServiceSupportDTO dto = new ServiceSupportDTO();
		dto.setServiceSupport(databaseManager.getRequestForStatus(status));
		return dto.getServiceSupport();
	}
	@POST
	@Path("assignrequest")
	@Produces(MediaType.APPLICATION_JSON)
	public ProdcastDTO assignRequest(@FormParam("employeeId")String employeeId,@FormParam("issueId") int issueId) {
		ProdcastDTO dto = new ProdcastDTO();
		int result = databaseManager.updateIssue(employeeId, issueId);
		if(result != 1) {
			dto.setError(true);
			dto.setErrorMessage("Unable to assigning this request");
		}else {
			dto.setError(false);
			dto.setErrorMessage("Assigned");
		}
		return dto;
	}
	@GET
	@Path("mytickets/{employeeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ServiceTicket> viewMyTicket(@PathParam("employeeId") String employeeId) {
		ServiceSupportDTO dto = new ServiceSupportDTO();
		dto.setServiceSupport(databaseManager.findTickerForEmployee(employeeId));
		return dto.getServiceSupport();
	}
	@POST
	@Path("updateticket")
	@Produces(MediaType.APPLICATION_JSON)
	public ProdcastDTO updateTicket(@FormParam("employeeId") String employeeId, @FormParam("issueId") int issueId,
			@FormParam("status") int status, @FormParam("comments") String comments) {
		ProdcastDTO dto = new ProdcastDTO();
		int update = databaseManager.updateTicket(employeeId,issueId,status,comments);
		if(update != 1) {
			dto.setError(true);
			dto.setErrorMessage("close issue not updated");
		}else {
			dto.setError(false);
			dto.setErrorMessage("Successfully closed the issue");
		}
		ServiceTicket support = databaseManager.getIssue(issueId);
		String msg = "Good News!! The Prodcast Issue Id: "+issueId+" is resolved.";
		String customerNumber = support.getIsdCode()+support.getPhoneNumber();
		Notifier.sendNotification( customerNumber , msg , null , null);;
		
		return dto;
	}
	@POST 
	@Path("reassignticket")
	@Produces(MediaType.APPLICATION_JSON)
	public ProdcastDTO reassignTicket(@FormParam("employeeId") String employeeId, @FormParam("issueId") int issueId) {
		int assign = databaseManager.viewSupport(employeeId, issueId);
		ProdcastDTO dto = new ProdcastDTO();
		if(assign != 1) {
			dto.setError(true);
			dto.setErrorMessage("Sorry ticket not assigned");
		}else {
			dto.setError(false);
			dto.setErrorMessage("Ticket assigned");
		}
		return dto;
	}
	
	@GET
	@Path("supportreport")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ServiceTicket> viewServiceReport(@QueryParam("employeeId") String employeeId, @QueryParam("startDate") String customStartDate,
			@QueryParam("endDate") String customEndDate, @QueryParam("type") int type, 
			@QueryParam("selectedEmployee") String selectedEmployee){
		ServiceSupportDTO dto = new ServiceSupportDTO();
		final long DAY = 24*60*60*1000;
		Calendar cal = Calendar.getInstance();
		java.sql.Date startDate = java.sql.Date.valueOf("2016-09-21");
		java.sql.Date endDate = new java.sql.Date(cal.getTime().getTime());
		
		try {
		if(type == 0) {
			startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			endDate = startDate;
			
		}else if (type == 1) {
			startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime() - DAY);
			endDate = startDate;
			
		}else if(type == 2) {
			endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			startDate = new java.sql.Date(cal.getTime().getTime());
			
		}else if(type == 3) {
			endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			cal.set(Calendar.DAY_OF_MONTH, 1 );
			startDate = new java.sql.Date(cal.getTime().getTime());
			
		
		}else {
			startDate = new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(customStartDate).getTime());
			endDate = new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(customEndDate).getTime());
			
		}
		if(selectedEmployee != null && selectedEmployee.equals("ALL")) {
			dto.setServiceSupport(databaseManager.viewReportForAllEmployee(startDate, endDate));
		}else {
			if(selectedEmployee != null) {
				employeeId = selectedEmployee;
			}
			dto.setServiceSupport(databaseManager.viewReport(employeeId, startDate, endDate));
		}
		}
	
		catch(Exception er) {
			er.printStackTrace();
			dto.setError(true);
			dto.setErrorMessage(er.toString());
			
		}
		
	return dto.getServiceSupport();
	}
	@GET
	@Path("allreports")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ServiceTicket> getAllReports(@QueryParam("status") int status){
		return databaseManager.getRequestForStatus(status);
	}


}
