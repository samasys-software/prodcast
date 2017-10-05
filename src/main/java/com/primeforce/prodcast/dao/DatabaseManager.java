package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.*;
import com.primeforce.prodcast.businessobjects.Collection;
import com.primeforce.prodcast.util.TimeZoneConvertor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sarathan732 on 4/22/2016.
 */
@Component ("DatabaseManager")
public class DatabaseManager {

    public final static String DATE_FORMAT = "dd/MM/yyyy";
    private final JdbcTemplate template;

    @Autowired
    public DatabaseManager(JdbcTemplate template) {
        this.template = template;
    }

    public Employee login(String userId, String password) {
        //return (Employee)template.queryForObject(DBSql.LOGIN_SQL, new Object[]{userId, password }, new EmployeeMapper());
        return (Employee) template.queryForObject(DBSql.LOGIN_SQL_NEW, new Object[]{userId, password}, new EmployeeMapper());
    }

    public CustomersLogin getDistributorsForCustomers(long accessId) {
        return template.queryForObject(DBSql.GET_LOGIN_FOR_CUSTOMER, new Object[]{accessId}, new CustomersLoginMapper());


    }

    public String changePinNumber(long accessId, String oldPinNumber, String newPinNumber) {
        int rowcount = template.update(DBSql.CUS_CHANGE_PINNUMBER, new Object[]{newPinNumber, accessId, oldPinNumber});
        if (rowcount == 0) {
            return null;
        }
        return template.queryForObject(DBSql.GET_CUSTOMER_PHONENUMBER, new Object[]{accessId}, String.class);
    }

    public List<Distributor> getAllDistributorsForCustomers(Long accessId) {

        return template.query(DBSql.GET_DISTRIBUTORS_FOR_CUSTOMERS, new Object[]{accessId}, new DistributorMapper());
    }

    public List<CustomerRegistration> fetchMobileNumbers(String country, String mobilePhone)
    {
        Object[] args = new Object[]{country, mobilePhone};
        return template.query(DBSql.FETCH_ALL_MOBILE_NUMBERS, args, new CustomerRegistrationMapper());


    }

    public int generateConfirmationNumber()
    {
        Random random = new Random();
        int randomNumber = (100000 + random.nextInt(900000));
        return randomNumber;

    }


    public int updateConfirmationCode(int code, String accessId) {

        Object[] args = new Object[]{code, Long.parseLong(accessId)};
        return template.update(DBSql.RESEND_CONFIRMATION_CODE, args);

    }

    public String fetchMobileNumber(String accessId) {

        Object[] args = new Object[]{Long.parseLong(accessId)};
        return template.queryForObject(DBSql.FETCH_MOBILE_NUMBER_TO_RESEND_CODE, args, String.class);

    }

    public String fetchCountry(Long accessId) {
        Object[] args = new Object[]{accessId};
        return template.queryForObject(DBSql.FETCH_COUNTRY, args, String.class);

    }


    public String fetchIsdCode(String country) {
        Object[] args = new Object[]{country};
        return template.queryForObject(DBSql.FETCH_ISD_CODE, args, String.class);
    }


    public CustomerRegistration registerCustomer(String country, String mobilePhone, String pinNumber, int code) {


        Object[] args = new Object[]{country, mobilePhone, Long.parseLong(pinNumber), code};
        template.update(DBSql.CUSTOMER_REGISTRATION, args);
        return fetchMobileNumbers(country, mobilePhone).get(0);

    }

    public List<Registration> register(String firstName, String lastName, String country, String emailId, String cellPhone) {


        Object[] args = new Object[]{firstName, lastName, country, emailId, cellPhone};
        template.update(DBSql.NEW_REGISTRATION, args);
        return getAllRegistors();
    }

    public List<Registration> getAllRegistors() {
        return template.query(DistributorDBSql.GET_ALL_REGISTORS, (Object[]) null, new RegistrationMapper());
    }

    public int updateRegistration(Long registrationId, String status, String comments) {
        return template.update(DistributorDBSql.UPDATE_REGISTRATION_SQL, new Object[]{status, comments, registrationId});

    }

    public String fetchCustomerCountryId(String countryId) {
        return template.queryForObject(DBSql.FETCH_NEW_REGISTRATION_COUNTRY, new Object[]{countryId}, String.class);
    }

    public List getTimezones() {
        //return template.query(DBSql.COUNTRY_SQL, (Object[])null,new CountryMapper());
        return template.query(DBSql.COUNTRY_FOR_TIMEZONE, (Object[]) null, new DBRowColumnMapper());

    }

    public List<Customer> fetchCustomers(long employeeId) {

        String userRole = template.queryForObject(DBSql.GET_EMP_ROLE, String.class, new Object[]{employeeId});
       boolean openToPublic=isOpenToPublic(employeeId);
        if (!openToPublic) {
            if (userRole.equals("S")) {
                return template.query(DBSql.CUSTOMER_SEARCH_SQL, new Object[]{employeeId}, new CustomerMapper(false));
            } else {
                return template.query(DBSql.CUSTOMER_SEARCH_SQL_D, new Object[]{employeeId}, new CustomerMapper(false));
            }
        } else {
            return template.query(DBSql.CUSTOMER_SEARCH_SQL_PUBLIC, new Object[]{employeeId}, new CustomerMapper(false));
        }
        // return  fetchStoreType();

    }


    public List<Bill> fetchOutstandingBillsForCustomers(long employeeId) {

        //boolean openToPublic = template.queryForObject( DBSql.GET_EMP_DIST_PUBLIC, Boolean.class , new Object[]{employeeId});

        return template.query(DBSql.CUSTOMERS_OUTSTANDING_BILL_SEARCH, new Object[]{employeeId}, new BillMapper());

    }

/*
    public int updateRegistration(String status,String comments)
    {
        return template.update( DistributorDBSql.UPDATE_REGISTRATION_SQL , new Object[]{status,comments });

    }*/


    public Distributor fetchSuperAdminDetails() {
        return (Distributor) template.queryForObject(DBSql.FETCH_ADMIN_DISTRIBUTORS, (Object[]) null, new DistributorMapper());
    }

    public String fetchSuperAdminCountryId(String countryId) {
        return template.queryForObject(DBSql.FETCH_ADMIN_DISTRIBUTORS_COUNTRY, new Object[]{countryId}, String.class);
    }

    public Customer getCustomer(String customerID) {
        return template.queryForObject(DBSql.CUSTOMER_SQL, new Object[]{Long.parseLong(customerID)}, new CustomerMapper(false));
    }

    public Customer getCustomerForBillId(String billId) {
        return template.queryForObject(DBSql.CUSTOMER_FROM_BILL_SQL, new Object[]{Long.parseLong(billId)}, new CustomerMapper(false));
    }

    public List<Product> fetchProducts(String startsWith) {
        return template.query(DBSql.PRODUCT_SEARCH_SQL + startsWith + "%'", (Object[]) null, new ProductMapper());
    }

    public List<Bill> fetchOutstandingBills(String customerID) {
        return template.query(DBSql.OUTSTANDING_BILL_SEARCH, new Object[]{Long.parseLong(customerID)}, new BillMapper());
    }

