package com.primeforce.prodcast.dao;

import com.primeforce.prodcast.businessobjects.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by sarathan732 on 4/22/2016.
 */
@Component ("DatabaseManager")
public class DatabaseManager {


    private final JdbcTemplate template;

    @Autowired
    public DatabaseManager(JdbcTemplate template){
        this.template = template;
    }

    public Employee login(String userId, String password){
        //return (Employee)template.queryForObject(DBSql.LOGIN_SQL, new Object[]{userId, password }, new EmployeeMapper());
        return (Employee) template.queryForObject( DBSql.LOGIN_SQL_NEW , new Object[]{userId, password } , new EmployeeMapper() );
    }

    public List<Customer> fetchCustomers(long employeeId){

        String userRole = template.queryForObject( DBSql.GET_EMP_ROLE, String.class , new Object[]{employeeId});
        if( userRole.equals("S")) {
            return template.query(DBSql.CUSTOMER_SEARCH_SQL, new Object[]{employeeId}, new CustomerMapper(false));
        }
        else{
            return template.query(DBSql.CUSTOMER_SEARCH_SQL_D, new Object[]{employeeId}, new CustomerMapper(false));
        }

    }

    public Customer getCustomer(String customerID){
        return template.queryForObject( DBSql.CUSTOMER_SQL , new Object[]{Long.parseLong( customerID) } , new CustomerMapper(false)  );
    }

    public List<Product> fetchProducts(String startsWith){
        return template.query( DBSql.PRODUCT_SEARCH_SQL +startsWith+"%'" , (Object[])null, new ProductMapper());
    }

    public List<Bill> fetchOutstandingBills(String customerID ){
        return template.query( DBSql.OUTSTANDING_BILL_SEARCH , new Object[]{Long.parseLong(customerID)} , new BillMapper() );
    }

    public int updateCollectionPayment(long dealerId, long billId, float amount ){
        return template.update( DBSql.COLLECTION_UPDATE_SQL , new Object[]{dealerId, billId , amount });
    }

    public boolean saveOrder(Order order, float paymentAmount){

        SimpleJdbcCall call = new  SimpleJdbcCall ( template.getDataSource() ).withProcedureName("sp_create_order");
        SqlParameterSource in = new MapSqlParameterSource().addValue("in_emp_id" , order.getEmployeeId() ).addValue("in_customer_id" , order.getCustomerId() );
        Map<String, Object> out = call.execute(in);

        long orderDetailId = (Long)out.get("out_orderdtl_id");
        long billNumber = (Long)out.get("out_bill_no");
        order.setOrderId( orderDetailId );
        order.setBillNumber( billNumber );
        for (OrderEntry entry:order.getOrderEntries()) {
            template.update( DBSql.ORDER_DETAILS_SQL , new Object[]{orderDetailId , entry.getProductId(), entry.getQuantity() , entry.getProductId() });
        }

        template.update(DBSql.ORDER_TOTAL_SQL , new Object[]{orderDetailId, orderDetailId});

        if( paymentAmount > 0 ){
            updateCollectionPayment( order.getEmployeeId() , billNumber, paymentAmount );
        }
        return true;
    }

    public int createCustomer(long  employeeId, String customerName, String customerType , long areaId , String weekDay,  String firstName ,
                              String billingAddress1,String lastName , String emailAddress , String cellPhoneNumber,
                              String phoneNumber, String unitNumber,
    String billingAddress2, String billingAddress3 , String city , String state, String countryId , String postalCode , String notes ){
        Object[] obj =  new Object[]{  employeeId, customerName, customerType , areaId , weekDay, firstName , lastName,
                emailAddress , cellPhoneNumber , phoneNumber, unitNumber,
                billingAddress1,billingAddress2, billingAddress3 , city , state, countryId , postalCode ,notes,employeeId };
        for(int i=0;i<obj.length;i++) System.out.println(" "+(i+1)+" =>"+obj[i]);
       return template.update( DBSql.CREATE_CUSTOMER_SQL , obj);
    }
    public Order fetchOrder(long billId ){
        Order order = template.queryForObject( DBSql.FETCH_ORDER_SQL  , new Object[]{ billId } , new OrderMapper());

        order.setOrderEntries(template.query( DBSql.FETCH_ORDER_DTL_SQL , new Object[]{order.getOrderId()} , new OrderDetailMapper() ) );

        order.setCollectionEntries( template.query( DBSql.FETCH_ORDER_COLLECTION_SQL , new Object[]{billId} , new CollectionMapper()) );
;
        return order;
    }


