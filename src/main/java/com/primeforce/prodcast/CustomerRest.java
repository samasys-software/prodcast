package com.primeforce.prodcast;

import com.primeforce.prodcast.businessobjects.*;
import com.primeforce.prodcast.dao.DatabaseManager;
import com.primeforce.prodcast.dao.Distributor;
import com.primeforce.prodcast.dto.*;
import com.primeforce.prodcast.util.Amazon;
import com.primeforce.prodcast.util.TimeZoneConvertor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.*;

/**
 * Created by Nandhini on 10/31/2016.
 */

@Named
@Path("/customer/")
public class CustomerRest {

    private final DatabaseManager databaseManager;

    @Autowired
    public CustomerRest(DatabaseManager manager) {
        databaseManager = manager;
    }


    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerLoginDTO authenticate(@FormParam("userid") String id,
                                         @FormParam("password") String password,
                                         @FormParam("country") String country) {

        CustomerLoginDTO dto = new CustomerLoginDTO();

        try {

            Confirmation confirmation = databaseManager.loginCustomer(id, password, country);
            Long accessId = confirmation.getAccessId();

            confirmation.setRegistered(databaseManager.isCustomerRegisteredForPublicOrders( accessId.longValue() ));
            dto.setRegistered( confirmation.isRegistered() );
            if (confirmation.getStatus().equals("0")) {
                dto.setVerified(false);
            } else {
                dto.setVerified(true);
                // List<Distributor> distributor=databaseManager.getDistributorsForCustomers(accessId)
            }


            CustomersLogin custLogin = databaseManager.getDistributorsForCustomers(accessId);
            dto.setResult(custLogin);


        } catch (Exception er) {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString());
        }
        return dto;
    }


    @POST
    @Path("customerRegistration")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerLoginDTO CustomerRegistration(

            @FormParam("country") String country,
            @FormParam("mobilePhone") String mobilePhone,
            @FormParam("pinNumber") String pinNumber) {


        CustomerLoginDTO dto = new CustomerLoginDTO();

        try {

            List customerData = databaseManager.fetchMobileNumbers(country, mobilePhone);
            if (customerData.size() > 0) {
                dto.setError(true);
                dto.setErrorMessage("The Mobile number Already Exists");

            } else {
                Random random = new Random();
                int code = (100000 + random.nextInt(900000));
                CustomerRegistration register = databaseManager.registerCustomer(country, mobilePhone, pinNumber, code);
                long accessId = register.getConfirmationId();
                dto.setRegistered( false );

                dto.setResult(register);
                String phoneNumber = databaseManager.fetchCustomerCountryId(country) + mobilePhone;
                String subject = "The Verfication code is:" + code + "\n";
                Amazon.sendSMS(subject, phoneNumber);
            }

        } catch (Exception er) {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString());
        }
        return dto;
    }


    @POST
    @Path("confirmationDetails")
    @Produces(MediaType.APPLICATION_JSON)

    public CustomerLoginDTO confirmCode(@FormParam("accessId") long accessId,
                                        @FormParam("confirmationCode") long confirmCode) {
        CustomerLoginDTO dto = new CustomerLoginDTO();

        try {
            CustomersLogin custLogin = null;
            long confimationNumber = databaseManager.getConfirmationCode(accessId);
            if (confimationNumber == confirmCode) {
                int rowCount = databaseManager.setConfirmationStatus(accessId);
                if (rowCount == 0) {
                    dto.setError(true);
                    dto.setErrorMessage("Sorry! Your Confirmation ...Please Try Again Later");
                } else {
                    custLogin = databaseManager.getDistributorsForCustomers(accessId);
                    dto.setRegistered( databaseManager.isCustomerRegisteredForPublicOrders( accessId ));
                    dto.setResult(custLogin);

                }
            } else {
                dto.setError(true);
                dto.setErrorMessage("Sorry! Your Confirmation Code Was Wrong..Please Try Again Later");
            }
        } catch (Exception er) {
            dto.setError(true);
            dto.setErrorMessage(er.toString());
            er.printStackTrace();

        }
        return dto;
    }



    @POST
    @Path("changePinNumber")
    @Produces(MediaType.APPLICATION_JSON)
    public ProdcastDTO changePinNumberAuth(@FormParam("accessId") long accessId, @FormParam("oldPinNumber") String oldPinNumber, @FormParam("newPinNumber") String newPinNumber) throws Exception {
        ProdcastDTO dto = new ProdcastDTO();
        try {

            String phoneNumber = databaseManager.changePinNumber(accessId, oldPinNumber, newPinNumber);
            if (phoneNumber == null) {
                dto.setError(true);
                dto.setErrorMessage("The old PinNumber did not match. Please reenter old PinNumber again");
            } else {
                // dto.setResult(phoneNumber);
                String country = databaseManager.fetchCountry(accessId);
                String isdcode = databaseManager.fetchIsdCode(country);
                String mobileNumber = isdcode + phoneNumber;
                String subject = "Your pin has been changed in PRODCAST \n";
                Amazon.sendSMS(subject, mobileNumber);
            }
        } catch (Exception er) {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString());
        }

        return dto;
    }


    @POST
    @Path("getCustomerDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO orderForCustomer(@FormParam("accessId") long accessId,
                                     @FormParam("distributorId") long distributorId) {
        AdminDTO dto = new AdminDTO();
        try {

            EmployeeDetails empDetails = databaseManager.getEmployeeDetailsForOrder(accessId, distributorId);
            if (empDetails == null) {
                dto.setError(true);
                dto.setErrorMessage("The Customer Can Not Have The Employee");
            } else {
                dto.setResult(empDetails);

            }
        } catch (Exception er) {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString());
        }

        return dto;
    }

    @POST
    @Path("resendConfirmationCode")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO resendConfirmationCode(

            @FormParam("accessId") String accessId) {


        AdminDTO dto = new AdminDTO();

        try {


            int code = databaseManager.generateConfirmationNumber();

            int rowCount = databaseManager.updateConfirmationCode(code, accessId);
            if (rowCount == 0) {
                dto.setError(true);
                dto.setErrorMessage("Code Not Sent. Please try again!");
            } else {
                dto.setResult(rowCount);


            }


            String mobileNumber = databaseManager.fetchMobileNumber(accessId);
            String country = databaseManager.fetchCountry(Long.parseLong(accessId));
            String isdcode = databaseManager.fetchIsdCode(country);
            String phoneNumber = isdcode + mobileNumber;


            String subject = "The Verfication code is:" + code + "\n";

            Amazon.sendSMS(subject, phoneNumber);


        } catch (Exception er) {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString());
        }
        return dto;
    }



    @GET
    @Path("reportForCustomers")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerReportDTO getReport(@QueryParam("reportType") String reportType,
                                       @QueryParam("accessId") String accessId,
                                       @QueryParam("startDate") String customStartDate,
                                       @QueryParam("endDate") String customEndDate,
                                       @QueryParam("selectedDistributor") String selectedDistributor,
                                       @QueryParam("reportId") String reportId) {

        CustomerReportDTO dto = new CustomerReportDTO();
        final long DAY = 24 * 60 * 60 * 1000;
        try {
            Calendar cal = Calendar.getInstance();
            java.sql.Date startDate = java.sql.Date.valueOf("2016-04-20");
            java.sql.Date endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

            if (reportType.equals("today")) {
                startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                endDate = startDate;
            } else if (reportType.equals("yesterday")) {
                startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime() - DAY);
                endDate = startDate;
            } else if (reportType.equals("week")) {
                endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                startDate = new java.sql.Date(cal.getTime().getTime());
            } else if (reportType.equals("last7")) {
                endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime() - 7 * DAY);
            } else if (reportType.equals("month")) {
                endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                cal.set(Calendar.DAY_OF_MONTH, 1);
                startDate = new java.sql.Date(cal.getTime().getTime());
            } else if (reportType.equals("last30")) {
                endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime() - 30 * DAY);
            } else {
                startDate = new java.sql.Date(new SimpleDateFormat(DatabaseManager.DATE_FORMAT).parse(customStartDate).getTime());
                endDate = new java.sql.Date(new SimpleDateFormat(DatabaseManager.DATE_FORMAT).parse(customEndDate).getTime());

            }



            if( startDate == endDate || startDate.equals( endDate)) {
                dto.setReportDates(new SimpleDateFormat(DatabaseManager.DATE_FORMAT).format(startDate));
            }
            else{
                dto.setReportDates(new SimpleDateFormat(DatabaseManager.DATE_FORMAT).format(startDate)+"-"+new SimpleDateFormat(DatabaseManager.DATE_FORMAT).format(endDate));
            }





            List<Order> billDetails=null;


           billDetails = databaseManager.fetchBillDetailsForCustomer(startDate, endDate, Long.parseLong(accessId), Long.parseLong(selectedDistributor));



            float totalAmount =0;
            float totalOutstandingBalance = 0;
            float totalAmountPaid=0;


                    for (Order order:billDetails)
                    {
                        totalAmount= totalAmount+order.getTotalAmount();
                        totalOutstandingBalance=totalOutstandingBalance+order.getOutstandingBalance();
                        totalAmountPaid=totalAmount-totalOutstandingBalance;



                    }



            dto.setTotalAmount( totalAmount );
            dto.setTotalOutstandingBalance(totalOutstandingBalance);
            dto.setTotalAmountPaid(totalAmountPaid);

            dto.setResult(billDetails);


        } catch (Exception er) {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString());
        }
        return dto;
    }


    @POST
    @Path("retrievePin")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO authenticate(@FormParam("mobilePhone") String mobilePhone, @FormParam("country") String country)throws Exception
    {
        AdminDTO dto = new AdminDTO();
        try
        {
            if(mobilePhone != null )
            {

                String password = databaseManager.getPinFromMobile(mobilePhone);

                if (password != null)
                {
                    String phoneNumber = databaseManager.fetchCustomerCountryId(country) + mobilePhone;

                    String subject = "Your Pin Number is " + password;

                    System.out.println(password);

                    Amazon.sendSMS(subject,phoneNumber);

                    return dto;
                }
                else
                {
                    dto.setError(true);
                    dto.setErrorMessage("The mobile number is not registered with PRODCAST");
                }
            }
        }
        catch (Exception er)
        {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString());
        }
        return dto;
    }

    @POST
    @Path("saveNewCustomer")
    @Produces(MediaType.APPLICATION_JSON)
    public ProdcastDTO saveNewCustomer(
            @FormParam("customerId") String customerId,
            @FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @FormParam("emailAddress") String emailAddress,
            @FormParam("cellPhone") String cellPhoneNumber,
            @FormParam("homePhoneNumber") String homePhoneNumber,
            @FormParam("billingAddress1") String billingAddress1,
            @FormParam("billingAddress2") String billingAddress2,
            @FormParam("billingAddress3") String billingAddress3,
            @FormParam("city") String city,
            @FormParam("state") String state,
            @FormParam("country") String country,
            @FormParam("postalCode") String postalCode,
            @FormParam("smsAllowed") String smsAllowed)

    {
        CustomerListDTO dto = new CustomerListDTO();
        try {
            int result;
                System.out.println("CustomerId="+customerId);
                if(customerId == null || customerId.trim().length() == 0 ) {

                    result = databaseManager.createNewCustomer(firstName, lastName,
                            emailAddress, cellPhoneNumber, homePhoneNumber, billingAddress1,
                            billingAddress2, billingAddress3, city,
                            state, country, postalCode, smsAllowed);
              System.out.println("+++++++++++++++++++++++++");
            System.out.println("NEW Customer Creation Page");
            System.out.println("+++++++++++++++++++++++");

            /* result = databaseManager.createCustomer(-1,firstName+" "+lastName,"W",-1,null, firstName, lastName,
                    emailAddress, cellPhoneNumber, homePhoneNumber, null, billingAddress1,
                    billingAddress2, billingAddress3, city,
                    state, country, postalCode, null, null, null, null, null, smsAllowed, "true", -1);*/

            System.out.println("+++++++++++++++++++++++++");
            System.out.println("NEW Customer Created Successfully");
            System.out.println("+++++++++++++++++++++++");


                }
                else{
                    System.out.println("+++++++++++++++++++++++++");
                    System.out.println("update NEW RESULT BEFORE Customer Creation Page");
                    System.out.println("+++++++++++++++++++++++");
                    result = databaseManager.updateNewCustomerRegistrationDetails(Long.parseLong(customerId),firstName, lastName,
                            emailAddress,cellPhoneNumber, homePhoneNumber, billingAddress1,
                            billingAddress2, billingAddress3, city,
                            state, country, postalCode, smsAllowed);
                    System.out.println("+++++++++++++++++++++++++");
                    System.out.println("Update New Customer Creation Page");
                    System.out.println("+++++++++++++++++++++++");

                }

            if (result != 1) {
                dto.setError(true);
                dto.setErrorMessage("Unable to save the Customer");
            }
           // NewCustomerRegistrationDetails saveNewCustRegDetails = databaseManager.fetchUpdateSaveNewcustomer(cellPhoneNumber);
            // System.out.print(saveNewCustRegDetails);
            System.out.println("+++++++++++++++++++++++++");
            System.out.println("last Page");
            System.out.println("+++++++++++++++++++++++");

           // dto.setResult(saveNewCustRegDetails);





        }

        catch(Exception er)
        {
            dto.setError( true );

            dto.setErrorMessage( er.toString());
            er.printStackTrace();
        }

        return  dto;
    }
  /*  @POST
    @Path("updateNewCustomerRegistrationDetails")
    @Produces(MediaType.APPLICATION_JSON)

    public CustomerListDTO updateNewCustomerRegistrationDetails(
            @FormParam("customerId") String customerId,
            @FormParam("cellPhone") String cellPhoneNumber,
            @FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @FormParam("emailAddress") String emailAddress,

            @FormParam("homePhoneNumber") String homePhoneNumber,
            @FormParam("billingAddress1") String billingAddress1,
            @FormParam("billingAddress2") String billingAddress2,
            @FormParam("billingAddress3") String billingAddress3,
            @FormParam("city") String city,
            @FormParam("state") String state,
            @FormParam("country") String country,
            @FormParam("postalCode") String postalCode,

            @FormParam("smsAllowed") String smsAllowed)

    {
        CustomerListDTO dto = new CustomerListDTO();
        try {
         int result=0;

           // String customerDistId=databaseManager.fetchCustomerDistId(cellPhoneNumber);
           // if(customerDistId != )

            if (customerId !=null)
            {
                result = databaseManager.updateNewCustomerRegistrationDetails(customerId,firstName, lastName,
                        emailAddress,cellPhoneNumber, homePhoneNumber, billingAddress1,
                        billingAddress2, billingAddress3, city,
                        state, country, postalCode, smsAllowed);

            }

        if ((result  == 0)) {
            dto.setError(true);
            dto.setErrorMessage("Unable to update the Customer");
        }
        else

        {

            NewCustomerRegistrationDetails updateNewCustRegDetails=databaseManager.fetchUpdatedSaveNewcustomer(cellPhoneNumber,customerId);

            dto.setResult(updateNewCustRegDetails);

        }

    }
        catch(Exception er)
        {
            dto.setError( true );
            dto.setErrorMessage( er.toString());
            er.printStackTrace();
        }

        return  dto;

    }*/
    @GET
    @Path("getNewCustomerRegistrationDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerListDTO getNewCustomerRegistrationDetails(@QueryParam("accessId")long accessId) {
        //AdminDTO dto = new AdminDTO();
        CustomerListDTO dto = new CustomerListDTO();
        NewCustomerRegistrationDetails getNewCustRegDetails;
        try {

            getNewCustRegDetails=databaseManager.fetchSaveNewcustomer(accessId);


        }
        catch(Exception er){
            getNewCustRegDetails=null;
           // er.printStackTrace();
           // dto.setError( true );
          //  dto.setErrorMessage( er.toString() );
        }
        dto.setResult(getNewCustRegDetails);
        return dto;
    }


    @GET
    @Path("getDistributorList")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerLoginDTO getDistributorList(@QueryParam("accessId") long accessId) {
        CustomerLoginDTO dto = new CustomerLoginDTO();


        try {

            List<Distributor> distributor = databaseManager.getAllDistributorsForCustomers(accessId);
            dto.setDistributors(distributor);
            List<Distributor> openDistributors = databaseManager.getAllDistributorsIfOpenToPublic(accessId);
            dto.setDistributorsPublic(openDistributors);
        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }
        return dto;
    }


}