    public List<Bill> fetchOutstandingBillsForCustomer(String customerId,String employeeId) {
        return template.query(DBSql.OUTSTANDING_BILL_SEARCH_CUST, new Object[]{Long.parseLong(customerId),Long.parseLong(employeeId)}, new BillMapper());
    }


    public List<Bill> fetchOutstandingBillsForDistOnly(String customerID, String employeeId) {
        return template.query(DBSql.OUTSTANDING_BILL_SEARCH_PUBLIC, new Object[]{Long.parseLong(customerID), Long.parseLong(employeeId)}, new BillMapper());
    }

    public int returnProduct(Order order, long billNo, long productId, int quantity, String comments, long employeeId) {
        //return template.update( DistributorDBSql.RETURN_DETAILS_SQL , new Object[]{billNo,productId,quantity,comments,employeeId,billNo,productId,billNo,productId,billNo,productId});

        int result = template.update(DistributorDBSql.RETURN_DETAILS_SQL_SP, new Object[]{employeeId, billNo, productId, quantity, comments});
        template.update(DBSql.ORDER_TOTAL_SQL, new Object[]{order.getOrderId(), order.getOrderId(), order.getEmployeeId(), order.getOrderId()});

        if (order.getDiscountType() == 2) {
        } else if (order.getDiscountType() == 1) {
            template.update(DBSql.ORDER_UPDATE_DISCOUNT_VALUE, new Object[]{order.getDiscount(), order.getOrderId()});
        }
        template.update(DBSql.ORDER_UPDATE_DISCOUNT_PERCENTAGE, new Object[]{order.getDiscount(), order.getOrderId()});

        template.update(DBSql.ORDER_UPDATE_TOTAL_SQL, new Object[]{billNo, billNo});
        return result;

    }


    public int updateCollectionPayment(long dealerId, long billId, float amount, String refNo, String refDetail, String paymentType) {
        System.out.println("welcome pei ghost");
        Employee employee = getEmployee(dealerId);
        Date date = TimeZoneConvertor.getCurrentDateForTimeZone(employee.getTimezone());
        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
        System.out.println("TimeZone="+employee.getTimezone());
        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");

        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
        System.out.println("date="+date);
        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
      // Date date=new Date(2017-03-30);
        return template.update(DBSql.COLLECTION_UPDATE_SQL, new Object[]{dealerId, billId, amount, paymentType, refNo, refDetail, date});
    }

    public long saveOrder(Order order, float paymentAmount, String refNo, String refDetail, String paymentType, String orderStatus,String shippingType,String deliveryAddress) {

        //java.sql.Date date=order.getBillDate();
        //TimeZone sourceTimeZone=TimeZone.getDefault();
        //TimeZone targetTimeZone=TimeZone.getTimeZone(timezone);
        //one(date,sourceTimeZone,targetTimeZone));

        Employee employee = getEmployee(order.getEmployeeId());

        Date date = TimeZoneConvertor.getCurrentDateForTimeZone(employee.getTimezone());


        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
        System.out.println("TimeZone="+employee.getTimezone());
        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");

        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
        System.out.println("date="+date);
        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");

        SimpleJdbcCall call = new SimpleJdbcCall(template.getDataSource()).withProcedureName("sp_create_order");
        SqlParameterSource in = new MapSqlParameterSource().addValue("in_emp_id", order.getEmployeeId()).addValue("in_customer_id", order.getCustomerId()).addValue("in_bill_date", date);
        Map<String, Object> out = call.execute(in);

        long orderDetailId = (Long) out.get("out_orderdtl_id");
        long billNumber = (Long) out.get("out_bill_no");
        order.setOrderId(orderDetailId);
        order.setBillNumber(billNumber);
        updateOrderStatus(billNumber, orderStatus);
        String customerType = getCustomerType(order.getCustomerId());

        for (OrderEntry entry : order.getOrderEntries()
                ) {
            if (customerType.equals("W"))
                template.update(DBSql.ORDER_DETAILS_SQL, new Object[]{orderDetailId, entry.getProductId(), entry.getQuantity(), entry.getProductId(), entry.getProductId(), entry.getProductId()});
            else
                template.update(DBSql.ORDER_DETAILS_SQL_RETAILER, new Object[]{orderDetailId, entry.getProductId(), entry.getQuantity(), entry.getProductId(), entry.getProductId(), entry.getProductId()});

        }
        template.update(DBSql.ORDER_TOTAL_SQL, new Object[]{orderDetailId, orderDetailId, order.getEmployeeId(),shippingType,deliveryAddress, orderDetailId});

        saveDiscount(order.getDiscountType(),order.getDiscount(),orderDetailId);

        if (paymentAmount > 0) {
            updateCollectionPayment(order.getEmployeeId(), billNumber, paymentAmount, refNo, refDetail, paymentType);
        }
        return billNumber;
    }

    public int saveDiscount(int discountType,float discount,long orderDetailId){
        int result;
        Discount dis=template.queryForObject(DBSql.GET_DISCOUNT_DETAILS, new Object[]{orderDetailId}, new DiscountMapper());
        if(discount > dis.getTotalAmount()) {

            result=0;
        }
        else {


            if (dis.getDiscountType() == 0) {
                System.out.println("#####################");
                System.out.println("New Discount");
                System.out.println("#####################");
                if (discountType == 2) {
                    template.update(DBSql.ORDER_UPDATE_DISCOUNT_PERCENTAGE, new Object[]{discount, orderDetailId});

                } else if (discountType == 1) {
                    template.update(DBSql.ORDER_UPDATE_DISCOUNT_VALUE, new Object[]{discount, orderDetailId});
                }
                result = 1;


            } else {

                if (discountType != dis.getDiscountType()) {
                    result = 0;
                    System.out.println("#####################");
                    System.out.println("Not Equal");
                    System.out.println("#####################");

                } else {

                    System.out.println("#####################");
                    System.out.println("Equals");
                    System.out.println("#####################");

                    float newDiscount = dis.getDiscountValue() + discount;

                    if (discountType == 2) {
                        template.update(DBSql.ORDER_UPDATE_DISCOUNT_PERCENTAGE_NEW, new Object[]{newDiscount, discount, discount, orderDetailId});
                    } else if (discountType == 1) {
                        template.update(DBSql.ORDER_UPDATE_DISCOUNT_VALUE_NEW, new Object[]{newDiscount, discount, discount, orderDetailId});
                    }

                    result = 1;

                }
            }
        }
        return result;
    }

    public String getPasswordFromEmail(String emailId) {
        try {
            return template.queryForObject(DBSql.GET_EMPLOYEE_EMAIL_FROM_EMAIL, new Object[]{emailId}, String.class);
        } catch (Exception er) {
            System.out.println(er.toString());
            return null;
        }
    }

    public String changePassword(long employeeId, String oldPassword, String newPassword) {
        int rowcount = template.update(DBSql.EMP_CHANGE_PASSWORD, new Object[]{newPassword, employeeId, oldPassword});
        if (rowcount == 0) {
            return null;
        }

        return template.queryForObject(DBSql.GET_EMPLOYEE_EMAIL, new Object[]{employeeId}, String.class);
    }