    public List<Order> fetchSalesReport(java.sql.Date startDate, java.sql.Date endDate , long employeeId ){
        return template.query( DBSql.REPORT_SALES_SQL , new Object[]{ startDate, endDate , employeeId } , new ReportOrderMapper());
    }

    public List<Collection> fetchCollectionReport(java.sql.Date startDate, java.sql.Date endDate , long employeeId ){
        return template.query( DBSql.REPORT_COLLECTION_SQL, new Object[]{ startDate, endDate , employeeId } , new CollectionMapper());

    }

    public List<Employee> fetchEmployeesForDistributor(long employeeId ){
        return template.query( DistributorDBSql.DISTRIBUTOR_GET_EMP_SQL, new Object[]{employeeId }, new EmployeeMapper() );
    }

    public List<Brand> saveBrandForDistributor(long employeeId , String brandName  ){
        int rowcount = template.update(DistributorDBSql.DISTRIBUTOR_CREATE_BRAND_SQL, new Object[]{brandName , employeeId , employeeId , employeeId });
        if( rowcount != 1 ) return null;
        return fetchBrandsForDistributor( employeeId );
    }

    public List<Brand> fetchBrandsForDistributor( long employeeId ){
        return template.query( DistributorDBSql.DISTRIBUTOR_GET_BRAND_SQL, new Object[]{employeeId }, new BrandMapper() );
    }
    public List<Area> saveAreaForDistributor(long employeeId , String areaName ){
        int rowcount = template.update(DistributorDBSql.DISTRIBUTOR_CREATE_AREA_SQL, new Object[]{areaName, employeeId , employeeId , employeeId , areaName});
        if( rowcount != 1 ) return null;
        return fetchAreasForDistributor( employeeId );
    }

    public List<Area> fetchAreasForEmployee(long employeeId  ){
        return template.query( DBSql.FETCH_AREAS_SQL , new Object[]{employeeId} , new AreaMapper() );
    }

    public CompanySetting fetchCompanySetting(long employeeId ){

        return template.queryForObject( DistributorDBSql.FETCH_DISTRIBUTOR_SQL , new Object[]{employeeId}, new CompanySettingMapper() );

    }
    public List<Area> fetchAreasForDistributor( long employeeId ){
        return template.query( DistributorDBSql.DISTRIBUTOR_GET_AREA_SQL, new Object[]{employeeId }, new AreaMapper() );
    }
    public List<Category> saveCategoryForDistributor(long employeeId , String categoryName ){
        int rowcount = template.update(DistributorDBSql.DISTRIBUTOR_CREATE_CATEGORY_SQL, new Object[]{categoryName , employeeId , employeeId , employeeId , categoryName });
        if( rowcount != 1 ) return null;
        return fetchCategoriesForDistributor( employeeId );
    }

    public List<Category> fetchCategoriesForDistributor( long employeeId ){
        return template.query( DistributorDBSql.DISTRIBUTOR_GET_CATEGORY_SQL, new Object[]{employeeId }, new CategoryMapper() );
    }

    public List<SubCategory> saveSubCategoryForDistributor(long employeeId , String subCategoryName , long categoryId ){
        int rowcount = template.update(DistributorDBSql.DISTRIBUTOR_CREATE_SUBCATEGORY_SQL, new Object[]{subCategoryName , employeeId , employeeId , employeeId , subCategoryName , categoryId });
        if( rowcount != 1 ) return null;
        return fetchSubCategoriesForDistributor( employeeId );
    }

    public List<SubCategory> fetchSubCategoriesForDistributor( long employeeId ){
        return template.query( DistributorDBSql.DISTRIBUTOR_GET_SUBCATEGORY_SQL, new Object[]{employeeId }, new SubCategoryMapper() );
    }


    public List<Product> saveProductForDistributor(long employeeId , long productId, String productName, String productDesc, String productSku, float unitPrice, String priceType,  long categoryId , long subCategoryId , long brandId, boolean active ){
        //(product_name , product_desc, product_sku, distributor_id ,  manufacturer_id ,  unitprice, price_type , prod_catg_id , prod_sub_catg_id , product_brand_id , active,  user_id , updt_dt_tm , ip_address)
        int rowcount = 0;
        if( productId == 0 ) {
            rowcount = template.update(DistributorDBSql.DISTRIBUTOR_CREATE_PRODUCT_SQL, new Object[]{productName, productDesc, productSku, employeeId, employeeId, unitPrice, priceType, categoryId, subCategoryId, brandId, active, employeeId});
        }
        else{
            rowcount = template.update(DistributorDBSql.DISTRIBUTOR_CREATE_PRODUCT_SQL, new Object[]{productName, productDesc, productSku, employeeId, employeeId, unitPrice, priceType, categoryId, subCategoryId, brandId, active, employeeId});
        }
        if( rowcount != 1 ) return null;
        return fetchProductsForDistributor( employeeId );
    }

