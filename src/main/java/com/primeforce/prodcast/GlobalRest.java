
package com.primeforce.prodcast;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.primeforce.prodcast.businessobjects.*;
import com.primeforce.prodcast.businessobjects.Collection;
import com.primeforce.prodcast.dao.DatabaseManager;
import com.primeforce.prodcast.businessobjects.Distributor;
import com.primeforce.prodcast.dto.*;
import com.primeforce.prodcast.messaging.MessagingManager;
import com.primeforce.prodcast.messaging.OrderDataProvider;
import com.primeforce.prodcast.util.Amazon;
import com.primeforce.prodcast.util.Notifier;
import com.primeforce.prodcast.util.encryptLoginDetails;
import com.primeforce.prodcast.util.decryptCookie;

import jdk.nashorn.internal.parser.JSONParser;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sarathan732 on 4/22/2016.
 */
@Named
@Path("/global/")
public class GlobalRest {


    private final DatabaseManager databaseManager;

    @Autowired
    public GlobalRest(DatabaseManager manager) {
        databaseManager = manager;
    }

    @GET
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public LoginDTO authenticate(@QueryParam("userid") String id, @QueryParam("password") String password) {

        LoginDTO dto = new LoginDTO();
        Employee employee = null;

        try {
            employee = databaseManager.login(id,password);
            if( employee == null ) dto.setSuccess( false );
            else dto.setSuccess(true);
            dto.setEmployee(employee);
        } catch (Exception er) {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage( er.toString() );
        }
        return dto;
    }

    @POST
    @Path("loginp")
    @Produces(MediaType.APPLICATION_JSON)
    public LoginDTO authenticatePost(@FormParam("userid") String id, @FormParam("password") String password) {

        LoginDTO dto = new LoginDTO();
        Employee employee = null;

        try {
            employee = databaseManager.login(id,password);
            if( employee == null ) dto.setSuccess( false );
            else {
                dto.setSuccess(true);
               /* final long DAY = 24*60*60*1000;
                long dateTime=Calendar.getInstance().getTime().getTime() + 5* DAY;
                String successmsg="userId="+employee.getEmployeeId()+"; Expiration Date Time="+dateTime +";";
                //System.out.println(successmsg);
                String encodedText=new encryptLoginDetails().encrypt(successmsg);
                System.out.println(encodedText);
                dto.setAuthenticationKey(encodedText);*/
            }

            dto.setEmployee(employee);
        } catch (Exception er) {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage( er.toString() );
        }
        return dto;
    }


    @POST
    @Path("saveRegistration")
    @Produces(MediaType.APPLICATION_JSON)
    public RegistrationDTO registerPost(@FormParam("firstName") String firstName,
                                 @FormParam("lastName") String lastName,

                                 @FormParam("country") String country,
                                 @FormParam("emailId") String emailId,
                                 @FormParam("cellPhone") String cellPhone){

        RegistrationDTO dto = new RegistrationDTO();
         List<Registration> registration = null;

        try {

            registration= databaseManager.register(firstName, lastName, country, emailId, cellPhone);
            dto.setResult(registration);
            Distributor distributor=databaseManager.fetchSuperAdminDetails();
            String email=distributor.getEmailAddress();
            String isdCode=databaseManager.fetchSuperAdminCountryId(distributor.getCountry());
            String phoneNumber=isdCode+distributor.getCellPhone();
            String[] emailds = { email};
            String message="The following potential customer is interested in knowing about prodcast.\n" +
                            "Name : "+firstName+" "+lastName+"\n"+
                            "Phone no : "+cellPhone+"\n" +
                            "Email id : " +emailId+"\n"+
                            "Of the interested person";
            Notifier.sendNotification(phoneNumber, "New User Request From Prodcast" , message , emailds );

        }
        catch (Exception er) {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage( er.toString() );
        }
        return dto;
    }