    public int createCustomer(long employeeId, String customerName, String customerType, long areaId, String weekDay, String firstName,
                              String lastName, String emailAddress, String cellPhoneNumber,
                              String phoneNumber, String unitNumber, String billingAddress1,
                              String billingAddress2, String billingAddress3, String city, String state, String countryId, String postalCode, String notes,
                              String customerid1, String desc1, String secondId, String desc2, String smsAllowed, String active, long storeType)

    {


            Employee employee = getEmployee(employeeId);


            Date date = TimeZoneConvertor.getCurrentDateForTimeZone(employee.getTimezone());




        Object[] obj = new Object[]{employeeId, customerName, customerType,
                areaId, weekDay, firstName, lastName,
                emailAddress, cellPhoneNumber, phoneNumber,
                unitNumber, billingAddress1, billingAddress2,
                billingAddress3, city, state,
                countryId, postalCode, notes, customerid1,
                desc1, secondId, desc2,
                Boolean.parseBoolean(smsAllowed), Boolean.parseBoolean(active), employeeId, storeType,date};

        //for(int i=0;i<obj.length;i++) System.out.println(" "+(i+1)+" =>"+obj[i]);
        return template.update(DBSql.CREATE_CUSTOMER_SQL, obj);
    }

 /*   public int createNewCustomers(long employeeId, String customerName, String customerType, long areaId, String weekDay, String firstName,
                              String lastName, String emailAddress, String cellPhoneNumber,
                              String phoneNumber, String unitNumber, String billingAddress1,
                              String billingAddress2, String billingAddress3, String city, String state, String countryId, String postalCode, String notes,
                              String customerid1, String desc1, String secondId, String desc2, String smsAllowed, String active, long storeType)

    {






        Object[] obj = new Object[]{employeeId, customerName, customerType,
                areaId, weekDay, firstName, lastName,
                emailAddress, cellPhoneNumber, phoneNumber,
                unitNumber, billingAddress1, billingAddress2,
                billingAddress3, city, state,
                countryId, postalCode, notes, customerid1,
                desc1, secondId, desc2,
                Boolean.parseBoolean(smsAllowed), Boolean.parseBoolean(active), employeeId, storeType,date};
        System.out.println("+++++++++++++++++++++++++");
        System.out.println("NEW Customer");
        System.out.println("+++++++++++++++++++++++");

        //for(int i=0;i<obj.length;i++) System.out.println(" "+(i+1)+" =>"+obj[i]);
        return template.update(DBSql.CREATE_CUSTOMER_SQL, obj);
    }

*/
    public int updateCustomer(String customerName, String customerType, long areaId, String weekDay, String firstName,
                              String lastName, String emailAddress, String cellPhoneNumber,
                              String phoneNumber, String unitNumber, String billingAddress1,
                              String billingAddress2, String billingAddress3, String city, String state, String countryId,
                              String postalCode, String customerid1, String desc1, String secondId, String desc2,
                              String smsAllowed, String active,
                              long employeeId, long storeType, long customerId) {
        Object[] obj = new Object[]{customerName, customerType, areaId, weekDay, firstName, lastName,
                emailAddress, cellPhoneNumber, phoneNumber, unitNumber,
                billingAddress1, billingAddress2, billingAddress3, city, state, countryId,
                postalCode, customerid1, desc1, secondId, desc2, Boolean.parseBoolean(smsAllowed), Boolean.parseBoolean(active), employeeId, storeType, customerId};
        for (int i = 0; i < obj.length; i++) System.out.println(" " + (i + 1) + " =>" + obj[i]);
        return template.update(DBSql.UPDATE_CUSTOMER_SQL, obj);
    }


    public Order fetchOrder(long billId, long employeeId) {
        System.out.println("billId=" + billId);
        System.out.println("EmployeeId=" + employeeId);
        Order order = template.queryForObject(DBSql.FETCH_ORDER_SQL, new Object[]{billId, employeeId}, new OrderMapper());


        order.setOrderEntries(template.query(DBSql.FETCH_ORDER_DTL_SQL, new Object[]{order.getOrderId()}, new OrderDetailMapper()));

        order.setReturnEntries(template.query(DBSql.FETCH_RETURN_DTL_SQL, new Object[]{order.getOrderId()}, new OrderDetailMapper()));

        order.setCollectionEntries(template.query(DBSql.FETCH_ORDER_COLLECTION_SQL, new Object[]{billId}, new CollectionMapper()));
        order.setDistributor(getDistributor(order.getDistributorId()));
        //Distributor distributor = databaseManager.getDistributor(order.getDistributorId());
        order.setEmployee(getEmployee(order.getEmployeeId()));
        //Employee employee = databaseManager.getEmployee( order.getEmployeeId() );
        order.setCustomer(getCustomer("" + order.getCustomerId()));
        //Customer orderCustomer = databaseManager.getCustomer( ""+order.getCustomerId() );


        return order;
    }


    public List<Order> fetchSalesReport(java.sql.Date startDate, java.sql.Date endDate, long employeeId) {
        return template.query(DBSql.REPORT_SALES_SQL, new Object[]{startDate, endDate, employeeId}, new ReportOrderMapper());
    }

    public List<Collection> fetchCollectionReport(java.sql.Date startDate, java.sql.Date endDate, long employeeId) {
        return template.query(DBSql.REPORT_COLLECTION_SQL, new Object[]{startDate, endDate, employeeId}, new CollectionMapper());

    }

    public int deleteOrder(long billNo) {
        return template.update(DistributorDBSql.ORDER_DELETE_SQL, new Object[]{billNo});

    }

    public int updateOrderStatus(long billNo, String orderStatus) {
        return template.update(DistributorDBSql.ORDER_STATUS_UPDATE_SQL, new Object[]{orderStatus, billNo});

    }


    public List<Order> fetchSalesReportForDistributor(java.sql.Date startDate, java.sql.Date endDate, long employeeId) {
        return template.query(DBSql.REPORT_SALES_SQL_DISTRIBUTOR, new Object[]{startDate, endDate, employeeId}, new ReportOrderMapper());
    }

    public List<Collection> fetchCollectionReportForDistributor(java.sql.Date startDate, java.sql.Date endDate, long employeeId) {
        return template.query(DBSql.REPORT_COLLECTION_SQL_DISTRIBUTOR, new Object[]{startDate, endDate, employeeId}, new CollectionMapper());

    }


    public List<Employee> fetchEmployeesForDistributor(long employeeId) {
        return template.query(DistributorDBSql.DISTRIBUTOR_GET_EMP_SQL, new Object[]{employeeId}, new EmployeeMapper());
    }

    public List<Expense> fetchExpenseForDistributor(Date startDate, String employeeId) {

        Date endDate = new java.sql.Date(startDate.getTime() + 24 * 60 * 60 * 1000);
        return template.query(DistributorDBSql.DISTRIBUTOR_GET_EXP_SQL, new Object[]{employeeId, startDate, endDate}, new ExpenseMapper());

    }

    public List<Expense> fetchExpenseReportForDistributor(Date startDate, Date endDate, String employeeId) {

        return template.query(DistributorDBSql.DISTRIBUTOR_GET_EXP_SQL_REPORT, new Object[]{employeeId, startDate, endDate}, new ExpenseMapper());

    }