    public List<Product> fetchProductsForDistributor( long employeeId ){
        return template.query( DistributorDBSql.DISTRIBUTOR_GET_PRODUCTS_SQL, new Object[]{employeeId }, new ProductMapper() );
    }

    public List<Employee> saveEmployeeForDistributor( long employeeId,
    String firstName,String lastName,String title, String sex,String dateOfBirth,String salary,String  hireDate,
                                                      String  terminationDate, String  allowance,
                                                      String userRole, String address1 , String address2, String address3 , String city,
                                                      String state, String countryId ,String postalCode,
    String location,String cellPhone , String homePhone, String workPhone , String emailId , String active, String comments,
                                                      String areas , long newEmployeeId) throws ParseException{
        long distributorId = 0;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        distributorId = template.queryForObject( DistributorDBSql.DISTRIBUTOR_GET_DIST_FROM_EMP_SQL,  Long.class , new Object[]{employeeId} );
        java.sql.Date termDate = null;

        if( terminationDate != null ) {
            termDate = new java.sql.Date( df.parse( terminationDate).getTime());
        }
        Object[] obj = new Object[]{distributorId,
                firstName
                 , lastName , title , emailId , cellPhone, homePhone, workPhone , salary,new java.sql.Date( df.parse( hireDate).getTime()), termDate,
                allowance,address1,address2,address3,city, state, postalCode,countryId, Boolean.parseBoolean(active)
                 , "", location,sex, new java.sql.Date( df.parse( dateOfBirth).getTime()), comments, employeeId
        };

        template.update( DistributorDBSql.DISTRIBUTOR_CREATE_EMP_SQL , obj);
        obj = new Object[]{emailId , emailId , userRole, employeeId };
        template.update( DistributorDBSql.DISTRIBUTOR_CREATE_EMP_ACCESS_SQL , obj );
        obj = new Object[]{emailId};
        Long empId = template.queryForObject(DistributorDBSql.GET_EMP_FROM_EMAIL, Long.class , obj );
        StringTokenizer st = new StringTokenizer( areas , "," );
        while( st.hasMoreTokens() ){
            String areaId = st.nextToken();
            obj = new Object[]{empId , areaId , employeeId };
            template.update( DistributorDBSql.DISTRIBUTOR_CREATE_AREA_MAPPING , obj );
        }

        return fetchEmployeesForDistributor( employeeId );
    }


    public List<Distributor> getDistributors(){
        return template.query( DistributorDBSql.ADMIN_ALL_DISTRIBUTORS ,  (Object[])null , new DistributorMapper());
    }
/*
cust_name,customer_type,firstname,lastname,title," +
            "email_id,cellphone,workphone,homephone,address_1,address_2,address_3,city,state,country_id,active_yn,sex,manufacture_yn,comments" +
            ",user_id , ip_address, enter_dt_tm , updt_dt_tm, notify_yn,landmark, manufacture_id , business_id
 */
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
                                             String gender,
                                             boolean manufacturer,
                                             boolean active, String comments){

        Object[] args = new Object[]{companyName,type,firstName,lastName,title,
                emailAddress,cellPhone,workPhone,homePhone,address1,
                address2,address3,city,state,postalCode , country,
                active,gender,manufacturer,comments,employeeId };
        int rowCount = template.update( DistributorDBSql.ADMIN_SAVE_DISTRIBUTOR , args );
        if( rowCount == 0 ) return null;
        return getDistributors();
    }

    //insert into expense_dtl (ref_no, account, catg_id, desc1, desc2, amount, pay_mode, enter_dt_tm, updt_dt_tm ) values (?, ?, ?, ?, ?, ?, ?, NOW(), NOW())
    public int saveExpense(int refNo, String account, int categoryId, String desc1, String desc2, double amount, String payMode, String ipAddress, String userId ){
        Object[] args = new Object[] {refNo, account, categoryId, desc1, desc2, amount, payMode, ipAddress, userId};
        int rowCount = template.update(DBSql.CREATE_EXPENSE_SQL, args);
        return rowCount;
    }

    public int saveExpenseCategory(String categoryDesc, String ipAddress, String userId ){
        Object[] args = new Object[] {categoryDesc, ipAddress, userId};
        int rowCount = template.update(DBSql.CREATE_EXPENSE_CATEGORY, args);
        return rowCount;
    }
}