    @GET
    @Path("areas")
    @Produces(MediaType.APPLICATION_JSON)
    public AreaDTO getAreas(@QueryParam("employeeId") String employeeId) {

        AreaDTO dto = new AreaDTO();
        try {
            List<Area> areas = databaseManager.fetchAreasForEmployee(Long.parseLong(employeeId));
            dto.setAreas(areas);
        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString() );
        }
        return dto;

    }




    @GET
    @Path("customers")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerListDTO getCustomerList(@QueryParam("employeeId") String employeeId) {
        CustomerListDTO dto = new CustomerListDTO();

        try {
            List customers = databaseManager.fetchCustomers(Long.parseLong(employeeId));
            dto.setCustomerList(customers);
            List outstandingBills=databaseManager.fetchOutstandingBillsForCustomers(Long.parseLong(employeeId));
            dto.setOutstandingBills(outstandingBills);
        }
        catch(Exception er ){
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage( er.toString() );
        }
        return dto;
    }

    @GET
    @Path("customer")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerDTO getCustomer(@QueryParam("id") String id,@QueryParam("employeeId") String employeeId) {
        CustomerDTO dto = new CustomerDTO();

        try {
            Customer customer = null;
            customer = databaseManager.getCustomer(id);
            dto.setCustomer( customer );
            if( employeeId!=null && employeeId.trim().length()>0)
                customer.setOutstandingBill( databaseManager.fetchOutstandingBillsForDistOnly( id , employeeId ));
            else
                customer.setOutstandingBill( databaseManager.fetchOutstandingBills( id ));
        }
        catch(Exception er ){
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage( er.toString() );
        }
        return dto;
    }

    @GET
    @Path("products")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductListDTO getProductList(@QueryParam("employeeId") String employeeId) {
        ProductListDTO dto = new ProductListDTO();
        try {
            List<Product> list = databaseManager.fetchProductsForDistributor(Long.parseLong( employeeId));
            dto.setProductList( list );
            dto.setProductOptionsList(databaseManager.fetchProductOptionsForDistributor(Long.parseLong( employeeId)));
            dto.setProductFlavorsList(databaseManager.fetchProductFlavorsForDistributor(Long.parseLong( employeeId)));
        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }
        return dto;
    }

    @GET
    @Path("billdetails")
    @Produces(MediaType.APPLICATION_JSON)
    public OrderDTO getBillDetails(@QueryParam("billId") long id,@QueryParam("employeeId") long employeeId,@QueryParam("userRole") String userRole) {
        OrderDTO dto = new OrderDTO();

        try {
            Order order = databaseManager.fetchOrder( id,employeeId);

            if(userRole.equals("S"))
            {
                if(order.getEmployeeId()!=employeeId)
                    order=null;

            }
            if(order==null)
            {
                dto.setError(true);
                dto.setErrorMessage("You Do Not Have Permission To View This Bill");
                return dto;
            }
            else
            {
                dto.setOrder(order);
            }

        }
        catch(Exception er){
            er.printStackTrace();

            dto.setError( true );

            dto.setErrorMessage( "The Bill Does Not Exist For Your Company" );
        }
        return dto;
    }


    @POST
    @Path("collection")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerDTO addCollectionPayment(@FormParam("employeeId") String employeeId,
                                            @FormParam("billId") String billId,
                                            @FormParam("amount") String amount ,
                                            @FormParam("customerId") String customerId,
                                            @FormParam("refNo")String refNo,
                                            @FormParam("refDetail")String refDetail,@FormParam("paymentType")String paymentType)
    {
        CustomerDTO dto = new CustomerDTO();
        try {
           int rowCount =  databaseManager.updateCollectionPayment(Long.parseLong(employeeId), Long.parseLong(billId), Float.parseFloat(amount),refNo,refDetail,paymentType);
            if( rowCount == 0 ) {
                dto.setError(true);
                dto.setErrorMessage("Unable to update collection. Please try again!");
            }
            else{
                Customer customer  = null;
                if( customerId!= null && customerId.trim().length()>0 ) {
                    customer = databaseManager.getCustomer(customerId);
                    customer.setOutstandingBill(  databaseManager.fetchOutstandingBillsForCustomer(customerId,employeeId ));
                }
                else {
                    customer = databaseManager.getCustomerForBillId(billId);
                    customer.setOutstandingBill( databaseManager.fetchOutstandingBillsForCustomers(Long.parseLong( employeeId)));
                }
                //long billNumber=
                dto.setCustomer( customer );
                dto.setBill(databaseManager.fetchOrder(Long.parseLong(billId),Long.parseLong(employeeId)));
                try {
                    Order order;
                    OrderDataProvider orderDataProvider = new OrderDataProvider();
                    orderDataProvider.setBillNo(Long.parseLong(billId));
                    orderDataProvider.setEmployeeId(Long.parseLong(employeeId));
                    orderDataProvider.setType(1);
                    orderDataProvider.setAmountPaid(Float.parseFloat(amount));
                    MessagingManager msgManager = new MessagingManager();
                    String mailMessage = msgManager.mailMerge(0, orderDataProvider,databaseManager);
                    String subject = orderDataProvider.getSubject();
                    order = orderDataProvider.getOrder();
                    //Amazon.sendSMS(subject, orderDataProvider.getSmsPhoneNumber());
                    String[] emailds = { order.getCustomerEmail() , order.getEmployeeEmail(), order.getDistributorEmail()  };
                    Notifier.sendNotification( orderDataProvider.getSmsPhoneNumber() , subject , mailMessage , emailds );
                }
                catch(Exception er){
                    er.printStackTrace();
                }


            }
        }
        catch(Exception er){
            dto.setError( true );
            dto.setErrorMessage( er.toString());
            er.printStackTrace();
        }

        return  dto;
    }

    @POST
    @Path("saveCustomer")
    @Produces(MediaType.APPLICATION_JSON)
    public ProdcastDTO saveCustomer(@FormParam("employeeId") String employeeId,
                                    @FormParam("customerName") String customerName,
                                    @FormParam("customerType") String customerType ,
                                    @FormParam("areaId") String areaId,
                                    @FormParam("weekDay") String weekDay,
                                    @FormParam("firstName") String firstName,
                                    @FormParam("lastName") String lastName,
                                    @FormParam("emailAddress") String emailAddress,
                                    @FormParam("cellPhone") String cellPhoneNumber,
                                    @FormParam("phoneNumber") String phoneNumber,
                                    @FormParam("unitNumber") String unitNumber,
                                    @FormParam("billingAddress1") String billingAddress1,
                                    @FormParam("billingAddress2") String billingAddress2,
                                    @FormParam("billingAddress3") String billingAddress3,
                                    @FormParam("city") String city,
                                    @FormParam("state") String state,
                                    @FormParam("country") String countryId,
                                    @FormParam("smsAllowed") String smsAllowed,
                                    @FormParam("postalCode") String postalCode,
                                    @FormParam("notes") String notes ,
                                    @FormParam("customerId1") String customerid1 ,
                                    @FormParam("customerId2") String secondId ,
                                    @FormParam("customerDesc1") String desc1 ,
                                    @FormParam("customerDesc2") String desc2 ,
                                    @FormParam("customerId") String customerId,
                                    @FormParam("active") String active,
                                    @FormParam("storeTypeId") String storeType)
    {



        CustomerListDTO dto = new CustomerListDTO();

        try {

                int rowCount = 0;
                if (customerId == null || customerId.trim().length() == 0) {
                    String custcellphone = databaseManager.fetchDistributorCustMobileNumber(cellPhoneNumber, employeeId);
                    if (custcellphone == null) {

                        rowCount = databaseManager.createCustomer(
                                Long.parseLong(employeeId), customerName, customerType,
                                Long.parseLong(areaId), weekDay, firstName, lastName,
                                emailAddress, cellPhoneNumber, phoneNumber, unitNumber, billingAddress1,
                                billingAddress2, billingAddress3, city,
                                state, countryId, postalCode, notes, customerid1, desc1, secondId, desc2, smsAllowed, active, Long.parseLong(storeType));


                        List customerData = databaseManager.fetchMobileNumbers(countryId, cellPhoneNumber);
                        if (customerData.size() == 0) {

                            Random random = new Random();
                            int code = (100000 + random.nextInt(900000));
                            int password = (100000 + random.nextInt(900000));
                            String pinNumber = Integer.toString(password);


                            CustomerRegistration register = databaseManager.registerCustomer(countryId, cellPhoneNumber, pinNumber, code);

                            String cellNumber = databaseManager.fetchCustomerCountryId(countryId) + cellPhoneNumber;

                            String subject = " PRODCAST -Verfication code is:" + code + " and your password to login : " + pinNumber + "\n";
                            Amazon.sendSMS(subject, cellNumber);


                        }
                    } else {
                        dto.setError(true);
                        dto.setErrorMessage("Different Customers Should Have Unique Numbers!");

                    }
                } else {
                    rowCount = databaseManager.updateCustomer(customerName, customerType, Long.parseLong(areaId), weekDay, firstName,
                            lastName, emailAddress, cellPhoneNumber,
                            phoneNumber, unitNumber, billingAddress1,
                            billingAddress2, billingAddress3, city, state, countryId, postalCode, customerid1, desc1, secondId, desc2, smsAllowed, active, Long.parseLong(employeeId), Long.parseLong(storeType), Long.parseLong(customerId));


                }


                if (rowCount == 0) {
                    dto.setError(true);
                    dto.setErrorMessage("Unable to update collection. Please try again!");
                } else {
                    dto.setCustomerList(databaseManager.fetchCustomers(Long.parseLong(employeeId)));
                }
        }
        catch(Exception er){

            dto.setError( true );
            dto.setErrorMessage( er.toString());
            er.printStackTrace();
        }

        return  dto;
    }

    @POST
    @Path("saveOrder")
    @Produces(MediaType.APPLICATION_JSON)




    public CustomerDTO saveOrder(@RequestBody OrderDetailDTO orderDto) {

        System.out.println(orderDto );
        CustomerDTO dto = new CustomerDTO();
        long start = System.currentTimeMillis();

        try {
            //Map OrderDetailDTO to Business Object
            Order order = new Order();
            order.setCustomerId( Long.parseLong( orderDto.getCustomerId()) );
            order.setEmployeeId( Long.parseLong( orderDto.getEmployeeId()) );
            try {
                order.setDiscount(Float.parseFloat(orderDto.getDiscountValue()));
                order.setDiscountType(Integer.parseInt(orderDto.getDiscountType()));
            }
            catch(Exception er){
                //NumberFormatException - Ignore.
            }
            List<OrderEntry> orderEntries = new LinkedList<OrderEntry>();
            order.setOrderEntries( orderEntries );
            HashMap<Long,List<OrderEntry>> map=new HashMap<Long, List<OrderEntry>>();

            for (OrderEntryDTO entryDto:orderDto.getEntries()) {
                OrderEntry entry = new OrderEntry();
                entry.setQuantity(Integer.parseInt( entryDto.getQuantity()));
                entry.setProductId( Long.parseLong( entryDto.getProductId() ) );
                entry.setOptionId(entryDto.getOptionId());
                entry.setFlavorId(entryDto.getFlavorId());
               List<OrderEntry> newEntriesList = new LinkedList<OrderEntry>();
               boolean alreadyExist=false;

                if(map.containsKey(entry.getProductId())) {

                    List<OrderEntry> existing = map.get(entry.getProductId());
                    newEntriesList.addAll(existing);

                    for (OrderEntry entries : existing) {
                        if (entries.getOptionId() > 0 && entries.getFlavorId() > 0) {
                            if ((entries.getOptionId()==entry.getOptionId()) && (entries.getOptionId()==entry.getFlavorId())) {
                                entries.setQuantity(entries.getQuantity() + entry.getQuantity());
                                alreadyExist=true;
                                break;
                            }
                        } else if (entries.getOptionId() > 0) {
                            if (entries.getOptionId()==entry.getOptionId()) {
                                entries.setQuantity(entries.getQuantity() + entry.getQuantity());
                                alreadyExist=true;
                                break;
                            }
                        }
                        else if (entries.getFlavorId() > 0) {
                            if (entries.getFlavorId()==entry.getFlavorId()) {
                                entries.setQuantity(entries.getQuantity() + entry.getQuantity());
                                alreadyExist=true;
                                break;
                            }
                        }
                        else {
                            entries.setQuantity(entries.getQuantity() + entry.getQuantity());
                            alreadyExist=true;
                            break;
                        }
                    }
                }
                if(!alreadyExist)
                {
                    newEntriesList.add(entry);
                    map.put(entry.getProductId(),newEntriesList);
                    orderEntries.add(entry);
                }
            }

            float paymentAmount = Float.parseFloat( orderDto.getPaymentAmount());
            String refNo=orderDto.getRefNO();
            String refDetail=orderDto.getRefDetail();
            String paymentType=orderDto.getPaymentType();
            String orderStatus=orderDto.getOrderStatus();

            long billNumber = databaseManager.saveOrder(order,paymentAmount,refNo,refDetail,paymentType,orderStatus,orderDto.getShippingType(),orderDto.getDeliveryAddress());
            System.out.println("Order Saved "+(System.currentTimeMillis() - start ));
            Customer customer  = databaseManager.getCustomer( orderDto.getCustomerId() );
            boolean openToPublic=databaseManager.isOpenToPublic(order.getEmployeeId());
            if(openToPublic)
                customer.setOutstandingBill(databaseManager.fetchOutstandingBillsForDistOnly(orderDto.getCustomerId(),orderDto.getEmployeeId()));
            else
                customer.setOutstandingBill(  databaseManager.fetchOutstandingBills(orderDto.getCustomerId() ));
            dto.setCustomer( customer );
            dto.setBill(databaseManager.fetchOrder(billNumber,Long.parseLong(orderDto.getEmployeeId())));
            System.out.println("After Outstanding Bill "+(System.currentTimeMillis() - start ));
            try {
                OrderDataProvider orderDataProvider = new OrderDataProvider();
                orderDataProvider.setBillNo(billNumber);

                orderDataProvider.setEmployeeId(order.getEmployeeId());
                orderDataProvider.setAmountPaid(paymentAmount);
                MessagingManager msgManager = new MessagingManager();
                String mailMessage = msgManager.mailMerge(0, orderDataProvider,databaseManager);
                String subject = orderDataProvider.getSubject();
                order = orderDataProvider.getOrder();
                    //Amazon.sendSMS(subject, orderDataProvider.getSmsPhoneNumber());
                String[] emailds = { order.getCustomerEmail() , order.getEmployeeEmail(), order.getDistributorEmail()  };
                Notifier.sendNotification( orderDataProvider.getSmsPhoneNumber() , subject , mailMessage , emailds );
            }
            catch(Exception er){
                er.printStackTrace();
            }
            System.out.println("AFter Notification "+(System.currentTimeMillis() - start ));

            System.out.println("AFter Notification "+(System.currentTimeMillis() - start ));



        }
        catch (Exception er) {
                er.printStackTrace();
                dto.setError(true);
                dto.setErrorMessage( er.toString() );
        }
        return dto;
    }

    @GET
    @Path("retrievePassword")
    @Produces(MediaType.APPLICATION_JSON)
    public ProdcastDTO retrievePassword(@QueryParam("emailId") String emailId) throws Exception{

        ProdcastDTO dto = new ProdcastDTO();
        try {
            if(emailId != null )
            {
                emailId = emailId.toLowerCase();

                String password = databaseManager.getPasswordFromEmail(emailId);

                if (password != null)
                {
                    Amazon.sendEmail(emailId, "Password email from PRODCAST", "Your password is "+password+"\r\n");
                }
                else {
                    dto.setError(true);
                    dto.setErrorMessage("The email id is not registered with PRODCAST");
                }
            }
        } catch (Exception er){
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString());
        }

        return dto;


    }




    @POST
    @Path("changePassword")
    @Produces(MediaType.APPLICATION_JSON)
    public ProdcastDTO changePasswordAuth(@FormParam("employeeId") String employeeId, @FormParam("oldPassword") String oldPassword, @FormParam("newPassword") String newPassword) throws Exception{

        ProdcastDTO dto = new ProdcastDTO();
        try {
            Employee employee=databaseManager.getEmployee(Long.parseLong(employeeId));
            System.out.println(employee);
            String email = databaseManager.changePassword(Long.parseLong(employeeId) , oldPassword , newPassword );
            String[] emailIds={email};

            String phoneNumber=employee.getCountryCode()+employee.getCellphone();
            if( email == null ){
                dto.setError(true);
                dto.setErrorMessage("The Old Password did not match. Please reenter old password again");

            }
            else {
                //        Amazon.sendEmail(email,"Password changed email from PRODCAST" , "Your password has been changed in PRODCAST\r\n");
                //   Amazon.sendSMS("Your password has been changed in PRODCAST\r\n",phoneNumber);
                Notifier.sendNotification( phoneNumber , "Your password has been changed in PRODCAST\r\n" , "Password Changed Notification From PRODCAST",emailIds );

            }
        } catch (Exception er){
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString());
        }

        return dto;


    }




    @GET
    @Path("salesReport")
    @Produces(MediaType.APPLICATION_JSON)
    public ReportDTO getReport(@QueryParam("reportType") String reportType,
                               @QueryParam("employeeId") String employeeId,
                               @QueryParam("selectedEmpId") String selectedEmployeeId,
                               @QueryParam("startDate") String customStartDate ,
                               @QueryParam("endDate") String customEndDate ) {
        ReportDTO dto = new ReportDTO();
        final long DAY = 24*60*60*1000;
        try {

            Calendar cal = Calendar.getInstance();
            java.sql.Date startDate = java.sql.Date.valueOf("2016-04-20");
            java.sql.Date endDate =new java.sql.Date(Calendar.getInstance().getTime().getTime());

            if( reportType.equals("today")){
                startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                endDate = startDate;
            }
            else if( reportType.equals("yesterday")){
                startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime() -DAY );
                endDate = startDate;
            }
            else if ( reportType.equals("week")){
                endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                cal.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY );
                startDate = new java.sql.Date(cal.getTime().getTime());
            }
            else if ( reportType.equals("last7")){
                endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                startDate = new java.sql.Date( Calendar.getInstance().getTime().getTime() - 7*DAY );
            }
            else if ( reportType.equals("month")){
                endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                cal.set(Calendar.DAY_OF_MONTH , 1 );
                startDate = new java.sql.Date(cal.getTime().getTime());
            }

            else if ( reportType.equals("last30")){
                endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                startDate = new java.sql.Date( Calendar.getInstance().getTime().getTime() - 30*DAY );
            }
            else{
                startDate = new java.sql.Date( new SimpleDateFormat(DatabaseManager.DATE_FORMAT).parse(customStartDate).getTime());
                endDate = new java.sql.Date( new SimpleDateFormat(DatabaseManager.DATE_FORMAT).parse(customEndDate).getTime());

            }
            System.out.println("Reporting on "+startDate+" to "+endDate );
            if( startDate == endDate || startDate.equals( endDate)) {
                dto.setReportDates(new SimpleDateFormat(DatabaseManager.DATE_FORMAT).format(startDate));
            }
            else{
                dto.setReportDates(new SimpleDateFormat(DatabaseManager.DATE_FORMAT).format(startDate)+"-"+new SimpleDateFormat(DatabaseManager.DATE_FORMAT).format(endDate));
            }


            List<Order> orders = null;
            List<Collection> collections = null;
            List<Expense> expenses = null;
            if( selectedEmployeeId!= null && selectedEmployeeId.equals("ALL") ){
              orders = databaseManager.fetchSalesReportForDistributor(startDate, endDate , Long.parseLong(employeeId ));
                collections = databaseManager.fetchCollectionReportForDistributor(startDate, endDate , Long.parseLong(employeeId ));
                expenses = databaseManager.fetchExpenseReportForDistributor( startDate,  endDate , employeeId);
            }

            else {
                if(selectedEmployeeId != null ){
                    employeeId = selectedEmployeeId;
                }

                orders = databaseManager.fetchSalesReport(startDate, endDate, Long.parseLong(employeeId));
                collections = databaseManager.fetchCollectionReport(startDate, endDate, Long.parseLong(employeeId));
                expenses = databaseManager.fetchExpenseReportForDistributor( startDate,  endDate , employeeId);
            }
            dto.setExpenses( expenses);
            float totalSales =0;
            float totalBalance = 0;
            for (Order order:orders
                 ) {
                    totalSales+=order.getTotalAmount();
                totalBalance+=order.getOutstandingBalance();
            }
            dto.setBalance( totalBalance );
            Map<String,Float> collectionGroup = new HashMap<String, Float>();
            float totalCollection = 0;
            for (Collection collection:collections
                 ) {
                totalCollection += collection.getAmountPaid();
                String paymentType = collection.getPaymentType();
                if( paymentType == null || paymentType.trim().length()==0) paymentType = "CASH";
                collection.setPaymentType( paymentType );
                if( collectionGroup.containsKey(paymentType )){
                    float val = collectionGroup.get(paymentType);
                    val+=collection.getAmountPaid();
                    collectionGroup.put(paymentType , val );
                }
                else
                    collectionGroup.put(paymentType , collection.getAmountPaid() );
            }

            dto.setTotalSales( totalSales );
            dto.setTotalCollection( totalCollection);
            dto.setCollectionGroup( collectionGroup );
            dto.setOrders( orders );
            dto.setCollections( collections );

        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }
        return dto;
    }
    @GET
    @Path("getCountries")
    @Produces(MediaType.APPLICATION_JSON)
    public CountryDTO getCountries() {
        CountryDTO dto = new CountryDTO();

        try {
            List countries = databaseManager.fetchCountries();
            dto.setResult( countries );
            List timezones=databaseManager.getTimezones();
            dto.setTimezones(timezones);
        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }
        return dto;
    }
    @GET
    @Path("getStoreType")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO getStoreType() {
        AdminDTO dto = new AdminDTO();

        try {
            List storeTypes = databaseManager.fetchStoreType();
            dto.setResult( storeTypes );

        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }
        return dto;
    }


}