    public List<ReportType> fetchReportType() {

        return template.query(DistributorDBSql.REPORT_TYPE_SQL, (Object[]) null, new ReportTypeMapper());

    }


    public Long fetchEmailInUse(String emailId) {
        try {
            return template.queryForObject(DistributorDBSql.CHECK_EMAIL_UNIQUE, new Object[]{emailId}, Long.class);
        } catch (Exception er) {
            return null;
        }
    }

    public List<Brand> saveBrandForDistributor(long employeeId, String brandName) {
        int rowcount = template.update(DistributorDBSql.DISTRIBUTOR_CREATE_BRAND_SQL,
                new Object[]{brandName, employeeId, employeeId, employeeId});
        if (rowcount != 1) return null;
        return fetchBrandsForDistributor(employeeId);
    }


    public Long fetchBrandInUse(long employeeId, String brandName) {
        try {

            return template.queryForObject(DistributorDBSql.GET_BRAND_SQL, new Object[]{brandName, employeeId}, Long.class);
        } catch (Exception er) {
            return null;
        }
    }


    public Long saveBrandFromFile(long employeeId, String brandName) {

                int rowcount = template.update(DistributorDBSql.DISTRIBUTOR_CREATE_BRAND_SQL,
                        new Object[]{brandName, employeeId, employeeId, employeeId});
                if (rowcount != 1) return null;
                return fetchBrandInUse(employeeId,brandName);

    }

    public Long fetchCategoryInUse(long employeeId, String categoryName) {
        try {

            return template.queryForObject(DistributorDBSql.GET_CATEGORY_SQL, new Object[]{categoryName, employeeId}, Long.class);
        } catch (Exception er) {
            return null;
        }
    }


    public Long saveCategoryFromFile(long employeeId, String categoryName) {

        int rowcount = template.update(DistributorDBSql.DISTRIBUTOR_CREATE_CATEGORY_SQL, new Object[]{categoryName, employeeId, employeeId, employeeId, categoryName});
        if (rowcount != 1) return null;
        return fetchCategoryInUse(employeeId,categoryName);

    }



    public Long fetchSubCategoryInUse(long employeeId,long categoryId, String subCategoryName) {
        try {

            return template.queryForObject(DistributorDBSql.GET_SUB_CATEGORY_SQL, new Object[]{categoryId,subCategoryName, employeeId}, Long.class);
        } catch (Exception er) {
            return null;
        }
    }


    public Long saveSubCategoryFromFile(long employeeId, long categoryId, String subCategoryName) {

        int rowcount = template.update(DistributorDBSql.DISTRIBUTOR_CREATE_SUBCATEGORY_SQL, new Object[]{subCategoryName, employeeId, employeeId, employeeId, subCategoryName, categoryId});

        if (rowcount != 1) return null;
        return fetchSubCategoryInUse(employeeId,categoryId, subCategoryName);

    }

    public Long fetchProductInUse(long employeeId,String productSKU) {
        try {

            return template.queryForObject(DistributorDBSql.GET_PRODUCT_SQL, new Object[]{productSKU, employeeId}, Long.class);
        } catch (Exception er) {
            return null;
        }
    }

    public List<Brand> fetchBrandsForDistributor(long employeeId) {
        return template.query(DistributorDBSql.DISTRIBUTOR_GET_BRAND_SQL, new Object[]{employeeId}, new BrandMapper());
    }

    public List<Area> saveAreaForDistributor(long employeeId, String areaName) {
        int rowcount = template.update(DistributorDBSql.DISTRIBUTOR_CREATE_AREA_SQL, new Object[]{areaName, employeeId, employeeId, employeeId, areaName});
        if (rowcount != 1) return null;
        return fetchAreasForDistributor(employeeId);
    }

    public int updateAbcsDistributor(int index, String name, long employeeId, long id) {
        Object[] obj = new Object[]{name, employeeId, id};


        return template.update(DistributorDBSql.ABCS_SQL[index], obj);

    }

    public List<Area> fetchAreasForEmployee(long employeeId) {
        return template.query(DBSql.FETCH_AREAS_SQL, new Object[]{employeeId}, new AreaMapper());
    }

    public CompanySetting fetchCompanySetting(long employeeId) {
        CompanySetting setting = null;
        try {
            return template.queryForObject(DistributorDBSql.FETCH_DISTRIBUTOR_SQL, new Object[]{employeeId}, new CompanySettingMapper());
        } catch (Exception er) {
            return setting;
        }

    }


    public int saveSettings(long distributorId, float tax, String companyname, String address, String city, String state, String postal, String country, String phonenumber, String fax, String timezone,String fulfillmenttype,float minimumDeliveryAmount,long employeeId) throws ParseException {

        Object[] args = new Object[]{distributorId, tax, companyname, address, city, state, postal, country, phonenumber, fax, timezone,fulfillmenttype,minimumDeliveryAmount, employeeId};
        int rowCount = template.update(DistributorDBSql.CREATE_SETTING_SQL, args);
        return rowCount;
    }

    public int updateSettings(long distributorId, float tax, String companyname, String address, String city, String state, String postal, String country, String phonenumber, String fax, String timezone,String fulfillmentType, float minimumDeliveryAmount, long employeeId) throws ParseException {
        Object[] args = new Object[]{tax, companyname, address, city, state, postal, country, phonenumber, fax, timezone,fulfillmentType,minimumDeliveryAmount,employeeId, distributorId};
        int rowCount = template.update(DistributorDBSql.ADMIN_UPDATE_SETTINGS, args);
        String addr[] = address.split(" ");
        template.update(DistributorDBSql.ADMIN_UPDATE_DISTRIBUTOR_SETTINGS, new Object[]{companyname,fulfillmentType,minimumDeliveryAmount,employeeId, distributorId});
        //template.update(DistributorDBSql.ADMIN_UPDATE_EMPLOYEE_SETTINGS, new Object[]{city, state, postal, country, phonenumber, employeeId});
        return rowCount;
    }

    public List<Area> fetchAreasForDistributor(long employeeId) {
        return template.query(DistributorDBSql.DISTRIBUTOR_GET_AREA_SQL, new Object[]{employeeId}, new AreaMapper());
    }

    public List<Category> saveCategoryForDistributor(long employeeId, String categoryName) {
        int rowcount = template.update(DistributorDBSql.DISTRIBUTOR_CREATE_CATEGORY_SQL, new Object[]{categoryName, employeeId, employeeId, employeeId, categoryName});
        if (rowcount != 1) return null;
        return fetchCategoriesForDistributor(employeeId);
    }

    public List<Category> fetchCategoriesForDistributor(long employeeId) {
        return template.query(DistributorDBSql.DISTRIBUTOR_GET_CATEGORY_SQL, new Object[]{employeeId}, new CategoryMapper());
    }

    public List<SubCategory> saveSubCategoryForDistributor(long employeeId, String subCategoryName, long categoryId) {
        int rowcount = template.update(DistributorDBSql.DISTRIBUTOR_CREATE_SUBCATEGORY_SQL, new Object[]{subCategoryName, employeeId, employeeId, employeeId, subCategoryName, categoryId});
        if (rowcount != 1) return null;
        return fetchSubCategoriesForDistributor(employeeId);
    }

    public List<SubCategory> fetchSubCategoriesForDistributor(long employeeId) {
        return template.query(DistributorDBSql.DISTRIBUTOR_GET_SUBCATEGORY_SQL, new Object[]{employeeId}, new SubCategoryMapper());
    }


    public List<Product> saveProductForDistributor(long employeeId, long productId, String productName, String productDesc, String productSku, float unitPrice, String priceType, long categoryId, long subCategoryId, long brandId, boolean active, String salesTaxRate, String otherTaxRate, String retailPrice, String unitofMeasure) {
        //(product_name , product_desc, product_sku, distributor_id ,  manufacturer_id ,  unitprice, price_type , prod_catg_id , prod_sub_catg_id , product_brand_id , active,  user_id , updt_dt_tm , ip_address)
        int rowcount = 0;
        rowcount = template.update(DistributorDBSql.DISTRIBUTOR_CREATE_PRODUCT_SQL, new Object[]{productName, productDesc, productSku, employeeId, employeeId, unitPrice, priceType, categoryId, subCategoryId, brandId, active, employeeId, salesTaxRate, otherTaxRate, retailPrice, unitofMeasure});
        if (rowcount != 1) return null;
        return fetchProductsForDistributor(employeeId);
    }

    public List<Product> updateProductForDistributor(long employeeId, long productId, String productName, String productDesc, String productSku, float unitPrice, String priceType, long categoryId, long subCategoryId, long brandId, boolean active, String salesTaxRate, String otherTaxRate, String retailPrice, String unitofMeasure) {
        //(product_name , product_desc, product_sku, distributor_id ,  manufacturer_id ,  unitprice, price_type , prod_catg_id , prod_sub_catg_id , product_brand_id , active,  user_id , updt_dt_tm , ip_address)
        int rowcount = template.update(DistributorDBSql.DISTRIBUTOR_UPDATE_PRODUCT_SQL, new Object[]{productName, productDesc, productSku, unitPrice, priceType, categoryId, subCategoryId, brandId, active, salesTaxRate, otherTaxRate, retailPrice, unitofMeasure, employeeId, productId});
        if (rowcount != 1) return null;
        return fetchProductsForDistributor(employeeId);
    }

    public List<Product> fetchProductsForDistributor(long employeeId) {
        return template.query(DistributorDBSql.DISTRIBUTOR_GET_PRODUCTS_SQL, new Object[]{employeeId}, new ProductMapper());
    }

    public long getDistributorForEmployee(long employeeId) {

        return template.queryForObject(DistributorDBSql.DISTRIBUTOR_GET_DIST_FROM_EMP_SQL, Long.class, new Object[]{employeeId});
    }

    public List<Employee> saveEmployeeForDistributor(long employeeId,
                                                     String firstName, String lastName, String title, String sex, String dateOfBirth, String salary, String hireDate,
                                                     String terminationDate, String allowance,
                                                     String userRole, String address1, String address2, String address3, String city,
                                                     String state, String countryId, String postalCode,
                                                     String location, String cellPhone, String homePhone, String workPhone, String emailId, String active, String comments,
                                                     String areas, long newEmployeeId, long distributorId) throws ParseException {

        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        if (distributorId < 0) {
            distributorId = template.queryForObject(DistributorDBSql.DISTRIBUTOR_GET_DIST_FROM_EMP_SQL, Long.class, new Object[]{employeeId});
        }
        //Now check Email Address Exists or not.

        try {
            template.queryForObject(DistributorDBSql.DIST_GET_EMP_FROM_EMAIL, Long.class, new Object[]{emailId});
            throw new RuntimeException("Please use a different email address. The email address is already registered with PRODCAST");
        } catch (Exception er) {
            //Ignore the exception. The email address does not exist.
            er.printStackTrace();
        }
        java.sql.Date termDate = null;

        if (terminationDate != null) {
            termDate = new java.sql.Date(df.parse(terminationDate).getTime());
        }
        Object[] obj = new Object[]{distributorId,
                firstName
                , lastName, title, emailId, cellPhone, homePhone, workPhone, salary, new java.sql.Date(df.parse(hireDate).getTime()), termDate,
                allowance, address1, address2, address3, city, state, postalCode, countryId, Boolean.parseBoolean(active)
                , userRole, location, sex, new java.sql.Date(df.parse(dateOfBirth).getTime()), comments, employeeId
        };

        template.update(DistributorDBSql.DISTRIBUTOR_CREATE_EMP_SQL, obj);
        obj = new Object[]{emailId, emailId, userRole, employeeId};
        template.update(DistributorDBSql.DISTRIBUTOR_CREATE_EMP_ACCESS_SQL, obj);
        obj = new Object[]{emailId};
        Long empId = template.queryForObject(DistributorDBSql.GET_EMP_FROM_EMAIL, Long.class, obj);
        StringTokenizer st = new StringTokenizer(areas, ",");
        while (st.hasMoreTokens()) {
            String areaId = st.nextToken();
            obj = new Object[]{empId, distributorId, areaId, employeeId};
            template.update(DistributorDBSql.DISTRIBUTOR_CREATE_AREA_MAPPING, obj);
        }

        return fetchEmployeesForDistributor(employeeId);
    }

    public List<Employee> updateEmployeeForDistributor(long employeeId,
                                                       String firstName, String lastName, String title, String sex, String dateOfBirth, String salary, String hireDate,
                                                       String terminationDate, String allowance,
                                                       String userRole, String address1, String address2, String address3, String city,
                                                       String state, String countryId, String postalCode,
                                                       String location, String cellPhone, String homePhone, String workPhone, String emailId, String active, String comments,
                                                       String areas, long newEmployeeId, long distributorId) throws ParseException {

        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        long empId = 0;

        //Now check Email Address Exists or not.

        try {
            empId = template.queryForObject(DistributorDBSql.DIST_GET_EMP_FROM_EMAIL, Long.class, new Object[]{emailId});
            System.out.println(empId);
            if (empId != newEmployeeId)
                throw new RuntimeException("Please use a different email address. The email address is already registered with PRODCAST");
        } catch (Exception er) {
            //Ignore the exception. The email address does not exist.
            er.printStackTrace();
        }
        java.sql.Date termDate = null;

        if (terminationDate != null) {
            termDate = new java.sql.Date(df.parse(terminationDate).getTime());
        }

        if (!Boolean.parseBoolean(active)) termDate = new java.sql.Date(System.currentTimeMillis());


        int activeChar = Boolean.parseBoolean(active) ? 1 : 0;
        System.out.println("Setting Active Char " + activeChar);
        Object[] objectForEmployeeUpdate = new Object[]{
                firstName, lastName, title, emailId,
                cellPhone, homePhone, workPhone, salary,
                new java.sql.Date(df.parse(hireDate).getTime()), termDate,
                allowance, address1, address2, address3, city, state, postalCode,
                countryId, activeChar, userRole, location, sex, new java.sql.Date(df.parse(dateOfBirth).getTime()),
                comments,newEmployeeId
        };


        template.update(DistributorDBSql.DISTRIBUTOR_UPDATE_EMP_SQL, objectForEmployeeUpdate);
        objectForEmployeeUpdate = new Object[]{emailId, userRole, activeChar, newEmployeeId};
        template.update(DistributorDBSql.DISTRIBUTOR_UPDATE_EMP_ACCESS_SQL, objectForEmployeeUpdate);


        objectForEmployeeUpdate = new Object[]{areas, employeeId, newEmployeeId};
        template.update(DistributorDBSql.DISTRIBUTOR_UPDATE_AREA_MAPPING, objectForEmployeeUpdate);


        return fetchEmployeesForDistributor(employeeId);
    }


    public List<Distributor> getDistributors() {
        return template.query(DistributorDBSql.ADMIN_ALL_DISTRIBUTORS, (Object[]) null, new DistributorMapper());
    }

    public Distributor getDistributor(long distributorId) {
        Object[] params = {distributorId};
        return template.queryForObject(DistributorDBSql.FETCH_DISTRIBUTOR, params, new DistributorMapper());
    }

    public Employee getEmployee(long employeeId) {
        Object[] params = {employeeId};
        return template.queryForObject(DistributorDBSql.DISTRIBUTOR_GET_EMPLOYEE_SQL, params, new EmployeeMapper());
    }

    public List<Distributor> saveDistributor(long employeeId,
                                             String companyName,
                                             String type,
                                             String firstName,
                                             String lastName,
                                             String title,
                                             String emailAddress,
                                             String cellPhone,
                                             String homePhone,
                                             String workPhone,
                                             String address1,
                                             String address2,
                                             String address3,
                                             String city,
                                             String state,
                                             String postalCode,
                                             String country,
                                             String timezone,
                                             String gender,
                                             boolean manufacturer,
                                             String active, String comments,
                                             String openToPublic,String fulfillmentType,float minimumDeliveryAmount) throws ParseException {


        long distributorId = -1;
     /*   try {
            distributorId = template.queryForObject(DistributorDBSql.ADMIN_GET_DIST_FROM_EMAIL, Long.class, new Object[]{emailAddress});
            System.out.println("distributorrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"+distributorId);
        } catch (Exception er) {
            er.printStackTrace();
            er.printStackTrace();
        }
        if (distributorId >= 0) {

            throw new RuntimeException("Please use a different email address. The email address that you used already exists in PRODCAST");
        }
        long empId = -1;
        try {
            empId = template.queryForObject(DistributorDBSql.DIST_GET_EMP_FROM_EMAIL, Long.class, new Object[]{emailAddress});
            throw new RuntimeException("Please use a different email address. The email address that you used already exists in PRODCAST");
        } catch (Exception er) {
            er.printStackTrace();
        }*/
        Object[] args = new Object[]{companyName, type, firstName, lastName, title,
                emailAddress, cellPhone, workPhone, homePhone, address1,
                address2, address3, city, state, postalCode, country, timezone,
                Boolean.parseBoolean(active), gender, manufacturer, comments, employeeId, Boolean.parseBoolean(openToPublic),fulfillmentType,minimumDeliveryAmount};


        int rowCount = template.update(DistributorDBSql.ADMIN_SAVE_DISTRIBUTOR, args);
        distributorId = template.queryForObject(DistributorDBSql.ADMIN_GET_DIST_FROM_EMAIL, Long.class, new Object[]{emailAddress});
        saveEmployeeForDistributor(employeeId, firstName, lastName, title, gender, "01/01/1901", "0", "01/01/1901", null, "0", "D", address1,
                address2, address3, city, state, country, postalCode, "", cellPhone, homePhone, workPhone, emailAddress, active, "", "0", 0, distributorId);
        saveSettings(distributorId, 0f, companyName, address1 + " " + address2 + " " + address3, city, state, postalCode, country, workPhone, "0", timezone,fulfillmentType,minimumDeliveryAmount,employeeId);
        if (rowCount == 0) return null;
        return getDistributors();
    }


    public List<Distributor> updateDistributor(String companyName, String type, String firstName, String lastName,
                                               String title, String emailAddress, String cellPhone, String homePhone, String workPhone, String address1,
                                               String address2, String address3,
                                               String city, String state, String postalCode, String country, String timezone, String active, String gender, boolean manufacturer,
                                               String comments, long employeeId, long newDistributorId, String openToPublic,String fulfillmentType,float minimumDeliveryAmount) throws ParseException {
       long distributorId = 0;
        //Now check Email Address Exists or not.
      /*  try {
            System.out.println("1.Distributor ID = " + distributorId + "New One " + newDistributorId);
            distributorId = template.queryForObject(DistributorDBSql.ADMIN_GET_DIST_FROM_EMAIL, Long.class, new Object[]{emailAddress});

            System.out.println("2.Distributor ID = " + distributorId + "New One " + newDistributorId);
        } catch (Exception er) {
            er.printStackTrace();
        }

        if (distributorId != newDistributorId)
            throw new RuntimeException("Please use a different email address. The email address is already registered with PRODCAST");


        long empId = -1;
        try {
            empId = template.queryForObject(DistributorDBSql.DIST_GET_EMP_FROM_EMAIL, Long.class, new Object[]{emailAddress});
            throw new RuntimeException("Please use a different email address. The email address that you used already exists in PRODCAST");
        } catch (Exception er) {
            er.printStackTrace();
        }*/
                   System.out.println("3.Distributor ID = " + distributorId + "New One " + newDistributorId);
        Object[] args = new Object[]{companyName, type, firstName, lastName, title,
                emailAddress, cellPhone, workPhone, homePhone, address1,
                address2, address3, city, state, postalCode, country, timezone,
                Boolean.parseBoolean(active), gender, manufacturer, comments, employeeId, Boolean.parseBoolean(openToPublic),fulfillmentType,minimumDeliveryAmount, newDistributorId};
        long newEmployeeId=getEmployeeIdForDistributor(newDistributorId);
        int rowCount = template.update(DistributorDBSql.ADMIN_UPDATE_DISTRIBUTOR, args);

        updateEmployeeForDistributor(employeeId, firstName, lastName, title, gender,"01/01/1901", "0", "01/01/1901", null, "0", "D", address1,
                address2, address3, city, state, country, postalCode, "", cellPhone, homePhone, workPhone, emailAddress, active, "", "0", newEmployeeId, newDistributorId);
        if (rowCount == 0)
            return null;

       rowCount = updateSettings(newDistributorId, 0f, companyName, address1 + " " + address2 + " " + address3, city, state, postalCode, country, workPhone, "0", timezone,fulfillmentType, minimumDeliveryAmount,employeeId);
       if (rowCount == 0) {
            rowCount = saveSettings(newDistributorId, 0f, companyName, address1 + " " + address2 + " " + address3, city, state, postalCode, country, workPhone, "0", timezone,fulfillmentType, minimumDeliveryAmount, employeeId);
        }
        if (rowCount == 0) {
            return null;
        }
        return getDistributors();
    }


    public int saveExpense(int refNo, String expenseDate, String account, int categoryId, String desc1, String desc2, double amount, String payMode, String userId) throws ParseException {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        if (expenseDate == null) {
            expenseDate = df.format(new java.util.Date());
        }
        Employee employee = getEmployee(Long.parseLong(userId));
        Date date = TimeZoneConvertor.getCurrentDateForTimeZone(employee.getTimezone());
        //new java.sql.Date( df.parse( expenseDate).getTime())
        Object[] args = new Object[]{refNo, date, account, categoryId, desc1, desc2, amount, payMode, userId};
        int rowCount = template.update(DistributorDBSql.CREATE_EXPENSE_SQL, args);
        return rowCount;
    }

    public int updateExpense(int refNo, long expenseId, String account, int categoryId, String desc1, String desc2, double amount, String payMode, String userId, String expenseDate) throws ParseException {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        if (expenseDate == null) {
            expenseDate = df.format(new java.util.Date());
        }

        Object[] args = new Object[]{account, categoryId, desc1, desc2, amount, payMode, userId, new java.sql.Date(df.parse(expenseDate).getTime()), expenseId};
        int rowCount = template.update(DistributorDBSql.UPDATE_EXPENSE_SQL, args);
        return rowCount;
    }

    public Object fetchReportForDistributors(String reportSql, String reportMapper, Date customStartDate, Date customEndDate, long employeeId, String condition,String groupedBy, long selectedEmployee) throws Exception {


        Object[] args = new Object[]{employeeId, customStartDate, customEndDate};
        if (selectedEmployee > 0) {
            if(condition!=null) {


                args = new Object[]{employeeId, customStartDate, customEndDate, (selectedEmployee + "")};
                reportSql = reportSql + " " + condition;

            }

        }
        if(groupedBy!=null) {
            reportSql = reportSql + " "+groupedBy;
        }


             return template.query(reportSql, args, (RowMapper) Class.forName(reportMapper).newInstance());

    }


    public int saveExpenseCategory(String categoryDesc, String userId) {
        Object[] args = new Object[]{categoryDesc, userId, Long.parseLong(userId)};
        int rowCount = template.update(DistributorDBSql.CREATE_EXPENSE_CATEGORY, args);
        return rowCount;
    }

    public int updateExpenseCategory(long categoryId, String categoryDesc, String userId) {
        Object[] args = new Object[]{categoryDesc, userId, categoryId};

        int rowCount = template.update(DistributorDBSql.UPDATE_EXPENSE_CATEGORY, args);
        return rowCount;
    }

    public List<ExpenseCategory> getExpenseCategories(long employeeId) {
        Object[] args = new Object[]{employeeId};
        return template.query(DistributorDBSql.DISTRIBUTOR_GET_EXPENSE_CATEGORY, args, new ExpenseCategoryMapper());
    }

    public List<Country> fetchCountries() {

        return template.query(DBSql.COUNTRY_SQL, (Object[]) null, new CountryMapper());

    }

    public long getConfirmationCode(long accessId) {
        Object[] args = new Object[]{accessId};
        return template.queryForObject(DBSql.CUSTOMERS_GET_CONFIRMATIONCODE_SQL, args, Long.class);

    }

    public List<Confirmation> getConfirmationDetails() {

        return template.query(DBSql.CUSTOMERS_DETAILS_GET_SQL, (Object[]) null, new ConfirmationMapper());

    }

    public int setConfirmationStatus(long accessId) {

        return template.update(DBSql.CUSTOMER_SET_STATUS_SQL, new Object[]{accessId});

    }

    public Confirmation loginCustomer(String id, String password, String country) {
        return template.queryForObject(DBSql.GET_STATUS_FOR_CUSTOMER, new Object[]{id, password, country}, new ConfirmationMapper());
    }

    public boolean isCustomerRegisteredForPublicOrders(long accessId) {
        return template.queryForObject(DBSql.IS_CUSTOMER_REGISTERED_FOR_PUBLIC, Boolean.class, new Object[]{accessId});
    }

    public EmployeeDetails getEmployeeDetailsForOrder(long accessId, long distributorId) {
        EmployeeDetails emp;

        Boolean openToPublic=template.queryForObject(DBSql.GET_IS_OPEN_TO_PUBLIC_DISTRIBUTOR, new Object[]{distributorId}, Boolean.class);
        if(openToPublic) {
            emp = template.queryForObject(DBSql.GET_EMPLOYEE_FOR_CUSTOMER_ORDER_OPEN_PUBLIC, new Object[]{accessId, distributorId}, new EmployeeDetailsMapper());

        }
        else{
            try {

                emp = template.queryForObject(DBSql.GET_EMPLOYEE_FOR_CUSTOMER_ORDER, new Object[]{accessId, distributorId}, new EmployeeDetailsMapper());


            } catch (Exception e) {
                System.out.println(e);
                //emp=null;
                emp=template.queryForObject(DBSql.GET_DISTRIBUTOR_FOR_CUSTOMER_ORDER, new Object[]{accessId, distributorId}, new EmployeeDetailsMapper());

            }

        }

        emp.setDistributor(template.queryForObject(DBSql.GET_DISTRIBUTOR, new Object[]{distributorId}, new DistributorMapper()));
        emp.setCustomer(getCustomer(""+emp.getCustomerId()));
        return emp;
    }





    public Object fetchReportForCustomers(String reportSql,
                                          String reportMapper,
                                          Date customStartDate,
                                          Date customEndDate,
                                          long accessId,
                                          String condition,
                                          long selectedDistributor) throws Exception {
        Object[] args = new Object[]{accessId, customStartDate, customEndDate};
        if (selectedDistributor > 0) {
            args = new Object[]{accessId, customStartDate, customEndDate, (selectedDistributor + "")};
            reportSql = reportSql + " " + condition;

        }
        return template.query(reportSql, args, (RowMapper) Class.forName(reportMapper).newInstance());

    }


    public List<Order> fetchBillDetailsForCustomer(java.sql.Date startDate, java.sql.Date endDate, long accessId,long selectedDistributor) {


        Distributor distributor = getDistributor(selectedDistributor);


        Date startDateOf = TimeZoneConvertor.convertDateForTimeZone(startDate,distributor.getTimezone());
        Date endDateOf = TimeZoneConvertor.convertDateForTimeZone(endDate,distributor.getTimezone());




            return template.query(DistributorDBSql.FETCH_BILLDETAILS_FOR_CUSTOMERS, new Object[]{accessId, startDateOf, endDateOf, selectedDistributor}, new ReportOrderMapper());

    }


    public String getCustomerType(long customerId) {

        return template.queryForObject(DBSql.GET_CUSTOMER_TYPE, new Object[]{customerId}, String.class);
    }


    public List<StoreType> createStoreType(String storeTypeName)

    {
        Object[] obj = new Object[]{storeTypeName};
        template.update(DBSql.CREATE_STORE_TYPE_SQL, obj);
        return fetchStoreType();
    }


    public List<StoreType> fetchStoreType() {
        return template.query(DBSql.STORETYPE_SQL, (Object[]) null, new StoreTypeMapper());
    }

    public int updateStoreType(String storeTypeName, long storeTypeId)

    {
        Object[] obj = new Object[]{storeTypeName, storeTypeId};

        return template.update(DBSql.UPDATE_STORE_TYPE_SQL, obj);

    }

    public List<StoreType> fetchStoreTypeId(long storeTypeId) {
        Object[] obj = new Object[]{storeTypeId};
        return template.query(DBSql.STORE_TYPE_ID_SQL, obj, new StoreTypeMapper());
    }


    public int deleteStoreType(long storeTypeId) {
        return template.update(DBSql.DELETE_STORE_TYPE_SQL, new Object[]{storeTypeId});

    }

    public String getPinFromMobile(String mobilePhone) {
        try {
            return template.queryForObject(DBSql.GET_CUSTOMER_PHONENUMBER_FROM_PHONENUMBER, new Object[]{mobilePhone}, String.class);
        } catch (Exception er) {
            System.out.println(er.toString());
            return null;
        }
    }


    public List<StoreType> saveStoreType(String storeTypeName) {
        int rowcount = template.update(DBSql.CREATE_STORE_TYPE, new Object[]{storeTypeName});
        if (rowcount != 1) return null;
        return fetchStoreType();
    }

    public int updateStore(String storeTypeName, long storeTypeId) {
        Object[] obj = new Object[]{storeTypeName, storeTypeId};


        return template.update(DBSql.UPDATE_STORE, obj);

    }


    public List<Customer> fetchMobileNumberOfCustomers(String country, String cellPhoneNumber) {
        Object[] args = new Object[]{country, cellPhoneNumber};
        return template.query(DBSql.FETCH_MOBILE_NUMBER_OF_CUSTOMERS, args, new CustomerMapper());


    }

    public int createNewCustomer(String firstName, String lastName, String emailAddress, String cellPhoneNumber,
                                 String homePhoneNumber, String billingAddress1,
                                 String billingAddress2, String billingAddress3, String city, String state, String country,
                                 String postalCode, String smsAllowed)

    {

       Date date=null;
        Object[] obj = new Object[]{firstName, lastName,
                emailAddress, cellPhoneNumber, homePhoneNumber,
                billingAddress1, billingAddress2,
                billingAddress3, city, state,
                country, postalCode, Boolean.parseBoolean(smsAllowed),date,};

        return template.update(DBSql.CREATE_NEW_CUSTOMER_SQL, obj);

    }
    public int updateNewCustomerRegistrationDetails(long customerId,String firstName, String lastName, String emailAddress, String cellPhoneNumber,
                                     String homePhoneNumber, String billingAddress1,
                                     String billingAddress2, String billingAddress3, String city, String state, String country,
                                     String postalCode, String smsAllowed)
    {
        Object[] obj=new Object[]{firstName, lastName,
                emailAddress, homePhoneNumber,
                billingAddress1, billingAddress2,
                billingAddress3, city, state,
                country, postalCode, Boolean.parseBoolean(smsAllowed),cellPhoneNumber,customerId};

        return template.update(DBSql.UPDATE_NEW_CUSTOMER_REG_DETAILS_SQL, obj);
    }

    public NewCustomerRegistrationDetails fetchUpdateSaveNewcustomer(String cellPhoneNumber) {
        return template.queryForObject(DBSql.FETCH__NEW_CUSTOMER_REG_DETAILS, new Object[]{cellPhoneNumber}, new NewCustomerRegistrationDetailsMapper());


    }
    public NewCustomerRegistrationDetails fetchUpdatedSaveNewcustomer(String cellPhoneNumber,String customerId) {
        return template.queryForObject(DBSql.FETCH__UPDATED_NEWCUSTOMER_REG_DETAILS, new Object[]{cellPhoneNumber,customerId}, new NewCustomerRegistrationDetailsMapper());


    }
    public NewCustomerRegistrationDetails fetchSaveNewcustomer(long accessId){
        return template.queryForObject(DBSql.FETCH_NEW_CUSTOMEREG_DETAILS, new Object[]{accessId,},new NewCustomerRegistrationDetailsMapper());
    }


    public List<Distributor> getAllDistributorsIfOpenToPublic(long accessId) {

        return template.query(DBSql.GET_DISTRIBUTORS_IF_OPEN_TO_PUBLIC, new Object[]{accessId}, new DistributorMapper());
    }

    public List<Customer> fetchAllCustomers() {

        return template.query(DBSql.FETCH_ALL_CUSTOMER_SQL, (Object[]) null, new CustomerMapper(false));
    }


    public List<Distributor> fetchDistributors() {

        return template.query(DBSql.FETCH_DISTRIBUTORS_LIST_SQL, (Object[]) null, new DistributorMapper());

    }

    public String fetchCustomersMobileNumber(long customerId) {
        return template.queryForObject(DBSql.FETCH_CUSTOMER_MOBILE_SQL, new Object[]{customerId}, String.class);


    }

    public String fetchDistributorName(long customerId,long billNo)
    {
        return template.queryForObject(DBSql.FETCH_DISTRIBUTOR_NAME_SQL, new Object[]{customerId,billNo}, String.class);


    }
    public String fetchCustomerEmailId(long customerId)
    {
        return template.queryForObject(DBSql.FETCH_CUSTOMER_EMAIL_ID_SQL, new Object[]{customerId}, String.class);


    }

    public long fetchEmployeeId(long billNo ) {

        Object[] args = new Object[]{billNo};
        return template.queryForObject(DBSql.FETCH_EMPLOYEE_ID_SQL, args,Long.class);

    }

    public String getMailIdForDistributor(long distributorId){
        return template.queryForObject(DBSql.FETCH_DISTRIBUTOR_EMAIL_ID_SQL, new Object[]{distributorId}, String.class);
    }

    public String getPasswordForDistributor(String emailAddress){
        return template.queryForObject(DBSql.FETCH_DISTRIBUTOR_PASSWORD, new Object[]{emailAddress}, String.class);
    }

    public long getEmployeeIdForDistributor(long newDistributorId) {
        if(newDistributorId==0)
            return 0;
        else
            return template.queryForObject(DistributorDBSql.ADMIN_EMPLOYEE_ID, new Object[]{newDistributorId}, Long.class);
    }

    public String fetchDistributorCustMobileNumber(String cellPhoneNumber,String employeeId){
        try {

            return template.queryForObject(DBSql.CHECK_CUSTOMER_CELLPHONE_UNIQUE, new Object[]{ cellPhoneNumber,employeeId}, String.class);
        }
        catch(Exception e){
            return null;
        }
    }


    public Boolean isOpenToPublic(long employeeId){
         return template.queryForObject(DBSql.GET_EMP_DIST_PUBLIC, Boolean.class, new Object[]{employeeId});
    }
    public String fetchCustomerDistId(String cellPhoneNumber) {
        return template.queryForObject(DBSql.FETCH_CUSTOMER_DISTID, new Object[]{cellPhoneNumber}, String.class);
    }
    public String fetchDistributorCustCellNumber(String cellPhoneNumber,String distributorId){
        try {

            return template.queryForObject(DBSql.CHECK_CUSTOMER_CELLPHONE, new Object[]{ cellPhoneNumber,distributorId}, String.class);
        }
        catch(Exception e){
            return null;
        }
    }



}
